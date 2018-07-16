package com.grasset.reservation;

import lombok.Getter;

@Getter
public enum BookReservationStatus {

    PENDING(1, "Pending"), IN_PROGRESS(2, "In Progress"), COMPLETED(3, "Completed"), CANCELED(4, "Canceled"),
    DEFERRED(5, "Deferred"), COMPLETED_WITH_PENALTY(6, "Completed with Penalty");

    BookReservationStatus(Integer idBookReservationStatus, String status) {
        this.idBookReservationStatus = idBookReservationStatus;
        this.status = status;
    }

    private Integer idBookReservationStatus;
    private String status;

    public static BookReservationStatus getStatus(Integer idBookReservationStatus) {
        switch (idBookReservationStatus) {
            case 1: return PENDING;
            case 2: return IN_PROGRESS;
            case 3: return COMPLETED;
            case 4: return CANCELED;
            case 5: return DEFERRED;
            case 6: return COMPLETED_WITH_PENALTY;
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookReservationStatus [");
        sb.append("idBookReservationStatus = ").append(idBookReservationStatus);
        sb.append(", status = ").append(status);
        sb.append(']');
        return sb.toString();
    }
}
