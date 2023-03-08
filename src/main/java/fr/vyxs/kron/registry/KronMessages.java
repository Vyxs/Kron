package fr.vyxs.kron.registry;

import fr.vyxs.kron.Kron;
import fr.vyxs.kron.network.packet.EnergySyncPacket;
import fr.vyxs.kron.network.packet.FluidSyncPacket;
import fr.vyxs.kron.network.packet.ItemStackSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class KronMessages {

    private static SimpleChannel channel;
    private static int id = 0;

    private static int nextId() {
        if (id == Integer.MAX_VALUE) {
            throw new RuntimeException("Too many messages");
        }
        return id++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Kron.MODID, "msg"))
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .networkProtocolVersion(() -> "1")
                .simpleChannel();
        channel = net;

        net.messageBuilder(EnergySyncPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(EnergySyncPacket::write)
                .decoder(EnergySyncPacket::new)
                .consumerMainThread(EnergySyncPacket::handle)
                .add();

        net.messageBuilder(FluidSyncPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(FluidSyncPacket::write)
                .decoder(FluidSyncPacket::new)
                .consumerMainThread(FluidSyncPacket::handle)
                .add();

        net.messageBuilder(ItemStackSyncPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ItemStackSyncPacket::write)
                .decoder(ItemStackSyncPacket::new)
                .consumerMainThread(ItemStackSyncPacket::handle)
                .add();
    }

    public static <T> void sendToServer(T message) {
        channel.sendToServer(message);
    }

    public static <T> void sendToPlayer(T message, ServerPlayer player) {
        channel.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <T> void sendToAllPlayers(T message) {
        channel.send(PacketDistributor.ALL.noArg(), message);
    }
}
