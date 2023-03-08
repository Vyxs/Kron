package fr.vyxs.kron.entity;

import fr.vyxs.kron.block.MineralInfusingStationBlock;
import fr.vyxs.kron.core.R;
import fr.vyxs.kron.entity.container.MineralInfusingStationContainerData;
import fr.vyxs.kron.registry.KronMessages;
import fr.vyxs.kron.network.packet.EnergySyncPacket;
import fr.vyxs.kron.network.packet.FluidSyncPacket;
import fr.vyxs.kron.recipe.MineralInfusingStationRecipe;
import fr.vyxs.kron.registry.KronBlockEntities;
import fr.vyxs.kron.tool.blockentity.BlockEntityUtil;
import fr.vyxs.kron.tool.blockentity.SideHandler;
import fr.vyxs.kron.tool.energy.ModEnergyStorage;
import fr.vyxs.kron.tool.type.SharedInt;
import fr.vyxs.kron.ui.menu.MineralInfusingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * TODO: 12/02/2023 To refactor like vinyl turntable
 **/
public class MineralInfusingStationBlockEntity extends BlockEntity implements MenuProvider, EnergyFluidInterface {

    public static final int DATA_COUNT = 2;
    public static final int INVENTORY_SIZE = 3;
    public static final int INFUSION_SLOT = 0;
    public static final int INPUT_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    public static final int FLUID_CAPACITY = 32000;
    private static final int ENERGY_PER_TICK = 64;
    private static final int FLUID_TRANSFER = 1000;
    private static final String INVENTORY_TAG = "inventory";
    private static final String PROGRESS_TAG = "progress";
    private static final String MAX_PROGRESS_TAG = "max_progress";
    private static final String ENERGY_TAG = "energy";
    private MineralInfusingStationRecipe recipe;
    private final int energyCapacity = 32000;
    private final SharedInt progress = SharedInt.of(0);
    private final SharedInt maxProgress = SharedInt.of(78);
    private final ContainerData containerData;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case INPUT_SLOT -> isIngredientPartOfRecipe(slot, stack);
                case INFUSION_SLOT -> isValidFuel(stack);
                case OUTPUT_SLOT -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final ModEnergyStorage energyStorage = new ModEnergyStorage(energyCapacity, 3600) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            KronMessages.sendToAllPlayers(new EnergySyncPacket(getBlockPos(), energy));
        }
    };

    private final FluidTank fluidTank = new FluidTank(FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide) {
                KronMessages.sendToAllPlayers(new FluidSyncPacket(worldPosition, fluid));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.LAVA;
        }
    };

    private final Map<Direction, LazyOptional<SideHandler>> sideHandlers = Map.of(
            Direction.UP, LazyOptional.of(SideHandler.ofInsertSupplier(itemStackHandler, INPUT_SLOT)),
            Direction.SOUTH, LazyOptional.of(SideHandler.ofInsertSupplier(itemStackHandler, INFUSION_SLOT)),
            Direction.DOWN, LazyOptional.of(SideHandler.ofExtractSupplier(itemStackHandler, OUTPUT_SLOT)),
            Direction.WEST, LazyOptional.of(SideHandler.ofInsertSupplier(itemStackHandler, INPUT_SLOT)),
            Direction.NORTH, LazyOptional.of(SideHandler.ofInsertSupplier(itemStackHandler, INFUSION_SLOT)),
            Direction.EAST, LazyOptional.of(SideHandler.ofExtractSupplier(itemStackHandler, OUTPUT_SLOT))
    );

    public MineralInfusingStationBlockEntity(BlockPos pos, BlockState state) {
        super(KronBlockEntities.MINERAL_INFUSING_STATION.get(), pos, state);
        containerData = new MineralInfusingStationContainerData(progress, maxProgress);
    }

    @Override
    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public void setEnergyLevel(int energy) {
        energyStorage.setEnergy(energy);
    }

    @Override
    public IFluidTank getFluidTank() {
        return fluidTank;
    }

    @Override
    public void setFluidStack(FluidStack fluid) {
        fluidTank.setFluid(fluid);
    }

    @Override
    public FluidStack getFluidStack() {
        return fluidTank.getFluid();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(R.Lang.CONTAINER_MINERAL_INFUSING_STATION);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        KronMessages.sendToAllPlayers(new EnergySyncPacket(getBlockPos(), energyStorage.getEnergyStored()));
        KronMessages.sendToAllPlayers(new FluidSyncPacket(worldPosition, getFluidStack()));
        return new MineralInfusingStationMenu(containerId, playerInventory, this, containerData);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(INVENTORY_TAG, itemStackHandler.serializeNBT());
        tag.putInt(PROGRESS_TAG, progress.get());
        tag.putInt(MAX_PROGRESS_TAG, maxProgress.get());
        tag.putInt(ENERGY_TAG, energyStorage.getEnergyStored());
        tag = fluidTank.writeToNBT(tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        itemStackHandler.deserializeNBT(tag.getCompound(INVENTORY_TAG));
        progress.set(tag.getInt(PROGRESS_TAG));
        maxProgress.set(tag.getInt(MAX_PROGRESS_TAG));
        energyStorage.setEnergy(tag.getInt(ENERGY_TAG));
        fluidTank.readFromNBT(tag);
        hasRecipe().ifPresent(recipe -> this.recipe = recipe);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        } else if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return BlockEntityUtil.getCapability(this, MineralInfusingStationBlock.FACING, lazyItemHandler, sideHandlers, side);
        } else if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MineralInfusingStationBlockEntity entity) {
        if (level.isClientSide) return;

        if (entity.hasSolidFuelToConsume()) {
            entity.consumeSolidFuel();
            setChanged(level, blockPos, blockState);
        } else if (entity.hasFluidFuelToConsume()) {
            entity.consumeFluidFuel();
            setChanged(level, blockPos, blockState);
        }

        if (entity.isCrafting()) {
            entity.progressCrafting();
            setChanged(level, blockPos, blockState);
        } else if (entity.hasFinishedCrafting()) {
            entity.finishCrafting();
            setChanged(level, blockPos, blockState);
        } else if (entity.hasNoRecipe()) {
            Optional<MineralInfusingStationRecipe> recipe = entity.hasRecipe();
            if (recipe.isPresent() && entity.canDoRecipe(recipe.get())) {
                entity.setRecipe(recipe.get());
                entity.startCrafting();
                setChanged(level, blockPos, blockState);
            }
        }
    }

    private boolean hasNoRecipe() {
        return recipe == null;
    }

    private boolean canDoRecipe(MineralInfusingStationRecipe recipe) {
        return canInsertItemIntoOutputSlot(recipe.getResultItem())
                && canInsertAmountIntoOutputSlot(recipe.getResultItem().getCount())
                && hasEnoughEnergy()
                && hasEnoughFluid(recipe.getFluidStack().getAmount());
    }

    private boolean hasEnoughFluid(int amount) {
        return fluidTank.getFluidAmount() >= amount;
    }

    private boolean hasEnoughEnergy() {
        return energyStorage.getEnergyStored() >= ENERGY_PER_TICK * maxProgress.get();
    }

    private boolean canInsertAmountIntoOutputSlot(int amount) {
        ItemStack itemStack = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
        return itemStack.getCount() + amount <= itemStack.getMaxStackSize();
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack itemStack) {
        ItemStack outputStack = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
        return outputStack.getItem() == itemStack.getItem() || outputStack.isEmpty();
    }
    private void setRecipe(MineralInfusingStationRecipe recipe) {
        this.recipe = recipe;
    }

    private boolean isIngredientPartOfRecipe(int slot, ItemStack stack) {
        if (level == null) return false;
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        inventory.setItem(slot, stack);
        return level.getRecipeManager().getRecipeFor(MineralInfusingStationRecipe.Type.INSTANCE, inventory, level).isPresent();
    }

    private boolean hasFluidFuelToConsume() {
        AtomicBoolean hasFluidFuel = new AtomicBoolean(false);
        itemStackHandler.getStackInSlot(INFUSION_SLOT).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(fluidHandler -> {
            if (fluidTank.isFluidValid(fluidHandler.getFluidInTank(0))) {
                hasFluidFuel.set(true);
            }
        });
        return hasFluidFuel.get();
    }

    private boolean hasSolidFuelToConsume() {
        Item fuel = itemStackHandler.getStackInSlot(INFUSION_SLOT).getItem();
        return fuel == Items.BLAZE_POWDER
                || fuel == Items.BLAZE_ROD
                || fuel == Items.COAL
                || fuel == Items.CHARCOAL
                || fuel == Items.COAL_BLOCK;
    }

    private boolean isValidFuel(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()
                || stack.getItem() == Items.BLAZE_POWDER
                || stack.getItem() == Items.BLAZE_ROD
                || stack.getItem() == Items.COAL
                || stack.getItem() == Items.CHARCOAL
                || stack.getItem() == Items.COAL_BLOCK;
    }

    private void consumeFluidFuel() {
        ItemStack possibleLavaBucket = itemStackHandler.getStackInSlot(INFUSION_SLOT);
        itemStackHandler.getStackInSlot(INFUSION_SLOT).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(fluidHandler -> {
            int amount = Math.min(fluidTank.getSpace(), FLUID_TRANSFER);
            FluidStack fluidStack = fluidHandler.drain(amount, IFluidHandler.FluidAction.EXECUTE);
            fluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
            if (possibleLavaBucket.getItem().equals(Items.LAVA_BUCKET)) {
                itemStackHandler.setStackInSlot(INFUSION_SLOT, new ItemStack(Items.BUCKET));
            }
        });
    }

    private void consumeSolidFuel() {
        if (energyStorage.isFull()) return;

        Item fuel = itemStackHandler.getStackInSlot(INFUSION_SLOT).getItem();
        if (Items.BLAZE_POWDER.equals(fuel)) {
            energyStorage.receiveEnergy(1000, false);
        } else if (Items.BLAZE_ROD.equals(fuel)) {
            energyStorage.receiveEnergy(2000, false);
        } else if (Items.COAL.equals(fuel) || Items.CHARCOAL.equals(fuel)) {
            energyStorage.receiveEnergy(400, false);
        } else if (Items.COAL_BLOCK.equals(fuel)) {
            energyStorage.receiveEnergy(3600, false);
        }
        itemStackHandler.extractItem(INFUSION_SLOT, 1, false);
    }

    private void consumeEnergy() {
        energyStorage.extractEnergy(ENERGY_PER_TICK, false);
    }

    private Optional<MineralInfusingStationRecipe> hasRecipe() {
        if (level == null) return Optional.empty();
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());

        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(MineralInfusingStationRecipe.Type.INSTANCE, inventory, level);
    }

    private void startCrafting() {
        progress.set(1);
        consumeEnergy();
    }

    private void progressCrafting() {
        if (itemStackHandler.getStackInSlot(INPUT_SLOT).isEmpty()) {
            cancelCrafting();
        } else {
            progress.add(1);
            consumeEnergy();
        }
    }

    private void cancelCrafting() {
        progress.set(0);
        recipe = null;
    }

    private boolean isCrafting() {
        return progress.get() > 0 && progress.get() < maxProgress.get();
    }

    private void finishCrafting() {
        if (recipe == null) {
            recipe = hasRecipe().orElse(null);
            if (recipe == null) {
                cancelCrafting();
                return;
            }
        }
        progress.set(0);

        itemStackHandler.extractItem(INPUT_SLOT, 1, false);
        fluidTank.drain(recipe.getFluidStack().getAmount(), IFluidHandler.FluidAction.EXECUTE);

        ItemStack output = recipe.getResultItem();
        ItemStack outputSlot = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
        if (outputSlot.isEmpty()) {
            itemStackHandler.setStackInSlot(OUTPUT_SLOT, output.copy());
        } else {
            outputSlot.grow(output.getCount());
        }
        recipe = null;
    }

    private boolean hasFinishedCrafting() {
        return progress.get() == maxProgress.get();
    }

    public void drops() {
        if (level == null) return;
        SimpleContainer simpleContainer = new SimpleContainer(INVENTORY_SIZE);

        IntStream.rangeClosed(0, itemStackHandler.getSlots() - 1)
                .mapToObj(itemStackHandler::getStackInSlot)
                .forEach(simpleContainer::addItem);

        Containers.dropContents(level, worldPosition, simpleContainer);
    }
}
