package com.grasset.reservation;

public enum RoomReservationStatus {

    PENDING(1, "Pending"), IN_PROGRESS(2, "In Progress"), COMPLETED(3, "Completed"), CANCELED(4, "Canceled");

    RoomReservationStatus(Integer idRoomReservationStatus, String status) {
        this.idRoomReservationStatus = idRoomReservationStatus;
        this.status = status;
    }

    private Integer idRoomReservationStatus;
    private String status;

    public static RoomReservationStatus getStatus(Integer idRoomReservationStatus) {
        switch (idRoomReservationStatus) {
            case 1: return PENDING;
            case 2: return IN_PROGRESS;
            case 3: return COMPLETED;
            case 4: return CANCELED;
        }
        return null;
    }

    public Integer getIdRoomReservationStatus() {
        return idRoomReservationStatus;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoomReservationStatus [");
        sb.append("idRoomReservationStatus = ").append(idRoomReservationStatus);
        sb.append(", status = ").append(status);
        sb.append(']');
        return sb.toString();
    }

}
