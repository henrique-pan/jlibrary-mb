package com.grasset.dao.user;

import com.grasset.dao.user.impl.SystemUserDAOImpl;
import com.grasset.user.SystemUser;
import com.grasset.user.SystemUserType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class SystemUserDAOTest {

    @Test
    @DisplayName("Find System User by Id")
    public void findByIdTest() {
        SystemUserDAO systemUserDAO = new SystemUserDAOImpl();

        SystemUser systemUser = systemUserDAO.find(1);

        assertNotNull(systemUser);
    }

    @Test
    @DisplayName("Find All System Users")
    public void findAllTest() {
        SystemUserDAO systemUserDAO = new SystemUserDAOImpl();

        Set<SystemUser> list = systemUserDAO.findAll();

        assertNotNull(list);
    }

    @Test
    @DisplayName("Persist System User")
    public void persistTest() {
        SystemUserDAO systemUserDAO = new SystemUserDAOImpl();

        SystemUser systemUser = new SystemUser();
        systemUser.setCode("CD345");
        systemUser.setUserType(SystemUserType.ADMIN);
        systemUser.setPassword("1234");
        systemUser.setActive(true);

        assertTrue(systemUserDAO.persist(systemUser));
    }

    @Test
    @DisplayName("Persist Second System User")
    public void persistSecondTest() {
        SystemUserDAO systemUserDAO = new SystemUserDAOImpl();

        SystemUser systemUser = new SystemUser();
        systemUser.setCode("CD346");
        systemUser.setUserType(SystemUserType.ADMIN);
        systemUser.setPassword("1234");
        systemUser.setActive(true);

        assertTrue(systemUserDAO.persist(systemUser));
    }

    @Test
    @DisplayName("Update System User")
    public void updateTest() {
        SystemUserDAO systemUserDAO = new SystemUserDAOImpl();

        SystemUser systemUser = new SystemUser();
        systemUser.setIdSystemUser(7);
        systemUser.setCode("CD347");
        systemUser.setUserType(SystemUserType.ADMIN);
        systemUser.setPassword("1234");
        systemUser.setActive(true);

        assertTrue(systemUserDAO.merge(systemUser));
    }

    @Test
    @DisplayName("Remove System User by Id")
    public void removeByIdTest() {
        SystemUserDAO systemUserDAO = new SystemUserDAOImpl();
        assertTrue(systemUserDAO.remove(7));
    }

    @Test
    @DisplayName("Remove System User")
    public void removeTest() {
        SystemUserDAO systemUserDAO = new SystemUserDAOImpl();

        SystemUser systemUser = systemUserDAO.find(8);

        assertTrue(systemUserDAO.remove(systemUser));
    }

}
