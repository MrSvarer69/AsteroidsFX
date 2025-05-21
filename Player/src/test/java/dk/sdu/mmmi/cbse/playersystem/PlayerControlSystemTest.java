package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerControlSystemTest {
/*
    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private GameKeys gameKeys;
    private World world;
    private Entity player;

    @BeforeEach
    public void setUp() {
        playerControlSystem = new PlayerControlSystem();
        gameData = Mockito.mock(GameData.class);
        gameKeys = Mockito.mock(GameKeys.class);
        world = new World();

        // Mock GameKeys in GameData
        Mockito.when(gameData.getKeys()).thenReturn(gameKeys);

        // Create a player entity with necessary components
        player = new Entity();
        PositionComponent position = new PositionComponent(50, 50, 0);
        MovementComponent movement = new MovementComponent(10, 100, 200, 5);
        player.add(position);
        player.add(movement);

        world.addEntity(player);
    }

    @Test
    public void testPlayerMovesRight() {
        // Simulate RIGHT key press
        Mockito.when(gameKeys.isDown(GameKeys.RIGHT)).thenReturn(true);
        Mockito.when(gameData.getDelta()).thenReturn(0.1f);

        // Process the player movement for 100 frames
        for (int i = 0; i < 100; i++) {
            playerControlSystem.process(gameData, world);
        }

        // Verify the player's position has increased on the X-axis
        PositionComponent position = player.getComponent(PositionComponent.class);
        assertTrue(position.getX() > 50.0f, "X should increase when RIGHT key is pressed");
        assertEquals(50.0f, position.getY(), 0.1f); // Y should remain the same
    }

 */
}