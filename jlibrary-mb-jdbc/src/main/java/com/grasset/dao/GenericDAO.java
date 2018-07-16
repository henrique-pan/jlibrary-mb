package com.grasset.dao;

import java.util.Set;

public interface GenericDAO<T> {

    T find(Integer idEntity);

    Set<T> findAll();

    boolean persist(T entity);

    boolean merge(T entity);

    boolean remove(Integer idEntity);

    boolean remove(T entity);

}
