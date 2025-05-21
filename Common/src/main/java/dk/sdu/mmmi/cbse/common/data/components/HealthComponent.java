package dk.sdu.mmmi.cbse.common.data.components;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.components.EntityComponent;

public class HealthComponent implements EntityComponent {

    private int health;
    private boolean wasDamaged;

    public HealthComponent(int initialHealth) {
        this.health = initialHealth;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int amount) {
        if (amount > 0) {
            health -= amount;
            wasDamaged = true;
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean wasDamagedThisFrame() {
        return wasDamaged;
    }

    public void resetDamageFlag() {
        wasDamaged = false;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        resetDamageFlag();
    }
}
