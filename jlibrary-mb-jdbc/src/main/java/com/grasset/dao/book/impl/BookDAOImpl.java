package com.grasset.dao.book.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.grasset.book.Book;
import com.grasset.dao.book.BookDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookDAOImpl implements BookDAO {

	@Override
	public Book find(Integer idEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Book> findAll() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM BOOK");
		
		try(Connection connection = ConnectionFactory.getDBConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
			
			ResultSet rs = preparedStatement.executeQuery();
			
			Set<Book> resultList = new HashSet<>();
			
			while(rs.next()) {
				Book book = new Book();
				
				book.setIdBook(rs.getInt("ID_BOOK"));
				book.setTitle(rs.getString("NM_TITLE"));
				book.setBookYear(rs.getInt("NR_YEAR"));
				book.setOriginalLanguage(rs.getString("DS_LANGUAGE"));
				book.setCreationDate(rs.getTimestamp("DT_CREATION"));
				book.setModificationDate(rs.getTimestamp("DT_MODIFICATION"));
				
				resultList.add(book);
			}
			
			log.info("Result query: {}", resultList);
			return resultList;
			
		} catch (SQLException e) {
			log.error("Error to execute query: ", e);
            throw new DBException(e);
		} catch (ClassNotFoundException e) {
			throw new DBException(e);
		}
	}

	@Override
	public boolean persist(Book entity) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO BOOK (NM_TITLE, NR_YEAR, DS_LANGUADE, DT_CREATION)");
		query.append("VALUES (?, ?, ?, ?");
		
		try(Connection connection = ConnectionFactory.getDBConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
			
			preparedStatement.setString(1, entity.getTitle());
			preparedStatement.setInt(2, entity.getBookYear());
			preparedStatement.setString(3, entity.getOriginalLanguage());
			preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
			
			log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();


            log.info("{} persisted successfully.", entity);
            return true;
		} catch (SQLException e) {
			log.error("Error to execute query: ", e);
            throw new DBException(e);
		} catch (ClassNotFoundException e) {
			throw new DBException(e);
		}
	}

	@Override
	public boolean merge(Book entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Integer idEntity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Book entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
