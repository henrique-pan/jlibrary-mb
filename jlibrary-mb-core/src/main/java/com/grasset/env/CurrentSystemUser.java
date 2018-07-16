package com.grasset.env;

import com.grasset.client.Client;
import com.grasset.user.ManagerUser;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserType;

public class CurrentSystemUser {

    private static SystemUser systemUser;

    private static Client client;

    private static ManagerUser managerUser;

    public static void set(SystemUser systemUser) {
        CurrentSystemUser.systemUser = systemUser;
    }

    public static SystemUser get() {
        return systemUser;
    }

    public static SystemUserType getType() {
        if(systemUser == null) return null;
        return systemUser.getUserType();
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        CurrentSystemUser.client = client;
    }

    public static ManagerUser getManagerUser() {
        return managerUser;
    }

    public static void setManagerUser(ManagerUser managerUser) {
        CurrentSystemUser.managerUser = managerUser;
    }
}
