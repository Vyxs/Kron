package fr.vyxs.kron.registry;

import fr.vyxs.kron.item.AdvancedPickaxe;
import fr.vyxs.kron.tool.ModTier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static fr.vyxs.kron.Kron.MODID;

/**
 * Katone
 * Varyte
 * Ternabir
 *
 * Chidrite
 * Rinium
 * Bozarite
 *
 * Enferium
 * Genudium
 * Jerikium
 *
 * Endite
 * Divinium
 * Galaxyte
 */
public class KronItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static final RegistryObject<Item> KATONE = ITEMS.register("katone", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> VARYTE = ITEMS.register("varyte", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> TERNABIR = ITEMS.register("ternabir", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> CHIDRITE = ITEMS.register("chidrite", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> RINIUM = ITEMS.register("rinium", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> BOZARITE = ITEMS.register("bozarite", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> ENFERIUM = ITEMS.register("enferium", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> GENUDIUM = ITEMS.register("genudium", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> JERIKIUM = ITEMS.register("jerikium", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> ENDITE = ITEMS.register("endite", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> DIVINIUM = ITEMS.register("divinium", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> GALAXYTE = ITEMS.register("galaxyte", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));

    public static final RegistryObject<Item> KATONE_NUGGET = ITEMS.register("katone_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> VARYTE_NUGGET = ITEMS.register("varyte_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> TERNABIR_NUGGET = ITEMS.register("ternabir_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> CHIDRITE_NUGGET = ITEMS.register("chidrite_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> RINIUM_NUGGET = ITEMS.register("rinium_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> BOZARITE_NUGGET = ITEMS.register("bozarite_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> ENFERIUM_NUGGET = ITEMS.register("enferium_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> GENUDIUM_NUGGET = ITEMS.register("genudium_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> JERIKIUM_NUGGET = ITEMS.register("jerikium_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> ENDITE_NUGGET = ITEMS.register("endite_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> DIVINIUM_NUGGET = ITEMS.register("divinium_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> GALAXYTE_NUGGET = ITEMS.register("galaxyte_nugget", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));

    public static final RegistryObject<Item> KATONE_INGOT = ITEMS.register("katone_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> VARYTE_INGOT = ITEMS.register("varyte_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> TERNABIR_INGOT = ITEMS.register("ternabir_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> CHIDRITE_INGOT = ITEMS.register("chidrite_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> RINIUM_INGOT = ITEMS.register("rinium_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> BOZARITE_INGOT = ITEMS.register("bozarite_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> ENFERIUM_INGOT = ITEMS.register("enferium_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> GENUDIUM_INGOT = ITEMS.register("genudium_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> JERIKIUM_INGOT = ITEMS.register("jerikium_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> ENDITE_INGOT = ITEMS.register("endite_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> DIVINIUM_INGOT = ITEMS.register("divinium_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));
    public static final RegistryObject<Item> GALAXYTE_INGOT = ITEMS.register("galaxyte_ingot", () -> new Item(new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)));

    public static final RegistryObject<Item> LARGE_IRON_PICKAXE = ITEMS.register("large_iron_pickaxe", () -> new AdvancedPickaxe(
            new ModTier(10, 2f, 5f, 1, 5),
            5,
            2f,
            new Item.Properties().tab(KronCreativeModeTabs.MAIN_TAB)
    ));
}
