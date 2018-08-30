package com.grasset.dao.book;

import com.grasset.book.BookEdition;
import com.grasset.book.BookSample;
import com.grasset.client.Client;
import com.grasset.dao.GenericDAO;
import com.grasset.reservation.BookReservation;

import java.util.Set;

public interface BookReservationDAO extends GenericDAO<BookReservation> {

    Set<BookReservation> findAll(Client client);
    BookReservation findActive(BookSample bookSample, Client client);
    Set<BookReservation> findAllActives(BookEdition bookEdition);
    BookSample findFirstAvailable(BookEdition bookEdition);

    void createBookReservationStatus();

}
