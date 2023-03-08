package fr.vyxs.kron.tool;

import fr.vyxs.kron.entity.MineralInfusingStationBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MenuUtil {

    private MenuUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static @NotNull ItemStack quickMoveStack(AbstractContainerMenu menu, @NotNull Player player, int index, @NotNull MoveItemStackTo func) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot sourceSlot = menu.slots.get(index);
        if (sourceSlot.hasItem()) {
            ItemStack copySourceSlot = sourceSlot.getItem();
            itemStack = copySourceSlot.copy();
            if (index < MineralInfusingStationBlockEntity.INVENTORY_SIZE) {
                if (!func.moveItemStackTo(copySourceSlot, MineralInfusingStationBlockEntity.INVENTORY_SIZE, menu.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!func.moveItemStackTo(copySourceSlot, 0, MineralInfusingStationBlockEntity.INVENTORY_SIZE, false)) {
                return ItemStack.EMPTY;
            }
            if (copySourceSlot.isEmpty()) {
                sourceSlot.set(ItemStack.EMPTY);
            } else {
                sourceSlot.setChanged();
            }
        }
        return itemStack;
    }

    @FunctionalInterface
    public interface MoveItemStackTo {
        boolean moveItemStackTo(ItemStack copySourceSlot, int i, int size, boolean b);
    }
}
