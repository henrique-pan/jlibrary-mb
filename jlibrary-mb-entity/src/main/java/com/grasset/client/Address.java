package com.grasset.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class Address {

    private Integer idAddress;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String addressProof;
    private boolean isValid;
    private Date creationDate;
    private Date modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(address, address1.address) &&
                Objects.equals(city, address1.city) &&
                Objects.equals(state, address1.state) &&
                Objects.equals(country, address1.country) &&
                Objects.equals(zipCode, address1.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, city, state, country, zipCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address [");
        sb.append("idAddress = ").append(idAddress);
        sb.append(", address = ").append(address);
        sb.append(", city = ").append(city);
        sb.append(", state = ").append(state);
        sb.append(", country = ").append(country);
        sb.append(", zipCode = ").append(zipCode);
        sb.append(", addressProof = ").append(addressProof);
        sb.append(", isValid = ").append(isValid);
        sb.append(", creationDate = ").append(creationDate);
        sb.append(", modificationDate = ").append(modificationDate);
        sb.append(']');
        return sb.toString();
    }
}
