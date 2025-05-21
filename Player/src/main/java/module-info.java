
    import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

    module Player {
        exports dk.sdu.mmmi.cbse.split;
        requires Common;
        requires CommonBullet;
        uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
        provides IGamePluginService with dk.sdu.mmmi.cbse.split.PlayerPlugin;
        provides IEntityProcessingService with dk.sdu.mmmi.cbse.split.PlayerControlSystem;
    }
