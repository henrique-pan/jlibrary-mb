package com.grasset.dao.manager;

import com.grasset.dao.GenericDAO;
import com.grasset.user.ManagerUser;

public interface ManagerUserDAO extends GenericDAO<ManagerUser> {

    ManagerUser findByIdSystemUser(Integer idSystemUser);

}
