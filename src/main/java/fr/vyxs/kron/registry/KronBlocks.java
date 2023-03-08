package fr.vyxs.kron.registry;

import fr.vyxs.kron.Kron;
import fr.vyxs.kron.block.MineralInfusingStationBlock;
import fr.vyxs.kron.block.VinylTurntableBlock;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class KronBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Kron.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        registerBlockItem(name, registryObject, tab);
        return registryObject;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        KronItems.registerBlockItem(name, block, tab);
    }

    private static final Supplier<Block> DEFAULT_BLOCK_PROPS = () -> new Block(
            BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5.0F)
                    .requiresCorrectToolForDrops()
    );

    public static final RegistryObject<Block> KATONE_BLOCK = register("katone_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> VARYTE_BLOCK = register("varyte_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> TERNABIR_BLOCK = register("ternabir_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> CHIDRITE_BLOCK = register("chidrite_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> RINIUM_BLOCK = register("rinium_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> BOZARITE_BLOCK = register("bozarite_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> ENFERIUM_BLOCK = register("enferium_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> GENUDIUM_BLOCK = register("genudium_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> JERIKIUM_BLOCK = register("jerikium_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> ENDITE_BLOCK = register("endite_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> DIVINIUM_BLOCK = register("divinium_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> GALAXYTE_BLOCK = register("galaxyte_block", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);

    public static final RegistryObject<Block> KATONE_ORE = register("katone_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> VARYTE_ORE = register("varyte_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> TERNABIR_ORE = register("ternabir_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> CHIDRITE_ORE = register("chidrite_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> RINIUM_ORE = register("rinium_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> BOZARITE_ORE = register("bozarite_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> ENFERIUM_ORE = register("enferium_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> GENUDIUM_ORE = register("genudium_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> JERIKIUM_ORE = register("jerikium_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> ENDITE_ORE = register("endite_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> DIVINIUM_ORE = register("divinium_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> GALAXYTE_ORE = register("galaxyte_ore", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);

    public static final RegistryObject<Block> KATONE_ORE_DEEPSLATE = register("katone_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> VARYTE_ORE_DEEPSLATE = register("varyte_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> TERNABIR_ORE_DEEPSLATE = register("ternabir_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> CHIDRITE_ORE_DEEPSLATE = register("chidrite_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> RINIUM_ORE_DEEPSLATE = register("rinium_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> BOZARITE_ORE_DEEPSLATE = register("bozarite_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> ENFERIUM_ORE_DEEPSLATE = register("enferium_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> GENUDIUM_ORE_DEEPSLATE = register("genudium_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> JERIKIUM_ORE_DEEPSLATE = register("jerikium_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> ENDITE_ORE_DEEPSLATE = register("endite_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> DIVINIUM_ORE_DEEPSLATE = register("divinium_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> GALAXYTE_ORE_DEEPSLATE = register("galaxyte_ore_deepslate", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);

    public static final RegistryObject<Block> ENFERIUM_ORE_NETHER = register("enferium_ore_nether", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> GENUDIUM_ORE_NETHER = register("genudium_ore_nether", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> JERIKIUM_ORE_NETHER = register("jerikium_ore_nether", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);

    public static final RegistryObject<Block> ENDITE_ORE_END = register("endite_ore_end", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> DIVINIUM_ORE_END = register("divinium_ore_end", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);
    public static final RegistryObject<Block> GALAXYTE_ORE_END = register("galaxyte_ore_end", DEFAULT_BLOCK_PROPS, KronCreativeModeTabs.MAIN_TAB);


    public static final RegistryObject<Block> MINERAL_INFUSING_STATION = register("mineral_infusing_station",
            () -> new MineralInfusingStationBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0F)
                    .noOcclusion()
                    .isValidSpawn((state, world, pos, type) -> false)
                    .isRedstoneConductor((state, world, pos) -> false)
                    .isSuffocating((state, world, pos) -> false)
                    .isViewBlocking((state, world, pos) -> false))
            , KronCreativeModeTabs.MAIN_TAB);

    public static final RegistryObject<Block> VINYL_TURNTABLE = register("vinyl_turntable",
            () -> new VinylTurntableBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0F)
                    .noOcclusion()
                    .isValidSpawn((state, world, pos, type) -> false)
                    .isRedstoneConductor((state, world, pos) -> false)
                    .isSuffocating((state, world, pos) -> false)
                    .isViewBlocking((state, world, pos) -> false))
            , KronCreativeModeTabs.MAIN_TAB);
}
