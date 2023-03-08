package fr.vyxs.kron.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fr.vyxs.kron.block.VinylTurntableBlock;
import fr.vyxs.kron.entity.VinylTurntableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VinylTurntableRenderer implements BlockEntityRenderer<VinylTurntableBlockEntity> {

    public VinylTurntableRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull VinylTurntableBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack record = blockEntity.getRecord();

        poseStack.pushPose();
        poseStack.translate(0.5, 0.6, 0.475);
        poseStack.scale(1.0f, 1f, 0.7f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90));

        int rotation = switch (blockEntity.getBlockState().getValue(VinylTurntableBlock.FACING)) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(rotation));

        itemRenderer.renderStatic(record, ItemTransforms.TransformType.GUI, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 1);
        poseStack.popPose();
    }

    private int getLightLevel(@Nullable Level level, BlockPos blockPos) {
        int light = level == null ? 0 : level.getBrightness(LightLayer.BLOCK, blockPos);
        int skyLight = level == null ? 0 : level.getBrightness(LightLayer.SKY, blockPos);
        return light << 4 | skyLight << 20;
    }
}
