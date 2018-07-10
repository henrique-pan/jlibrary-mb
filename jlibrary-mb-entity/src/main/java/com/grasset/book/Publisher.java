package com.grasset.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Publisher {

    private Integer idPublisher;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(name, publisher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Publisher [");
        sb.append("idPublisher = ").append(idPublisher);
        sb.append(", name = ").append(name);
        sb.append(']');
        return sb.toString();
    }
}
