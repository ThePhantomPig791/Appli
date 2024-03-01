package net.thephantompig791.appli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.thephantompig791.appli.power.ModifyModelPartsPower;
import net.thephantompig791.appli.util.ModelPartTransformation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity>
        extends AnimalModel<T>
        implements ModelWithArms,
        ModelWithHead {
    @Shadow @Final public ModelPart head;

    @Shadow @Final public ModelPart hat;

    @Shadow @Final public ModelPart body;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart leftLeg;

    @Shadow @Final public ModelPart rightLeg;

    @Shadow protected abstract Iterable<ModelPart> getBodyParts();

    @Inject(
            method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
            at = @At(value = "HEAD")
    )
    public void appli$setAnglesHead(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        /*if (!PowerHolderComponent.hasPower(livingEntity, ModifyModelPartsPower.class)) {
            this.getBodyParts().forEach(modelPart -> {
                modelPart.xScale = 1;
                modelPart.yScale = 1;
                modelPart.zScale = 1;
            });
        }*/
        this.getBodyParts().forEach(modelPart -> {
            modelPart.pivotX = modelPart.getDefaultTransform().pivotX;
            modelPart.pivotY = modelPart.getDefaultTransform().pivotY;
            modelPart.pivotZ = modelPart.getDefaultTransform().pivotZ;
            modelPart.pitch = modelPart.getDefaultTransform().pitch;
            modelPart.yaw = modelPart.getDefaultTransform().yaw;
            modelPart.roll = modelPart.getDefaultTransform().roll;
            modelPart.xScale = 1;
            modelPart.yScale = 1;
            modelPart.zScale = 1;
        });
        head.pivotX = head.getDefaultTransform().pivotX;
        head.pivotY = head.getDefaultTransform().pivotY;
        head.pivotZ = head.getDefaultTransform().pivotZ;
        head.yaw = head.getDefaultTransform().yaw;
        head.roll = head.getDefaultTransform().roll;
        head.xScale = 1;
        head.yScale = 1;
        head.zScale = 1;
    }

    @Inject(
            method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
            at = @At(value = "TAIL")
    )
    public void appli$setAnglesTail(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (!PowerHolderComponent.hasPower(livingEntity, ModifyModelPartsPower.class)) return;

        List<ModelPartTransformation> transformations = new ArrayList<>();
        PowerHolderComponent.getPowers(livingEntity, ModifyModelPartsPower.class).forEach(power -> {
            transformations.addAll(power.getTransformations());
        });

        //please excuse this disgusting double switch statement
        transformations.forEach(t -> {
            switch (t.getModelPart().toLowerCase()) {
                case "head" -> {
                    switch (t.getType().toLowerCase()) {
                        case "pitch" -> head.pitch += t.getValue();
                        case "yaw" -> head.yaw += t.getValue();
                        case "roll" -> head.roll += t.getValue();
                        case "visible" ->
                                head.visible = t.getValue() != 0; //false (not visible) if value is zero, true (visible) otherwise
                        case "hidden" -> head.hidden = t.getValue() != 0; //same thing
                        case "xscale" -> head.xScale = t.getValue();
                        case "yscale" -> head.yScale = t.getValue();
                        case "zscale" -> head.zScale = t.getValue();
                        case "pivotx" -> head.pivotX += t.getValue();
                        case "pivoty" -> head.pivotY += t.getValue();
                        case "pivotz" -> head.pivotZ += t.getValue();
                    }
                }
                case "hat" -> {
                    switch (t.getType().toLowerCase()) {
                        case "pitch" -> hat.pitch += t.getValue();
                        case "yaw" -> hat.yaw += t.getValue();
                        case "roll" -> hat.roll += t.getValue();
                        case "visible" -> hat.visible = t.getValue() != 0;
                        case "hidden" -> hat.hidden = t.getValue() != 0;
                        case "xscale" -> hat.xScale = t.getValue();
                        case "yscale" -> hat.yScale = t.getValue();
                        case "zscale" -> hat.zScale = t.getValue();
                        case "pivotx" -> hat.pivotX += t.getValue();
                        case "pivoty" -> hat.pivotY += t.getValue();
                        case "pivotz" -> hat.pivotZ += t.getValue();
                    }
                }
                case "body" -> {
                    switch (t.getType().toLowerCase()) {
                        case "pitch" -> body.pitch += t.getValue();
                        case "yaw" -> body.yaw += t.getValue();
                        case "roll" -> body.roll += t.getValue();
                        case "visible" -> body.visible = t.getValue() != 0;
                        case "hidden" -> body.hidden = t.getValue() != 0;
                        case "xscale" -> body.xScale = t.getValue();
                        case "yscale" -> body.yScale = t.getValue();
                        case "zscale" -> body.zScale = t.getValue();
                        case "pivotx" -> body.pivotX += t.getValue();
                        case "pivoty" -> body.pivotY += t.getValue();
                        case "pivotz" -> body.pivotZ += t.getValue();
                    }
                }
                case "rightarm" -> {
                    switch (t.getType().toLowerCase()) {
                        case "pitch" -> rightArm.pitch += t.getValue();
                        case "yaw" -> rightArm.yaw += t.getValue();
                        case "roll" -> rightArm.roll += t.getValue();
                        case "visible" -> rightArm.visible = t.getValue() != 0;
                        case "hidden" -> rightArm.hidden = t.getValue() != 0;
                        case "xscale" -> rightArm.xScale = t.getValue();
                        case "yscale" -> rightArm.yScale = t.getValue();
                        case "zscale" -> rightArm.zScale = t.getValue();
                        case "pivotx" -> rightArm.pivotX += t.getValue();
                        case "pivoty" -> rightArm.pivotY += t.getValue();
                        case "pivotz" -> rightArm.pivotZ += t.getValue();
                    }
                }
                case "leftarm" -> {
                    switch (t.getType().toLowerCase()) {
                        case "pitch" -> leftArm.pitch += t.getValue();
                        case "yaw" -> leftArm.yaw += t.getValue();
                        case "roll" -> leftArm.roll += t.getValue();
                        case "visible" -> leftArm.visible = t.getValue() != 0;
                        case "hidden" -> leftArm.hidden = t.getValue() != 0;
                        case "xscale" -> leftArm.xScale = t.getValue();
                        case "yscale" -> leftArm.yScale = t.getValue();
                        case "zscale" -> leftArm.zScale = t.getValue();
                        case "pivotx" -> leftArm.pivotX += t.getValue();
                        case "pivoty" -> leftArm.pivotY += t.getValue();
                        case "pivotz" -> leftArm.pivotZ += t.getValue();
                    }
                }
                case "rightleg" -> {
                    switch (t.getType().toLowerCase()) {
                        case "pitch" -> rightLeg.pitch += t.getValue();
                        case "yaw" -> rightLeg.yaw += t.getValue();
                        case "roll" -> rightLeg.roll += t.getValue();
                        case "visible" -> rightLeg.visible = t.getValue() != 0;
                        case "hidden" -> rightLeg.hidden = t.getValue() != 0;
                        case "xscale" -> rightLeg.xScale = t.getValue();
                        case "yscale" -> rightLeg.yScale = t.getValue();
                        case "zscale" -> rightLeg.zScale = t.getValue();
                        case "pivotx" -> rightLeg.pivotX += t.getValue();
                        case "pivoty" -> rightLeg.pivotY += t.getValue();
                        case "pivotz" -> rightLeg.pivotZ += t.getValue();
                    }
                }
                case "leftleg" -> {
                    switch (t.getType().toLowerCase()) {
                        case "pitch" -> leftLeg.pitch += t.getValue();
                        case "yaw" -> leftLeg.yaw += t.getValue();
                        case "roll" -> leftLeg.roll += t.getValue();
                        case "visible" -> leftLeg.visible = t.getValue() != 0;
                        case "hidden" -> leftLeg.hidden = t.getValue() != 0;
                        case "xscale" -> leftLeg.xScale = t.getValue();
                        case "yscale" -> leftLeg.yScale = t.getValue();
                        case "zscale" -> leftLeg.zScale = t.getValue();
                        case "pivotx" -> leftLeg.pivotX += t.getValue();
                        case "pivoty" -> leftLeg.pivotY += t.getValue();
                        case "pivotz" -> leftLeg.pivotZ += t.getValue();
                    }
                }
            }
        });
    }
}
