package fr.vyxs.kron.tool.shape;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShapeUtil {

    private ShapeUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static VoxelShape cuboidDirectional(double x1, double y1, double z1, double x2, double y2, double z2, Direction direction) {
        return switch (direction) {
            case SOUTH -> Block.box(16 - x2, y1, 16 - z2, 16 - x1, y2, 16 - z1);
            case WEST -> Block.box(z1, y1, 16 - x2, z2, y2, 16 - x1);
            case EAST -> Block.box(16 - z2, y1, x1, 16 - z1, y2, x2);
            default -> Block.box(x1, y1, z1, x2, y2, z2);
        };
    }

    public static VoxelShape stairDirectional(Direction direction) {
        return Shapes.or(
            cuboidDirectional(0, 8, 8, 16, 16, 16, direction),
            cuboidDirectional(0, 0, 0, 16, 8, 16, direction)
        );
    }
}
