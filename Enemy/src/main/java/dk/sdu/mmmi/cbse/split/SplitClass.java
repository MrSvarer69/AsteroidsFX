package dk.sdu.mmmi.cbse.split;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;

public class SplitClass implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        world.respawnEntity(() -> EnemyControlSystem.createEnemy(gameData));
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Enemy.class).forEach(world::removeEntity);
    }
}