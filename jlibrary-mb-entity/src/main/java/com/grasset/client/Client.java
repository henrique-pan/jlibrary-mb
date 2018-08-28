package com.grasset.client;

import com.grasset.user.SystemUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class Client extends SystemUser {

    private Integer idClient;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Address address;
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
        Client client = (Client) o;
        return Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client [");
        sb.append("idClient = ").append(idClient);
        sb.append(", name = ").append(name);
        sb.append(", lastName = ").append(lastName);
        sb.append(", phoneNumber = ").append(phoneNumber);
        sb.append(", email = ").append(email);
        sb.append(", address = ").append(address);
        sb.append(", creationDate = ").append(creationDate);
        sb.append(", modificationDate = ").append(modificationDate);
        sb.append(']');
        return sb.toString();
    }
}
