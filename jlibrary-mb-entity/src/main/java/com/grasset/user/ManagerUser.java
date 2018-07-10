package com.grasset.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ManagerUser extends SystemUser {

    private Integer idManagerUser;
    private String name;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ManagerUser that = (ManagerUser) o;
        return Objects.equals(idManagerUser, that.idManagerUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idManagerUser);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ManagerUser [");
        sb.append("idManagerUser = ").append(idManagerUser);
        sb.append(", name = ").append(name);
        sb.append(", lastName = ").append(lastName);
        sb.append(']');
        return sb.toString();
    }
}
