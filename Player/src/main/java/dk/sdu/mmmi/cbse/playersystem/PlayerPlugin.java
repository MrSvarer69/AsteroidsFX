package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.data.components.MovementComponent;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;

public class PlayerPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        world.respawnEntity(() -> PlayerControlSystem.createPlayer(gameData));
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Player.class).forEach(world::removeEntity);;
    }
}
