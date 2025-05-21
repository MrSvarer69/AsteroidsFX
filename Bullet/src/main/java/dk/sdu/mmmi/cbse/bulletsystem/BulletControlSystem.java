package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import dk.sdu.mmmi.cbse.common.data.components.TimeComponent;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {

            PositionComponent positionComponent = bullet.getComponent(PositionComponent.class);
            MovementComponent movementComponent = bullet.getComponent(MovementComponent.class);
            TimeComponent timeComponent = bullet.getComponent(TimeComponent.class);

            if (positionComponent == null || movementComponent == null || timeComponent == null)
                continue;

            // Move bullet forward
            movementComponent.setUp(true);

            // Remove bullet when timer runs out
            if (timeComponent.isCooldownReady()) {
                world.removeEntity(bullet);
                continue;
            }

            // Update components
            timeComponent.process(gameData, bullet);
            movementComponent.process(gameData, bullet);
            positionComponent.process(gameData, bullet);

            setShape(bullet);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        PositionComponent shooterPos = shooter.getComponent(PositionComponent.class);
        TimeComponent cooldown = shooter.getComponent(TimeComponent.class);

        if (shooterPos == null) return null;

        // Ensure the shooter has a cooldown component
        if (cooldown == null) {
            cooldown = new TimeComponent(1); // 1-second cooldown
            shooter.add(cooldown);
        }

        // Check if the cooldown is ready
        if (!cooldown.tryUseCooldown()) {
            return null;
        }

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float angle = shooterPos.getAngle();
        float speed = 350;

        float offset = shooter.getRadius() + 5;
        float bx = (float) (x + Math.cos(angle) * offset);
        float by = (float) (y + Math.sin(angle) * offset);

        Entity bullet = new Bullet();
        bullet.setRadius(2);

        bullet.add(new PositionComponent(bx, by, angle));
        bullet.add(new HealthComponent(1));
        bullet.add(new MovementComponent(0, 5000000, speed, 0));
        bullet.add(new TimeComponent(1)); // bullet expires after 1 second

        bullet.setShapeX(new float[2]);
        bullet.setShapeY(new float[2]);

        return bullet;
    }
    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);

        if (positionComponent == null)
            return;

        float x = positionComponent.getX();
        float y = positionComponent.getY();
        float angle = positionComponent.getAngle();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + cos(angle));
        shapey[1] = (float) (y + sin(angle));

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
