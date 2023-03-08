package fr.vyxs.kron.ui.menu;

import fr.vyxs.kron.entity.MineralInfusingStationBlockEntity;
import fr.vyxs.kron.metric.MineralInfusingStationMetric;
import fr.vyxs.kron.registry.KronBlocks;
import fr.vyxs.kron.registry.KronMenuTypes;
import fr.vyxs.kron.tool.InventoryUtil;
import fr.vyxs.kron.tool.MenuUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MineralInfusingStationMenu extends AbstractContainerMenu {

    private final MineralInfusingStationBlockEntity blockEntity;
    private final Level level;
    private final ContainerData containerData;
    private FluidStack fluidStack;


    public MineralInfusingStationMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
        this(id, inventory, inventory.player.level.getBlockEntity(buffer.readBlockPos()), new SimpleContainerData(MineralInfusingStationBlockEntity.DATA_COUNT));
    }

    public MineralInfusingStationMenu(int id, Inventory inventory, BlockEntity blockEntity, ContainerData containerData) {
        super(KronMenuTypes.MINERAL_INFUSING_STATION_MENU.get(), id);
        checkContainerSize(inventory, MineralInfusingStationBlockEntity.INVENTORY_SIZE);
        this.blockEntity = (MineralInfusingStationBlockEntity) blockEntity;
        level = inventory.player.level;
        this.containerData = containerData;
        this.fluidStack = this.blockEntity.getFluidStack();

        InventoryUtil.getPlayerInventory(inventory).forEach(this::addSlot);
        InventoryUtil.getPlayerHotbar(inventory).forEach(this::addSlot);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            addSlot(new SlotItemHandler(handler, MineralInfusingStationBlockEntity.INFUSION_SLOT, MineralInfusingStationMetric.INFUSION_SLOT_X, MineralInfusingStationMetric.INFUSION_SLOT_Y));
            addSlot(new SlotItemHandler(handler, MineralInfusingStationBlockEntity.INPUT_SLOT, MineralInfusingStationMetric.INPUT_SLOT_X, MineralInfusingStationMetric.INPUT_SLOT_Y));
            addSlot(new SlotItemHandler(handler, MineralInfusingStationBlockEntity.OUTPUT_SLOT, MineralInfusingStationMetric.OUTPUT_SLOT_X, MineralInfusingStationMetric.OUTPUT_SLOT_Y));
        });
        this.addDataSlots(containerData);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return MenuUtil.quickMoveStack(this, player, index, this::moveItemStackTo);
    }

    public boolean isCrafting() {
        return containerData.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = containerData.get(0);
        int maxProgress = containerData.get(1);
        final int progressArrowWidth = 26;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowWidth / maxProgress : 0;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, KronBlocks.MINERAL_INFUSING_STATION.get());
    }

    public MineralInfusingStationBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public void setFluidStack(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }
}
