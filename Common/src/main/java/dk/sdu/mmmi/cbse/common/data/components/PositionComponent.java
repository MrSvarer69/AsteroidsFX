package dk.sdu.mmmi.cbse.common.data.components;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * Stores position and rotation data for an entity.
 */
public class PositionComponent implements EntityComponent {

    private float x;
    private float y;
    private float angle; // replaces "radians" for clearer naming

    public PositionComponent(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        // No processing needed for static position
    }
}
