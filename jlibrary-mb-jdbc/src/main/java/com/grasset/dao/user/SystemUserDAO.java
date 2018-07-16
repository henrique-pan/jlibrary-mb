package com.grasset.dao.user;

import com.grasset.dao.GenericDAO;
import com.grasset.user.SystemUser;

public interface SystemUserDAO extends GenericDAO<SystemUser> {

    SystemUser findByCode(String code);

}
