module Core {
    requires javafx.controls;
    requires javafx.graphics;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.boot.starter.web;
    requires CommonBullet;
    requires Common;
    requires Bullet;
    requires Enemy;
    requires Player;
    requires Collision;
    requires Scoring;
    exports dk.sdu.mmmi.cbse.main to javafx.graphics;
    opens dk.sdu.mmmi.cbse.main to spring.core, spring.beans, spring.context;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
}


