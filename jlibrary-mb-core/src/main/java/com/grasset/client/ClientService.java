package com.grasset.client;

import java.util.Set;

public interface ClientService {

    Client getClient(String userCode) throws Exception;

	void save(Client client) throws Exception;
	
	void delete(Client client) throws Exception;

	Set<Client> getClients() throws Exception;
	
}
