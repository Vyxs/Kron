package fr.vyxs.kron.ui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.vyxs.kron.Kron;
import fr.vyxs.kron.core.R;
import fr.vyxs.kron.ui.menu.VinylTurntableMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import static fr.vyxs.kron.metric.VinylTurntableMetric.*;

public class VinylTurntableScreen extends AbstractContainerScreen<VinylTurntableMenu> {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(Kron.MODID, R.Gui.VINYL_TURNTABLE);

    public VinylTurntableScreen(VinylTurntableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
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
    }

    @Override
    protected void renderBg(@NotNull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = getX();
        int y = getY();
        this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }
}
