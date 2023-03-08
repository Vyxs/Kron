package fr.vyxs.kron.ui.menu;

import fr.vyxs.kron.core.R;
import fr.vyxs.kron.entity.VinylTurntableBlockEntity;
import fr.vyxs.kron.metric.VinylTurntableMetric;
import fr.vyxs.kron.registry.KronBlocks;
import fr.vyxs.kron.registry.KronMenuTypes;
import fr.vyxs.kron.tool.InventoryUtil;
import fr.vyxs.kron.tool.MenuUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class VinylTurntableMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess usabilityTest;


    public static VinylTurntableMenu getClientMenu(int id, Inventory playerInventory) {
        return new VinylTurntableMenu(id, playerInventory, new ItemStackHandler(VinylTurntableBlockEntity.INVENTORY_SIZE), BlockPos.ZERO);
    }

    public static MenuProvider getServerMenuProvider(VinylTurntableBlockEntity blockEntity, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) -> new VinylTurntableMenu(id, inventory, blockEntity.getItemHandler(), pos), Component.translatable(R.Lang.CONTAINER_VINYL_TURNTABLE));
    }

    public VinylTurntableMenu(int id, Inventory inventory, ItemStackHandler handler, BlockPos pos) {
        super(KronMenuTypes.VINYL_TURNTABLE_MENU.get(), id);
        checkContainerSize(inventory, VinylTurntableBlockEntity.INVENTORY_SIZE);
        this.usabilityTest = ContainerLevelAccess.create(inventory.player.level, pos);

        InventoryUtil.getPlayerInventory(inventory).forEach(this::addSlot);
        InventoryUtil.getPlayerHotbar(inventory).forEach(this::addSlot);

        addSlot(new SlotItemHandler(handler, VinylTurntableBlockEntity.INPUT_SLOT, VinylTurntableMetric.INPUT_SLOT_X, VinylTurntableMetric.INPUT_SLOT_Y));
        addSlot(new SlotItemHandler(handler, VinylTurntableBlockEntity.PLAY_SLOT, VinylTurntableMetric.PLAY_SLOT_X, VinylTurntableMetric.PLAY_SLOT_Y));
        addSlot(new SlotItemHandler(handler, VinylTurntableBlockEntity.OUTPUT_SLOT, VinylTurntableMetric.OUTPUT_SLOT_X, VinylTurntableMetric.OUTPUT_SLOT_Y));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return MenuUtil.quickMoveStack(this, player, index, this::moveItemStackTo);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(usabilityTest, player, KronBlocks.VINYL_TURNTABLE.get());
    }
}
