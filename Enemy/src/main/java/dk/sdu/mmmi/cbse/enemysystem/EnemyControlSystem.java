package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.data.components.TimeComponent;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.BoundaryHandler;

import java.util.Random;
import java.util.ServiceLoader;

public class EnemyControlSystem implements IEntityProcessingService {

    private BulletSPI bulletService;
    private static final Random random = new Random();

    public EnemyControlSystem() {
        ServiceLoader<BulletSPI> loader = ServiceLoader.load(BulletSPI.class);
        if (loader.findFirst().isPresent()) {
            bulletService = loader.findFirst().get();
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionComponent position = enemy.getComponent(PositionComponent.class);
            MovementComponent movement = enemy.getComponent(MovementComponent.class);
            HealthComponent health = enemy.getComponent(HealthComponent.class);
            TimeComponent cooldown = enemy.getComponent(TimeComponent.class);

            if (position == null || movement == null || health == null) {
                continue;
            }

            // Random movement logic
            float rng = random.nextFloat();
            movement.setUp(rng > 0.1f && rng < 0.9f);
            movement.setLeft(rng < 0.2f);
            movement.setRight(rng > 0.8f);

            movement.process(gameData, enemy);

            // Screen wrapping logic
            BoundaryHandler.wrapAround(position, gameData);

            updateShape(enemy);

            // Reset movement
            movement.setRight(false);
            movement.setLeft(false);
            movement.setUp(false);

            if (cooldown == null) {
                cooldown = new TimeComponent(1);
                enemy.add(cooldown);
            }

            cooldown.process(gameData, enemy);

            // Random shooting logic
            if (bulletService != null && cooldown.isCooldownReady() && random.nextFloat() < 0.50f) {
                Entity bullet = bulletService.createBullet(enemy, gameData);
                if (bullet != null) {
                    world.addEntity(bullet);
                }
            }

            // Check if the enemy is destroyed
            if (health.getHealth() <= 1) {
                world.removeEntity(enemy);
                world.respawnEntity(() -> createEnemy(gameData));
                world.addScore(30);
            }
        }
    }

    static Entity createEnemy(GameData gameData) {
        Entity enemy = new Enemy();

        float x = random.nextFloat() * gameData.getDisplayWidth();
        float y = random.nextFloat() * gameData.getDisplayHeight();
        PositionComponent position = new PositionComponent(x, y, 0);

        enemy.add(position);
        enemy.add(new MovementComponent(10, 50, 100, 3));
        enemy.add(new HealthComponent(3));
        enemy.setRadius(10);

        return enemy;
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];

        PositionComponent position = entity.getComponent(PositionComponent.class);
        float x = position.getX();
        float y = position.getY();
        float radians = position.getAngle();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * Math.PI / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * Math.PI / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + Math.PI) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + Math.PI) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * Math.PI / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * Math.PI / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}