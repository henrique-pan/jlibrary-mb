package com.grasset.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Author {

    private Integer idAuthor;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author [");
        sb.append("idAuthor = ").append(idAuthor);
        sb.append(", name = ").append(name);
        sb.append(']');
        return sb.toString();
    }
}
