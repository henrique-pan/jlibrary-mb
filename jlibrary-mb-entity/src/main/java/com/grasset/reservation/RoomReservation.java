package com.grasset.reservation;

import com.grasset.book.BookEdition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class RoomReservation {

    private Integer idRoomReservation;
    private Room room;
    private BookEdition bookEdition;
    private Date reservationDate;
    private RoomPeriod roomPeriod;
    private RoomReservationStatus status;
    private Date creationDate;
    private Date modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomReservation that = (RoomReservation) o;
        return Objects.equals(room, that.room) &&
                Objects.equals(bookEdition, that.bookEdition) &&
                Objects.equals(reservationDate, that.reservationDate) &&
                roomPeriod == that.roomPeriod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, bookEdition, reservationDate, roomPeriod);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoomReservation [");
        sb.append("idRoomReservation = ").append(idRoomReservation);
        sb.append(", room = ").append(room);
        sb.append(", bookEdition = ").append(bookEdition);
        sb.append(", reservationDate = ").append(reservationDate);
        sb.append(", roomPeriod = ").append(roomPeriod);
        sb.append(", status = ").append(status);
        sb.append(", creationDate = ").append(creationDate);
        sb.append(", modificationDate = ").append(modificationDate);
        sb.append(']');
        return sb.toString();
    }
}
