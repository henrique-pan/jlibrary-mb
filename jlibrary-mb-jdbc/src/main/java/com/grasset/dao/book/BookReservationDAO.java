package com.grasset.dao.book;

import com.grasset.dao.GenericDAO;
import com.grasset.reservation.BookReservation;

public interface BookReservationDAO extends GenericDAO<BookReservation> {

    void createBookReservationStatus();

}
