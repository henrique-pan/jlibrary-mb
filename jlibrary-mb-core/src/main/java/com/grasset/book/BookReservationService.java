package com.grasset.book;

import com.grasset.client.Client;
import com.grasset.exception.InvalidActionException;
import com.grasset.reservation.BookReservation;

import java.util.Set;

public interface BookReservationService {

    BookReservation getById(Integer idBookReservation);
    Set<BookReservation> getAll(Client client);
    BookReservation getActive(BookSample bookSample, Client client);
    Set<BookReservation> getAllActives(BookEdition bookEdition);
    BookSample getFirstAvailable(BookEdition bookEdition);
    boolean reserve(BookEdition bookEdition, Client client) throws Exception;
    boolean cancel(BookReservation bookReservation) throws InvalidActionException;

}
