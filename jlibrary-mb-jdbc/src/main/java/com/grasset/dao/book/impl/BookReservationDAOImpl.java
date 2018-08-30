package com.grasset.dao.book.impl;

import com.grasset.book.Author;
import com.grasset.book.Book;
import com.grasset.book.BookEdition;
import com.grasset.book.BookSample;
import com.grasset.client.Client;
import com.grasset.dao.book.BookDAO;
import com.grasset.dao.book.BookReservationDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.reservation.BookReservation;
import com.grasset.reservation.BookReservationStatus;
import com.grasset.reservation.BookWaitingListStatus;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BookReservationDAOImpl implements BookReservationDAO {

    @Override
    public BookReservation find(Integer idEntity) {
        return null;
    }

    @Override
    public BookReservation findActive(BookSample bookSample, Client client) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_RESERVATION br ");
        query.append("WHERE br.ID_BOOK_SAMPLE = ? AND br.ID_CLIENT = ? AND ID_BOOK_RESERVATION_STATUS IN(2, 5) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookSample.getIdBookSample());
            preparedStatement.setInt(2, client.getIdClient());

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            BookReservation bookReservation = null;
            while (rs.next()) {
                bookReservation = new BookReservation();
                bookReservation.setIdBookReservation(rs.getInt("br.ID_BOOK_RESERVATION"));
                bookReservation.setBookSample(bookSample);
                bookReservation.setClient(client);
                Integer status = rs.getInt("br.ID_BOOK_RESERVATION_STATUS");
                BookReservationStatus reservationStatus = BookReservationStatus.getStatus(status);
                bookReservation.setReservationStatus(reservationStatus);
                bookReservation.setCreationDate(rs.getTimestamp("br.DT_CREATION"));
                bookReservation.setModificationDate(rs.getTimestamp("br.DT_MODIFICATION"));
            }

            log.info("Query Result: \n\t{}", bookReservation);
            return bookReservation;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<BookReservation> findAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_RESERVATION br ");
        query.append("INNER JOIN BOOK_SAMPLE bs ON(bs.ID_BOOK_SAMPLE = br.ID_BOOK_SAMPLE) ");
        query.append("INNER JOIN CLIENT c ON(c.ID_CLIENT = br.ID_CLIENT) ");
        query.append("ORDER BY br.DT_MODIFICATION DESC ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Set<BookReservation> set = new HashSet<>();
            while (rs.next()) {
                BookReservation bookReservation = new BookReservation();
                bookReservation.setIdBookReservation(rs.getInt("br.ID_BOOK_RESERVATION"));

                BookSample bookSample = new BookSample();
                bookSample.setIdBookSample(rs.getInt("bs.ID_BOOK_SAMPLE"));
                bookSample.setCodeSample(rs.getString("bs.CD_CODE"));
                bookSample.setCreationDate(rs.getTimestamp("bs.DT_CREATION"));
                bookSample.setModificationDate(rs.getTimestamp("bs.DT_MODIFICATION"));
                bookReservation.setBookSample(bookSample);

                Client client = new Client();
                client.setIdClient(rs.getInt("c.ID_CLIENT"));
                client.setName(rs.getString("c.NM_NAME"));
                client.setLastName(rs.getString("c.NM_LAST_NAME"));
                client.setPhoneNumber(rs.getString("c.DS_PHONE_NUMBER"));
                client.setEmail(rs.getString("c.DS_EMAIL"));
                bookReservation.setClient(client);

                Integer status = rs.getInt("br.ID_BOOK_RESERVATION_STATUS");
                BookReservationStatus reservationStatus = BookReservationStatus.getStatus(status);
                bookReservation.setReservationStatus(reservationStatus);
                bookReservation.setCreationDate(rs.getTimestamp("br.DT_CREATION"));
                bookReservation.setModificationDate(rs.getTimestamp("br.DT_MODIFICATION"));

                set.add(bookReservation);
                log.info("Query Result: \n\t{}", bookReservation);
            }


            return set;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<BookReservation> findAll(Client client) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_RESERVATION br ");
        query.append("INNER JOIN BOOK_SAMPLE bs ON(bs.ID_BOOK_SAMPLE = br.ID_BOOK_SAMPLE) ");
        query.append("INNER JOIN CLIENT c ON(c.ID_CLIENT = br.ID_CLIENT) ");
        query.append("WHERE c.ID_CLIENT = ? ");
        query.append("ORDER BY br.DT_MODIFICATION DESC ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, client.getIdClient());

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Set<BookReservation> set = new HashSet<>();
            while (rs.next()) {
                BookReservation bookReservation = new BookReservation();
                bookReservation.setIdBookReservation(rs.getInt("br.ID_BOOK_RESERVATION"));

                BookSample bookSample = new BookSample();
                bookSample.setIdBookSample(rs.getInt("bs.ID_BOOK_SAMPLE"));
                bookSample.setCodeSample(rs.getString("bs.CD_CODE"));
                bookSample.setCreationDate(rs.getTimestamp("bs.DT_CREATION"));
                bookSample.setModificationDate(rs.getTimestamp("bs.DT_MODIFICATION"));
                bookReservation.setBookSample(bookSample);

                bookReservation.setClient(client);

                Integer status = rs.getInt("br.ID_BOOK_RESERVATION_STATUS");
                BookReservationStatus reservationStatus = BookReservationStatus.getStatus(status);
                bookReservation.setReservationStatus(reservationStatus);
                bookReservation.setCreationDate(rs.getTimestamp("br.DT_CREATION"));
                bookReservation.setModificationDate(rs.getTimestamp("br.DT_MODIFICATION"));

                set.add(bookReservation);
                log.info("Query Result: \n\t{}", bookReservation);
            }


            return set;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<BookReservation> findAllActives(BookEdition bookEdition) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_RESERVATION br ");
        query.append("INNER JOIN BOOK_SAMPLE bs ON(bs.ID_BOOK_SAMPLE = br.ID_BOOK_SAMPLE) ");
        query.append("INNER JOIN CLIENT c ON(c.ID_CLIENT = br.ID_CLIENT) ");
        query.append("WHERE bs.ID_BOOK_EDITION = ? AND ID_BOOK_RESERVATION_STATUS NOT IN(2, 5) ");
        query.append("ORDER BY br.DT_MODIFICATION DESC ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Set<BookReservation> set = new HashSet<>();
            while (rs.next()) {
                BookReservation bookReservation = new BookReservation();
                bookReservation.setIdBookReservation(rs.getInt("br.ID_BOOK_RESERVATION"));

                BookSample bookSample = new BookSample();
                bookSample.setIdBookSample(rs.getInt("bs.ID_BOOK_SAMPLE"));
                bookSample.setCodeSample(rs.getString("bs.CD_CODE"));
                bookSample.setCreationDate(rs.getTimestamp("bs.DT_CREATION"));
                bookSample.setModificationDate(rs.getTimestamp("bs.DT_MODIFICATION"));
                bookReservation.setBookSample(bookSample);

                Client client = new Client();
                client.setIdClient(rs.getInt("c.ID_CLIENT"));
                client.setName(rs.getString("c.NM_NAME"));
                client.setLastName(rs.getString("c.NM_LAST_NAME"));
                client.setPhoneNumber(rs.getString("c.DS_PHONE_NUMBER"));
                client.setEmail(rs.getString("c.DS_EMAIL"));
                bookReservation.setClient(client);

                Integer status = rs.getInt("br.ID_BOOK_RESERVATION_STATUS");
                BookReservationStatus reservationStatus = BookReservationStatus.getStatus(status);
                bookReservation.setReservationStatus(reservationStatus);
                bookReservation.setCreationDate(rs.getTimestamp("br.DT_CREATION"));
                bookReservation.setModificationDate(rs.getTimestamp("br.DT_MODIFICATION"));

                set.add(bookReservation);
                log.info("Query Result: \n\t{}", bookReservation);
            }


            return set;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public BookSample findFirstAvailable(BookEdition bookEdition) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_SAMPLE bs ");
        query.append("WHERE bs.ID_BOOK_SAMPLE NOT IN( ");
        query.append("SELECT ID_BOOK_SAMPLE FROM BOOK_RESERVATION ");
        query.append("WHERE ID_BOOK_RESERVATION_STATUS NOT IN(2, 5)) AND bs.ID_BOOK_EDITION = ? ");
        query.append("LIMIT 1 ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            BookSample bookSample = null;
            while (rs.next()) {
                bookSample = new BookSample();
                bookSample.setIdBookSample(rs.getInt("bs.ID_BOOK_SAMPLE"));
                bookSample.setCodeSample(rs.getString("bs.CD_CODE"));
                bookSample.setCreationDate(rs.getTimestamp("bs.DT_CREATION"));
                bookSample.setModificationDate(rs.getTimestamp("bs.DT_MODIFICATION"));
            }

            log.info("Query Result: \n\t{}", bookSample);
            return bookSample;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean persist(BookReservation bookReservation) {
        log.info("Start persist process");

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BOOK_RESERVATION(ID_BOOK_SAMPLE, ID_CLIENT, ID_BOOK_RESERVATION_STATUS, DT_CREATION, DT_MODIFICATION) ");
        query.append("VALUES(?, ?, ?, ?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookReservation.getBookSample().getIdBookSample());
            preparedStatement.setInt(2, bookReservation.getClient().getIdClient());
            preparedStatement.setInt(3, BookReservationStatus.COMPLETED.getIdBookReservationStatus());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();


            log.info("{} persisted successfully.", bookReservation);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean merge(BookReservation bookReservation) {
        log.info("Start merge process");

        StringBuilder query = new StringBuilder();
        query.append("UPDATE BOOK_RESERVATION SET ID_BOOK_RESERVATION_STATUS = ?, DT_MODIFICATION = ? ");
        query.append("WHERE ID_BOOK_RESERVATION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookReservation.getReservationStatus().getIdBookReservationStatus());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
            preparedStatement.setInt(3, bookReservation.getIdBookReservation());

            log.info("Executing update: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} updated successfully.", bookReservation);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
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

            if (rs.next()) return true;

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
        for (BookReservationStatus status : BookReservationStatus.values()) {
            if (!exists(status)) {
                persist(status);
            }
        }
    }
}
