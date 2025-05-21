package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;

import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    private final Random random = new Random();

    @Override
    public void createSplitAsteroid(Entity asteroid, World world) {
        HealthComponent health = asteroid.getComponent(HealthComponent.class);
        PositionComponent position = asteroid.getComponent(PositionComponent.class);
        MovementComponent movement = asteroid.getComponent(MovementComponent.class);

        if (health == null || position == null || movement == null) {
            return;
        }

        if (health.getHealth() > 1) {
            int numSplitAsteroids = 2; // Number of smaller asteroids to create
            for (int i = 0; i < numSplitAsteroids; i++) {
                Entity smallerAsteroid = new Asteroid();

                // Scale down the radius
                smallerAsteroid.setRadius(asteroid.getRadius() * 0.5f);

                // Set health to 1 for smaller asteroids
                smallerAsteroid.add(new HealthComponent(1));

                // Offset position slightly to avoid overlap
                float offsetX = (random.nextFloat() - 0.5f) * asteroid.getRadius();
                float offsetY = (random.nextFloat() - 0.5f) * asteroid.getRadius();

                // Adjust angle and speed for smaller asteroids
                float angleOffset = (float) (random.nextFloat() * Math.PI / 4 - Math.PI / 8);
                float newAngle = position.getAngle() + angleOffset;
                float newSpeed = movement.getSpeed() * 1.2f;

                smallerAsteroid.add(new PositionComponent(
                        position.getX() + offsetX,
                        position.getY() + offsetY,
                        newAngle
                ));
                smallerAsteroid.add(new MovementComponent(0, newSpeed, newSpeed, 0));

                // Add the smaller asteroid to the world
                world.addEntity(smallerAsteroid);
            }
        }

        // Remove the original asteroid
        world.removeEntity(asteroid);

        // Add score for destroying the asteroid
        world.addScore(20);
    }
}