package com.grasset.reservation;

import lombok.Getter;

@Getter
public enum RoomPeriod {

    MORNING(1, "07:00 - 12:00"), AFTERNOON(1, "12:00 - 17:00"), NIGHT(3, "17:00 - 22:00");

    RoomPeriod(Integer idRoomPeriod, String description) {
        this.idRoomPeriod = idRoomPeriod;
        this.description = description;
    }

    private Integer idRoomPeriod;
    private String description;

    public static RoomPeriod getDescription(Integer idRoomPeriod) {
        switch (idRoomPeriod) {
            case 1: return MORNING;
            case 2: return AFTERNOON;
            case 3: return NIGHT;
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoomPeriod [");
        sb.append("idRoomPeriod = ").append(idRoomPeriod);
        sb.append(", description = ").append(description);
        sb.append(']');
        return sb.toString();
    }

}
