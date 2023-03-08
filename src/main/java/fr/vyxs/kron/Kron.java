package fr.vyxs.kron;

import com.mojang.logging.LogUtils;
import fr.vyxs.kron.registry.KronMessages;
import fr.vyxs.kron.registry.*;
import fr.vyxs.kron.item.AdvancedPickaxe;
import fr.vyxs.kron.renderer.VinylTurntableRenderer;
import fr.vyxs.kron.ui.screen.MineralInfusingStationScreen;
import fr.vyxs.kron.ui.screen.VinylTurntableScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Kron.MODID)
public class Kron {

    public static final String MODID = "kron";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Kron() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);


        KronItems.register(bus);
        KronBlocks.register(bus);
        KronBlockEntities.register(bus);
        KronMenuTypes.register(bus);
        KronRecipes.register(bus);
        KronConfiguredFeatures.register(bus);
        KronPlacedFeatures.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(KronMessages::register);
        MinecraftForge.EVENT_BUS.register(AdvancedPickaxe.class);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(KronMenuTypes.MINERAL_INFUSING_STATION_MENU.get(), MineralInfusingStationScreen::new);
            MenuScreens.register(KronMenuTypes.VINYL_TURNTABLE_MENU.get(), VinylTurntableScreen::new);
        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(KronBlockEntities.VINYL_TURNTABLE.get(), VinylTurntableRenderer::new);
        }
    }
}
