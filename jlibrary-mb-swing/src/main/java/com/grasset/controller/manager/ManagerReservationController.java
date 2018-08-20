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

import java.util.Date;

/**
 *
 * @author henrique
 */
public class ManagerReservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerReservationController.class);

    private final ManagerController managerController;

    // JComponents
    private final JTextField jTextFieldReservationBookISBN;
    private final JTextField jTextFieldReservationBookName;
    private final JTextField jTextFieldReservationBookCodeSample;
    private final JCheckBox jCheckBoxReservationRare;
    private final JTextField jTextFieldReservationClientCode;
    private final JTextField jTextFieldReservationClientName;
    private final JTextField jTextFieldReservationDate;
    private final JTextField jTextFieldReservationReturnDate;
    private final JTextField jTextFieldReservationStatus;
    private final JButton jButtonReservationFinish;
    private final JButton jButtonReservationCancel;
    private final JButton jButtonReservationRenew;
    private final JButton jButtonReservationClear;
    private final JTextField jTextFieldReservationSearch;

    public ManagerReservationController(ManagerController managerController) {
        this.managerController = managerController;

        jTextFieldReservationBookISBN = managerView().getjTextFieldReservationBookISBN();
        jTextFieldReservationBookName = managerView().getjTextFieldReservationBookName();
        jTextFieldReservationBookCodeSample = managerView().getjTextFieldReservationBookCodeSample();
        jCheckBoxReservationRare = managerView().getjCheckBoxReservationRare();
        jTextFieldReservationClientCode = managerView().getjTextFieldReservationClientCode();
        jTextFieldReservationClientName = managerView().getjTextFieldReservationClientName();
        jTextFieldReservationDate = managerView().getjTextFieldReservationDate();
        jTextFieldReservationReturnDate = managerView().getjTextFieldReservationReturnDate();
        jTextFieldReservationStatus = managerView().getjTextFieldReservationStatus();
        jButtonReservationFinish = managerView().getjButtonReservationFinish();
        jButtonReservationCancel = managerView().getjButtonReservationCancel();
        jButtonReservationRenew = managerView().getjButtonReservationRenew();
        jButtonReservationClear = managerView().getjButtonReservationClear();
        jTextFieldReservationSearch = managerView().getjTextFieldReservationSearch();

        setEvents();
    }

    private void setEvents() {
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonReservationFinish.addActionListener(e -> {

        });

        jButtonReservationCancel.addActionListener(e -> {

        });

        jButtonReservationRenew.addActionListener(e -> {

        });

        jButtonReservationClear.addActionListener(e -> {
        		jTextFieldReservationBookISBN.setText("");
            jTextFieldReservationBookName.setText("");
            jTextFieldReservationBookCodeSample.setText("");
            jCheckBoxReservationRare.setSelected(false);
            jTextFieldReservationClientCode.setText("");
            jTextFieldReservationClientName.setText("");
            jTextFieldReservationDate.setText("");
            jTextFieldReservationReturnDate.setText("");
            jTextFieldReservationStatus.setText("");
        });
    }

    // Getters & Setters
    public ManagerJPanelView managerView() {
        return managerController.managerView;
    }

    public String bookISBN() {
        return jTextFieldReservationBookISBN.getText();
    }

    public String bookName() {
        return jTextFieldReservationBookName.getText();
    }

    public String bookSampleCode() {
        return jTextFieldReservationBookCodeSample.getText();
    }

    public boolean bookIsRare() {
        return jCheckBoxReservationRare.isSelected();
    }

    public String clientCode() {
        return jTextFieldReservationClientCode.getText();
    }

    public String clientName() {
        return jTextFieldReservationClientName.getText();
    }

    public Date reservationDate() {
        jTextFieldReservationDate.getText();
        return new Date();
    }

    public Date reservationReturnDate() {
        jTextFieldReservationReturnDate.getText();
        return new Date();
    }

    public String reservationStatus() {
        return jTextFieldReservationStatus.getText();
    }
}
