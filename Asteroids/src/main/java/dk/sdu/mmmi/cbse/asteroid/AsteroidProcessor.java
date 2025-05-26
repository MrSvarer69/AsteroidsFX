package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.BoundaryHandler;

import java.util.Random;

public class AsteroidProcessor implements IEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionComponent position = asteroid.getComponent(PositionComponent.class);
            MovementComponent movement = asteroid.getComponent(MovementComponent.class);
            HealthComponent health = asteroid.getComponent(HealthComponent.class);

            if (position == null || movement == null || health == null) {
                continue;
            }

            BoundaryHandler.wrapAround(position, gameData);

            int numPoints = 12;
            float speed = (float) Math.random() * 10f + 20f;
            if (health.getHealth() == 1) {
                numPoints = 8;
                speed = (float) Math.random() * 30f + 70f;
            } else if (health.getHealth() == 2) {
                numPoints = 10;
                speed = (float) Math.random() * 10f + 50f;
            }

            movement.setUp(true);
            movement.setSpeed(speed);

            movement.process(gameData, asteroid);

            // Creating the split asteroid
            if (health.wasDamagedThisFrame()) {
                asteroidSplitter.createSplitAsteroid(asteroid, world);

                // Respawning the asteroid if it is destroyed
                if (health.getHealth() > 1) {
                    world.removeEntity(asteroid);
                    world.respawnEntity(() -> createAsteroid(gameData));
                }
            }

            setShape(asteroid, numPoints);
        }
    }

    static Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();

        float radians = (float) (Math.random() * 2 * Math.PI);
        float speed = (float) Math.random() * 10f + 20f;
        float x = rnd.nextFloat() * gameData.getDisplayWidth();
        float y = rnd.nextFloat() * gameData.getDisplayHeight();

        asteroid.setRadius(20);

        asteroid.add(new PositionComponent(x, y, radians));
        asteroid.add(new MovementComponent(0, speed, speed, 0));
        asteroid.add(new HealthComponent(3));

        return asteroid;
    }

    private void setShape(Entity entity, int numPoints) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        if (position == null) return;

        float[] shapex = new float[numPoints];
        float[] shapey = new float[numPoints];
        float radians = position.getAngle();
        float x = position.getX();
        float y = position.getY();
        float radius = entity.getRadius();

        float angle = 0;

        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * radius;
            shapey[i] = y + (float) Math.sin(angle + radians) * radius;
            angle += 2 * 3.1415f / numPoints;
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}