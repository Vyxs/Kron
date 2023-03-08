package fr.vyxs.kron.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AdvancedPickaxe extends PickaxeItem {

    public AdvancedPickaxe(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.STONE && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem().getClass() == AdvancedPickaxe.class) {
            event.getPlayer().addItem(new ItemStack(Items.STICK));
        }
    }
}