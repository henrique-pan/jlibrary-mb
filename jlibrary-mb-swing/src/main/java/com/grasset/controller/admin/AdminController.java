/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.admin;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.grasset.manager.ManagerService;
import com.grasset.manager.ManagerServiceImpl;
import com.grasset.user.SystemUserService;
import com.grasset.user.SystemUserServiceImpl;
import com.grasset.controller.Controller;
import com.grasset.user.ManagerUser;
import com.grasset.view.AdminJPanelView;
import com.grasset.view.alerts.JAlertHelper;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author henrique
 */
@Slf4j
public class AdminController extends Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    // JPanel
    private final AdminJPanelView adminView;

    // JComponents
    private final JTextField jTextFieldCode;
    private final JTextField jTextFieldName;
    private final JTextField jTextFieldLastName;
    private final JCheckBox jCheckBoxStatus;
    private final JButton jButtonSave;
    private final JButton jButtonDelete;
    private final JButton jButtonClear;
    private final JPasswordField jPasswordField;
    private final JTextField jTextFieldSearch;
    private final JTable jTableManager;

    //Service
    private final ManagerService manageService;
    private final SystemUserService systemUserService;

    public AdminController() {
        adminView = new AdminJPanelView();

        jTextFieldCode = adminView.getjTextFieldCode();
        jTextFieldName = adminView.getjTextFieldName();
        jTextFieldLastName = adminView.getjTextFieldLastName();
        jCheckBoxStatus = adminView.getjCheckBoxStatus();
        jButtonSave = adminView.getjButtonSave();
        jButtonDelete = adminView.getjButtonDelete();
        jButtonClear = adminView.getjButtonClear();
        jPasswordField = adminView.getjPasswordField();
        jTextFieldSearch = adminView.getjTextFieldSearch();
        jTableManager = adminView.getjTable();

        manageService = new ManagerServiceImpl();
        systemUserService = new SystemUserServiceImpl();

        setEvents();
    }

    private void setEvents() {
        // Table Events
        setTableEvents();
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonSave.addActionListener(e -> {
            try {
                log.info("Start save process.");

                ManagerUser managerUser = new ManagerUser();

                managerUser.setCode(adminCode());
                managerUser.setName(adminName());
                managerUser.setLastName(adminLastName());
                managerUser.setActive(adminStatus());
                managerUser.setPassword(password());

                // Entries validation
                if (managerUser.getCode() == null || managerUser.getCode().isEmpty()) {
                    JAlertHelper.showError("Erreur de Code", "Le code de l'utilisateur est obligatoire.");
                    return;
                }

                if (managerUser.getName() == null || managerUser.getName().isEmpty()) {
                    JAlertHelper.showError("Erreur de Prénom", "Le Prénom de l'utilisateur est obligatoire.");
                    return;
                }

                if (managerUser.getLastName() == null || managerUser.getLastName().isEmpty()) {
                    JAlertHelper.showError("Erreur de Nom de famille", "Le Nom de famille de l'utilisateur est obligatoire.");
                    return;
                }
                if (managerUser.getPassword() == null || managerUser.getPassword().isEmpty()) {
                    JAlertHelper.showError("Erreur de Mot de Passe", "Le Mot de Passe de l'utilisateur est obligatoire.");
                    return;
                }
                log.info("Code: [{}] Name: [{}] LastName [{}] Active [{}]", managerUser.getCode(), managerUser.getName(), managerUser.getLastName(), managerUser.isActive());
                // Entries validation

                manageService.save(managerUser);
                updateTable();
            } catch (Exception exp) {
                JAlertHelper.showError("Erreur de Enregistrement", "Erreur pour faire le enregistrement: " + exp.getMessage());
            }
        });

        jButtonDelete.addActionListener(e -> {
            try {
                log.info("Start delete process.");

                ManagerUser managerUser = new ManagerUser();

                managerUser.setCode(adminCode());
                managerUser.setName(adminName());
                managerUser.setLastName(adminLastName());
                managerUser.setActive(adminStatus());

                // Entries validation
                if (managerUser.getCode() == null || managerUser.getCode().isEmpty()) {
                    JAlertHelper.showError("Erreur de Code", "Le code de l'utilisateur est obligatoire.");
                    return;
                }

                if (managerUser.getName() == null || managerUser.getName().isEmpty()) {
                    JAlertHelper.showError("Erreur de Prénom", "Le Prénom de l'utilisateur est obligatoire.");
                    return;
                }

                if (managerUser.getLastName() == null || managerUser.getLastName().isEmpty()) {
                    JAlertHelper.showError("Erreur de Nom de famille", "Le Nom de famille de l'utilisateur est obligatoire.");
                    return;
                }
                log.info("Code: [{}] Name: [{}] LastName [{}] Active [{}]", managerUser.getCode(), managerUser.getName(), managerUser.getLastName(), managerUser.isActive());
                // Entries validation


                manageService.delete(managerUser);
                clear();
                updateTable();
            } catch (Exception exp) {
                JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
            }
        });

        jButtonClear.addActionListener(e -> {
            clear();
            updateTable();
        });


    }

    private void setTableEvents() {
        updateTable();

        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    doSearch();
                } catch (Exception exp) {
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                try {
                    doSearch();
                } catch (Exception exp) {
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });

        jTableManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                try {
                    adminView.actualSelectedVenue = jTableManager.getSelectedRow();
                    if (jTableManager.getRowCount() > 0 && adminView.actualSelectedVenue != null) {
                        adminView.actualSelectedVenue = jTableManager.getSelectedRow();
                        String userCode = (String) jTableManager.getModel().getValueAt(adminView.actualSelectedVenue, 0);
                        ManagerUser managerUser = manageService.getManagerUser(userCode);
                        adminView.setFields(managerUser);
                    }
                } catch (Exception exp) {
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });

        jTableManager.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    adminView.actualSelectedVenue = jTableManager.getSelectedRow();
                    if (jTableManager.getRowCount() > 0 && adminView.actualSelectedVenue != null) {
                        adminView.actualSelectedVenue = jTableManager.getSelectedRow();
                        String userCode = (String) jTableManager.getModel().getValueAt(adminView.actualSelectedVenue, 0);
                        ManagerUser managerUser = manageService.getManagerUser(userCode);
                        adminView.setFields(managerUser);
                    }
                } catch (Exception exp) {
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });
    }

    private void updateTable() {
        try {
            Set<ManagerUser> set = manageService.getManagerUsers();
            adminView.updateTable(set);
        } catch (Exception exp) {
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void updateTable(Set<ManagerUser> set) {
        try {
            adminView.updateTable(set);
        } catch (Exception exp) {
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void doSearch() throws Exception {
        String text = jTextFieldSearch.getText();

        if (text.equals("")) {
            updateTable();
        } else {
            adminView.actualSelectedVenue = null;
            Set<ManagerUser> resultSet = new HashSet<>();
            Set<ManagerUser> set = manageService.getManagerUsers();
            for (ManagerUser managerUser : set) {
                String code = managerUser.getCode().toUpperCase();
                String name = managerUser.getName().toUpperCase();
                String lastName = managerUser.getLastName().toUpperCase();
                String s = code.concat(name).concat(lastName);
                if (s.toUpperCase().contains(text.toUpperCase())) {
                    resultSet.add(managerUser);
                }
            }
            updateTable(resultSet);
        }
    }

    private void clear() {
        jTextFieldCode.setText("");
        jTextFieldLastName.setText("");
        jTextFieldName.setText("");
        jTextFieldSearch.setText("");
        jCheckBoxStatus.setSelected(false);
        jPasswordField.setText("");
    }

    // Getters & Setters
    @Override
    public AdminJPanelView getView() {
        return adminView;
    }

    public String adminCode() {
        return jTextFieldCode.getText();
    }

    public String adminName() {
        return jTextFieldName.getText();
    }

    public String adminLastName() {
        return jTextFieldLastName.getText();
    }

    public boolean adminStatus() {
        return jCheckBoxStatus.isSelected();
    }

    public String password() {
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
