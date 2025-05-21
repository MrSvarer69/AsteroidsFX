package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        float x = gameData.getDisplayWidth() / 2f;
        float y = gameData.getDisplayHeight() / 2f;
        float radians = (float) Math.PI / 2;

        Entity playerEntity = new Player(); // Player extends Entity
        playerEntity.setRadius(8);

        playerEntity.add(new PositionComponent(x, y, radians));
        playerEntity.add(new MovementComponent(10, 100, 200, 5));
        playerEntity.add(new HealthComponent(3)); // 3 lives

        return playerEntity;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}
