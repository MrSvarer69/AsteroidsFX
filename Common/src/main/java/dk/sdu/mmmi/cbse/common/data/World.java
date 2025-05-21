package dk.sdu.mmmi.cbse.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private final GameScore gameScore = new GameScore(); // Scoring system

    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID) {
        Entity entity = entityMap.get(entityID);
        if (entity != null) {
            entityMap.remove(entityID);
        }
    }

    public void removeEntity(Entity entity) {
        if (entity != null && entityMap.containsKey(entity.getID())) {
            entityMap.remove(entity.getID());
        }
    }

    public void respawnEntity(Supplier<Entity> entitySupplier) {
        Entity newEntity = entitySupplier.get();
        if (newEntity != null) {
            addEntity(newEntity);
        }
    }

    public void addScore(int points) {
        gameScore.addScore(points);

        // Send the score to the ScoringSystem Spring application
        try {
            URL url = new URL("http://localhost:8080/score?point=" + points); // Adjust the URL if needed
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            connection.disconnect();
        } catch (Exception e) {
            System.err.println("Error while sending score to ScoringSystem: " + e.getMessage());
        }
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.isAssignableFrom(entity.getClass())) {
                    result.add(entity);
                    break;
                }
            }
        }
        return result;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    public GameScore getGameScore() {
        return gameScore;
    }
}