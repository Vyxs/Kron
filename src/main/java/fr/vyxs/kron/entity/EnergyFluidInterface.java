package fr.vyxs.kron.entity;

import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public interface EnergyFluidInterface {

    IEnergyStorage getEnergyStorage();
    void setEnergyLevel(int energy);
    IFluidTank getFluidTank();
    void setFluidStack(FluidStack fluid);
    FluidStack getFluidStack();
}
