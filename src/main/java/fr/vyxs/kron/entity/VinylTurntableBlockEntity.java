package fr.vyxs.kron.entity;

import fr.vyxs.kron.block.VinylTurntableBlock;
import fr.vyxs.kron.core.R;
import fr.vyxs.kron.network.packet.ItemStackSyncPacket;
import fr.vyxs.kron.registry.KronBlockEntities;
import fr.vyxs.kron.registry.KronMessages;
import fr.vyxs.kron.tool.blockentity.BlockEntityUtil;
import fr.vyxs.kron.tool.blockentity.SideHandler;
import fr.vyxs.kron.ui.menu.VinylTurntableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.IntStream;

public class VinylTurntableBlockEntity extends BlockEntity implements MenuProvider {

    public static final int INVENTORY_SIZE = 3;
    public static final int INPUT_SLOT = 0;
    public static final int PLAY_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    private ItemStack record = ItemStack.EMPTY;

    private static final String RECORD_ITEM_TAG = "RecordItem";
    private static final String INVENTORY_TAG = "inventory";
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
            if (level != null && !level.isClientSide) {
                KronMessages.sendToAllPlayers(new ItemStackSyncPacket(worldPosition, getStackInSlot(PLAY_SLOT)));
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case INPUT_SLOT, PLAY_SLOT, OUTPUT_SLOT -> stack.getItem() instanceof RecordItem;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final Map<Direction, LazyOptional<SideHandler>> sideHandlers = Map.of(
            Direction.UP, LazyOptional.of(SideHandler.ofInsertSupplier(itemStackHandler, INPUT_SLOT)),
            Direction.SOUTH, LazyOptional.of(SideHandler.ofInsertSupplier(itemStackHandler, PLAY_SLOT)),
            Direction.DOWN, LazyOptional.of(SideHandler.ofExtractSupplier(itemStackHandler, OUTPUT_SLOT)),
            Direction.WEST, LazyOptional.of(SideHandler.ofInsertSupplier(itemStackHandler, INPUT_SLOT)),
            Direction.NORTH, LazyOptional.of(SideHandler.ofInsertSupplier(itemStackHandler, PLAY_SLOT)),
            Direction.EAST, LazyOptional.of(SideHandler.ofExtractSupplier(itemStackHandler, OUTPUT_SLOT))
    );

    public VinylTurntableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(KronBlockEntities.VINYL_TURNTABLE.get(), pPos, pBlockState);
    }

    public ItemStackHandler getItemHandler() {
        return itemStackHandler;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(R.Lang.CONTAINER_VINYL_TURNTABLE);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new VinylTurntableMenu(containerId, playerInventory, itemStackHandler, getBlockPos());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains(RECORD_ITEM_TAG, 10)) {
            this.setRecord(ItemStack.of(tag.getCompound(RECORD_ITEM_TAG)));
        }
        itemStackHandler.deserializeNBT(tag.getCompound(INVENTORY_TAG));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.getRecord().isEmpty()) {
            tag.put(RECORD_ITEM_TAG, this.getRecord().save(new CompoundTag()));
        }
        tag.put(INVENTORY_TAG, itemStackHandler.serializeNBT());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return BlockEntityUtil.getCapability(this, VinylTurntableBlock.FACING, lazyItemHandler, sideHandlers, side);
        }
        return super.getCapability(cap, side);
    }

    public ItemStack getRecord() {
        return this.record;
    }

    public void setRecord(ItemStack pRecord) {
        this.record = pRecord;
        this.setChanged();
    }

    private int tickCount = 0;
    private int soundDuration = 1;
    private boolean isPlaying = false;

    public static void tick(Level level, BlockPos pos, BlockState state, VinylTurntableBlockEntity entity) {
        if (level.isClientSide) return; // should not happen, maybe i need to remove this
        entity.tickCount++;

        if (entity.isSoundFinish()) {
            entity.stopSound();
        }
        if (entity.isPlaying) return;
        if (entity.hasRecordInPlaySlot()) {
            entity.playSound();
        } else if (entity.hasRecordInInputSlot()) {
            entity.moveRecordFromInputToPlaySlot();
            entity.playSound();
        }


    }

    private void stopSound() {
        tickCount = 0;
        if (hasRecordInPlaySlot()) {
            moveRecordFromPlayToOutputSlot();
        }
    }

    private boolean isSoundFinish() {
        return tickCount >= soundDuration;
    }

    private boolean hasRecordInInputSlot() {
        return itemStackHandler.getStackInSlot(INPUT_SLOT).getItem() instanceof RecordItem;
    }

    private boolean hasRecordInPlaySlot() {
        return itemStackHandler.getStackInSlot(PLAY_SLOT).getItem() instanceof RecordItem;
    }

    private void playSound() {
        if (level == null) return;
        RecordItem recordItem = (RecordItem) itemStackHandler.getStackInSlot(PLAY_SLOT).getItem();
        SoundEvent soundEvent = recordItem.getSound();
        soundDuration = recordItem.getLengthInTicks();
        tickCount = 0;

        level.playSound(null, worldPosition, soundEvent, SoundSource.RECORDS, 1.0F, 1.0F);
    }

    private void moveRecordFromInputToPlaySlot() {
        if (itemStackHandler.getStackInSlot(INPUT_SLOT).getItem() instanceof RecordItem && itemStackHandler.getStackInSlot(PLAY_SLOT).isEmpty())
            itemStackHandler.insertItem(PLAY_SLOT, itemStackHandler.extractItem(INPUT_SLOT, 1, false), false);
    }

    private void moveRecordFromPlayToOutputSlot() {
        if (itemStackHandler.getStackInSlot(PLAY_SLOT).getItem() instanceof RecordItem && itemStackHandler.getStackInSlot(OUTPUT_SLOT).isEmpty())
            itemStackHandler.insertItem(OUTPUT_SLOT, itemStackHandler.extractItem(PLAY_SLOT, 1, false), false);
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
