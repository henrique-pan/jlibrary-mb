package com.grasset.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class SystemUser {

    private Integer idSystemUser;
    private String code;
    private String password;
    private SystemUserType userType;
    private boolean isActive;
    private Date creationDate;
    private Date modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemUser that = (SystemUser) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemUser [");
        sb.append("idSystemUser = ").append(idSystemUser);
        sb.append(", code = ").append(code);
        sb.append(", password = ").append(password);
        sb.append(", userType = ").append(userType);
        sb.append(", isActive = ").append(isActive);
        sb.append(", creationDate = ").append(creationDate);
        sb.append(", modificationDate = ").append(modificationDate);
        sb.append(']');
        return sb.toString();
    }
}
