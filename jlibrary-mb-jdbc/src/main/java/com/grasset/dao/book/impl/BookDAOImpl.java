package com.grasset.dao.book.impl;

import com.grasset.book.*;
import com.grasset.dao.book.BookDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.reservation.BookWaitingListStatus;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BookDAOImpl implements BookDAO {

    @Override
    public Book find(Integer idBook) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK b ");
        query.append("WHERE b.ID_BOOK = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idBook);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Book book = null;
            while (rs.next()) {
                book = new BookSample();
                book.setIdBook(rs.getInt("b.ID_BOOK"));
                book.setTitle(rs.getString("b.NM_TITLE"));
                book.setBookYear(rs.getInt("b.NR_YEAR"));
                book.setOriginalLanguage(rs.getString("b.DS_LANGUAGE"));
                book.setCreationDate(rs.getTimestamp("b.DT_CREATION"));
                book.setModificationDate(rs.getTimestamp("b.DT_MODIFICATION"));
            }

            log.info("Query Result: \n\t{}", book);
            return book;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Book find(String title) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK b ");
        query.append("WHERE b.NM_TITLE = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, title);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Book book = null;
            while (rs.next()) {
                book = new BookSample();
                book.setIdBook(rs.getInt("b.ID_BOOK"));
                book.setTitle(rs.getString("b.NM_TITLE"));
                book.setBookYear(rs.getInt("b.NR_YEAR"));
                book.setOriginalLanguage(rs.getString("b.DS_LANGUAGE"));
                book.setCreationDate(rs.getTimestamp("b.DT_CREATION"));
                book.setModificationDate(rs.getTimestamp("b.DT_MODIFICATION"));
            }

            log.info("Query Result: \n\t{}", book);
            return book;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<Book> findAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK b ");
        query.append("INNER JOIN BOOK_EDITION be ON(be.ID_BOOK = b.ID_BOOK) ");
        query.append("INNER JOIN PUBLISHER p ON(be.ID_PUBLISHER = p.ID_PUBLISHER) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Set<Book> set = new HashSet<>();
            while (rs.next()) {
                BookEdition bookEdition = new BookSample();
                bookEdition.setIdBook(rs.getInt("b.ID_BOOK"));
                bookEdition.setTitle(rs.getString("b.NM_TITLE"));
                bookEdition.setBookYear(rs.getInt("b.NR_YEAR"));
                bookEdition.setOriginalLanguage(rs.getString("b.DS_LANGUAGE"));
                bookEdition.setBookCreationDate(rs.getTimestamp("b.DT_CREATION"));
                bookEdition.setBookModificationDate(rs.getTimestamp("b.DT_MODIFICATION"));

                // EDITION
                bookEdition.setIdBookEdition(rs.getInt("be.ID_BOOK_EDITION"));

                Publisher publisher = new Publisher();
                publisher.setIdPublisher(rs.getInt("p.ID_PUBLISHER"));
                publisher.setName(rs.getString("p.NM_NAME"));
                publisher.setCreationDate(rs.getTimestamp("p.DT_CREATION"));
                publisher.setModificationDate(rs.getTimestamp("p.DT_MODIFICATION"));
                bookEdition.setPublisher(publisher);

                bookEdition.setISBN(rs.getString("be.CD_ISBN"));
                bookEdition.setEdition(rs.getString("be.DS_EDITION"));
                bookEdition.setEditionYear(rs.getInt("be.NR_YEAR"));
                bookEdition.setFormat(rs.getString("be.DS_FORMAT"));
                bookEdition.setTotalPages(rs.getInt("be.NR_TOTAL_PAGES"));
                bookEdition.setPenaltyPrice(rs.getDouble("be.VL_PENALTY_PRICE"));
                bookEdition.setBookPrice(rs.getDouble("be.VL_BOOK_PRICE"));
                bookEdition.setEditionLanguage(rs.getString("be.DS_LANGUAGE"));
                bookEdition.setRare(rs.getString("be.FL_RARE").equalsIgnoreCase("Y"));
                bookEdition.setCreationDate(rs.getTimestamp("be.DT_CREATION"));
                bookEdition.setModificationDate(rs.getTimestamp("be.DT_MODIFICATION"));

                set.add(bookEdition);
                log.info("Query Result: \n\t{}", bookEdition);
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
    public Set<Author> findAllAuthor(BookEdition bookEdition) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_EDITION_AUTHOR bea ");
        query.append("INNER JOIN AUTHOR a ON(a.ID_AUTHOR = bea.ID_AUTHOR) ");
        query.append("WHERE bea.ID_BOOK_EDITION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Set<Author> set = new HashSet<>();
            while (rs.next()) {
                Author author = new Author();
                author.setIdAuthor(rs.getInt("a.ID_AUTHOR"));
                author.setName(rs.getString("a.NM_NAME"));
                author.setCreationDate(rs.getTimestamp("a.DT_CREATION"));
                author.setModificationDate(rs.getTimestamp("a.DT_MODIFICATION"));

                set.add(author);
                log.info("Query Result: \n\t{}", bookEdition);
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
    public boolean persist(Book book) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BOOK(NM_TITLE, NR_YEAR, DS_LANGUAGE, DT_CREATION) ");
        query.append("VALUES(?, ?, ?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getBookYear());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));


            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", book);
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

    @Override
    public Book findByISBN(String ISBN) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK b ");
        query.append("INNER JOIN BOOK_EDITION be ON(be.ID_BOOK = b.ID_BOOK) ");
        query.append("INNER JOIN PUBLISHER p ON(be.ID_PUBLISHER = p.ID_PUBLISHER) ");
        query.append("WHERE be.CD_ISBN = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, ISBN);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            BookEdition bookEdition = null;
            while (rs.next()) {
                bookEdition = new BookSample();
                bookEdition.setIdBook(rs.getInt("b.ID_BOOK"));
                bookEdition.setTitle(rs.getString("b.NM_TITLE"));
                bookEdition.setBookYear(rs.getInt("b.NR_YEAR"));
                bookEdition.setOriginalLanguage(rs.getString("b.DS_LANGUAGE"));
                bookEdition.setBookCreationDate(rs.getTimestamp("b.DT_CREATION"));
                bookEdition.setBookModificationDate(rs.getTimestamp("b.DT_MODIFICATION"));

                // EDITION
                bookEdition.setIdBookEdition(rs.getInt("be.ID_BOOK_EDITION"));

                Publisher publisher = new Publisher();
                publisher.setIdPublisher(rs.getInt("p.ID_PUBLISHER"));
                publisher.setName(rs.getString("p.NM_NAME"));
                publisher.setCreationDate(rs.getTimestamp("p.DT_CREATION"));
                publisher.setModificationDate(rs.getTimestamp("p.DT_MODIFICATION"));
                bookEdition.setPublisher(publisher);

                bookEdition.setISBN(rs.getString("be.CD_ISBN"));
                bookEdition.setEdition(rs.getString("be.DS_EDITION"));
                bookEdition.setEditionYear(rs.getInt("be.NR_YEAR"));
                bookEdition.setFormat(rs.getString("be.DS_FORMAT"));
                bookEdition.setTotalPages(rs.getInt("be.NR_TOTAL_PAGES"));
                bookEdition.setPenaltyPrice(rs.getDouble("be.VL_PENALTY_PRICE"));
                bookEdition.setBookPrice(rs.getDouble("be.VL_BOOK_PRICE"));
                bookEdition.setEditionLanguage(rs.getString("be.DS_LANGUAGE"));
                bookEdition.setRare(rs.getString("be.FL_RARE").equalsIgnoreCase("Y"));
                bookEdition.setCreationDate(rs.getTimestamp("be.DT_CREATION"));
                bookEdition.setModificationDate(rs.getTimestamp("be.DT_MODIFICATION"));

            }

            log.info("Query Result: \n\t{}", bookEdition);
            return bookEdition;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Book findByIdBook(Integer idBook) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK b ");
        query.append("INNER JOIN BOOK_EDITION be ON(be.ID_BOOK = b.ID_BOOK) ");
        query.append("INNER JOIN PUBLISHER p ON(be.ID_PUBLISHER = p.ID_PUBLISHER) ");
        query.append("WHERE be.ID_BOOK = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idBook);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            BookEdition bookEdition = null;
            while (rs.next()) {
                bookEdition = new BookSample();
                bookEdition.setIdBook(rs.getInt("b.ID_BOOK"));
                bookEdition.setTitle(rs.getString("b.NM_TITLE"));
                bookEdition.setBookYear(rs.getInt("b.NR_YEAR"));
                bookEdition.setOriginalLanguage(rs.getString("b.DS_LANGUAGE"));
                bookEdition.setBookCreationDate(rs.getTimestamp("b.DT_CREATION"));
                bookEdition.setBookModificationDate(rs.getTimestamp("b.DT_MODIFICATION"));

                // EDITION
                bookEdition.setIdBookEdition(rs.getInt("be.ID_BOOK_EDITION"));

                Publisher publisher = new Publisher();
                publisher.setIdPublisher(rs.getInt("p.ID_PUBLISHER"));
                publisher.setName(rs.getString("p.NM_NAME"));
                publisher.setCreationDate(rs.getTimestamp("p.DT_CREATION"));
                publisher.setModificationDate(rs.getTimestamp("p.DT_MODIFICATION"));
                bookEdition.setPublisher(publisher);

                bookEdition.setISBN(rs.getString("be.CD_ISBN"));
                bookEdition.setEdition(rs.getString("be.DS_EDITION"));
                bookEdition.setEditionYear(rs.getInt("be.NR_YEAR"));
                bookEdition.setFormat(rs.getString("be.DS_FORMAT"));
                bookEdition.setTotalPages(rs.getInt("be.NR_TOTAL_PAGES"));
                bookEdition.setPenaltyPrice(rs.getDouble("be.VL_PENALTY_PRICE"));
                bookEdition.setBookPrice(rs.getDouble("be.VL_BOOK_PRICE"));
                bookEdition.setEditionLanguage(rs.getString("be.DS_LANGUAGE"));
                bookEdition.setRare(rs.getString("be.FL_RARE").equalsIgnoreCase("Y"));
                bookEdition.setCreationDate(rs.getTimestamp("be.DT_CREATION"));
                bookEdition.setModificationDate(rs.getTimestamp("be.DT_MODIFICATION"));

            }

            log.info("Query Result: \n\t{}", bookEdition);
            return bookEdition;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<Book> findByEdition(Integer idEdition) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK b ");
        query.append("INNER JOIN BOOK_EDITION be ON(be.ID_BOOK = b.ID_BOOK) ");
        query.append("INNER JOIN BOOK_SAMPLE bs ON(bs.ID_BOOK_EDITION = be.ID_BOOK_EDITION) ");
        query.append("INNER JOIN PUBLISHER p ON(be.ID_PUBLISHER = p.ID_PUBLISHER) ");
        query.append("WHERE be.ID_BOOK_EDITION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idEdition);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Set<Book> set = new HashSet<>();
            while (rs.next()) {
                BookSample bookSample = new BookSample();
                bookSample.setIdBook(rs.getInt("b.ID_BOOK"));
                bookSample.setTitle(rs.getString("b.NM_TITLE"));
                bookSample.setBookYear(rs.getInt("b.NR_YEAR"));
                bookSample.setOriginalLanguage(rs.getString("b.DS_LANGUAGE"));
                bookSample.setBookCreationDate(rs.getTimestamp("b.DT_CREATION"));
                bookSample.setBookModificationDate(rs.getTimestamp("b.DT_MODIFICATION"));

                // EDITION
                bookSample.setIdBookEdition(rs.getInt("be.ID_BOOK_EDITION"));

                Publisher publisher = new Publisher();
                publisher.setIdPublisher(rs.getInt("p.ID_PUBLISHER"));
                publisher.setName(rs.getString("p.NM_NAME"));
                publisher.setCreationDate(rs.getTimestamp("p.DT_CREATION"));
                publisher.setModificationDate(rs.getTimestamp("p.DT_MODIFICATION"));
                bookSample.setPublisher(publisher);

                bookSample.setISBN(rs.getString("be.CD_ISBN"));
                bookSample.setEdition(rs.getString("be.DS_EDITION"));
                bookSample.setEditionYear(rs.getInt("be.NR_YEAR"));
                bookSample.setFormat(rs.getString("be.DS_FORMAT"));
                bookSample.setTotalPages(rs.getInt("be.NR_TOTAL_PAGES"));
                bookSample.setPenaltyPrice(rs.getDouble("be.VL_PENALTY_PRICE"));
                bookSample.setBookPrice(rs.getDouble("be.VL_BOOK_PRICE"));
                bookSample.setEditionLanguage(rs.getString("be.DS_LANGUAGE"));
                bookSample.setRare(rs.getString("be.FL_RARE").equalsIgnoreCase("Y"));
                bookSample.setEditionCreationDate(rs.getTimestamp("be.DT_CREATION"));
                bookSample.setEditionModificationDate(rs.getTimestamp("be.DT_MODIFICATION"));

                // SAMPLE
                bookSample.setIdBookSample(rs.getInt("bs.ID_BOOK_SAMPLE"));
                bookSample.setCodeSample(rs.getString("bs.CD_CODE"));
                bookSample.setCreationDate(rs.getTimestamp("bs.DT_CREATION"));
                bookSample.setModificationDate(rs.getTimestamp("bs.DT_MODIFICATION"));

                set.add(bookSample);
            }

            log.info("Query Result: \n\t{}", set);
            return set;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public BookSample findByCode(String codeSample) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK b ");
        query.append("INNER JOIN BOOK_EDITION be ON(be.ID_BOOK = b.ID_BOOK) ");
        query.append("INNER JOIN BOOK_SAMPLE bs ON(bs.ID_BOOK_EDITION = be.ID_BOOK_EDITION) ");
        query.append("INNER JOIN PUBLISHER p ON(be.ID_PUBLISHER = p.ID_PUBLISHER) ");
        query.append("WHERE bs.CD_CODE = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, codeSample);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            BookSample bookSample = null;
            while (rs.next()) {
                bookSample = new BookSample();
                bookSample.setIdBook(rs.getInt("b.ID_BOOK"));
                bookSample.setTitle(rs.getString("b.NM_TITLE"));
                bookSample.setBookYear(rs.getInt("b.NR_YEAR"));
                bookSample.setOriginalLanguage(rs.getString("b.DS_LANGUAGE"));
                bookSample.setBookCreationDate(rs.getTimestamp("b.DT_CREATION"));
                bookSample.setBookModificationDate(rs.getTimestamp("b.DT_MODIFICATION"));

                // EDITION
                bookSample.setIdBookEdition(rs.getInt("be.ID_BOOK_EDITION"));

                Publisher publisher = new Publisher();
                publisher.setIdPublisher(rs.getInt("p.ID_PUBLISHER"));
                publisher.setName(rs.getString("p.NM_NAME"));
                publisher.setCreationDate(rs.getTimestamp("p.DT_CREATION"));
                publisher.setModificationDate(rs.getTimestamp("p.DT_MODIFICATION"));
                bookSample.setPublisher(publisher);

                bookSample.setISBN(rs.getString("be.CD_ISBN"));
                bookSample.setEdition(rs.getString("be.DS_EDITION"));
                bookSample.setEditionYear(rs.getInt("be.NR_YEAR"));
                bookSample.setFormat(rs.getString("be.DS_FORMAT"));
                bookSample.setTotalPages(rs.getInt("be.NR_TOTAL_PAGES"));
                bookSample.setPenaltyPrice(rs.getDouble("be.VL_PENALTY_PRICE"));
                bookSample.setBookPrice(rs.getDouble("be.VL_BOOK_PRICE"));
                bookSample.setEditionLanguage(rs.getString("be.DS_LANGUAGE"));
                bookSample.setRare(rs.getString("be.FL_RARE").equalsIgnoreCase("Y"));
                bookSample.setEditionCreationDate(rs.getTimestamp("be.DT_CREATION"));
                bookSample.setEditionModificationDate(rs.getTimestamp("be.DT_MODIFICATION"));

                // SAMPLE
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
    public Publisher findPublisher(String name) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM PUBLISHER p ");
        query.append("WHERE p.NM_NAME = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, name);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Publisher publisher = null;
            while (rs.next()) {
                publisher = new Publisher();
                publisher.setIdPublisher(rs.getInt("p.ID_PUBLISHER"));
                publisher.setName(rs.getString("p.NM_NAME"));
                publisher.setCreationDate(rs.getTimestamp("p.DT_CREATION"));
                publisher.setModificationDate(rs.getTimestamp("p.DT_MODIFICATION"));
            }

            log.info("Query Result: \n\t{}", publisher);
            return publisher;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Author findAuthor(String name) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM AUTHOR a ");
        query.append("WHERE a.NM_NAME = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, name);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Author author = null;
            while (rs.next()) {
                author = new Author();
                author.setIdAuthor(rs.getInt("a.ID_AUTHOR"));
                author.setName(rs.getString("a.NM_NAME"));
                author.setCreationDate(rs.getTimestamp("a.DT_CREATION"));
                author.setModificationDate(rs.getTimestamp("a.DT_MODIFICATION"));
            }

            log.info("Query Result: \n\t{}", author);
            return author;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean persist(BookEdition bookEdition) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BOOK_EDITION(ID_BOOK, ID_PUBLISHER, CD_ISBN, DS_EDITION, ");
        query.append("NR_YEAR, DS_FORMAT, NR_TOTAL_PAGES, VL_PENALTY_PRICE, VL_BOOK_PRICE, ");
        query.append("DS_LANGUAGE, FL_RARE, DT_CREATION) ");
        query.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBook());
            preparedStatement.setInt(2, bookEdition.getPublisher().getIdPublisher());
            preparedStatement.setString(3, bookEdition.getISBN());
            preparedStatement.setString(4, bookEdition.getEdition());
            preparedStatement.setInt(5, bookEdition.getEditionYear());
            preparedStatement.setString(6, bookEdition.getFormat());
            preparedStatement.setInt(7, bookEdition.getTotalPages());
            preparedStatement.setDouble(8, bookEdition.getPenaltyPrice());
            preparedStatement.setDouble(9, bookEdition.getBookPrice());
            preparedStatement.setString(10, bookEdition.getEditionLanguage());
            preparedStatement.setString(11, bookEdition.isRare() ? "Y" : "N");
            preparedStatement.setTimestamp(12, new Timestamp(new Date().getTime()));


            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", bookEdition);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean persist(BookEdition bookEdition, Author author) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BOOK_EDITION_AUTHOR(ID_BOOK_EDITION, ID_AUTHOR) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());
            preparedStatement.setInt(2, author.getIdAuthor());

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", bookEdition);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean exists(BookEdition bookEdition, Author author) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM BOOK_EDITION_AUTHOR ");
        query.append("WHERE ID_BOOK_EDITION = ? AND ID_AUTHOR = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());
            preparedStatement.setInt(2, author.getIdAuthor());

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


    @Override
    public boolean merge(BookEdition bookEdition) {
        log.info("Start merge process");

        StringBuilder query = new StringBuilder();
        query.append("UPDATE BOOK_EDITION SET ID_BOOK = ?, ID_PUBLISHER = ?, DS_EDITION = ?, ");
        query.append("NR_YEAR = ?, DS_FORMAT = ?, NR_TOTAL_PAGES = ?, VL_PENALTY_PRICE = ?,  ");
        query.append("VL_BOOK_PRICE = ?, DS_LANGUAGE = ?, FL_RARE = ?, DT_MODIFICATION = ?  ");
        query.append("WHERE ID_BOOK_EDITION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBook());
            preparedStatement.setInt(2, bookEdition.getPublisher().getIdPublisher());
            preparedStatement.setString(3, bookEdition.getEdition());
            preparedStatement.setInt(4, bookEdition.getEditionYear());
            preparedStatement.setString(5, bookEdition.getFormat());
            preparedStatement.setInt(6, bookEdition.getTotalPages());
            preparedStatement.setDouble(7, bookEdition.getPenaltyPrice());
            preparedStatement.setDouble(8, bookEdition.getBookPrice());
            preparedStatement.setString(9, bookEdition.getEditionLanguage());
            preparedStatement.setString(10, bookEdition.isRare() ? "Y" : "N");
            preparedStatement.setTimestamp(11, new Timestamp(new Date().getTime()));
            preparedStatement.setInt(12, bookEdition.getIdBookEdition());

            log.info("Executing update: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} updated successfully.", bookEdition);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean persist(BookSample bookSample) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BOOK_SAMPLE(ID_BOOK_EDITION, CD_CODE, DT_CREATION) ");
        query.append("VALUES(?, ?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookSample.getIdBookEdition());
            preparedStatement.setString(2, bookSample.getCodeSample());
            preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", bookSample);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean persist(Publisher publisher) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO PUBLISHER(NM_NAME, DT_CREATION) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", publisher);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean persist(Author author) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO AUTHOR(NM_NAME, DT_CREATION) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, author.getName());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", author);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Integer countSamples(BookEdition bookEdition) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*) as TOTAL_SAMPLE FROM BOOK_SAMPLE ");
        query.append("WHERE ID_BOOK_EDITION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());

            log.info("Executing query: \n\t{}", preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Integer result = 0;
            if(rs.next()) {
                result = rs.getInt("TOTAL_SAMPLE");
            }

            return result;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
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
    public boolean remove(BookSample bookSample) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM BOOK_SAMPLE WHERE ID_BOOK_SAMPLE = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookSample.getIdBookSample());

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} removed successfully.", bookSample);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean removeAuthors(BookEdition bookEdition) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM BOOK_EDITION_AUTHOR WHERE ID_BOOK_EDITION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} removed successfully.", bookEdition);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean remove(BookEdition bookEdition) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM BOOK_SAMPLE WHERE ID_BOOK_EDITION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} removed successfully.", bookEdition);
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }

        query = new StringBuilder();
        query.append("DELETE FROM BOOK_EDITION_AUTHOR WHERE ID_BOOK_EDITION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} removed successfully.", bookEdition);
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }

        query = new StringBuilder();
        query.append("DELETE FROM BOOK_EDITION WHERE ID_BOOK_EDITION = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, bookEdition.getIdBookEdition());

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} removed successfully.", bookEdition);
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
