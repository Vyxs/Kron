package fr.vyxs.kron.tool.blockentity;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class SideHandler implements IItemHandlerModifiable {

    public static final BiPredicate<Integer, ItemStack> NO_INSERTION = (slot, stack) -> false;
    public static final Predicate<Integer> NO_EXTRACTION = slot -> false;
    private final IItemHandlerModifiable handler;
    private final Predicate<Integer> extract;
    private final BiPredicate<Integer, ItemStack> insert;

    public SideHandler(IItemHandlerModifiable handler, Predicate<Integer> extract,
                       BiPredicate<Integer, ItemStack> insert) {
        this.handler = handler;
        this.extract = extract;
        this.insert = insert;
    }

    /**
     * Creates a new handler that only allows extraction from the given slot
     *
     * @param handler The handler to wrap
     * @param slot The slot to allow extraction
     * @return A new handler that only allows extraction from the given slot
     */
    public static SideHandler ofExtract(IItemHandlerModifiable handler, int slot) {
        return new SideHandler(handler, (index) -> index == slot, SideHandler.NO_INSERTION);
    }

    /**
     * Creates a new handler that only allows insertion in the given slot. It will check if the item is valid for the
     * slot using {@link IItemHandlerModifiable#isItemValid(int, ItemStack)}
     *
     * @param handler The handler to wrap
     * @param slot The slot to allow insertion
     * @return A new handler that only allows insertion in the given slot
     */
    public static SideHandler ofInsert(IItemHandlerModifiable handler, int slot) {
        return new SideHandler(handler, SideHandler.NO_EXTRACTION, (index, stack) -> index == slot && handler.isItemValid(index, stack));
    }

    /**
     * Creates a new handler that only allows insertion in the given slot. It will check if the item is valid for the
     * slot using {@link IItemHandlerModifiable#isItemValid(int, ItemStack)}
     *
     * @param handler The handler to wrap
     * @param slot The slot to allow insertion
     * @return A new handler wrapped in a supplier that only allows insertion in the given slot
     */
    public static NonNullSupplier<SideHandler> ofInsertSupplier(IItemHandlerModifiable handler, int slot) {
        return () -> ofInsert(handler, slot);
    }

    /**
     * Creates a new handler that only allows extraction from the given slot
     *
     * @param handler The handler to wrap
     * @param slot The slot to allow extraction
     * @return A new handler wrapped in a supplier that only allows extraction from the given slot
     */
    public static NonNullSupplier<SideHandler> ofExtractSupplier(IItemHandlerModifiable handler, int slot) {
        return () -> ofExtract(handler, slot);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        this.handler.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return this.handler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.handler.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return this.insert.test(slot, stack) ? this.handler.insertItem(slot, stack, simulate) : stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.extract.test(slot) ? this.handler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.handler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return this.insert.test(slot, stack) && this.handler.isItemValid(slot, stack);
    }
}
