package com.grasset;

import com.grasset.user.ManagerUser;

public interface ManagerUserService {

	void save(ManagerUser managerUser) throws Exception;
	
	void delete(ManagerUser managerUser) throws Exception;
	
}
