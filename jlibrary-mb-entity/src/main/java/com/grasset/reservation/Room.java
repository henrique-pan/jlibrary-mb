package com.grasset.reservation;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Room {

    private Integer idRoom;
    private String codeRoom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(codeRoom, room.codeRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeRoom);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Room [");
        sb.append("idRoom = ").append(idRoom);
        sb.append(", codeRoom = ").append(codeRoom);
        sb.append(']');
        return sb.toString();
    }
}
