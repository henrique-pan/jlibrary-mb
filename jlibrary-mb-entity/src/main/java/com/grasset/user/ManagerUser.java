package com.grasset.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class ManagerUser extends SystemUser {

    private Integer idManagerUser;
    private String name;
    private String lastName;
    private Date creationDate;
    private Date modificationDate;

    public void setSystemUserCreationDate(Date date) {
        super.setCreationDate(date);
    }

    public void setSystemUserModificationDate(Date date) {
        super.setModificationDate(date);
    }

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
        sb.append(", creationDate = ").append(creationDate);
        sb.append(", modificationDate = ").append(modificationDate);

        sb.append(']');
        return sb.toString();
    }
}
