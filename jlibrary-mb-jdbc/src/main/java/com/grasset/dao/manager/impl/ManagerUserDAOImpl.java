package com.grasset.dao.manager.impl;

import com.grasset.dao.manager.ManagerUserDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.user.ManagerUser;
import com.grasset.user.SystemUserType;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Slf4j
public class ManagerUserDAOImpl implements ManagerUserDAO {

    @Override
    public ManagerUser find(Integer idEntity) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM MANAGER_USER mu ");
        query.append("INNER JOIN SYSTEM_USER su ON(mu.ID_SYSTEM_USER = su.ID_SYSTEM_USER) ");
        query.append("WHERE mu.ID_MANAGER_USER = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idEntity);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            ManagerUser managerUser = null;
            while (rs.next()) {
                managerUser = new ManagerUser();

                // System User
                managerUser.setIdSystemUser(rs.getInt("su.ID_SYSTEM_USER"));
                managerUser.setCode(rs.getString("su.CD_USER"));
                managerUser.setPassword(rs.getString("su.DS_PASSWORD"));

                SystemUserType systemUserType = SystemUserType.getType(rs.getInt("su.ID_SYSTEM_USER"));
                managerUser.setUserType(systemUserType);
                managerUser.setActive(rs.getString("su.FL_ACTIVE").equalsIgnoreCase("Y"));
                managerUser.setSystemUserCreationDate(rs.getTimestamp("su.DT_CREATION"));
                managerUser.setSystemUserModificationDate(rs.getTimestamp("su.DT_MODIFICATION"));
                // System User
                // Manager User
                managerUser.setIdManagerUser(rs.getInt("mu.ID_CLIENT"));
                managerUser.setName(rs.getString("mu.NM_NAME"));
                managerUser.setLastName(rs.getString("mu.NM_LAST_NAME"));
                managerUser.setCreationDate(rs.getTimestamp("mu.DT_CREATION"));
                managerUser.setModificationDate(rs.getTimestamp("mu.DT_MODIFICATION"));
                // Manager User
            }

            log.info("Query Result: \n\t{}", managerUser);
            return managerUser;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public ManagerUser findByIdSystemUser(Integer idSystemUser) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM MANAGER_USER mu ");
        query.append("INNER JOIN SYSTEM_USER su ON(mu.ID_SYSTEM_USER = su.ID_SYSTEM_USER) ");
        query.append("WHERE mu.ID_SYSTEM_USER = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idSystemUser);

            log.info("Executing query: \n\t{}", query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            ManagerUser managerUser = null;
            while (rs.next()) {
                managerUser = new ManagerUser();

                // System User
                managerUser.setIdSystemUser(rs.getInt("su.ID_SYSTEM_USER"));
                managerUser.setCode(rs.getString("su.CD_USER"));
                managerUser.setPassword(rs.getString("su.DS_PASSWORD"));

                SystemUserType systemUserType = SystemUserType.getType(rs.getInt("su.ID_SYSTEM_USER"));
                managerUser.setUserType(systemUserType);
                managerUser.setActive(rs.getString("su.FL_ACTIVE").equalsIgnoreCase("Y"));
                managerUser.setSystemUserCreationDate(rs.getTimestamp("su.DT_CREATION"));
                managerUser.setSystemUserModificationDate(rs.getTimestamp("su.DT_MODIFICATION"));
                // System User
                // Manager User
                managerUser.setIdManagerUser(rs.getInt("mu.ID_CLIENT"));
                managerUser.setName(rs.getString("mu.NM_NAME"));
                managerUser.setLastName(rs.getString("mu.NM_LAST_NAME"));
                managerUser.setCreationDate(rs.getTimestamp("mu.DT_CREATION"));
                managerUser.setModificationDate(rs.getTimestamp("mu.DT_MODIFICATION"));
                // Manager User
            }

            log.info("Query Result: \n\t{}", managerUser);
            return managerUser;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<ManagerUser> findAll() {
        return null;
    }

    @Override
    public boolean persist(ManagerUser entity) {
        return false;
    }

    @Override
    public boolean merge(ManagerUser entity) {
        return false;
    }

    @Override
    public boolean remove(Integer idEntity) {
        return false;
    }

    @Override
    public boolean remove(ManagerUser entity) {
        return false;
    }
}
