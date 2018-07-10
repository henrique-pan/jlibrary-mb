package com.grasset.user;

import lombok.Getter;

@Getter
public enum SystemUserType {

    ADMIN(1, "Admin"), MANAGER(2, "Manager"), CLIENT(3, "Client");

    SystemUserType(Integer idSystemUserType, String type) {
        this.idSystemUserType = idSystemUserType;
        this.type = type;
    }

    private Integer idSystemUserType;
    private String type;

    public SystemUserType getType(Integer idSystemUserType) {
        switch (idSystemUserType) {
            case 1: return ADMIN;
            case 2: return MANAGER;
            case 3: return CLIENT;
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemUserType [");
        sb.append("idSystemUserType = ").append(idSystemUserType);
        sb.append(", type = ").append(type);
        sb.append(']');
        return sb.toString();
    }
}
