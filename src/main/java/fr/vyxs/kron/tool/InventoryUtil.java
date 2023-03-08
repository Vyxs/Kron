package fr.vyxs.kron.tool;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {

    public static final int HOTBAR_SLOT_COUNT = 9;
    public static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    public static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    public static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    public static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    public static final int VANILLA_FIRST_SLOT_INDEX = 0;
    public static final int FIRST_PLAYER_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private InventoryUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Slot> getPlayerInventory(Inventory inventory) {
        List<Slot> slots = new ArrayList<>();
        for (int row = 0; row < PLAYER_INVENTORY_ROW_COUNT; row++) {
            for (int column = 0; column < PLAYER_INVENTORY_COLUMN_COUNT; column++) {
                slots.add(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 86 + row * 18));
            }
        }
        return slots;
    }

    public static List<Slot> getPlayerHotbar(Inventory inventory) {
        List<Slot> slots = new ArrayList<>();
        for (int column = 0; column < HOTBAR_SLOT_COUNT; column++) {
            slots.add(new Slot(inventory, column, 8 + column * 18, 144));
        }
        return slots;
    }
}
