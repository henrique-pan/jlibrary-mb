package com.grasset.reservation;

import com.grasset.book.BookSample;
import com.grasset.client.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class BookReservation {

    private Integer idBookReservation;
    private BookSample bookSample;
    private Client client;
    private BookReservationStatus reservationStatus;
    private Date creationDate;
    private Date modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookReservation that = (BookReservation) o;
        return Objects.equals(bookSample, that.bookSample) &&
                Objects.equals(client, that.client) &&
                Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookSample, client, creationDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookReservation [");
        sb.append("idBookReservation = ").append(idBookReservation);
        sb.append(", bookSample = ").append(bookSample);
        sb.append(", client = ").append(client);
        sb.append(", reservationStatus = ").append(reservationStatus);
        sb.append(", creationDate = ").append(creationDate);
        sb.append(", modificationDate = ").append(modificationDate);
        sb.append(']');
        return sb.toString();
    }
}
