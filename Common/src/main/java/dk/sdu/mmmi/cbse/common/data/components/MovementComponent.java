package dk.sdu.mmmi.cbse.common.data.components;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Handles movement for entities.
 */
public class MovementComponent implements EntityComponent {

    private float dx, dy;
    private float deceleration, acceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up;

    public MovementComponent(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    public void setSpeed(float speed) {
        this.acceleration = speed;
        this.maxSpeed = speed;
    }

    public float getSpeed() {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionComponent location = entity.getComponent(PositionComponent.class);
        float x = location.getX();
        float y = location.getY();
        float angle = location.getAngle();
        float dt = gameData.getDelta();

        // Turning logic
        if (left) {
            angle += rotationSpeed * dt;
        }
        if (right) {
            angle -= rotationSpeed * dt;
        }

        // Acceleration logic
        if (up) {
            dx += cos(angle) * acceleration * dt;
            dy += sin(angle) * acceleration * dt;
        }

        // Deceleration
        float vec = (float) sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            float harshDeceleration = deceleration * 3;
            dx -= (dx / vec) * harshDeceleration * dt;
            dy -= (dy / vec) * harshDeceleration * dt;

            // Prevent overshooting to negative speeds
            if (Math.abs(dx) < 0.01f) dx = 0;
            if (Math.abs(dy) < 0.01f) dy = 0;
        }

        // Clamp to max speed
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // Update position
        x += dx * dt;
        y += dy * dt;

        // Apply updates
        location.setPosition(x, y);
        location.setAngle(angle);
    }
}
