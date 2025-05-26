package dk.sdu.mmmi.cbse.common.data.components;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class TimeComponent implements EntityComponent {
    private final float expiration;
    private float elapsedTime = 0;

    public TimeComponent(float expiration) {
        this.expiration = expiration;
    }

    public void process(GameData gameData, Entity entity) {
        elapsedTime += gameData.getDelta();
    }

    public boolean isCooldownReady() {
        return elapsedTime >= expiration;
    }

    public boolean tryUseCooldown() {
        if (isCooldownReady()) {
            reset();
            return true;
        }
        return false;
    }

    public void reset() {
        elapsedTime = 0;
    }
}