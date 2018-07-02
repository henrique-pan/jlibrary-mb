/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.manager;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.grasset.view.ManagerJPanelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author henrique
 */
public class ManagerClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerClientController.class);

    private final ManagerController managerController;

    // JComponents
    private final JTextField jTextFieldClientName;
    private final JTextField jTextFieldClientLastName;
    private final JTextField jTextFieldClientId;
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

    public ManagerClientController(ManagerController managerController) {
        this.managerController = managerController;

        jTextFieldClientName = managerView().getjTextFieldClientName();
        jTextFieldClientLastName = managerView().getjTextFieldClientLastName();
        jTextFieldClientId = managerView().getjTextFieldClientId();
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

        setEvents();
    }

    private void setEvents() {
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonClientSave.addActionListener(e -> {

        });

        jButtonClientDelete.addActionListener(e -> {

        });

        jButtonClientDetails.addActionListener(e -> {

        });

        jButtonClientReserve.addActionListener(e -> {

        });

        jButtonClientClear.addActionListener(e -> {

        });
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

    public String clientId() {
        return jTextFieldClientId.getText();
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
}
