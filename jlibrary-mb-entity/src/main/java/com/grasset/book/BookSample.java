package com.grasset.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class BookSample extends BookEdition {

    private Integer idBookSample;
    private String codeSample;
    private Date creationDate;
    private Date modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookSample that = (BookSample) o;
        return Objects.equals(codeSample, that.codeSample);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), codeSample);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookSample [");
        sb.append("idBookSample = ").append(idBookSample);
        sb.append(", codeSample = ").append(codeSample);
        sb.append(", creationDate = ").append(creationDate);
        sb.append(", modificationDate = ").append(modificationDate);
        sb.append(']');
        return sb.toString();
    }
}
