package com.grasset.dao.book;

import com.grasset.book.*;
import com.grasset.dao.GenericDAO;

import java.util.Set;

public interface BookDAO extends GenericDAO<Book> {

    void createBookWaitingListStatus();

    Book find(String title);
    Book findByISBN(String ISBN);
    BookSample findByCode(String codeSample);
    Book findByIdBook(Integer idBook);
    Set<Book> findByEdition(Integer idEdition);
    Publisher findPublisher(String name);
    Author findAuthor(String name);

    Set<Author> findAllAuthor(BookEdition bookEdition);

    boolean removeAuthors(BookEdition bookEdition);

    boolean persist(Book book);
    boolean persist(BookEdition bookEdition);
    boolean persist(BookSample bookSample);
    boolean persist(Publisher publisher);
    boolean persist(Author author);

    boolean persist(BookEdition bookEdition, Author author);

    boolean merge(BookEdition bookEdition);

    boolean remove(BookSample bookSample);
    boolean remove(BookEdition bookEdition);

    boolean exists(BookEdition bookEdition, Author author);

    Integer countSamples(BookEdition bookEdition);
}
