package fr.vyxs.kron.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KronCreativeModeTabs {

    public static final CreativeModeTab MAIN_TAB = new CreativeModeTab("kron.main_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(KronItems.KATONE.get());
        }
    };

}
