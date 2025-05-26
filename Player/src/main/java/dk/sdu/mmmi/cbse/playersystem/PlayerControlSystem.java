package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import dk.sdu.mmmi.cbse.common.data.components.TimeComponent;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.BoundaryHandler;

public class PlayerControlSystem implements IEntityProcessingService {

    private BulletSPI bulletService;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionComponent position = player.getComponent(PositionComponent.class);
            MovementComponent movement = player.getComponent(MovementComponent.class);
            HealthComponent health = player.getComponent(HealthComponent.class);
            TimeComponent cooldown = player.getComponent(TimeComponent.class);

            if (position == null || movement == null || health == null) {
                continue;
            }

            BoundaryHandler.wrapAround(position, gameData);

            movement.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movement.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movement.setUp(gameData.getKeys().isDown(GameKeys.UP));

            movement.process(gameData, player);

            wrapAround(position, gameData);

            updateShape(player, position);

            if (cooldown == null) {
                cooldown = new TimeComponent(0.5f);
                player.add(cooldown);
            }

            cooldown.process(gameData, player);

            // Shooting logic
            if (bulletService != null && cooldown.isCooldownReady() && gameData.getKeys().isPressed(GameKeys.SPACE)) {
                Entity bullet = bulletService.createBullet(player, gameData);
                if (bullet != null) {
                    world.addEntity(bullet);
                }
            }
        }
    }

    private void wrapAround(PositionComponent position, GameData gameData) {
        float x = position.getX();
        float y = position.getY();
        float screenWidth = gameData.getDisplayWidth();
        float screenHeight = gameData.getDisplayHeight();

        if (x < 0) {
            position.setX(screenWidth);
        } else if (x > screenWidth) {
            position.setX(0);
        }

        if (y < 0) {
            position.setY(screenHeight);
        } else if (y > screenHeight) {
            position.setY(0);
        }
    }

    private void updateShape(Entity entity, PositionComponent position) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        float x = position.getX();
        float y = position.getY();
        float radians = position.getAngle();
        float radius = entity.getRadius();

        shapex[0] = (float) (x + Math.cos(radians) * radius);
        shapey[0] = (float) (y + Math.sin(radians) * radius);

        shapex[1] = (float) (x + Math.cos(radians - 4 * Math.PI / 5) * radius);
        shapey[1] = (float) (y + Math.sin(radians - 4 * Math.PI / 5) * radius);

        shapex[2] = (float) (x + Math.cos(radians + Math.PI) * radius * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + Math.PI) * radius * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * Math.PI / 5) * radius);
        shapey[3] = (float) (y + Math.sin(radians + 4 * Math.PI / 5) * radius);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}