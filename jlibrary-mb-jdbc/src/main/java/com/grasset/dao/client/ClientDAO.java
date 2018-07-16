package com.grasset.dao.client;

import com.grasset.client.Client;
import com.grasset.dao.GenericDAO;

public interface ClientDAO extends GenericDAO<Client> {

    Client findByIdSystemUser(Integer idSystemUser);

}
