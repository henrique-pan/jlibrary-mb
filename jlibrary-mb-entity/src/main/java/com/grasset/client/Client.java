package com.grasset.client;

import com.grasset.user.SystemUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Client extends SystemUser {

    private Integer idClient;
    private String name;
    private String lastName;
    private String id;
    private String phoneNumber;
    private String email;
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) ||
                Objects.equals(phoneNumber, client.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, phoneNumber);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client [");
        sb.append("idClient = ").append(idClient);
        sb.append(", name = ").append(name);
        sb.append(", lastName = ").append(lastName);
        sb.append(", id = ").append(id);
        sb.append(", phoneNumber = ").append(phoneNumber);
        sb.append(", email = ").append(email);
        sb.append(", address = ").append(address);
        sb.append(']');
        return sb.toString();
    }
}
