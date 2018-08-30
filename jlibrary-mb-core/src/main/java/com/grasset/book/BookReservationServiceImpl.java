package com.grasset.book;

import com.grasset.client.Client;
import com.grasset.dao.book.BookDAO;
import com.grasset.dao.book.BookReservationDAO;
import com.grasset.dao.book.impl.BookDAOImpl;
import com.grasset.dao.book.impl.BookReservationDAOImpl;
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
    public BookReservation getById(Integer idBookReservation) {
        return bookReservationDAO.find(idBookReservation);
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
    public Set<BookReservation> getAllReserved(BookEdition bookEdition) {
        return bookReservationDAO.findAllReserved(bookEdition);
    }

    @Override
    public BookSample getFirstAvailable(BookEdition bookEdition) {
        return bookReservationDAO.findFirstAvailable(bookEdition);
    }

    @Override
    public boolean reserve(BookEdition bookEdition, Client client) throws Exception {
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
        Set<BookReservation> allReserved = getAllReserved(bookEdition);
        Integer totalReserved = allReserved.size();

        if (totalExistent > totalReserved) {
            BookSample bookSample = getFirstAvailable(bookEdition);

            BookReservation bookReservation = new BookReservation();
            bookReservation.setBookSample(bookSample);
            bookReservation.setClient(client);
            bookReservation.setReservationStatus(BookReservationStatus.PENDING);

            bookReservationDAO.persist(bookReservation);
        } else {
            throw new InvalidActionException("Il n'y a plus de livres disponibles.");
        }

        return true;
    }

    @Override
    public boolean cancel(BookReservation bookReservation) throws InvalidActionException {
        bookReservation.setReservationStatus(BookReservationStatus.CANCELED);
        bookReservationDAO.merge(bookReservation);
        return true;
    }

    @Override
    public boolean finnish(BookReservation bookReservation) throws InvalidActionException {
        bookReservation.setReservationStatus(BookReservationStatus.COMPLETED);
        bookReservationDAO.merge(bookReservation);
        return true;
    }

    @Override
    public boolean renew(BookReservation bookReservation) throws InvalidActionException {
        bookReservation.setReservationStatus(BookReservationStatus.DEFERRED);
        bookReservationDAO.merge(bookReservation);
        return true;
    }

    @Override
    public Set<BookReservation> getAll() {
        return bookReservationDAO.findAll();
    }
}
