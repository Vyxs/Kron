package fr.vyxs.kron.ui.screen.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.vyxs.kron.core.R;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Arrays;
import java.util.List;

public class EnergyCapacityComponent extends GuiComponent {

    private static final int RED = 0xFF982649;
    private static final int LIGHT_RED = 0xFFD95C5C; //f71414
    private static final int GREEN = 0xFF269875;

    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;
    protected final IEnergyStorage energyStorage;

    public EnergyCapacityComponent(IEnergyStorage energyStorage, Rect2i area) {
        this.x = area.getX();
        this.y = area.getY();
        this.width = area.getWidth();
        this.height = area.getHeight();
        this.energyStorage = energyStorage;
    }

    public void render(PoseStack poseStack) {
        int stored = (int) (height * (energyStorage.getEnergyStored() / (float) energyStorage.getMaxEnergyStored()));
        fillGradient(poseStack, x, y + (height - stored), x + width, y + height, LIGHT_RED, RED);
    }

    public List<Component> getTooltip() {
        return List.of(Component.translatable(R.Lang.GUI_ENERGY_CAPACITY, determinePower(energyStorage.getEnergyStored()), determinePower(energyStorage.getMaxEnergyStored())));
    }

    /**
     * Example :
     * determinePower(32845) -> 32.8 k
     * determinePower(32845000) -> 32.8 M
     **/
    private static String determinePower(int energy) {
        if (energy < 1000) {
            return energy + " ";
        }
        int exp = (int) (Math.log(energy) / Math.log(1000));
        return String.format("%.1f %c", energy / Math.pow(1000, exp), "kMGTPE".charAt(exp - 1));
    }
}
