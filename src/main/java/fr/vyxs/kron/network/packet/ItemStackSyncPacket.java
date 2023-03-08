package fr.vyxs.kron.network.packet;

import fr.vyxs.kron.entity.MineralInfusingStationBlockEntity;
import fr.vyxs.kron.entity.VinylTurntableBlockEntity;
import fr.vyxs.kron.ui.menu.MineralInfusingStationMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ItemStackSyncPacket {

    private final ItemStack itemStack;
    private final BlockPos pos;

    public ItemStackSyncPacket(BlockPos blockPos, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.pos = blockPos;
    }

    public ItemStackSyncPacket(FriendlyByteBuf buffer) {
        this.itemStack = buffer.readItem();
        this.pos = buffer.readBlockPos();
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeItem(itemStack);
        buffer.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side
            final Minecraft client = Minecraft.getInstance();
            if (client.level == null) return;
            if (client.level.getBlockEntity(pos) instanceof VinylTurntableBlockEntity blockEntity) {
                blockEntity.setRecord(itemStack);
            }
        });
        return true;
    }
}

