package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.HealthComponent;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity other : world.getEntities()) {
                if (entity.getID().equals(other.getID())) continue;

                if (collides(entity, other)) {
                    HealthComponent health = entity.getComponent(HealthComponent.class);
                    if (health != null && !health.isDead()) {
                        health.damage(1);
                        if (health.isDead()) {
                            world.removeEntity(entity);
                        }
                    }
                }
            }
        }
    }

    private boolean collides(Entity e1, Entity e2) {
        PositionComponent pos1 = e1.getComponent(PositionComponent.class);
        PositionComponent pos2 = e2.getComponent(PositionComponent.class);

        if (pos1 == null || pos2 == null) return false;

        float dx = pos1.getX() - pos2.getX();
        float dy = pos1.getY() - pos2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < (e1.getRadius() + e2.getRadius());
    }
}
