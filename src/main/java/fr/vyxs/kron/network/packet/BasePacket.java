package fr.vyxs.kron.network.packet;

import fr.vyxs.kron.blockEntity.SyncedBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class BasePacket<T extends SyncedBlockEntity> {

    protected BlockPos blockPos;

    public BasePacket(FriendlyByteBuf buffer) {
        this.blockPos = buffer.readBlockPos();
    }

    public BasePacket(BlockPos pos) {
        this.blockPos = pos;
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(blockPos);
        writeData(buffer);
    }

    @SuppressWarnings("unchecked")
    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ClientLevel world = Minecraft.getInstance().level;

            if (world == null)
                return;

            BlockEntity blockEntity = world.getBlockEntity(blockPos);

            if (blockEntity instanceof SyncedBlockEntity syncedBlockEntity) {
                handlePacket((T) syncedBlockEntity);
            }
        });
        ctx.setPacketHandled(true);
    }

    protected abstract void writeData(FriendlyByteBuf buffer);

    protected abstract void handlePacket(T blockEntity);
}
