package fr.vyxs.kron.tool;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModTier implements Tier {

    private final int uses;
    private final float speed;
    private final float atkDmgBonus;
    private final int level;
    private final int enchantability;

    public ModTier(int uses, float speed, float atkDmgBonus, int level, int enchantability) {
        this.uses = uses;
        this.speed = speed;
        this.atkDmgBonus = atkDmgBonus;
        this.level = level;
        this.enchantability = enchantability;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return atkDmgBonus;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }

    @Override
    public @Nullable TagKey<Block> getTag() {
        return Tier.super.getTag();
    }
}