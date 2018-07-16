package com.grasset.dao.client.impl;

import com.grasset.client.Address;
import com.grasset.client.Client;
import com.grasset.dao.client.ClientDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.user.SystemUserType;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                client.setId(rs.getString("c.DS_ID"));
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
                client.setId(rs.getString("c.DS_ID"));
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
                client.setId(rs.getString("c.DS_ID"));
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
        return false;
    }

    @Override
    public boolean merge(Client entity) {
        return false;
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
