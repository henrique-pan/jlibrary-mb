package com.grasset.login;

import com.grasset.user.SystemUser;

public interface LoginService {

    boolean validate(String systemUserCode, String password);

    void doLogin(SystemUser systemUser) throws Exception;

}
