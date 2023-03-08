package fr.vyxs.kron.tool.type;

public class SharedInt {

    private int value;

    public SharedInt(int value) {
        this.value = value;
    }

    public static SharedInt of(int value) {
        return new SharedInt(value);
    }

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }

    public void add(int value) {
        this.value += value;
    }

    public void sub(int value) {
        this.value -= value;
    }
}
