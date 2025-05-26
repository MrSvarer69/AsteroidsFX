package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.components.EntityComponent;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a game entity composed of components.
 */
public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;

    // Entity components mapped by class type
    private final Map<Class<? extends EntityComponent>, EntityComponent> components = new ConcurrentHashMap<>();

    // Custom properties (for any ad-hoc metadata)
    private final Map<String, Object> properties = new ConcurrentHashMap<>();

    // ID
    public String getID() {
        return ID.toString();
    }

    // Component Management

    public <T extends EntityComponent> void add(T component) {
        components.put(component.getClass(), component);
    }

    public <T extends EntityComponent> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    // Shape

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    // Radius

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return this.radius;
    }

}
