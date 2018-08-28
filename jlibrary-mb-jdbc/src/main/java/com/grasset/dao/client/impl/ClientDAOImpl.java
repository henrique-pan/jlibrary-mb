package com.grasset.dao.client.impl;

import com.grasset.client.Address;
import com.grasset.client.Client;
import com.grasset.dao.client.ClientDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.user.SystemUserType;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClientDAOImpl implements ClientDAO {

    @Override
    public Client find(Integer idEntity) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM CLIENT c ");
        query.append("INNER JOIN SYSTEM_USER su ON(c.ID_SYSTEM_USER = su.ID_SYSTEM_USER) ");
        query.append("INNER JOIN ADDRESS a ON(c.ID_ADDRESS = a.ID_ADDRESS) ");
        query.append("WHERE c.ID_CLIENT = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idEntity);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Client client = null;
            while (rs.next()) {
                client = new Client();

                // System User
                client.setIdSystemUser(rs.getInt("su.ID_SYSTEM_USER"));
                client.setCode(rs.getString("su.CD_USER"));
                client.setPassword(rs.getString("su.DS_PASSWORD"));

                SystemUserType systemUserType = SystemUserType.getType(rs.getInt("su.ID_SYSTEM_USER"));
                client.setUserType(systemUserType);

                client.setActive(rs.getString("su.FL_ACTIVE").equalsIgnoreCase("Y"));
                client.setSystemUserCreationDate(rs.getTimestamp("su.DT_CREATION"));
                client.setSystemUserModificationDate(rs.getTimestamp("su.DT_MODIFICATION"));
                // System User
                // Client
                client.setIdClient(rs.getInt("c.ID_CLIENT"));
                client.setName(rs.getString("c.NM_NAME"));
                client.setLastName(rs.getString("c.NM_LAST_NAME"));
                client.setPhoneNumber(rs.getString("c.DS_PHONE_NUMBER"));
                client.setEmail(rs.getString("c.DS_EMAIL"));
                client.setCreationDate(rs.getTimestamp("c.DT_CREATION"));
                client.setModificationDate(rs.getTimestamp("c.DT_MODIFICATION"));
                // Client
                // Address
                Address address = new Address();
                address.setIdAddress(rs.getInt("a.ID_ADDRESS"));
                address.setAddress(rs.getString("a.DS_ADDRESS"));
                address.setCity(rs.getString("a.NM_CITY"));
                address.setState(rs.getString("a.NM_STATE"));
                address.setCountry(rs.getString("a.NM_COUNTRY"));
                address.setZipCode(rs.getString("a.DS_ZIP_CODE"));
                address.setAddressProof(rs.getString("a.DS_ADDRESS_PROOF"));
                address.setValid(rs.getString("a.FL_VALID").equalsIgnoreCase("Y"));
                address.setCreationDate(rs.getTimestamp("a.DT_CREATION"));
                address.setModificationDate(rs.getTimestamp("a.DT_MODIFICATION"));
                client.setAddress(address);
                // Address
            }

            log.info("Query Result: \n\t{}", client);
            return client;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Client findByIdSystemUser(Integer idSystemUser) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM CLIENT c ");
        query.append("INNER JOIN SYSTEM_USER su ON(c.ID_SYSTEM_USER = su.ID_SYSTEM_USER) ");
        query.append("INNER JOIN ADDRESS a ON(c.ID_ADDRESS = a.ID_ADDRESS) ");
        query.append("WHERE c.ID_SYSTEM_USER = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idSystemUser);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Client client = null;
            while (rs.next()) {
                client = new Client();

                // System User
                client.setIdSystemUser(rs.getInt("su.ID_SYSTEM_USER"));
                client.setCode(rs.getString("su.CD_USER"));
                client.setPassword(rs.getString("su.DS_PASSWORD"));

                SystemUserType systemUserType = SystemUserType.getType(rs.getInt("su.ID_SYSTEM_USER"));
                client.setUserType(systemUserType);

                client.setActive(rs.getString("su.FL_ACTIVE").equalsIgnoreCase("Y"));

                client.setCreationDate(rs.getTimestamp("su.DT_CREATION"));
                client.setModificationDate(rs.getTimestamp("su.DT_MODIFICATION"));
                // System User
                // Client
                client.setIdClient(rs.getInt("c.ID_CLIENT"));
                client.setName(rs.getString("c.NM_NAME"));
                client.setLastName(rs.getString("c.NM_LAST_NAME"));
                client.setPhoneNumber(rs.getString("c.DS_PHONE_NUMBER"));
                client.setEmail(rs.getString("c.DS_EMAIL"));
                // Client
                // Address
                Address address = new Address();
                address.setIdAddress(rs.getInt("a.ID_ADDRESS"));
                address.setAddress(rs.getString("a.DS_ADDRESS"));
                address.setCity(rs.getString("a.NM_CITY"));
                address.setState(rs.getString("a.NM_STATE"));
                address.setCountry(rs.getString("a.NM_COUNTRY"));
                address.setZipCode(rs.getString("a.DS_ZIP_CODE"));
                address.setAddressProof(rs.getString("a.DS_ADDRESS_PROOF"));
                address.setValid(rs.getString("a.FL_VALID").equalsIgnoreCase("Y"));
                address.setCreationDate(rs.getTimestamp("a.DT_CREATION"));
                address.setModificationDate(rs.getTimestamp("a.DT_MODIFICATION"));
                client.setAddress(address);
                // Address
            }

            log.info("Query Result: \n\t{}", client);
            return client;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<Client> findAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM CLIENT c ");
        query.append("INNER JOIN SYSTEM_USER su ON(c.ID_SYSTEM_USER = su.ID_SYSTEM_USER) ");
        query.append("INNER JOIN ADDRESS a ON(c.ID_ADDRESS = a.ID_ADDRESS) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Set<Client> resultList = new HashSet<>();

            while (rs.next()) {
                Client client = new Client();

                // System User
                client.setIdSystemUser(rs.getInt("su.ID_SYSTEM_USER"));
                client.setCode(rs.getString("su.CD_USER"));
                client.setPassword(rs.getString("su.DS_PASSWORD"));

                SystemUserType systemUserType = SystemUserType.getType(rs.getInt("su.ID_SYSTEM_USER"));
                client.setUserType(systemUserType);

                client.setActive(rs.getString("su.FL_ACTIVE").equalsIgnoreCase("Y"));

                client.setCreationDate(rs.getTimestamp("su.DT_CREATION"));
                client.setModificationDate(rs.getTimestamp("su.DT_MODIFICATION"));
                // System User
                // Client
                client.setIdClient(rs.getInt("c.ID_CLIENT"));
                client.setName(rs.getString("c.NM_NAME"));
                client.setLastName(rs.getString("c.NM_LAST_NAME"));
                client.setPhoneNumber(rs.getString("c.DS_PHONE_NUMBER"));
                client.setEmail(rs.getString("c.DS_EMAIL"));
                // Client
                // Address
                Address address = new Address();
                address.setIdAddress(rs.getInt("a.ID_ADDRESS"));
                address.setAddress(rs.getString("a.DS_ADDRESS"));
                address.setCity(rs.getString("a.NM_CITY"));
                address.setState(rs.getString("a.NM_STATE"));
                address.setCountry(rs.getString("a.NM_COUNTRY"));
                address.setZipCode(rs.getString("a.DS_ZIP_CODE"));
                address.setAddressProof(rs.getString("a.DS_ADDRESS_PROOF"));
                address.setValid(rs.getString("a.FL_VALID").equalsIgnoreCase("Y"));
                address.setCreationDate(rs.getTimestamp("a.DT_CREATION"));
                address.setModificationDate(rs.getTimestamp("a.DT_MODIFICATION"));
                client.setAddress(address);
                // Address

                resultList.add(client);
            }

            log.info("Query Result: \n\t{}", resultList);
            return resultList;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean persist(Client entity) {
        log.info("Start persist process");

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO CLIENT(NM_NAME, NM_LAST_NAME, DS_PHONE_NUMBER, DS_EMAIL, ID_ADDRESS, ID_SYSTEM_USER, DT_CREATION, DT_MODIFICATION) ");
        query.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPhoneNumber());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setInt(5, entity.getAddress().getIdAddress());
            preparedStatement.setInt(6, entity.getIdSystemUser());
            preparedStatement.setTimestamp(7, new Timestamp(new java.util.Date().getTime()));
            preparedStatement.setTimestamp(8, new Timestamp(new Date().getTime()));

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
    public Address findAddress(String zipCode) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ADDRESS a ");
        query.append("WHERE a.DS_ZIP_CODE = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, zipCode);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Address address = null;
            while (rs.next()) {
                address = new Address();
                address.setIdAddress(rs.getInt("a.ID_ADDRESS"));
                address.setAddress(rs.getString("a.DS_ADDRESS"));
                address.setCity(rs.getString("a.NM_CITY"));
                address.setState(rs.getString("a.NM_STATE"));
                address.setCountry(rs.getString("a.NM_COUNTRY"));
                address.setZipCode(rs.getString("a.DS_ZIP_CODE"));
                address.setAddressProof(rs.getString("a.DS_ADDRESS_PROOF"));
                address.setValid(rs.getString("a.FL_VALID").equalsIgnoreCase("Y"));
                address.setCreationDate(rs.getTimestamp("a.DT_CREATION"));
                address.setModificationDate(rs.getTimestamp("a.DT_MODIFICATION"));
                // Address
            }

            log.info("Query Result: \n\t{}", address);
            return address;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean persist(Address address) {
        log.info("Start persist process");

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ADDRESS(DS_ADDRESS, NM_CITY, NM_STATE, NM_COUNTRY, DS_ZIP_CODE, DS_ADDRESS_PROOF, FL_VALID, DT_CREATION, DT_MODIFICATION) ");
        query.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, address.getAddress());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getState());
            preparedStatement.setString(4, address.getCountry());
            preparedStatement.setString(5, address.getZipCode());
            preparedStatement.setString(6, address.getAddressProof());
            preparedStatement.setString(7, address.isValid() ? "Y" : "N");
            preparedStatement.setTimestamp(8, new Timestamp(new Date().getTime()));
            preparedStatement.setTimestamp(9, new Timestamp(new Date().getTime()));

            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();


            log.info("{} persisted successfully.", address);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean merge(Address address) {
        log.info("Start merge process");

        StringBuilder query = new StringBuilder();
        query.append("UPDATE ADDRESS SET DS_ADDRESS = ?, NM_CITY = ?, NM_STATE = ?, FL_VALID = ?, ");
        query.append("NM_COUNTRY = ?, DS_ZIP_CODE = ?, DS_ADDRESS_PROOF = ?, DT_MODIFICATION = ?  ");
        query.append("WHERE ID_ADDRESS = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, address.getAddress());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getState());
            preparedStatement.setString(4, address.isValid() ? "Y" : "N");
            preparedStatement.setString(5, address.getCountry());
            preparedStatement.setString(6, address.getZipCode());
            preparedStatement.setString(7, address.getAddressProof());
            preparedStatement.setTimestamp(8, new Timestamp(new Date().getTime()));
            preparedStatement.setInt(9, address.getIdAddress());

            log.info("Executing update: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} updated successfully.", address);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean merge(Client entity) {
        log.info("Start merge process");

        if (entity.getIdSystemUser() == null) new DBException("Error: idSystemUser not found.");

        StringBuilder query = new StringBuilder();
        query.append("UPDATE CLIENT SET NM_NAME = ?, NM_LAST_NAME = ?, ");
        query.append("DS_PHONE_NUMBER = ?, DS_EMAIL = ?, ID_ADDRESS = ?, DT_MODIFICATION = ?  ");
        query.append("WHERE ID_CLIENT = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPhoneNumber());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setInt(5, entity.getAddress().getIdAddress());
            preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
            preparedStatement.setInt(7, entity.getIdClient());

            log.info("Executing update: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} updated successfully.", entity);
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
    public boolean remove(Client entity) {
        return false;
    }
}
