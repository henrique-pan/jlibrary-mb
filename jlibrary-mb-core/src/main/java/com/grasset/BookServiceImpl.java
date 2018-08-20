package com.grasset;

import java.util.HashSet;
import java.util.Set;

import com.grasset.book.Book;
import com.grasset.dao.book.BookDAO;
import com.grasset.dao.book.impl.BookDAOImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookServiceImpl implements BookService {
	
	private BookDAO bookDAO;
	
	public BookServiceImpl() {
		bookDAO = new BookDAOImpl();
	}

	@Override
	public int save(Book entity) {
		log.info("Start Save Process");
		
		bookDAO.persist(entity);
		
		//PEGAR O ULTIMO ID CRIADO PARA RETORNAR O ID - INACABADO
		Set<Book> books = new HashSet<>(bookDAO.findAll());
		int ultimoId = 0;
//		books.forEach(book -> {
//			if (book.getIdBook() > ultimoId) ultimoId = book.getIdBook(); });
		
		
		return ultimoId;
	}

}
