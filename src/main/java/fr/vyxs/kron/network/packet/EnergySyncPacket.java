package fr.vyxs.kron.network.packet;

import fr.vyxs.kron.entity.MineralInfusingStationBlockEntity;
import fr.vyxs.kron.ui.menu.MineralInfusingStationMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncPacket {

    private final int energy;
    private final BlockPos pos;

    public EnergySyncPacket(BlockPos blockPos, int energyStored) {
        this.energy = energyStored;
        this.pos = blockPos;
    }

    public EnergySyncPacket(FriendlyByteBuf buffer) {
        this.energy = buffer.readInt();
        this.pos = buffer.readBlockPos();
    }
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(energy);
        buffer.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side
            final Minecraft client = Minecraft.getInstance();
            if (client.level == null) return;
            if (client.level.getBlockEntity(pos) instanceof MineralInfusingStationBlockEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);
                if (client.player.containerMenu instanceof MineralInfusingStationMenu menu && menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.setEnergyLevel(energy);
                }
            }
        });
        return true;
    }
}
