package com.grasset.user;

import com.grasset.dao.user.SystemUserDAO;
import com.grasset.dao.user.impl.SystemUserDAOImpl;
import com.grasset.env.CurrentSystemUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemUserServiceImpl implements SystemUserService {

    private SystemUserDAO systemUserDAO;

    public SystemUserServiceImpl() {
        systemUserDAO = new SystemUserDAOImpl();
    }

    @Override
    public int createNewSystemUser(SystemUser systemUser) {
        log.info("Start createNewSystemUser process");

        SystemUser currentUser = CurrentSystemUser.get();

        switch (currentUser.getUserType()) {
            case ADMIN:
                systemUser.setUserType(SystemUserType.MANAGER);
                break;
            case MANAGER:
                systemUser.setUserType(SystemUserType.CLIENT);
                break;
            case CLIENT:
                systemUser.setUserType(SystemUserType.CLIENT);
                break;
            default:
                log.error("DataBase error");
                break;
        }

        systemUserDAO.persist(systemUser);

        systemUser = systemUserDAO.findByCode(systemUser.getCode());

        return systemUser.getIdSystemUser();

    }

    @Override
    public void update(SystemUser systemUser) {
        log.info("Start update process");
        systemUserDAO.merge(systemUser);
    }

}
