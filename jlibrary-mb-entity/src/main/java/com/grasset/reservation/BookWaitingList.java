package com.grasset.reservation;

import com.grasset.book.BookEdition;
import com.grasset.client.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class BookWaitingList {

    private Integer idBookWaitingList;
    private BookEdition bookEdition;
    private Client client;
    private BookWaitingListStatus waitingStatus;
    private Date creationDate;
    private Date modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookWaitingList that = (BookWaitingList) o;
        return Objects.equals(idBookWaitingList, that.idBookWaitingList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBookWaitingList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookWaitingList [");
        sb.append("idBookWaitingList = ").append(idBookWaitingList);
        sb.append(", bookEdition = ").append(bookEdition);
        sb.append(", client = ").append(client);
        sb.append(", waitingStatus = ").append(waitingStatus);
        sb.append(", creationDate = ").append(creationDate);
        sb.append(", modificationDate = ").append(modificationDate);
        sb.append(']');
        return sb.toString();
    }
}
