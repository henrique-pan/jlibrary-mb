package com.grasset.dao.room.impl;

import com.grasset.dao.room.RoomReservationDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.reservation.RoomReservation;
import com.grasset.reservation.RoomReservationStatus;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Slf4j
public class RoomReservationDAOImpl implements RoomReservationDAO {

    @Override
    public RoomReservation find(Integer idEntity) {
        return null;
    }

    @Override
    public Set<RoomReservation> findAll() {
        return null;
    }

    @Override
    public boolean persist(RoomReservation entity) {
        return false;
    }

    @Override
    public boolean merge(RoomReservation entity) {
        return false;
    }

    @Override
    public boolean remove(Integer idEntity) {
        return false;
    }

    @Override
    public boolean remove(RoomReservation entity) {
        return false;
    }

    // BOOT UP LOAD
    private boolean exists(RoomReservationStatus roomReservationStatus) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ROOM_RESERVATION_STATUS ");
        query.append("WHERE ID_ROOM_RESERVATION_STATUS = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, roomReservationStatus.getIdRoomReservationStatus());

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

    private boolean persist(RoomReservationStatus roomReservationStatus) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ROOM_RESERVATION_STATUS(ID_ROOM_RESERVATION_STATUS, DS_STATUS) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, roomReservationStatus.getIdRoomReservationStatus());
            preparedStatement.setString(2, roomReservationStatus.getStatus());


            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", roomReservationStatus);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createRoomReservationStatus() {
        for(RoomReservationStatus status : RoomReservationStatus.values()) {
            if(!exists(status)) {
                persist(status);
            }
        }
    }
}
