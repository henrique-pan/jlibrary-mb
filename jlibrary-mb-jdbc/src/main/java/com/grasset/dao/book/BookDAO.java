package com.grasset.dao.book;

import com.grasset.book.Book;
import com.grasset.dao.GenericDAO;

public interface BookDAO extends GenericDAO<Book> {

    void createBookWaitingListStatus();

}
