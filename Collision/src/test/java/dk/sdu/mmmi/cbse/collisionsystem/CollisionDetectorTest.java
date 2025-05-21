package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CollisionDetectorTest {

    private CollisionDetector collisionDetector;
    private GameData gameData;
    private World world;

    @BeforeEach
    public void setUp() {
        collisionDetector = new CollisionDetector();
        gameData = new GameData();
        world = new World();
    }

    @Test
    public void testCollisionBetweenEntities() {
        // Create two entities with the same position
        Entity asteroid = new Entity();
        asteroid.add(new PositionComponent(100, 100, 0));
        asteroid.setRadius(20);

        Entity player = new Entity();
        player.add(new PositionComponent(100, 100, 0));
        player.setRadius(20);
        player.add(new HealthComponent(3)); // Add health to the player

        // Add entities to the world
        world.addEntity(asteroid);
        world.addEntity(player);

        // Process collisions
        collisionDetector.process(gameData, world);

        // Verify that the player's health is reduced
        HealthComponent health = player.getComponent(HealthComponent.class);
        assertEquals(2, health.getHealth(), "Player's health should decrease by 1 after collision");

        // Verify that the player is removed if health reaches zero
        collisionDetector.process(gameData, world);
        collisionDetector.process(gameData, world);
        assertFalse(world.getEntities().contains(player), "Player should be removed when health reaches zero");
    }
}