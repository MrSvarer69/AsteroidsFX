package dk.sdu.mmmi.cbse.common.util;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.components.PositionComponent;

public class BoundaryHandler {

    public static void wrapAround(PositionComponent position, GameData gameData) {
        float x = position.getX();
        float y = position.getY();
        float screenWidth = gameData.getDisplayWidth();
        float screenHeight = gameData.getDisplayHeight();

        if (x < 0) {
            position.setX(screenWidth);
        } else if (x > screenWidth) {
            position.setX(0);
        }

        if (y < 0) {
            position.setY(screenHeight);
        } else if (y > screenHeight) {
            position.setY(0);
        }
    }
}