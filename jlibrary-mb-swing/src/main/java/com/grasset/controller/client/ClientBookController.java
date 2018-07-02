/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.client;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.grasset.view.ClientJPanelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author henrique
 */
public class ClientBookController extends ClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientBookController.class);

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
    private final JButton jButtonBookClear;
    private final JTextField jTextFieldBookSearch;

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
        jButtonBookClear = clientView().getjButtonBookClear();
        jTextFieldBookSearch = clientView().getjTextFieldBookSearch();

        setEvents();
    }

    private void setEvents() {
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonBookWaitList.addActionListener(e -> {

        });

        jButtonBookReserve.addActionListener(e -> {

        });

        jButtonBookClear.addActionListener(e -> {

        });
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
