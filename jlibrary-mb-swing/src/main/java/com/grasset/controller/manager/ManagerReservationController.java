/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.manager;

import javax.swing.*;

import com.grasset.book.Book;
import com.grasset.book.BookReservationService;
import com.grasset.book.BookReservationServiceImpl;
import com.grasset.client.Client;
import com.grasset.env.CurrentSystemUser;
import com.grasset.exception.InvalidActionException;
import com.grasset.reservation.BookReservation;
import com.grasset.reservation.BookReservationStatus;
import com.grasset.view.ManagerJPanelView;
import com.grasset.view.alerts.JAlertHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author henrique
 */
@Slf4j
public class ManagerReservationController {

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
    private final JTable jTable;

    private BookReservationService bookReservationService;

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

        bookReservationService = new BookReservationServiceImpl();

        jTable = managerView().getjTableReservations();

        setEvents();
    }

    private void setEvents() {
        // Table Events
        setTableEvents();
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonReservationFinish.addActionListener(e -> {
            try {
                managerView().actualReservationSelected = jTable.getSelectedRow();
                if (jTable.getRowCount() > 0 && managerView().actualReservationSelected != null) {
                    managerView().actualReservationSelected = jTable.getSelectedRow();
                    Integer idBookReservation = (Integer) jTable.getModel().getValueAt(managerView().actualReservationSelected, 0);
                    BookReservation bookReservation = bookReservationService.getById(idBookReservation);

                    if(bookReservation.getReservationStatus().equals(BookReservationStatus.CANCELED) ||
                            bookReservation.getReservationStatus().equals(BookReservationStatus.COMPLETED) ||
                            bookReservation.getReservationStatus().equals(BookReservationStatus.COMPLETED_WITH_PENALTY) ||
                            bookReservation.getReservationStatus().equals(BookReservationStatus.IN_PROGRESS)) {
                        throw new InvalidActionException("Status invalid pour canceler");
                    } else {
                        bookReservationService.finnish(bookReservation);
                    }
                }
                updateTable();
            } catch (InvalidActionException exp) {
                exp.printStackTrace();
                JAlertHelper.showInfo("Attention", exp.getMessage());
            } catch (Exception exp) {
                exp.printStackTrace();
                JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
            }
        });

        jButtonReservationCancel.addActionListener(e -> {
            try {
                managerView().actualReservationSelected = jTable.getSelectedRow();
                if (jTable.getRowCount() > 0 && managerView().actualReservationSelected != null) {
                    managerView().actualReservationSelected = jTable.getSelectedRow();
                    Integer idBookReservation = (Integer) jTable.getModel().getValueAt(managerView().actualReservationSelected, 0);
                    BookReservation bookReservation = bookReservationService.getById(idBookReservation);

                    if(bookReservation.getReservationStatus().equals(BookReservationStatus.CANCELED) ||
                            bookReservation.getReservationStatus().equals(BookReservationStatus.COMPLETED) ||
                            bookReservation.getReservationStatus().equals(BookReservationStatus.COMPLETED_WITH_PENALTY) ||
                            bookReservation.getReservationStatus().equals(BookReservationStatus.IN_PROGRESS)) {
                        throw new InvalidActionException("Status invalid pour canceler");
                    } else {
                        bookReservationService.cancel(bookReservation);
                    }
                }
                updateTable();
            } catch (InvalidActionException exp) {
                exp.printStackTrace();
                JAlertHelper.showInfo("Attention", exp.getMessage());
            } catch (Exception exp) {
                exp.printStackTrace();
                JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
            }
        });

        jButtonReservationRenew.addActionListener(e -> {
            try {
                managerView().actualReservationSelected = jTable.getSelectedRow();
                if (jTable.getRowCount() > 0 && managerView().actualReservationSelected != null) {
                    managerView().actualReservationSelected = jTable.getSelectedRow();
                    Integer idBookReservation = (Integer) jTable.getModel().getValueAt(managerView().actualReservationSelected, 0);
                    BookReservation bookReservation = bookReservationService.getById(idBookReservation);

                    if(!bookReservation.getReservationStatus().equals(BookReservationStatus.IN_PROGRESS)) {
                        throw new InvalidActionException("Status invalid pour renouveler");
                    } else {
                        bookReservationService.renew(bookReservation);
                    }
                    updateTable();
                }
            } catch (InvalidActionException exp) {
                exp.printStackTrace();
                JAlertHelper.showInfo("Attention", exp.getMessage());
            } catch (Exception exp) {
                exp.printStackTrace();
                JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
            }
        });

        jButtonReservationClear.addActionListener(e -> {
            clear();
        });
    }

    private void setTableEvents() {
        updateTable();

        jTextFieldReservationSearch.addKeyListener(new java.awt.event.KeyAdapter() {
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

        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                try {
                    managerView().actualReservationSelected = jTable.getSelectedRow();
                    if (jTable.getRowCount() > 0 && managerView().actualReservationSelected != null) {
                        managerView().actualReservationSelected = jTable.getSelectedRow();
                        Integer idBookReservation = (Integer) jTable.getModel().getValueAt(managerView().actualReservationSelected, 0);
                        BookReservation bookReservation = bookReservationService.getById(idBookReservation);
                        managerView().setReservationsFields(bookReservation);
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });

        jTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    managerView().actualReservationSelected = jTable.getSelectedRow();
                    if (jTable.getRowCount() > 0 && managerView().actualReservationSelected != null) {
                        managerView().actualReservationSelected = jTable.getSelectedRow();
                        Integer idBookReservation = (Integer) jTable.getModel().getValueAt(managerView().actualReservationSelected, 0);
                        BookReservation bookReservation = bookReservationService.getById(idBookReservation);
                        managerView().setReservationsFields(bookReservation);
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });
    }

    private void updateTable() {
        try {
            Set<BookReservation> set = bookReservationService.getAll();
            managerView().updateReservationsTable(set);
        } catch (Exception exp) {
            exp.printStackTrace();
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void updateTable(Set<BookReservation> set) {
        try {
            managerView().updateReservationsTable(set);
        } catch (Exception exp) {
            exp.printStackTrace();
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void doSearch() throws Exception {
        String text = jTextFieldReservationSearch.getText();

        if (text.equals("")) {
            updateTable();
        } else {
            managerView().actualReservationSelected = null;
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

    private void clear() {
        jTextFieldReservationBookISBN.setText("");
        jTextFieldReservationBookName.setText("");
        jTextFieldReservationBookCodeSample.setText("");
        jTextFieldReservationClientCode.setText("");
        jTextFieldReservationClientName.setText("");
        jTextFieldReservationDate.setText("");
        jTextFieldReservationReturnDate.setText("");
        jTextFieldReservationStatus.setText("");
        jCheckBoxReservationRare.setSelected(false);
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
