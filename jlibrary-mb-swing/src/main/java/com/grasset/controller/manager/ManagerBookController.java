/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class ManagerBookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerBookController.class);

    private final ManagerController managerController;

    private final JTextField jTextFieldBookTitle;
    private final JTextField jTextFieldBookYear;
    private final JTextField jTextFieldBookAuthors;
    private final JTextField jTextFieldBookISBN;
    private final JTextField jTextFieldEditor;
    private final JTextField jTextFieldBookEdition;
    private final JTextField jTextFieldEditionYear;
    private final JTextField jTextFieldBookFormat;
    private final JTextField jTextFieldNumberPages;
    private final JTextField jTextFieldPenalityPrice;
    private final JTextField jTextFieldBookPrice;
    private final JTextField jTextFieldTotalSamples;
    private final JTextField jTextFieldOriginalLanguage;
    private final JTextField jTextFieldEditionLanguage;
    private final JTextField jTextFieldSampleCode;
    private final JCheckBox jCheckBoxBookRare;
    private final JButton jButtonBookSave;
    private final JButton jButtonBookDelete;
    private final JButton jButtonBookDetails;
    private final JButton jButtonBookReserve;
    private final JButton jButtonBookClear;
    private final JTextField jTextFieldBookSearch;

    public ManagerBookController(ManagerController managerController) {
        this.managerController = managerController;

        jTextFieldBookTitle = managerView().getjTextFieldBookTitle();
        jTextFieldBookYear = managerView().getjTextFieldBookYear();
        jTextFieldBookAuthors = managerView().getjTextFieldBookAuthors();
        jTextFieldBookISBN = managerView().getjTextFieldBookISBN();
        jTextFieldEditor = managerView().getjTextFieldEditor();
        jTextFieldBookEdition = managerView().getjTextFieldBookEdition();
        jTextFieldEditionYear = managerView().getjTextFieldEditionYear();
        jTextFieldBookFormat = managerView().getjTextFieldBookFormat();
        jTextFieldNumberPages = managerView().getjTextFieldNumberPages();
        jTextFieldPenalityPrice = managerView().getjTextFieldPenalityPrice();
        jTextFieldBookPrice = managerView().getjTextFieldBookPrice();
        jTextFieldTotalSamples = managerView().getjTextFieldTotalSamples();
        jTextFieldOriginalLanguage = managerView().getjTextFieldOriginalLanguage();
        jTextFieldEditionLanguage = managerView().getjTextFieldEditionLanguage();
        jTextFieldSampleCode = managerView().getjTextFieldSampleCode();
        jCheckBoxBookRare = managerView().getjCheckBoxBookRare();
        jButtonBookSave = managerView().getjButtonBookSave();
        jButtonBookDelete = managerView().getjButtonBookDelete();
        jButtonBookDetails = managerView().getjButtonBookDetails();
        jButtonBookReserve = managerView().getjButtonBookReserve();
        jButtonBookClear = managerView().getjButtonBookClear();
        
        jTextFieldBookSearch = managerView().getjTextFieldBookSearch();
        
        setEvents();
    }
    
    private void setEvents() {
        // Button Events
        setButtonEvents();
    }
    
    private void setButtonEvents() {
        jButtonBookSave.addActionListener(e -> {
            
        });
        
        jButtonBookDelete.addActionListener(e -> {
            
        });
        
        jButtonBookDetails.addActionListener(e -> {
            
        });
        
        jButtonBookReserve.addActionListener(e -> {
            
        });
        
        jButtonBookClear.addActionListener(e -> {
            
        });
    }

    // Getters & Setters
    public ManagerJPanelView managerView() {
        return managerController.managerView;
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

    public BigDecimal editionPenalityPrice() {
        return new BigDecimal(jTextFieldPenalityPrice.getText());
    }
    
    public BigDecimal editionBookPrice() {
        return new BigDecimal(jTextFieldBookPrice.getText());
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
    
    public String sampleCode() {
        return jTextFieldSampleCode.getText();
    }
    
    public boolean isRare() {
        return jCheckBoxBookRare.isSelected();
    }
}
