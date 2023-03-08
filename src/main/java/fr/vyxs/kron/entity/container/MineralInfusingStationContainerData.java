package fr.vyxs.kron.entity.container;

import fr.vyxs.kron.tool.type.SharedInt;
import net.minecraft.world.inventory.ContainerData;

// Not thread-safe
public record MineralInfusingStationContainerData(
        SharedInt progress,
        SharedInt maxProgress
) implements ContainerData {

    @Override
    public int get(int index) {
        return switch (index) {
            case 0 -> progress.get();
            case 1 -> maxProgress.get();
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index) {
            case 0 -> progress.set(value);
            case 1 -> maxProgress.set(value);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
