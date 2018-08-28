package com.grasset.dao.user;

import com.grasset.dao.GenericDAO;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserType;

public interface SystemUserDAO extends GenericDAO<SystemUser> {

    SystemUser findByCode(String code);
    void createAdmin();
    void createSystemUserTypes();

}
