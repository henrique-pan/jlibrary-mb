package com.grasset.manager;

import com.grasset.user.ManagerUser;

import java.util.Set;

public interface ManagerService {

    ManagerUser getManagerUser(String userCode) throws Exception;

	void save(ManagerUser managerUser) throws Exception;
	
	void delete(ManagerUser managerUser) throws Exception;

	Set<ManagerUser> getManagerUsers() throws Exception;
	
}
