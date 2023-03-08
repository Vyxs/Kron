package fr.vyxs.kron.tool.energy;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {

    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (received != 0) {
            onEnergyChanged();
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (extracted != 0) {
            onEnergyChanged();
        }
        return extracted;
    }

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public abstract void onEnergyChanged();

    public boolean isFull() {
        return energy == capacity;
    }
}
