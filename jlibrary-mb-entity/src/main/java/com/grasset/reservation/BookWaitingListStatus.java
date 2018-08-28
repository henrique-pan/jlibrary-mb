package com.grasset.reservation;

public enum BookWaitingListStatus {

    COMPLETED(1, "Completed"), WAITING(2, "Waiting"), CANCELED(3, "Canceled");

    BookWaitingListStatus(Integer idBookWaitingListStatus, String status) {
        this.idBookWaitingListStatus = idBookWaitingListStatus;
        this.status = status;
    }

    private Integer idBookWaitingListStatus;
    private String status;

    public static BookWaitingListStatus getStatus(Integer idBookWaitingListStatus) {
        switch (idBookWaitingListStatus) {
            case 1: return COMPLETED;
            case 2: return WAITING;
            case 3: return CANCELED;
        }
        return null;
    }

    public Integer getIdBookWaitingListStatus() {
        return idBookWaitingListStatus;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookWaitingListStatus [");
        sb.append("idBookWaitingListStatus = ").append(idBookWaitingListStatus);
        sb.append(", status = ").append(status);
        sb.append(']');
        return sb.toString();
    }
}
