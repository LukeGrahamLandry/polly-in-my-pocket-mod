package ca.lukegrahamlandry.pollyinmypocket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HeldPetRenderer extends BlockEntityWithoutLevelRenderer {
    public HeldPetRenderer(BlockEntityRenderDispatcher $$0, EntityModelSet $$1) {
        super($$0, $$1);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType $$1, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
        matrixStack.pushPose();

        Entity entity = getEntity(stack);

        matrixStack.mulPose(Vector3f.YN.rotation(30));

        float f = 0.53125F;
        float f1 = Math.max(entity.getBbWidth(), entity.getBbHeight());
        if (f1 > 1.0D)  f /= f1;
        matrixStack.scale(f, f, f);

        matrixStack.translate(1, 1, 1);
        if (entity.getType().equals(EntityType.PARROT)) matrixStack.scale(2, 2, 2);

        Minecraft.getInstance().getEntityRenderDispatcher().render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0, matrixStack, renderTypeBuffer, combinedLightIn);

        matrixStack.popPose();
    }

    Map<ItemStack, Entity> memoize = new HashMap<>();
    private Entity getEntity(ItemStack stack) {
        if (memoize.containsKey(stack)) return memoize.get(stack);
        Entity entity = HeldPetItem.readEntity(stack, Minecraft.getInstance().level);
        memoize.put(stack, entity);
        return entity;
    }

}