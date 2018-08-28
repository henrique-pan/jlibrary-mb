package com.grasset.dao.client;

import com.grasset.client.Address;
import com.grasset.client.Client;
import com.grasset.dao.GenericDAO;

public interface ClientDAO extends GenericDAO<Client> {

    Client findByIdSystemUser(Integer idSystemUser);

    Address findAddress(String zipCode);

    boolean persist(Address address);

    boolean merge(Address address);

}
