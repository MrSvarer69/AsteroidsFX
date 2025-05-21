import dk.sdu.mmmi.cbse.split.SplitClass;

module Enemy {
    exports dk.sdu.mmmi.cbse.split;
    requires Common;
    requires CommonEnemy;
    requires CommonBullet;
    requires java.sql;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService with SplitClass;
    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService with dk.sdu.mmmi.cbse.split.EnemyControlSystem;
}