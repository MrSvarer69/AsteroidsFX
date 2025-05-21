package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.ServiceLocator;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        // Load plugin services using ServiceLocator
        List<IGamePluginService> gamePluginServices = ServiceLocator.INSTANCE.locateAll(IGamePluginService.class);
        List<IEntityProcessingService> entityProcessingServices = ServiceLocator.INSTANCE.locateAll(IEntityProcessingService.class);
        List<IPostEntityProcessingService> postEntityProcessingServices = ServiceLocator.INSTANCE.locateAll(IPostEntityProcessingService.class);

        // Initialize the game
        Game game = new Game(gamePluginServices, entityProcessingServices, postEntityProcessingServices);

        // Create the scene with the exact game display size
        Scene scene = new Scene(game.getCanvas(), game.getGameData().getDisplayWidth(), game.getGameData().getDisplayHeight());
        window.setScene(scene);

        // Adjust the stage size to fit the scene
        window.sizeToScene();

        // Correct the height by subtracting the title bar height
        double titleBarHeight = window.getHeight() - scene.getHeight();
        window.setHeight(game.getGameData().getDisplayHeight() + titleBarHeight);

        window.setTitle("Asteroids Game");
        window.show();

        // Start the game loop
        game.start(window);
    }
}