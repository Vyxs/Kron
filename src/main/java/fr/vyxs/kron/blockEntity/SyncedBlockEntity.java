package fr.vyxs.kron.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.PacketDistributor;

public abstract class SyncedBlockEntity extends BlockEntity {

    public SyncedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return writeClient(new CompoundTag());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        readClient(tag);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tag = packet.getTag();
        readClient(tag == null ? new CompoundTag() : tag);
    }

    public void readClient(CompoundTag tag) {
        load(tag);
    }

    public CompoundTag writeClient(CompoundTag tag) {
        saveAdditional(tag);
        return tag;
    }

    public void sendData() {
        if (level != null && !level.isClientSide)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2 | 4 | 16);
    }

    public void causeBlockUpdate() {
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 1);
    }

    public void notifyUpdate() {
        setChanged();
        sendData();
    }

    public PacketDistributor.PacketTarget packetTarget() {
        return PacketDistributor.TRACKING_CHUNK.with(this::containedChunk);
    }

    public LevelChunk containedChunk() {
        return level.getChunkAt(worldPosition);
    }
}