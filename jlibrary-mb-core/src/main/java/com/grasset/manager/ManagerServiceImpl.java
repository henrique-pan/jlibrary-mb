package com.grasset.manager;

import com.grasset.user.SystemUserService;
import com.grasset.user.SystemUserServiceImpl;
import com.grasset.dao.manager.ManagerUserDAO;
import com.grasset.dao.manager.impl.ManagerUserDAOImpl;
import com.grasset.dao.user.SystemUserDAO;
import com.grasset.dao.user.impl.SystemUserDAOImpl;
import com.grasset.user.ManagerUser;
import com.grasset.user.SystemUser;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class ManagerServiceImpl implements ManagerService {
	
	private ManagerUserDAO managerUserDAO;
	private SystemUserDAO systemUserDAO;
	private SystemUserService systemUserService;
	
	public ManagerServiceImpl() {
		managerUserDAO = new ManagerUserDAOImpl();
		systemUserDAO = new SystemUserDAOImpl();
		systemUserService = new SystemUserServiceImpl();
	}

    @Override
    public ManagerUser getManagerUser(String userCode) throws Exception {
        SystemUser systemUser = systemUserDAO.findByCode(userCode);
	    if(systemUser == null) return null;

        ManagerUser managerUser = managerUserDAO.findByIdSystemUser(systemUser.getIdSystemUser());
        return managerUser;
    }

    @Override
	public void save(ManagerUser managerUser) throws Exception {
		log.info("Start save progress");
		if(managerUser == null) throw new Exception("Error to save: managerUser is null");
		
		SystemUser systemUser = systemUserDAO.findByCode(managerUser.getCode());

        // SAVE SYSTEM USER
		if(systemUser == null) {
            Integer idSystemUser = systemUserService.createNewSystemUser(managerUser);
            managerUser.setIdSystemUser(idSystemUser);
            managerUserDAO.persist(managerUser);
		} else {
			managerUser.setIdSystemUser(systemUser.getIdSystemUser());
            managerUser.setUserType(systemUser.getUserType());
            systemUserDAO.merge(managerUser);
            managerUserDAO.merge(managerUser);
		}
	}

	@Override
	public void delete(ManagerUser managerUser) throws Exception {
		log.info("Start delete process");
		if(managerUser == null) throw new Exception("Error to delete: managerUser is null");
		
		managerUser.setActive(false);
		SystemUser systemUser = systemUserDAO.findByCode(managerUser.getCode());
		
		if(systemUser == null) {
			throw new Exception("Error to delete: systemUser is null");
		} else {
			managerUser.setIdSystemUser(systemUser.getIdSystemUser());
			managerUser.setPassword(systemUser.getPassword());
			managerUser.setUserType(systemUser.getUserType());
			systemUserService.update(managerUser);
		}
	}

    @Override
    public Set<ManagerUser> getManagerUsers() throws Exception {
        return managerUserDAO.findAll();
    }
}
