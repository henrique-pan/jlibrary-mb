package com.grasset.dao.book.impl;

import com.grasset.book.Book;
import com.grasset.dao.book.BookDAO;
import com.grasset.dao.book.BookReservationDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.reservation.BookReservation;
import com.grasset.reservation.BookReservationStatus;
import com.grasset.reservation.BookWaitingListStatus;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Slf4j
public class BookReservationDAOImpl implements BookReservationDAO {

    @Override
    public BookReservation find(Integer idEntity) {
        return null;
    }

    @Override
    public Set<BookReservation> findAll() {
        return null;
    }

    @Override
    public boolean persist(BookReservation entity) {
        return false;
    }

    @Override
    public boolean merge(BookReservation entity) {
        return false;
    }

    @Override
    public boolean remove(Integer idEntity) {
        return false;
    }

    @Override
    public boolean remove(BookReservation entity) {
        return false;
    }

    // BOOT UP LOAD
    private boolean exists(BookReservationStatus bookReservationStatus) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_RESERVATION_STATUS ");
        query.append("WHERE ID_BOOK_RESERVATION_STATUS = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookReservationStatus.getIdBookReservationStatus());

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

    private boolean persist(BookReservationStatus bookReservationStatus) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BOOK_RESERVATION_STATUS(ID_BOOK_RESERVATION_STATUS, DS_STATUS) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookReservationStatus.getIdBookReservationStatus());
            preparedStatement.setString(2, bookReservationStatus.getStatus());


            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", bookReservationStatus);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createBookReservationStatus() {
        for(BookReservationStatus status : BookReservationStatus.values()) {
            if(!exists(status)) {
                persist(status);
            }
        }
    }
}
