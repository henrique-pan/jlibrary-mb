/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.client;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.grasset.book.Book;
import com.grasset.book.BookEdition;
import com.grasset.book.BookReservationService;
import com.grasset.book.BookReservationServiceImpl;
import com.grasset.client.Client;
import com.grasset.controller.manager.ManagerController;
import com.grasset.env.CurrentSystemUser;
import com.grasset.reservation.BookReservation;
import com.grasset.view.ClientJPanelView;
import com.grasset.view.ManagerJPanelView;
import com.grasset.view.alerts.JAlertHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author henrique
 */
@Slf4j
public class ClientInfoController {

    private final ClientController clientController;

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
    private final JCheckBox jCheckBoxValid;
    private final JButton jButtonInfoRenew;
    private final JButton jButtonInfoCancel;
    private final JButton jButtonReload;
    private final JTextField jTextFieldClientSearch;

    private BookReservationService bookReservationService;

    public ClientInfoController(ClientController clientController) {
        this.clientController = clientController;

        jTextFieldClientName = clientView().getjTextFieldClientName();
        jTextFieldClientLastName = clientView().getjTextFieldClientLastName();
        jTextFieldClientPhone = clientView().getjTextFieldClientPhone();
        jTextFieldClientEmail = clientView().getjTextFieldClientEmail();
        jTextFieldClientCode = clientView().getjTextFieldClientCode();
        jTextFieldClientAddress = clientView().getjTextFieldClientAddress();
        jTextFieldClientCity = clientView().getjTextFieldClientCity();
        jTextFieldClientState = clientView().getjTextFieldClientState();
        jTextFieldCountry = clientView().getjTextFieldCountry();
        jTextFieldZIPCode = clientView().getjTextFieldZIPCode();
        jTextFieldDocVerification = clientView().getjTextFieldDocVerification();
        jCheckBoxValid = clientView().getjCheckBoxValid();
        jButtonInfoRenew = clientView().getjButtonInfoRenew();
        jButtonInfoCancel = clientView().getjButtonInfoCancel();
        jButtonReload = clientView().getjButtonInfoReload();
        jTextFieldClientSearch = clientView().getjTextFieldClientSearch();

        bookReservationService = new BookReservationServiceImpl();

        loadInfo();

        setEvents();
    }

    private void setEvents() {
        // Table Events
        setTableEvents();
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonInfoRenew.addActionListener(e -> {

        });

        jButtonInfoCancel.addActionListener(e -> {
            BookReservation bookReservation = new BookReservation();
            bookReservationService.cancel(bookReservation);
        });

        jButtonReload.addActionListener(e -> {
            updateTable();
        });
    }

    private void loadInfo() {
        Client client = CurrentSystemUser.getClient();
        jTextFieldClientName.setText(client.getName());
        jTextFieldClientLastName.setText(client.getLastName());
        jTextFieldClientPhone.setText(client.getPhoneNumber());
        jTextFieldClientEmail.setText(client.getEmail());
        jTextFieldClientCode.setText(client.getCode());
        jTextFieldClientAddress.setText(client.getAddress().getAddress());
        jTextFieldClientCity.setText(client.getAddress().getCity());
        jTextFieldClientState.setText(client.getAddress().getState());
        jTextFieldCountry.setText(client.getAddress().getCountry());
        jTextFieldZIPCode.setText(client.getAddress().getZipCode());
        jTextFieldDocVerification.setText(client.getAddress().getAddressProof());
        jCheckBoxValid.setSelected(client.getAddress().isValid());
    }

    private void setTableEvents() {
        updateTable();

        jTextFieldClientSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    doSearch();
                } catch (Exception exp) {
                    exp.printStackTrace();
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                try {
                    doSearch();
                } catch (Exception exp) {
                    exp.printStackTrace();
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });
    }

    private void updateTable() {
        try {
            Client client = CurrentSystemUser.getClient();
            Set<BookReservation> set = bookReservationService.getAll(client);
            clientView().updateReservationTable(set);
        } catch (Exception exp) {
            exp.printStackTrace();
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void updateTable(Set<BookReservation> set) {
        try {
            clientView().updateReservationTable(set);
        } catch (Exception exp) {
            exp.printStackTrace();
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void doSearch() throws Exception {
        String text = jTextFieldClientSearch.getText();

        if (text.equals("")) {
            updateTable();
        } else {
            clientView().actualBookSelected = null;
            Set<BookReservation> resultSet = new HashSet<>();
            Client client = CurrentSystemUser.getClient();
            Set<BookReservation> set = bookReservationService.getAll(client);
            for (BookReservation bookReservation : set) {
                String title = bookReservation.getBookSample().getTitle();
                String ISBN = bookReservation.getClient().getCode();
                String s = title.concat(ISBN);
                if (s.toUpperCase().contains(text.toUpperCase())) {
                    resultSet.add(bookReservation);
                }
            }
            updateTable(resultSet);
        }
    }


    // Getters & Setters
    public ClientJPanelView clientView() {
        return clientController.clientView;
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
        return jCheckBoxValid.isSelected();
    }
}
