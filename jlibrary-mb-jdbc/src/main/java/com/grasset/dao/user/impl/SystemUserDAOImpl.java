package com.grasset.dao.user.impl;

import com.grasset.dao.user.SystemUserDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.reservation.Room;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserType;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SystemUserDAOImpl implements SystemUserDAO {

    @Override
    public SystemUser find(Integer idEntity) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM SYSTEM_USER ");
        query.append("WHERE ID_SYSTEM_USER = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idEntity);

            log.info("Executing query: \n\t{}", preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            SystemUser systemUser = null;
            while (rs.next()) {
                systemUser = new SystemUser();
                systemUser.setIdSystemUser(rs.getInt("ID_SYSTEM_USER"));
                systemUser.setCode(rs.getString("CD_USER"));
                systemUser.setPassword(rs.getString("DS_PASSWORD"));

                SystemUserType systemUserType = SystemUserType.getType(rs.getInt("ID_SYSTEM_USER_TYPE"));
                systemUser.setUserType(systemUserType);

                systemUser.setActive(rs.getString("FL_ACTIVE").equalsIgnoreCase("Y"));

                systemUser.setCreationDate(rs.getTimestamp("DT_CREATION"));
                systemUser.setModificationDate(rs.getTimestamp("DT_MODIFICATION"));
            }

            log.info("Query Result: \n\t{}", systemUser);
            return systemUser;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public SystemUser findByCode(String code) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM SYSTEM_USER ");
        query.append("WHERE CD_USER = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, code);

            log.info("Executing query: \n\t{}", preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            SystemUser systemUser = null;
            while (rs.next()) {
                systemUser = new SystemUser();
                systemUser.setIdSystemUser(rs.getInt("ID_SYSTEM_USER"));
                systemUser.setCode(rs.getString("CD_USER"));
                systemUser.setPassword(rs.getString("DS_PASSWORD"));

                SystemUserType systemUserType = SystemUserType.getType(rs.getInt("ID_SYSTEM_USER_TYPE"));
                systemUser.setUserType(systemUserType);

                systemUser.setActive(rs.getString("FL_ACTIVE").equalsIgnoreCase("Y"));

                systemUser.setCreationDate(rs.getTimestamp("DT_CREATION"));
                systemUser.setModificationDate(rs.getTimestamp("DT_MODIFICATION"));
            }

            log.info("Query Result: \n\t{}", systemUser);
            return systemUser;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<SystemUser> findAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM SYSTEM_USER");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Set<SystemUser> resultList = new HashSet<>();

            while (rs.next()) {
                SystemUser systemUser = new SystemUser();
                systemUser.setIdSystemUser(rs.getInt("ID_SYSTEM_USER"));
                systemUser.setCode(rs.getString("CD_USER"));
                systemUser.setPassword(rs.getString("DS_PASSWORD"));

                SystemUserType systemUserType = SystemUserType.getType(rs.getInt("ID_SYSTEM_USER"));
                systemUser.setUserType(systemUserType);

                systemUser.setActive(rs.getString("FL_ACTIVE").equalsIgnoreCase("Y"));

                systemUser.setCreationDate(rs.getTimestamp("DT_CREATION"));
                systemUser.setModificationDate(rs.getTimestamp("DT_MODIFICATION"));

                resultList.add(systemUser);
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
    public boolean persist(SystemUser entity) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO SYSTEM_USER(CD_USER, DS_PASSWORD, ID_SYSTEM_USER_TYPE, FL_ACTIVE, DT_CREATION) ");
        query.append("VALUES(?, ?, ?, ?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getUserType().getIdSystemUserType());
            preparedStatement.setString(4, entity.isActive() ? "Y" : "N");
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));

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
    public boolean merge(SystemUser entity) {
        if(entity.getIdSystemUser() == null) new DBException("Error: idSystemUser not found.");

        StringBuilder query = new StringBuilder();
        query.append("UPDATE SYSTEM_USER SET CD_USER = ?, DS_PASSWORD = ?, ID_SYSTEM_USER_TYPE = ?, ");
        query.append("FL_ACTIVE = ?, DT_MODIFICATION = ? ");
        query.append("WHERE ID_SYSTEM_USER = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getUserType().getIdSystemUserType());
            preparedStatement.setString(4, entity.isActive() ? "Y" : "N");
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));
            preparedStatement.setInt(6, entity.getIdSystemUser());

            log.info("Executing update: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();


            log.info("{} merged successfully.", entity);
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
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM SYSTEM_USER WHERE ID_SYSTEM_USER = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idEntity);

            log.info("Executing delete: \n\t{}", query.toString());
            preparedStatement.executeUpdate();
            log.info("id[{}] removed successfully.", idEntity);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean remove(SystemUser entity) {
        if(entity.getIdSystemUser() == null) new DBException("Error: idSystemUser not found.");
        return remove(entity.getIdSystemUser());
    }

    // BOOT UP LOAD
    @Override
    public void createAdmin() {
        String code = "admin";
        SystemUser systemUser = findByCode(code);
        if(systemUser == null) {
            systemUser = new SystemUser();
            systemUser.setCode("admin");
            systemUser.setPassword("81DC9BDB52D04DC20036DBD8313ED055");
            systemUser.setActive(true);
            systemUser.setUserType(SystemUserType.ADMIN);
            persist(systemUser);
        }
    }

    private boolean exists(SystemUserType systemUserType) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM SYSTEM_USER_TYPE ");
        query.append("WHERE ID_SYSTEM_USER_TYPE = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, systemUserType.getIdSystemUserType());

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

    private boolean persist(SystemUserType systemUserType) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO SYSTEM_USER_TYPE(ID_SYSTEM_USER_TYPE, DS_TYPE) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, systemUserType.getIdSystemUserType());
            preparedStatement.setString(2, systemUserType.getType());


            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", systemUserType);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createSystemUserTypes() {
        for(SystemUserType type : SystemUserType.values()) {
            if(!exists(type)) {
                persist(type);
            }
        }
    }
}
