package fr.vyxs.kron.tool.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BlockEntityUtil {

    private BlockEntityUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static @NotNull <T> LazyOptional<T> getCapability(BlockEntity blockEntity, Property<Direction> facing,
                                                      LazyOptional<IItemHandler> lazyItemHandler, Map<Direction, LazyOptional<SideHandler>> sideHandlers,
                                                      @Nullable Direction side) {
        if (side == null) return lazyItemHandler.cast();

        if (sideHandlers.containsKey(side)) {
            Direction localDirection = blockEntity.getBlockState().getValue(facing);

            if (side == Direction.DOWN || side == Direction.UP)
                return sideHandlers.get(side).cast();

            return switch (localDirection) {
                case SOUTH -> sideHandlers.get(side).cast();
                case WEST -> sideHandlers.get(side.getCounterClockWise()).cast();
                case EAST -> sideHandlers.get(side.getClockWise()).cast();
                default -> sideHandlers.get(side.getOpposite()).cast();
            };
        }
        return lazyItemHandler.cast();
    }

}
