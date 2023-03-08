package fr.vyxs.kron.registry;

import fr.vyxs.kron.Kron;
import fr.vyxs.kron.ui.menu.MineralInfusingStationMenu;
import fr.vyxs.kron.ui.menu.VinylTurntableMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KronMenuTypes {

    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Kron.MODID);

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static final RegistryObject<MenuType<MineralInfusingStationMenu>> MINERAL_INFUSING_STATION_MENU =
            registerMenuType("mineral_infusing_station_menu", MineralInfusingStationMenu::new);

    public static final RegistryObject<MenuType<VinylTurntableMenu>> VINYL_TURNTABLE_MENU =
            MENUS.register("vinyl_turntable_menu", () -> new MenuType<>(VinylTurntableMenu::getClientMenu));
}
