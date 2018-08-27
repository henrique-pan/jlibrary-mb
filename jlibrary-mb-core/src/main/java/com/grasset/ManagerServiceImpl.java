package com.grasset;

import com.grasset.dao.manager.ManagerUserDAO;
import com.grasset.dao.manager.impl.ManagerUserDAOImpl;
import com.grasset.dao.user.SystemUserDAO;
import com.grasset.dao.user.impl.SystemUserDAOImpl;
import com.grasset.user.ManagerUser;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserType;

import lombok.extern.slf4j.Slf4j;

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
	public void save(ManagerUser managerUser) throws Exception {
		log.info("Start save progress");
		if(managerUser == null) throw new Exception("Error to save: managerUser is null");
		
		SystemUser systemUser = systemUserDAO.findByCode(managerUser.getCode());
		
		if(systemUser == null) {
			systemUserService.createNewSystemUser(managerUser);
		} else {
			managerUser.setIdSystemUser(systemUser.getIdSystemUser());
			managerUser.setPassword(systemUser.getPassword());
			managerUser.setUserType(systemUser.getUserType());
			systemUserService.modifyStatus(managerUser);
		}
		
		if(managerUserDAO.findByIdSystemUser(systemUser.getIdSystemUser()) == null) {
			managerUser.setIdSystemUser(systemUser.getIdSystemUser());
			managerUserDAO.persist(managerUser);
			return;
		} else {
			managerUser.setIdSystemUser(systemUser.getIdSystemUser());
			managerUserDAO.merge(managerUser);
			return;
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
			systemUserService.modifyStatus(managerUser);
		}
		
		if(!(managerUserDAO.findByIdSystemUser(systemUser.getIdSystemUser()) == null)) {
			managerUserDAO.remove(managerUser);
			return;
		}

	}
	
}
