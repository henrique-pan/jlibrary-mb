/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.client;

import javax.swing.*;

import com.grasset.book.*;
import com.grasset.client.Client;
import com.grasset.env.CurrentSystemUser;
import com.grasset.exception.InvalidActionException;
import com.grasset.reservation.BookReservation;
import com.grasset.reservation.BookReservationStatus;
import com.grasset.view.ClientJPanelView;
import com.grasset.view.alerts.JAlertHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author henrique
 */
@Slf4j
public class ClientBookController {

    private final ClientController clientController;

    // JComponents
    private final JTextField jTextFieldBookTitle;
    private final JTextField jTextFieldBookYear;
    private final JTextField jTextFieldBookAuthors;
    private final JCheckBox jCheckBoxRare;
    private final JTextField jTextFieldBookISBN;
    private final JTextField jTextFieldEditor;
    private final JTextField jTextFieldBookEdition;
    private final JTextField jTextFieldEditionYear;
    private final JTextField jTextFieldBookFormat;
    private final JTextField jTextFieldNumberPages;
    private final JTextField jTextFieldTotalSamples;
    private final JTextField jTextFieldOriginalLanguage;
    private final JTextField jTextFieldEditionLanguage;
    private final JButton jButtonBookWaitList;
    private final JButton jButtonBookReserve;
    private final JTextField jTextFieldBookSearch;
    private final JTable jTable;

    //Service
    private final BookService bookService;
    private final BookReservationService bookReservationService;

    public ClientBookController(ClientController clientController) {
        this.clientController = clientController;

        jTextFieldBookTitle = clientView().getjTextFieldBookTitle();
        jTextFieldBookYear = clientView().getjTextFieldBookYear();
        jTextFieldBookAuthors = clientView().getjTextFieldBookAuthors();
        jCheckBoxRare = clientView().getjCheckBoxRare();
        jTextFieldBookISBN = clientView().getjTextFieldBookISBN();
        jTextFieldEditor = clientView().getjTextFieldEditor();
        jTextFieldBookEdition = clientView().getjTextFieldBookEdition();
        jTextFieldEditionYear = clientView().getjTextFieldEditionYear();
        jTextFieldBookFormat = clientView().getjTextFieldBookFormat();
        jTextFieldNumberPages = clientView().getjTextFieldNumberPages();
        jTextFieldTotalSamples = clientView().getjTextFieldTotalSamples();
        jTextFieldOriginalLanguage = clientView().getjTextFieldOriginalLanguage();
        jTextFieldEditionLanguage = clientView().getjTextFieldEditionLanguage();
        jButtonBookWaitList = clientView().getjButtonBookWaitList();
        jButtonBookReserve = clientView().getjButtonBookReserve();
        jTextFieldBookSearch = clientView().getjTextFieldBookSearch();

        jTable = clientView().getjTableBooks();

        bookService = new BookServiceImpl();
        bookReservationService = new BookReservationServiceImpl();

        setEvents();
    }

    private void setEvents() {
        // Table Events
        setTableEvents();
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonBookWaitList.addActionListener(e -> {

        });

        jButtonBookReserve.addActionListener(e -> {
            try {
                clientView().actualBookSelectedVenue = jTable.getSelectedRow();
                if (jTable.getRowCount() > 0 && clientView().actualBookSelectedVenue != null) {
                    clientView().actualBookSelectedVenue = jTable.getSelectedRow();
                    String ISBN = (String) jTable.getModel().getValueAt(clientView().actualBookSelectedVenue, 0);
                    BookEdition bookEdition = (BookEdition) bookService.getBook(ISBN);

                    Client client = CurrentSystemUser.getClient();

                    bookReservationService.reserve(bookEdition, client);
                }
            } catch (InvalidActionException exp) {
                exp.printStackTrace();
                JAlertHelper.showInfo("Attention", exp.getMessage());
            } catch (Exception exp) {
                exp.printStackTrace();
                JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
            }
        });
    }

    private void setTableEvents() {
        updateTable();

        jTextFieldBookSearch.addKeyListener(new java.awt.event.KeyAdapter() {
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
                    clientView().actualBookSelectedVenue = jTable.getSelectedRow();
                    if (jTable.getRowCount() > 0 && clientView().actualBookSelectedVenue != null) {
                        clientView().actualBookSelectedVenue = jTable.getSelectedRow();
                        String ISBN = (String) jTable.getModel().getValueAt(clientView().actualBookSelectedVenue, 0);
                        Book book = bookService.getBook(ISBN);
                        clientView().setBookFields(book);
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
                    clientView().actualBookSelectedVenue = jTable.getSelectedRow();
                    if (jTable.getRowCount() > 0 && clientView().actualBookSelectedVenue != null) {
                        clientView().actualBookSelectedVenue = jTable.getSelectedRow();
                        String ISBN = (String) jTable.getModel().getValueAt(clientView().actualBookSelectedVenue, 0);
                        Book book = bookService.getBook(ISBN);
                        clientView().setBookFields(book);
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
            Set<Book> set = bookService.getBooks();
            clientView().updateBookTable(set);
        } catch (Exception exp) {
            exp.printStackTrace();
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void updateTable(Set<Book> set) {
        try {
            clientView().updateBookTable(set);
        } catch (Exception exp) {
            exp.printStackTrace();
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void doSearch() throws Exception {
        String text = jTextFieldBookSearch.getText();

        if (text.equals("")) {
            updateTable();
        } else {
            clientView().actualBookSelectedVenue = null;
            Set<Book> resultSet = new HashSet<>();
            Set<Book> set = bookService.getBooks();
            for (Book book : set) {
                BookEdition bookEdition = (BookEdition) book;
                String title = book.getTitle();
                String ISBN = book.getISBN();
                String publisher = bookEdition.getPublisher().getName();
                String s = title.concat(ISBN).concat(publisher).concat(publisher);
                if (s.toUpperCase().contains(text.toUpperCase())) {
                    resultSet.add(bookEdition);
                }
            }
            updateTable(resultSet);
        }
    }

    private void clear() {
        jTextFieldBookTitle.setText("");
        jTextFieldBookYear.setText("");
        jTextFieldBookAuthors.setText("");
        jTextFieldBookISBN.setText("");
        jTextFieldEditor.setText("");
        jTextFieldBookEdition.setText("");
        jTextFieldEditionYear.setText("");
        jTextFieldBookFormat.setText("");
        jTextFieldNumberPages.setText("");
        jTextFieldTotalSamples.setText("");
        jTextFieldOriginalLanguage.setText("");
        jTextFieldEditionLanguage.setText("");
        jCheckBoxRare.setSelected(false);
    }

    // Getters & Setters
    public ClientJPanelView clientView() {
        return clientController.clientView;
    }

    public String bookTitle() {
        return jTextFieldBookTitle.getText();
    }

    public String bookYear() {
        return jTextFieldBookYear.getText();
    }

    public List<String> bookAuthors() {
        jTextFieldBookAuthors.getText();
        return new ArrayList<String>();
    }

    public boolean isRare() {
        return jCheckBoxRare.isSelected();
    }

    public String bookISBN() {
        return jTextFieldBookISBN.getText();
    }

    public String bookEditor() {
        return jTextFieldEditor.getText();
    }

    public String edition() {
        return jTextFieldBookEdition.getText();
    }

    public String editionYear() {
        return jTextFieldEditionYear.getText();
    }

    public String editionFormat() {
        return jTextFieldBookFormat.getText();
    }

    public int editionNumberPages() {
        return Integer.parseInt(jTextFieldNumberPages.getText());
    }

    public int totalSamples() {
        return Integer.parseInt(jTextFieldTotalSamples.getText());
    }

    public String bookOriginalLanguage() {
        return jTextFieldOriginalLanguage.getText();
    }

    public String editionLanguage() {
        return jTextFieldEditionLanguage.getText();
    }


}
