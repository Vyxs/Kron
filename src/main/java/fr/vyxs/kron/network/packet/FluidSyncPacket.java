package fr.vyxs.kron.network.packet;

import fr.vyxs.kron.entity.MineralInfusingStationBlockEntity;
import fr.vyxs.kron.ui.menu.MineralInfusingStationMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidSyncPacket {

    private final FluidStack fluidStack;
    private final BlockPos pos;

    public FluidSyncPacket(BlockPos blockPos, FluidStack fluidStack) {
        this.fluidStack = fluidStack;
        this.pos = blockPos;
    }

    public FluidSyncPacket(FriendlyByteBuf buffer) {
        this.fluidStack = buffer.readFluidStack();
        this.pos = buffer.readBlockPos();
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeFluidStack(fluidStack);
        buffer.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side
            final Minecraft client = Minecraft.getInstance();
            if (client.level == null) return;
            if (client.level.getBlockEntity(pos) instanceof MineralInfusingStationBlockEntity blockEntity) {
                blockEntity.setFluidStack(fluidStack);
                if (client.player.containerMenu instanceof MineralInfusingStationMenu menu && menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluidStack(fluidStack);
                }
            }
        });
        return true;
    }
}
