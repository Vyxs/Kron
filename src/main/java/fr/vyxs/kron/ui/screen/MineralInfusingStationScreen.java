package fr.vyxs.kron.ui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.vyxs.kron.Kron;
import fr.vyxs.kron.core.R;
import fr.vyxs.kron.metric.MineralInfusingStationMetric;
import fr.vyxs.kron.tool.controller.MouseUtil;
import fr.vyxs.kron.ui.menu.MineralInfusingStationMenu;
import fr.vyxs.kron.ui.screen.renderer.EnergyCapacityComponent;
import fr.vyxs.kron.ui.screen.renderer.FluidTankRenderer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static fr.vyxs.kron.metric.MineralInfusingStationMetric.*;

public class MineralInfusingStationScreen extends AbstractContainerScreen<MineralInfusingStationMenu> {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(Kron.MODID, R.Gui.MINERAL_INFUSING_STATION);
    private EnergyCapacityComponent energyCapacityComponent;
    private FluidTankRenderer fluidTankRenderer;

    public MineralInfusingStationScreen(MineralInfusingStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    private int getX() {
        return (this.width - this.imageWidth) / 2;
    }

    private int getY() {
        return (this.height - this.imageHeight) / 2;
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = BACKGROUND_WIDTH;
        this.imageHeight = BACKGROUND_HEIGHT;
        this.energyCapacityComponent = new EnergyCapacityComponent(menu.getBlockEntity().getEnergyStorage(),
                new Rect2i(getX() + ENERGY_BAR_X, getY() + ENERGY_BAR_Y, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT));
        this.fluidTankRenderer = new FluidTankRenderer(menu.getBlockEntity().getFluidTank().getCapacity(), true, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);
        if (MouseUtil.isMouseAboveComponent(mouseX, mouseY, getX(), getY(), ENERGY_BAR_X, ENERGY_BAR_Y, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT))
            renderTooltip(poseStack, energyCapacityComponent.getTooltip(), Optional.empty(), mouseX - getX(), mouseY - getY());
        if (MouseUtil.isMouseAboveComponent(mouseX, mouseY, getX(), getY(), FLUID_TANK_X, FLUID_TANK_Y, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT))
            renderTooltip(poseStack, fluidTankRenderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL), Optional.empty(), mouseX - getX(), mouseY - getY());
    }

    @Override
    protected void renderBg(@NotNull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = getX();
        int y = getY();
        this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        renderProgressArrow(poseStack, x, y, menu.getScaledProgress());
        energyCapacityComponent.render(poseStack);
        fluidTankRenderer.render(poseStack, x + FLUID_TANK_X, y + FLUID_TANK_Y, menu.getFluidStack());
    }

    private void renderProgressArrow(PoseStack poseStack, int x, int y, int progress) {
        if (menu.isCrafting()) {
            blit(poseStack, x  + ARROW_X, y + ARROW_Y, ARROW_TEXTURE_X, ARROW_TEXTURE_Y, ARROW_WIDTH, progress);
        }
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }
}
