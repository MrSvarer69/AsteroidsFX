package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.List;

public class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;
    private BulletSPI bulletService;

    public GameData getGameData() {
        return gameData;
    }

    public Pane getCanvas() {
        return new Pane(); // Return a new Pane instance to avoid reuse issues
    }

    public void setBulletService(BulletSPI bulletService) {
        this.bulletService = bulletService;
    }

    public Game(List<IGamePluginService> gamePluginServices,
                List<IEntityProcessingService> entityProcessingServiceList,
                List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    public void start(Stage window) throws Exception {
        Pane gameWindow = new Pane();
        Text scoreText = new Text(10, 20, "Destroyed asteroids: 0");
        scoreText.setFill(Color.WHITE); // Set text color to white for visibility
        scoreText.setStyle("-fx-font-size: 20;"); // Optional: Set font size
        gameWindow.getChildren().add(scoreText);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> gameData.getKeys().setKey(GameKeys.UP, true);
                case LEFT -> gameData.getKeys().setKey(GameKeys.LEFT, true);
                case RIGHT -> gameData.getKeys().setKey(GameKeys.RIGHT, true);
                case DOWN -> gameData.getKeys().setKey(GameKeys.DOWN, true);
                case W -> gameData.getKeys().setKey(GameKeys.W, true);
                case A -> gameData.getKeys().setKey(GameKeys.A, true);
                case S -> gameData.getKeys().setKey(GameKeys.S, true);
                case D -> gameData.getKeys().setKey(GameKeys.D, true);
                case SPACE -> gameData.getKeys().setKey(GameKeys.SPACE, true);
                case SHIFT -> gameData.getKeys().setKey(GameKeys.SHIFT, true);
                case ENTER -> gameData.getKeys().setKey(GameKeys.ENTER, true);
                case ESCAPE -> gameData.getKeys().setKey(GameKeys.ESCAPE, true);
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP -> gameData.getKeys().setKey(GameKeys.UP, false);
                case LEFT -> gameData.getKeys().setKey(GameKeys.LEFT, false);
                case RIGHT -> gameData.getKeys().setKey(GameKeys.RIGHT, false);
                case DOWN -> gameData.getKeys().setKey(GameKeys.DOWN, false);
                case W -> gameData.getKeys().setKey(GameKeys.W, false);
                case A -> gameData.getKeys().setKey(GameKeys.A, false);
                case S -> gameData.getKeys().setKey(GameKeys.S, false);
                case D -> gameData.getKeys().setKey(GameKeys.D, false);
                case SPACE -> gameData.getKeys().setKey(GameKeys.SPACE, false);
                case SHIFT -> gameData.getKeys().setKey(GameKeys.SHIFT, false);
                case ENTER -> gameData.getKeys().setKey(GameKeys.ENTER, false);
                case ESCAPE -> gameData.getKeys().setKey(GameKeys.ESCAPE, false);
            }
        });

        // Initialize game plugins
        for (IGamePluginService pluginService : gamePluginServices) {
            pluginService.start(gameData, world);
        }

        // Set up the stage and display
        window.setScene(scene);
        window.setTitle("Asteroids");
        window.setWidth(800);
        window.setHeight(800);
        window.show();
        gameWindow.setStyle("-fx-background-color: black;"); // Set background color to black

        // Start the game loop (AnimationTimer)
        new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                // Calculate delta time
                float delta = (now - lastTime) / 1_000_000_000.0f; // Convert nanoseconds to seconds
                gameData.setDelta(delta);
                lastTime = now;

                // Update game logic and render
                update(gameWindow);
                draw(gameWindow);
            }
        }.start();
    }

    private void update(Pane gameWindow) {
        // Update the score display
        Text scoreText = (Text) gameWindow.getChildren().get(0);
        scoreText.setText("Destroyed asteroids: " + world.getGameScore().getScore());

        // Process entities in the game world
        for (IEntityProcessingService processor : entityProcessingServiceList) {
            processor.process(gameData, world);
        }
        for (IPostEntityProcessingService postProcessor : postEntityProcessingServices) {
            postProcessor.process(gameData, world);
        }

        // Handle shooting bullets
        if (gameData.getKeys().isPressed(GameKeys.SPACE) && bulletService != null) {
            Entity player = world.getEntities().stream()
                    .filter(e -> e.getClass().getSimpleName().equals("SplitClass"))
                    .findFirst()
                    .orElse(null);

            if (player != null) {
                Entity bullet = bulletService.createBullet(player, gameData);
                if (bullet != null) {
                    world.addEntity(bullet);
                }
            }
        }
    }

    private void draw(Pane gameWindow) {
        gameWindow.getChildren().removeIf(node -> node instanceof Polygon);

        for (Entity entity : world.getEntities()) {

            float[] shapeX = entity.getShapeX();
            float[] shapeY = entity.getShapeY();

            Polygon polygon = new Polygon();
            for (int i = 0; i < shapeX.length; i++) {
                polygon.getPoints().addAll((double) shapeX[i], (double) shapeY[i]);
            }

            polygon.setStroke(Color.RED);
            polygon.setFill(null);
            polygon.setStrokeWidth(1.5);

            gameWindow.getChildren().add(polygon);
        }
    }
}