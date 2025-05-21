package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@Configuration
public class GameConfig {

    @Bean
    public BulletControlSystem bulletControlSystem() {
        return new BulletControlSystem();
    }

    @Bean
    public EnemyControlSystem enemyControlSystem(BulletControlSystem bulletControlSystem) {
        EnemyControlSystem enemyControlSystem = new EnemyControlSystem();
        enemyControlSystem.setBulletService(bulletControlSystem);
        return enemyControlSystem;
    }

    @Bean
    public PlayerControlSystem playerControlSystem(BulletControlSystem bulletControlSystem) {
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();
        playerControlSystem.setBulletService(bulletControlSystem);
        return playerControlSystem;
    }

    @Bean
    public EnemyPlugin enemyPlugin() {
        return new EnemyPlugin();
    }

    @Bean
    public BulletPlugin bulletPlugin() {
        return new BulletPlugin();
    }

    @Bean
    public PlayerPlugin playerPlugin() {
        return new PlayerPlugin();
    }

    @Bean
    public CollisionDetector collisionDetector() {
        return new CollisionDetector();
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServices(
            EnemyControlSystem enemyControlSystem,
            PlayerControlSystem playerControlSystem,
            BulletControlSystem bulletControlSystem) {
        List<IEntityProcessingService> services = new ArrayList<>(List.of(
                enemyControlSystem, playerControlSystem, bulletControlSystem
        ));
        ServiceLoader<IEntityProcessingService> loader = ServiceLoader.load(IEntityProcessingService.class);
        loader.forEach(service -> {
            if (!services.contains(service)) {
                services.add(service);
            }
        });
        return services;
    }

    @Bean
    public List<IGamePluginService> gamePluginServices(
            EnemyPlugin enemyPlugin,
            BulletPlugin bulletPlugin,
            PlayerPlugin playerPlugin) {
        List<IGamePluginService> plugins = new ArrayList<>(List.of(
                enemyPlugin, bulletPlugin, playerPlugin
        ));
        ServiceLoader<IGamePluginService> loader = ServiceLoader.load(IGamePluginService.class);
        loader.forEach(plugin -> {
            if (!plugins.contains(plugin)) {
                plugins.add(plugin);
            }
        });
        return plugins;
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices(CollisionDetector collisionDetector) {
        List<IPostEntityProcessingService> services = new ArrayList<>(List.of(collisionDetector));
        ServiceLoader<IPostEntityProcessingService> loader = ServiceLoader.load(IPostEntityProcessingService.class);
        loader.forEach(service -> {
            if (!services.contains(service)) {
                services.add(service);
            }
        });
        return services;
    }
}