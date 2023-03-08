package fr.vyxs.kron.registry;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import fr.vyxs.kron.Kron;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class KronConfiguredFeatures {

    private static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Kron.MODID);

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_KATONE_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.KATONE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.KATONE_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> KATONE_ORE = CONFIGURED_FEATURES.register("katone_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_KATONE_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_VARYTE_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.VARYTE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.VARYTE_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> VARYTE_ORE = CONFIGURED_FEATURES.register("varyte_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_VARYTE_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_TERNABIR_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.TERNABIR_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.TERNABIR_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> TERNABIR_ORE = CONFIGURED_FEATURES.register("ternabir_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_TERNABIR_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_CHIDRITE_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.CHIDRITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.CHIDRITE_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> CHIDRITE_ORE = CONFIGURED_FEATURES.register("chidrite_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_CHIDRITE_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_RINIUM_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.RINIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.RINIUM_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> RINIUM_ORE = CONFIGURED_FEATURES.register("rinium_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_RINIUM_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_BOZARITE_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.BOZARITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.BOZARITE_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> BOZARITE_ORE = CONFIGURED_FEATURES.register("bozarite_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_BOZARITE_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_ENFERIUM_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.ENFERIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.ENFERIUM_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> ENFERIUM_ORE = CONFIGURED_FEATURES.register("enferium_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_ENFERIUM_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_GENUDIUM_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.GENUDIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.GENUDIUM_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> GENUDIUM_ORE = CONFIGURED_FEATURES.register("genudium_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_GENUDIUM_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_JERIKIUM_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.JERIKIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.JERIKIUM_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> JERIKIUM_ORE = CONFIGURED_FEATURES.register("jerikium_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_JERIKIUM_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_ENDITE_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.ENDITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.ENDITE_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> ENDITE_ORE = CONFIGURED_FEATURES.register("endite_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_ENDITE_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_DIVINIUM_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.DIVINIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.DIVINIUM_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> DIVINIUM_ORE = CONFIGURED_FEATURES.register("divinium_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_DIVINIUM_ORES.get(), 9))
    );

    private static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_GALAXYTE_ORES = Suppliers.memoize(() ->
        List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KronBlocks.GALAXYTE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KronBlocks.GALAXYTE_ORE_DEEPSLATE.get().defaultBlockState())
        )
    );
    public static final RegistryObject<ConfiguredFeature<?, ?>> GALAXYTE_ORE = CONFIGURED_FEATURES.register("galaxyte_ore", () ->
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_GALAXYTE_ORES.get(), 9))
    );


}
