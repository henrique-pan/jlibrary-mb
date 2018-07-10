/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller;

import com.grasset.view.LoginJPanelView;
import com.grasset.view.alerts.JAlertHelper;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author henrique
 */
@Slf4j
public class LoginController extends Controller {

    // JPanel
    private final LoginJPanelView loginView;

    // JComponents
    private final JTextField jTextFieldUserCode;
    private final JPasswordField jPasswordField;
    private final JButton jButtonConnect;

    public LoginController() {
        loginView = new LoginJPanelView();
        
        jTextFieldUserCode = loginView.getjTextFieldUserCode();
        jPasswordField = loginView.getjPasswordField();
        jButtonConnect = loginView.getjButtonConnect();
        
        setEvents();
    }

    private void setEvents() {
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonConnect.addActionListener(e -> {
            log.info("Doing login: {} {}", userCode(), password());
            if(userCode().equalsIgnoreCase("admin")) {
                log.info("Calling admin");
                mainController.showAdminView();
            } else if(userCode().equalsIgnoreCase("manager")) {
                log.info("Calling manager");
                mainController.showManagerView();
            } else if(userCode().equalsIgnoreCase("client")) {
                log.info("Calling client");
                mainController.showUserView();
            } else {
                JAlertHelper.showError("Login error", "Error during the login");
            }
        });
    }

    // Getters & Setters
    @Override
    public LoginJPanelView getView() {
        return loginView;
    }
    
    private String userCode() {
        return jTextFieldUserCode.getText();
    }
    
    private String password() {
        return String.valueOf(jPasswordField.getPassword());
    }

}
