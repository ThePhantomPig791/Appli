package net.thephantompig791.appli.titan_shifters_content.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import net.thephantompig791.appli.titan_shifters_content.TitanShifters;
import net.thephantompig791.appli.titan_shifters_content.entity.TitanSkinBoltEntity;

public class TitanSkinBoltEntityRenderer extends ProjectileEntityRenderer<TitanSkinBoltEntity> {
    public TitanSkinBoltEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(TitanSkinBoltEntity entity) {
        return TitanShifters.identifier("textures/entity/titan_skin_bolt.png");
    }
}
