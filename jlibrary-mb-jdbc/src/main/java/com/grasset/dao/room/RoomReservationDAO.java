package com.grasset.dao.room;

import com.grasset.dao.GenericDAO;
import com.grasset.reservation.Room;
import com.grasset.reservation.RoomReservation;

public interface RoomReservationDAO extends GenericDAO<RoomReservation> {

    void createRoomReservationStatus();

}
