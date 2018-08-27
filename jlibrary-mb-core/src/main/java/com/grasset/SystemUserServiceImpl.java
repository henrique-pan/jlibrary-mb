package com.grasset;

import com.grasset.dao.user.SystemUserDAO;
import com.grasset.dao.user.impl.SystemUserDAOImpl;
import com.grasset.env.CurrentSystemUser;
import com.grasset.user.ManagerUser;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserType;

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
		
		systemUser.setPassword("81DC9BDB52D04DC20036DBD8313ED055");
		
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
		
		return systemUser.getIdSystemUser();
	}

	@Override
	public void modifyStatus(ManagerUser managerUser) {
		log.info("Start modifyStatus process");
		systemUserDAO.merge(managerUser);
		
	}

}
