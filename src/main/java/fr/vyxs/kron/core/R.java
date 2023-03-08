package fr.vyxs.kron.core;

public class R {

    private R() {
        throw new IllegalStateException("Utility class");
    }

    public static class Lang {

        private Lang() {
            throw new IllegalStateException("Utility class");
        }

        public static final String GUI_ENERGY_CAPACITY = "gui.kron.energy_capacity";
        public static final String GUI_FLUID_CAPACITY = "gui.kron.fluid_capacity";
        public static final String GUI_FLUID_AMOUNT = "gui.kron.fluid_amount";
        public static final String CONTAINER_MINERAL_INFUSING_STATION = "container.mineral_infusing_station";
        public static final String CONTAINER_VINYL_TURNTABLE = "container.vinyl_turntable";
    }

    public static class Gui {

        private Gui() {
            throw new IllegalStateException("Utility class");
        }

        public static final String MINERAL_INFUSING_STATION = "textures/gui/mineral_infusing_station.png";
        public static final String VINYL_TURNTABLE = "textures/gui/vinyl_turntable.png";
    }
}
