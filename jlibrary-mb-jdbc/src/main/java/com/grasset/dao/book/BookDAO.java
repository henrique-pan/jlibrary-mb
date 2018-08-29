package com.grasset.dao.book;

import com.grasset.book.*;
import com.grasset.dao.GenericDAO;

public interface BookDAO extends GenericDAO<Book> {

    void createBookWaitingListStatus();

    Book find(String title);
    Book findByISBN(String ISBN);
    Book findByIdBook(Integer idBook);
    Book findByEdition(Integer idEdition);
    Publisher findPublisher(String name);
    Author findAuthor(String name);

    boolean persist(Book book);
    boolean persist(BookEdition bookEdition);
    boolean persist(BookSample bookSample);
    boolean persist(Publisher publisher);
    boolean persist(Author author);

}
