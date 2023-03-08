package fr.vyxs.kron.integration.jei;

import fr.vyxs.kron.Kron;
import fr.vyxs.kron.metric.MineralInfusingStationMetric;
import fr.vyxs.kron.recipe.MineralInfusingStationRecipe;
import fr.vyxs.kron.registry.KronBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static fr.vyxs.kron.entity.MineralInfusingStationBlockEntity.FLUID_CAPACITY;

public class MineralInfusingStationRecipeCategory implements IRecipeCategory<MineralInfusingStationRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Kron.MODID, "mineral_infusing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Kron.MODID, "textures/gui/mineral_infusing_station.png");
    private final IDrawable background;
    private final IDrawable icon;

    public MineralInfusingStationRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(TEXTURE, 0, 0, MineralInfusingStationMetric.BACKGROUND_WIDTH, MineralInfusingStationMetric.BACKGROUND_HEIGHT_RECIPE);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(KronBlocks.MINERAL_INFUSING_STATION.get()));
    }

    @Override
    public @NotNull RecipeType<MineralInfusingStationRecipe> getRecipeType() {
        return JeiKronPlugin.MINERAL_INFUSING_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Mineral Infusing Station");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull MineralInfusingStationRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, MineralInfusingStationMetric.INPUT_SLOT_X, MineralInfusingStationMetric.INFUSION_SLOT_Y).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, MineralInfusingStationMetric.FLUID_TANK_X, MineralInfusingStationMetric.FLUID_TANK_Y)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getFluidStack()))
                .setFluidRenderer(FLUID_CAPACITY, false, MineralInfusingStationMetric.FLUID_TANK_WIDTH, MineralInfusingStationMetric.FLUID_TANK_HEIGHT);
        builder.addSlot(RecipeIngredientRole.OUTPUT, MineralInfusingStationMetric.OUTPUT_SLOT_X, MineralInfusingStationMetric.OUTPUT_SLOT_Y).addItemStack(recipe.getResultItem());
    }
}
