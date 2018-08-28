package com.grasset.dao.room;

import com.grasset.book.Book;
import com.grasset.dao.GenericDAO;
import com.grasset.reservation.Room;

public interface RoomDAO extends GenericDAO<Room> {

    void createRooms();
    void createRoomPeriod();

}
