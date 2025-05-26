package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window){
        List<IGamePluginService> gamePluginServices = StreamSupport
                .stream(ServiceLoader.load(IGamePluginService.class).spliterator(), false)
                .collect(Collectors.toList());

        List<IEntityProcessingService> entityProcessingServices = StreamSupport
                .stream(ServiceLoader.load(IEntityProcessingService.class).spliterator(), false)
                .collect(Collectors.toList());

        List<IPostEntityProcessingService> postEntityProcessingServices = StreamSupport
                .stream(ServiceLoader.load(IPostEntityProcessingService.class).spliterator(), false)
                .collect(Collectors.toList());

        BulletSPI bulletSPI = ServiceLoader.load(BulletSPI.class)
                .findFirst()
                .orElse(null);

        Game game = new Game(gamePluginServices, entityProcessingServices, postEntityProcessingServices);
        game.setBulletService(bulletSPI);

        Scene scene = new Scene(game.getCanvas(), game.getGameData().getDisplayWidth(), game.getGameData().getDisplayHeight());
        window.setScene(scene);
        window.sizeToScene();

        double titleBarHeight = window.getHeight() - scene.getHeight();
        window.setHeight(game.getGameData().getDisplayHeight() + titleBarHeight);

        window.setTitle("Asteroids Game");
        window.show();

        game.start(window);
    }
}