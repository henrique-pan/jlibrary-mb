package com.grasset.dao.book.impl;

import com.grasset.book.Book;
import com.grasset.dao.book.BookDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.reservation.BookWaitingListStatus;
import com.grasset.user.SystemUserType;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Slf4j
public class BookDAOImpl implements BookDAO {

    @Override
    public Book find(Integer idEntity) {
        return null;
    }

    @Override
    public Set<Book> findAll() {
        return null;
    }

    @Override
    public boolean persist(Book entity) {
        return false;
    }

    @Override
    public boolean merge(Book entity) {
        return false;
    }

    @Override
    public boolean remove(Integer idEntity) {
        return false;
    }

    @Override
    public boolean remove(Book entity) {
        return false;
    }

    // BOOT UP LOAD
    private boolean exists(BookWaitingListStatus bookWaitingListStatus) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_WAITING_LIST_STATUS ");
        query.append("WHERE ID_BOOK_WAITING_LIST_STATUS = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookWaitingListStatus.getIdBookWaitingListStatus());

            log.info("Executing query: \n\t{}", preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) return true;

        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }

        return false;
    }

    private boolean persist(BookWaitingListStatus bookWaitingListStatus) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BOOK_WAITING_LIST_STATUS(ID_BOOK_WAITING_LIST_STATUS, DS_STATUS) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookWaitingListStatus.getIdBookWaitingListStatus());
            preparedStatement.setString(2, bookWaitingListStatus.getStatus());


            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", bookWaitingListStatus);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createBookWaitingListStatus() {
        for(BookWaitingListStatus status : BookWaitingListStatus.values()) {
            if(!exists(status)) {
                persist(status);
            }
        }
    }
}
