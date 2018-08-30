package com.grasset.book;

import com.grasset.client.Client;
import com.grasset.dao.book.BookDAO;
import com.grasset.dao.book.BookReservationDAO;
import com.grasset.dao.book.impl.BookDAOImpl;
import com.grasset.dao.book.impl.BookReservationDAOImpl;
import com.grasset.exception.DBException;
import com.grasset.exception.InvalidActionException;
import com.grasset.reservation.BookReservation;
import com.grasset.reservation.BookReservationStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class BookReservationServiceImpl implements BookReservationService {

    private BookDAO bookDAO;
    private BookReservationDAO bookReservationDAO;

    public BookReservationServiceImpl() {
        bookDAO = new BookDAOImpl();
        bookReservationDAO = new BookReservationDAOImpl();
    }

    @Override
    public Set<BookReservation> getAll(Client client) {
        return bookReservationDAO.findAll(client);
    }

    @Override
    public BookReservation getActive(BookSample bookSample, Client client) {
        return bookReservationDAO.findActive(bookSample, client);
    }

    @Override
    public Set<BookReservation> getAllActives(BookEdition bookEdition) {
        return bookReservationDAO.findAllActives(bookEdition);
    }

    @Override
    public BookSample getFirstAvailable(BookEdition bookEdition) {
        return bookReservationDAO.findFirstAvailable(bookEdition);
    }

    @Override
    public boolean reserve(BookEdition bookEdition, Client client) throws InvalidActionException {
        int count = 0;
        Set<BookReservation> allByClient = getAll(client);
        for (BookReservation bookReservation : allByClient) {
            if (bookReservation.getReservationStatus().equals(BookReservationStatus.COMPLETED) || bookReservation.getReservationStatus().equals(BookReservationStatus.DEFERRED)) {
                count++;
            }
            if (count == 4) {
                throw new InvalidActionException("Attention L'utilisateur a déjà loué 4 livres");
            }
        }

        Integer totalExistent = bookEdition.getTotalSamples();
        Set<BookReservation> allActives = getAllActives(bookEdition);
        Integer totalActives = allActives.size();

        if (totalExistent >= totalActives) {
            BookSample bookSample = getFirstAvailable(bookEdition);

            BookReservation bookReservation = new BookReservation();
            bookReservation.setBookSample(bookSample);
            bookReservation.setClient(client);
            bookReservation.setReservationStatus(BookReservationStatus.PENDING);

            bookReservationDAO.persist(bookReservation);
        } else {
            // ENTRAR NA FILA?
        }

        return true;
    }
}
