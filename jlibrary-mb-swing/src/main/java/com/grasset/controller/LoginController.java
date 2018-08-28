/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller;

import com.grasset.login.LoginService;
import com.grasset.login.LoginServiceImpl;
import com.grasset.env.CurrentSystemUser;
import com.grasset.user.SystemUser;
import com.grasset.view.LoginJPanelView;
import com.grasset.view.alerts.JAlertHelper;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
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

    // Service
    private final LoginService loginService;

    public LoginController() {
        loginView = new LoginJPanelView();

        jTextFieldUserCode = loginView.getjTextFieldUserCode();
        jPasswordField = loginView.getjPasswordField();
        jButtonConnect = loginView.getjButtonConnect();

        loginService = new LoginServiceImpl();

        setEvents();
    }

    private void setEvents() {
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    for (ActionListener a : jButtonConnect.getActionListeners()) {
                        a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
                        });
                    }
                }
            }
        };

        jTextFieldUserCode.addKeyListener(keyListener);
        jButtonConnect.addKeyListener(keyListener);
        jPasswordField.addKeyListener(keyListener);

        jButtonConnect.addActionListener(event -> {
            log.info("Start login process.");

            // Entries validation
            String userCode = userCode();
            if (userCode == null || userCode.isEmpty()) {
                JAlertHelper.showError("Erreur de Login", "Le code de l'utilisateur est obligatoire.");
                return;
            }

            String password = password();
            if (password == null || password.isEmpty()) {
                JAlertHelper.showError("Erreur de Login", "Le mot de passe de l'utilisateur est obligatoire.");
                return;
            }
            log.info("userCode: [{}] password: [{}]", userCode, password);
            // Entries validation


            if (!loginService.validate(userCode, password)) {
                JAlertHelper.showError("Erreur de Login", "Le code ou le mot de passe n'est pas correct");
                return;
            }

            SystemUser systemUser = CurrentSystemUser.get();
            try {
                loginService.doLogin(systemUser);

                switch (systemUser.getUserType()) {
                    case ADMIN:
                        log.info("Doing login as Admin");
                        mainController.showAdminView();
                        break;
                    case MANAGER:
                        log.info("Doing login as Manager");
                        mainController.showManagerView();
                        break;
                    case CLIENT:
                        log.info("Doing login as Client");
                        mainController.showClientView();
                        break;
                    default:
                        JAlertHelper.showError("Erreur de Login", "Erreur pour choisir prochine écran.");
                        break;
                }
            } catch (Exception e) {
                JAlertHelper.showError("Erreur de Login", "Erreur pour faire le login: " + e.getMessage());
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
        char[] passwordChar = jPasswordField.getPassword();
        if (passwordChar == null) return null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(String.valueOf(passwordChar).getBytes());
            byte[] digest = md.digest();

            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("Error to get the MD5 generator: {}", e.getMessage());
        }

        return null;
    }

}
