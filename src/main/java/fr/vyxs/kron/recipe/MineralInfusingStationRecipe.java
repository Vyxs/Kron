package fr.vyxs.kron.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.vyxs.kron.Kron;
import fr.vyxs.kron.entity.MineralInfusingStationBlockEntity;
import fr.vyxs.kron.tool.FluidJsonUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MineralInfusingStationRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeIngredients;
    private final FluidStack fluidStack;

    public MineralInfusingStationRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeIngredients, FluidStack fluidStack) {
        this.id = id;
        this.output = output;
        this.recipeIngredients = recipeIngredients;
        this.fluidStack = fluidStack;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, @NotNull Level level) {
        if (level.isClientSide) return false;
        return recipeIngredients.get(0).test(container.getItem(MineralInfusingStationBlockEntity.INPUT_SLOT));
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return recipeIngredients;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer container) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MineralInfusingStationRecipe> {

        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "mineral_infusing";
    }

    public static class Serializer implements RecipeSerializer<MineralInfusingStationRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Kron.MODID, Type.ID);

        @Override
        public @NotNull MineralInfusingStationRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject serializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients");
            NonNullList<Ingredient> recipeIngredients = NonNullList.withSize(1, Ingredient.EMPTY);
            FluidStack fluid = FluidJsonUtil.fromJson(serializedRecipe.get("fluid").getAsJsonObject());

            for (int i = 0; i < ingredients.size(); i++) {
                recipeIngredients.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new MineralInfusingStationRecipe(recipeId, output, recipeIngredients, fluid);
        }

        @Override
        public @Nullable MineralInfusingStationRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            NonNullList<Ingredient> recipeIngredients = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);
            FluidStack fluid = buffer.readFluidStack();
            recipeIngredients.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
            ItemStack output = buffer.readItem();
            return new MineralInfusingStationRecipe(recipeId, output, recipeIngredients, fluid);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull MineralInfusingStationRecipe recipe) {
            buffer.writeInt(recipe.recipeIngredients.size());
            buffer.writeFluidStack(recipe.fluidStack);

            for (Ingredient ingredient : recipe.recipeIngredients) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.output);
        }
    }
}
