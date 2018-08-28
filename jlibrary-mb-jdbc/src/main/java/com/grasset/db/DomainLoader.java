package com.grasset.db;

import com.grasset.dao.book.BookDAO;
import com.grasset.dao.book.BookReservationDAO;
import com.grasset.dao.book.impl.BookDAOImpl;
import com.grasset.dao.book.impl.BookReservationDAOImpl;
import com.grasset.dao.room.RoomDAO;
import com.grasset.dao.room.RoomReservationDAO;
import com.grasset.dao.room.impl.RoomDAOImpl;
import com.grasset.dao.room.impl.RoomReservationDAOImpl;
import com.grasset.dao.user.SystemUserDAO;
import com.grasset.dao.user.impl.SystemUserDAOImpl;

public class DomainLoader {

    private BookDAO bookDAO;
    private RoomDAO roomDAO;
    private SystemUserDAO systemUserDAO;
    private BookReservationDAO bookReservationDAO;
    private RoomReservationDAO roomReservationDAO;

    public DomainLoader() {
        roomDAO = new RoomDAOImpl();
        bookDAO = new BookDAOImpl();
        systemUserDAO = new SystemUserDAOImpl();
        bookReservationDAO = new BookReservationDAOImpl();
        roomReservationDAO = new RoomReservationDAOImpl();
    }


    public void loadDomainTables() {
        // SYSTEM_USER_TYPE
        loadSystemUserType();
        loadBookWaitingListStatus();
        loadBookReservationStatus();
        loadRoomReservationStatus();
        loadRoomPeriod();
        loadRooms();
        createAdmin();
    }

    private void loadSystemUserType() {
        systemUserDAO.createSystemUserTypes();
    }

    private void loadBookWaitingListStatus() {
        bookDAO.createBookWaitingListStatus();
    }

    private void loadBookReservationStatus() {
        bookReservationDAO.createBookReservationStatus();
    }

    private void loadRooms() {
        roomDAO.createRooms();
    }

    private void loadRoomPeriod() {
        roomDAO.createRoomPeriod();
    }

    private void loadRoomReservationStatus() {
        roomReservationDAO.createRoomReservationStatus();
    }

    private void createAdmin() {
        systemUserDAO.createAdmin();
    }

}
