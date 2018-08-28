package com.grasset.client;

import com.grasset.dao.client.ClientDAO;
import com.grasset.dao.client.impl.ClientDAOImpl;
import com.grasset.dao.user.SystemUserDAO;
import com.grasset.dao.user.impl.SystemUserDAOImpl;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserService;
import com.grasset.user.SystemUserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class ClientServiceImpl implements ClientService {

	private ClientDAO clientDAO;
	private SystemUserDAO systemUserDAO;
	private SystemUserService systemUserService;

	public ClientServiceImpl() {
		clientDAO = new ClientDAOImpl();
		systemUserDAO = new SystemUserDAOImpl();
		systemUserService = new SystemUserServiceImpl();
	}

    @Override
    public Client getClient(String userCode) throws Exception {
        SystemUser systemUser = systemUserDAO.findByCode(userCode);
	    if(systemUser == null) return null;

        Client client = clientDAO.findByIdSystemUser(systemUser.getIdSystemUser());
        return client;
    }

    @Override
	public void save(Client client) throws Exception {
		log.info("Start save progress");
		if(client == null) throw new Exception("Error to save: client is null");
		
		SystemUser systemUser = systemUserDAO.findByCode(client.getCode());

        // SAVE SYSTEM USER
		if(systemUser == null) {
            Integer idSystemUser = systemUserService.createNewSystemUser(client);
			client.setIdSystemUser(idSystemUser);

            Address address = clientDAO.findAddress(client.getAddress().getZipCode());
            if(address != null) {
				client.setAddress(address);
			} else {
				clientDAO.persist(client.getAddress());
				address = clientDAO.findAddress(client.getAddress().getZipCode());
				client.setAddress(address);
			}

			clientDAO.persist(client);
		} else {
            Client existentClient = clientDAO.findByIdSystemUser(systemUser.getIdSystemUser());
            if(existentClient == null) {
                Address address = clientDAO.findAddress(client.getAddress().getZipCode());
                if(address != null) {
                    client.setAddress(address);
                } else {
                    clientDAO.persist(client.getAddress());
                    address = clientDAO.findAddress(client.getAddress().getZipCode());
                    client.setAddress(address);
                }
            } else {
                client.setIdSystemUser(systemUser.getIdSystemUser());
                client.setUserType(systemUser.getUserType());
                client.setIdClient(existentClient.getIdClient());

                Address address = clientDAO.findAddress(client.getAddress().getZipCode());
                client.getAddress().setIdAddress(address.getIdAddress());

                systemUserDAO.merge(client);
                clientDAO.merge(client.getAddress());
                clientDAO.merge(client);
            }
		}
	}

	@Override
	public void delete(Client client) throws Exception {
		log.info("Start delete process");
		if(client == null) throw new Exception("Error to delete: client is null");

		client.setActive(false);
		SystemUser systemUser = systemUserDAO.findByCode(client.getCode());
		
		if(systemUser == null) {
			throw new Exception("Error to delete: systemUser is null");
		} else {
			client.setIdSystemUser(systemUser.getIdSystemUser());
			client.setPassword(systemUser.getPassword());
			client.setUserType(systemUser.getUserType());
			systemUserService.update(client);
		}
	}

    @Override
    public Set<Client> getClients() throws Exception {
        return clientDAO.findAll();
    }
}
