/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.manager;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;

import com.grasset.client.Address;
import com.grasset.client.Client;
import com.grasset.client.ClientService;
import com.grasset.client.ClientServiceImpl;
import com.grasset.manager.ManagerService;
import com.grasset.manager.ManagerServiceImpl;
import com.grasset.user.ManagerUser;
import com.grasset.user.SystemUserService;
import com.grasset.user.SystemUserServiceImpl;
import com.grasset.view.ManagerJPanelView;
import com.grasset.view.alerts.JAlertHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author henrique
 */
@Slf4j
public class ManagerClientController {

    private final ManagerController managerController;

    // JComponents
    private final JTextField jTextFieldClientName;
    private final JTextField jTextFieldClientLastName;
    private final JTextField jTextFieldClientPhone;
    private final JTextField jTextFieldClientEmail;
    private final JTextField jTextFieldClientCode;
    private final JTextField jTextFieldClientAddress;
    private final JTextField jTextFieldClientCity;
    private final JTextField jTextFieldClientState;
    private final JTextField jTextFieldCountry;
    private final JTextField jTextFieldZIPCode;
    private final JTextField jTextFieldDocVerification;
    private final JCheckBox jCheckBoxClientValid;
    private final JButton jButtonClientSave;
    private final JButton jButtonClientDelete;
    private final JButton jButtonClientDetails;
    private final JButton jButtonClientReserve;
    private final JButton jButtonClientClear;
    private final JTextField jTextFieldClientSearch;
    private final JTable jTableClient;
    private final JPasswordField jPasswordField;

    //Service
    private final ClientService clientService;
    private final SystemUserService systemUserService;

    public ManagerClientController(ManagerController managerController) {
        this.managerController = managerController;

        jTextFieldClientName = managerView().getjTextFieldClientName();
        jTextFieldClientLastName = managerView().getjTextFieldClientLastName();
        jTextFieldClientPhone = managerView().getjTextFieldClientPhone();
        jTextFieldClientEmail = managerView().getjTextFieldClientEmail();
        jTextFieldClientCode = managerView().getjTextFieldClientCode();
        jTextFieldClientAddress = managerView().getjTextFieldClientAddress();
        jTextFieldClientCity = managerView().getjTextFieldClientCity();
        jTextFieldClientState = managerView().getjTextFieldClientState();
        jTextFieldCountry = managerView().getjTextFieldCountry();
        jTextFieldZIPCode = managerView().getjTextFieldZIPCode();
        jTextFieldDocVerification = managerView().getjTextFieldDocVerification();
        jCheckBoxClientValid = managerView().getjCheckBoxClientValid();
        jButtonClientSave = managerView().getjButtonClientSave();
        jButtonClientDelete = managerView().getjButtonClientDelete();
        jButtonClientDetails = managerView().getjButtonClientDetails();
        jButtonClientReserve = managerView().getjButtonClientReserve();
        jButtonClientClear = managerView().getjButtonClientClear();
        jTextFieldClientSearch = managerView().getjTextFieldClientSearch();
        jTableClient = managerView().getjTableClients();
        jPasswordField = managerView().getjPasswordFieldClient();

        clientService = new ClientServiceImpl();
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
        jButtonClientSave.addActionListener(e -> {
            try {
                log.info("Start save process.");

                Client client = new Client();

                client.setName(clientName());
                client.setLastName(clientLastName());
                client.setPhoneNumber(clientPhone());
                client.setEmail(clientEmail());
                client.setCode(clientCode());
                client.setPassword(clientPassword());

                Address address = new Address();
                address.setAddress(clientAddress());
                address.setCity(clientCity());
                address.setState(clientState());
                address.setCountry(clientCountry());
                address.setZipCode(clientZIPCode());
                address.setAddressProof(clientDocVerification());
                address.setValid(clientIsValid());

                client.setAddress(address);

                // Entries validation

                log.info("Code: [{}] Name: [{}] LastName [{}] Active [{}]", client.getCode(), client.getName(), client.getLastName(), client.isActive());
                // Entries validation

                clientService.save(client);
                updateTable();
            } catch (Exception exp) {
                JAlertHelper.showError("Erreur de Enregistrement", "Erreur pour faire le enregistrement: " + exp.getMessage());
            }
        });

        jButtonClientDelete.addActionListener(e -> {
            clear();
        });

        jButtonClientDetails.addActionListener(e -> {

        });

        jButtonClientReserve.addActionListener(e -> {

        });

        jButtonClientClear.addActionListener(e -> {
            clear();
        });
    }

    private void setTableEvents() {
        updateTable();

        jTextFieldClientSearch.addKeyListener(new java.awt.event.KeyAdapter() {
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

        jTableClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                try {
                    managerView().actualClientSelectedVenue = jTableClient.getSelectedRow();
                    if (jTableClient.getRowCount() > 0 && managerView().actualClientSelectedVenue != null) {
                        managerView().actualClientSelectedVenue = jTableClient.getSelectedRow();
                        String userCode = (String) jTableClient.getModel().getValueAt(managerView().actualClientSelectedVenue, 0);
                        Client client = clientService.getClient(userCode);
                        managerView().setClientFields(client);
                    }
                } catch (Exception exp) {
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });

        jTableClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    managerView().actualClientSelectedVenue = jTableClient.getSelectedRow();
                    if (jTableClient.getRowCount() > 0 && managerView().actualClientSelectedVenue != null) {
                        managerView().actualClientSelectedVenue = jTableClient.getSelectedRow();
                        String userCode = (String) jTableClient.getModel().getValueAt(managerView().actualClientSelectedVenue, 0);
                        Client client = clientService.getClient(userCode);
                        managerView().setClientFields(client);
                    }
                } catch (Exception exp) {
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });
    }

    private void updateTable() {
        try {
            Set<Client> set = clientService.getClients();
            managerView().updateClientTable(set);
        } catch (Exception exp) {
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void updateTable(Set<Client> set) {
        try {
            managerView().updateClientTable(set);
        } catch (Exception exp) {
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void doSearch() throws Exception {
        String text = jTextFieldClientSearch.getText();

        if (text.equals("")) {
            updateTable();
        } else {
            managerView().actualClientSelectedVenue = null;
            Set<Client> resultSet = new HashSet<>();
            Set<Client> set = clientService.getClients();
            for (Client client : set) {
                String code = client.getCode();
                String name = client.getName();
                String lastName = client.getLastName();
                String email = client.getEmail();
                String s = code.concat(name).concat(lastName).concat(email);
                if (s.toUpperCase().contains(text.toUpperCase())) {
                    resultSet.add(client);
                }
            }
            updateTable(resultSet);
        }
    }

    private void clear() {
        jTextFieldClientName.setText("");
        jTextFieldClientLastName.setText("");
        jTextFieldClientPhone.setText("");
        jTextFieldClientEmail.setText("");
        jTextFieldClientCode.setText("");
        jTextFieldClientAddress.setText("");
        jTextFieldClientCity.setText("");
        jTextFieldClientState.setText("");
        jTextFieldCountry.setText("");
        jTextFieldZIPCode.setText("");
        jTextFieldDocVerification.setText("");


        jCheckBoxClientValid.setSelected(false);
    }

    // Getters & Setters
    public ManagerJPanelView managerView() {
        return managerController.managerView;
    }

    public String clientName() {
        return jTextFieldClientName.getText();
    }

    public String clientLastName() {
        return jTextFieldClientLastName.getText();
    }

    public String clientPhone() {
        return jTextFieldClientPhone.getText();
    }

    public String clientEmail() {
        return jTextFieldClientEmail.getText();
    }

    public String clientCode() {
        return jTextFieldClientCode.getText();
    }

    public String clientAddress() {
        return jTextFieldClientAddress.getText();
    }

    public String clientCity() {
        return jTextFieldClientCity.getText();
    }

    public String clientState() {
        return jTextFieldClientState.getText();
    }

    public String clientCountry() {
        return jTextFieldCountry.getText();
    }

    public String clientZIPCode() {
        return jTextFieldZIPCode.getText();
    }

    public String clientDocVerification() {
        return jTextFieldDocVerification.getText();
    }

    public boolean clientIsValid() {
        return jCheckBoxClientValid.isSelected();
    }

    public String clientPassword() {
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
