module Core {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires Common;
    requires CommonBullet;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    exports dk.sdu.mmmi.cbse.main to javafx.graphics;
}