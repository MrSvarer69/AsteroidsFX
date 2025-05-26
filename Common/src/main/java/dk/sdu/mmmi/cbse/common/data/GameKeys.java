package dk.sdu.mmmi.cbse.common.data;

public class GameKeys {

    private static boolean[] keys;
    private static boolean[] pkeys;

    private static final int NUM_KEYS = 12;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int ENTER = 4;
    public static final int ESCAPE = 5;
    public static final int SPACE = 6;
    public static final int SHIFT = 7;
    public static final int W = 8;
    public static final int A = 9;
    public static final int S = 10;
    public static final int D = 11;

    public GameKeys() {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    public void setKey(int k, boolean b) {
        if (k >= 0 && k < keys.length) {
            keys[k] = b;
        }
    }

    public boolean isDown(int key) {
        boolean state = key >= 0 && key < keys.length && keys[key];
        return state;
    }

    public boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }

}
