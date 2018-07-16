package com.grasset;

import com.grasset.client.Client;
import com.grasset.dao.client.ClientDAO;
import com.grasset.dao.client.impl.ClientDAOImpl;
import com.grasset.dao.manager.ManagerUserDAO;
import com.grasset.dao.manager.impl.ManagerUserDAOImpl;
import com.grasset.dao.user.SystemUserDAO;
import com.grasset.dao.user.impl.SystemUserDAOImpl;
import com.grasset.env.CurrentSystemUser;
import com.grasset.user.ManagerUser;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserType;

public class LoginServiceImpl implements LoginService {

    private ClientDAO clientDAO;
    private SystemUserDAO systemUserDAO;
    private ManagerUserDAO managerUserDAO;

    public LoginServiceImpl() {
        clientDAO = new ClientDAOImpl();
        systemUserDAO = new SystemUserDAOImpl();
        managerUserDAO = new ManagerUserDAOImpl();
    }

    @Override
    public boolean validate(String systemUserCode, String password) {
        SystemUser systemUser = systemUserDAO.findByCode(systemUserCode);

        if(systemUser == null) return false;

        if(systemUser.getPassword().equalsIgnoreCase(password)) {
            CurrentSystemUser.set(systemUser);
            return true;
        }

        return false;
    }

    @Override
    public void doLogin(SystemUser systemUser) throws Exception {
        if(systemUser == null) throw new Exception("Error to do the login: systemUser is null");

        if(systemUser.getUserType().equals(SystemUserType.CLIENT)) {
            Client client = clientDAO.findByIdSystemUser(systemUser.getIdSystemUser());
            if (client == null) throw new Exception("Error to do the login: client is null");

            CurrentSystemUser.setClient(client);
            return;
        }

        if(systemUser.getUserType().equals(SystemUserType.MANAGER)) {
            ManagerUser managerUser = managerUserDAO.findByIdSystemUser(systemUser.getIdSystemUser());
            if (managerUser == null) throw new Exception("Error to do the login: managerUser is null");

            CurrentSystemUser.setManagerUser(managerUser);
            return;
        }
    }
}
