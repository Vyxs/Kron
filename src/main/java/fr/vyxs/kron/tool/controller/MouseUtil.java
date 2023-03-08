package fr.vyxs.kron.tool.controller;

public class MouseUtil {

    private MouseUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isMouseAboveComponent(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, width, height);
    }

    public static boolean isMouseOver(int mouseX, int mouseY, int x, int y, int size) {
        return isMouseOver(mouseX, mouseY, x, y, size, size);
    }

    public static boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return (mouseX >= x && mouseX <= x + width) && (mouseY >= y && mouseY <= y + height);
    }
}
