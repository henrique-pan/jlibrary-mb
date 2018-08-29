package com.grasset.book;

import com.grasset.client.Address;
import com.grasset.client.Client;
import com.grasset.client.ClientService;
import com.grasset.dao.book.BookDAO;
import com.grasset.dao.book.impl.BookDAOImpl;
import com.grasset.dao.client.ClientDAO;
import com.grasset.dao.client.impl.ClientDAOImpl;
import com.grasset.dao.user.SystemUserDAO;
import com.grasset.dao.user.impl.SystemUserDAOImpl;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserService;
import com.grasset.user.SystemUserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class BookServiceImpl implements BookService {

    private BookDAO bookDAO;

    public BookServiceImpl() {
        bookDAO = new BookDAOImpl();
    }

    @Override
    public Book getBook(String ISBN) throws Exception {
        return bookDAO.findByISBN(ISBN);
    }

    @Override
    public void save(Book book) throws Exception {

        Book existentBook = bookDAO.find(book.getTitle());

        // BOOK
        if (existentBook == null) {
            bookDAO.persist(book);
            existentBook = bookDAO.find(book.getTitle());
        }
        book.setIdBook(existentBook.getIdBook());

        BookEdition bookEdition = (BookEdition) book;
        Publisher existentPublisher = bookDAO.findPublisher(bookEdition.getPublisher().getName());

        if (existentPublisher == null) {
            bookDAO.persist(bookEdition.getPublisher());
            existentPublisher = bookDAO.findPublisher(bookEdition.getPublisher().getName());
        }
        bookEdition.getPublisher().setIdPublisher(existentPublisher.getIdPublisher());

        // BOOK_EDITION
        BookEdition existentBookEdition = (BookEdition) bookDAO.findByISBN(bookEdition.getISBN());
        if (existentBookEdition == null) {
            bookDAO.persist(bookEdition);
        }

        /* AUTHOR
        Set<Author> authors = bookEdition.getAuthors();
        for (Author author : authors) {
            bookDAO.persist(author);
        }
        */
    }

    @Override
    public void delete(Book book) throws Exception {

    }

    @Override
    public Set<Book> getBooks() throws Exception {
        return bookDAO.findAll();
    }
}
