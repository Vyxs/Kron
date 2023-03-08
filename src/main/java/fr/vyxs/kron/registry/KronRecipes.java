package fr.vyxs.kron.registry;

import fr.vyxs.kron.Kron;
import fr.vyxs.kron.recipe.MineralInfusingStationRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KronRecipes {

    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Kron.MODID);

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }

    public static final RegistryObject<RecipeSerializer<MineralInfusingStationRecipe>> MINERAL_INFUSING_STATION =
            SERIALIZERS.register("mineral_infusing", () -> MineralInfusingStationRecipe.Serializer.INSTANCE);
}
