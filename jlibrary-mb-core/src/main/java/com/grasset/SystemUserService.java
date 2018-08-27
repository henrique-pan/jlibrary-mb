package com.grasset;

import com.grasset.user.ManagerUser;
import com.grasset.user.SystemUser;

public interface SystemUserService {
	
	int createNewSystemUser(SystemUser systemUser);

	void modifyStatus(ManagerUser managerUser);
}
