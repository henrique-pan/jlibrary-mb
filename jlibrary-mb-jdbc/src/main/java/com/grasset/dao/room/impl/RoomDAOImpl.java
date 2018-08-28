package com.grasset.dao.room.impl;

import com.grasset.book.Book;
import com.grasset.dao.book.BookDAO;
import com.grasset.dao.room.RoomDAO;
import com.grasset.db.ConnectionFactory;
import com.grasset.exception.DBException;
import com.grasset.reservation.BookWaitingListStatus;
import com.grasset.reservation.Room;
import com.grasset.reservation.RoomPeriod;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserType;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;
import java.util.Set;

@Slf4j
public class RoomDAOImpl implements RoomDAO {

    @Override
    public Room find(Integer idEntity) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ROOM ");
        query.append("WHERE ID_ROOM = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, idEntity);

            log.info("Executing query: \n\t{}", preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            Room room = null;
            while (rs.next()) {
                room = new Room();
                room.setIdRoom(rs.getInt("ID_ROOM"));
                room.setCodeRoom(rs.getString("CD_ROOM"));
            }

            log.info("Query Result: \n\t{}", room);
            return room;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Set<Room> findAll() {
        return null;
    }

    @Override
    public boolean persist(Room entity) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ROOM(ID_ROOM, CD_ROOM) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, entity.getIdRoom());
            preparedStatement.setString(2, entity.getCodeRoom());

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
    public boolean merge(Room entity) {
        return false;
    }

    @Override
    public boolean remove(Integer idEntity) {
        return false;
    }

    @Override
    public boolean remove(Room entity) {
        return false;
    }

    // BOOT UP LOAD
    @Override
    public void createRooms() {
        Integer room1Id = 1;
        Room room1 = find(room1Id);
        if(room1 == null) {
            room1 = new Room();
            room1.setIdRoom(room1Id);
            room1.setCodeRoom("Room 1");
            persist(room1);
        }

        Integer room2Id = 2;
        Room room2 = find(room2Id);
        if(room2 == null) {
            room2 = new Room();
            room2.setIdRoom(room2Id);
            room2.setCodeRoom("Room 2");
            persist(room2);
        }

        Integer room3Id = 3;
        Room room3 = find(room3Id);
        if(room3 == null) {
            room3 = new Room();
            room3.setIdRoom(room3Id);
            room3.setCodeRoom("Room 3");
            persist(room3);
        }
    }

    private boolean exists(RoomPeriod roomPeriod) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ROOM_PERIOD ");
        query.append("WHERE ID_ROOM_PERIOD = ? ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, roomPeriod.getIdRoomPeriod());

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

    private boolean persist(RoomPeriod roomPeriod) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ROOM_PERIOD(ID_ROOM_PERIOD, DS_PERIOD) ");
        query.append("VALUES(?, ?) ");

        try (Connection connection = ConnectionFactory.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.setInt(1, roomPeriod.getIdRoomPeriod());
            preparedStatement.setString(2, roomPeriod.getDescription());


            log.info("Executing insert: \n\t{}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            log.info("{} persisted successfully.", roomPeriod);
            return true;
        } catch (SQLException e) {
            log.error("Error to execute query: ", e);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createRoomPeriod() {
        for(RoomPeriod period : RoomPeriod.values()) {
            if(!exists(period)) {
                persist(period);
            }
        }
    }
}
