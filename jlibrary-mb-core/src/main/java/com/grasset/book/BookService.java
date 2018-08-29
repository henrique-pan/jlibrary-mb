package com.grasset.book;

import java.util.Set;

public interface BookService {

    Book getBook(String ISBN) throws Exception;

    void save(Book book) throws Exception;

    void delete(Book book) throws Exception;

    Set<Book> getBooks() throws Exception;

}
