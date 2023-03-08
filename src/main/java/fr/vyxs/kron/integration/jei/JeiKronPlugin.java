package fr.vyxs.kron.integration.jei;

import fr.vyxs.kron.Kron;
import fr.vyxs.kron.recipe.MineralInfusingStationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JeiKronPlugin implements IModPlugin {

    public static RecipeType<MineralInfusingStationRecipe> MINERAL_INFUSING_TYPE = new RecipeType<>(
            MineralInfusingStationRecipeCategory.UID, MineralInfusingStationRecipe.class
    );

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(Kron.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MineralInfusingStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<MineralInfusingStationRecipe> recipes = manager.getAllRecipesFor(MineralInfusingStationRecipe.Type.INSTANCE);
        registration.addRecipes(MINERAL_INFUSING_TYPE, recipes);
    }
}
