package fr.vyxs.kron.registry;

import fr.vyxs.kron.Kron;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class KronPlacedFeatures {

    private static final int DEFAULT_VEIN_PER_CHUNK = 8;

    private static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Kron.MODID);

    public static List<PlacementModifier> orePlacement(PlacementModifier placementModifier1, PlacementModifier placementModifier) {
        return List.of(placementModifier1, InSquarePlacement.spread(), placementModifier, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int vein, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(vein), placementModifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int vein, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(vein), placementModifier);
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }

    public static final RegistryObject<PlacedFeature> OVERWORLD_KATONE_ORE = PLACED_FEATURES.register("overworld_katone_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.KATONE_ORE.getHolder().get(), commonOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_VARYTE_ORE = PLACED_FEATURES.register("overworld_varyte_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.VARYTE_ORE.getHolder().get(), commonOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_TERNABIR_ORE = PLACED_FEATURES.register("overworld_ternabir_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.TERNABIR_ORE.getHolder().get(), commonOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_CHIDRITE_ORE = PLACED_FEATURES.register("overworld_chidrite_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.CHIDRITE_ORE.getHolder().get(), commonOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_RINIUM_ORE = PLACED_FEATURES.register("overworld_rinium_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.RINIUM_ORE.getHolder().get(), commonOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_BOZARITE_ORE = PLACED_FEATURES.register("overworld_bozarite_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.BOZARITE_ORE.getHolder().get(), commonOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_ENFERIUM_ORE = PLACED_FEATURES.register("overworld_enferium_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.ENFERIUM_ORE.getHolder().get(), rareOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_GENUDIUM_ORE = PLACED_FEATURES.register("overworld_genudium_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.GENUDIUM_ORE.getHolder().get(), rareOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_JERIKIUM_ORE = PLACED_FEATURES.register("overworld_jerikium_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.JERIKIUM_ORE.getHolder().get(), rareOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_ENDITE_ORE = PLACED_FEATURES.register("overworld_endite_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.ENDITE_ORE.getHolder().get(), rareOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_DIVINIUM_ORE = PLACED_FEATURES.register("overworld_divinium_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.DIVINIUM_ORE.getHolder().get(), rareOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
    public static final RegistryObject<PlacedFeature> OVERWORLD_GALAXYTE_ORE = PLACED_FEATURES.register("overworld_galaxyte_ore", () ->
            new PlacedFeature(KronConfiguredFeatures.GALAXYTE_ORE.getHolder().get(), rareOrePlacement(DEFAULT_VEIN_PER_CHUNK, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(320))))
    );
}
