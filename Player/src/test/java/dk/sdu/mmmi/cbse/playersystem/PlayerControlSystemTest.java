package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerControlSystemTest {
    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private World world;
    private Entity player;
    private GameKeys gameKeys;

    @BeforeEach
    public void setUp() {
        // Initialize the system and create mocks
        playerControlSystem = new PlayerControlSystem();
        gameData = mock(GameData.class);
        world = new World();
        gameKeys = mock(GameKeys.class);

        // Setup basic game data
        when(gameData.getKeys()).thenReturn(gameKeys);
        when(gameData.getDelta()).thenReturn(0.016f); // Simulate 60 FPS
        when(gameData.getDisplayWidth()).thenReturn(800);
        when(gameData.getDisplayHeight()).thenReturn(600);

        // Create a player entity with components
        player = new Player();
        PositionComponent position = new PositionComponent(400, 300, 0); // Start in middle
        MovementComponent movement = new MovementComponent(10, 100, 200, 5);
        HealthComponent health = new HealthComponent(3);

        player.add(position);
        player.add(movement);
        player.add(health);
        player.setRadius(8);

        world.addEntity(player);
    }

    @Test
    public void testPlayerForwardMovement() {
        when(gameKeys.isDown(GameKeys.UP)).thenReturn(true);
        when(gameKeys.isDown(GameKeys.LEFT)).thenReturn(false);
        when(gameKeys.isDown(GameKeys.RIGHT)).thenReturn(false);

        PositionComponent position = player.getComponent(PositionComponent.class);
        float initialX = position.getX();
        float initialY = position.getY();

        playerControlSystem.process(gameData, world);

        assertTrue(position.getX() != initialX || position.getY() != initialY,
                "Player should have moved from initial position");
    }
}