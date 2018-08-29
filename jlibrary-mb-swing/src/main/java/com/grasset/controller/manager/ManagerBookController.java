/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.swing.*;

import com.grasset.book.*;
import com.grasset.client.Address;
import com.grasset.client.Client;
import com.grasset.client.ClientService;
import com.grasset.controller.MainFrameController;
import com.grasset.view.JDialogBookSample;
import com.grasset.view.ManagerJPanelView;
import com.grasset.view.alerts.JAlertHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author henrique
 */
@Slf4j
public class ManagerBookController {

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
    private final JTextField jTextFieldPenaltyPrice;
    private final JTextField jTextFieldBookPrice;
    private final JTextField jTextFieldTotalSamples;
    private final JTextField jTextFieldOriginalLanguage;
    private final JTextField jTextFieldEditionLanguage;
    private final JCheckBox jCheckBoxBookRare;
    private final JButton jButtonBookSave;
    private final JButton jButtonBookDelete;
    private final JButton jButtonBookDetails;
    private final JButton jButtonBookReserve;
    private final JButton jButtonBookClear;
    private final JTextField jTextFieldBookSearch;
    private final JTable jTableBook;

    //Service
    private final BookService bookService;

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
        jTextFieldPenaltyPrice = managerView().getjTextFieldPenalityPrice();
        jTextFieldBookPrice = managerView().getjTextFieldBookPrice();
        jTextFieldTotalSamples = managerView().getjTextFieldTotalSamples();
        jTextFieldOriginalLanguage = managerView().getjTextFieldOriginalLanguage();
        jTextFieldEditionLanguage = managerView().getjTextFieldEditionLanguage();
        jCheckBoxBookRare = managerView().getjCheckBoxBookRare();
        jButtonBookSave = managerView().getjButtonBookSave();
        jButtonBookDelete = managerView().getjButtonBookDelete();
        jButtonBookDetails = managerView().getjButtonBookDetails();
        jButtonBookReserve = managerView().getjButtonBookReserve();
        jButtonBookClear = managerView().getjButtonBookClear();
        jTableBook = managerView().getjTableBooks();

        jTextFieldBookSearch = managerView().getjTextFieldBookSearch();

        bookService = new BookServiceImpl();

        setEvents();
    }

    private void setEvents() {
        // Table Events
        setTableEvents();
        // Button Events
        setButtonEvents();
    }

    private void setButtonEvents() {
        jButtonBookSave.addActionListener(e -> {
            try {
                log.info("Start save process.");

                BookSample bookSample = new BookSample();
                bookSample.setTitle(bookTitle());
                bookSample.setBookYear(bookYear());
                bookSample.setISBN(bookISBN());

                Set<Author> authors = new HashSet<>();
                List<String> authorsName = bookAuthors();
                for (String authorName : authorsName) {
                    Author author = new Author();
                    author.setName(authorName);
                    authors.add(author);
                }
                bookSample.setAuthors(authors);

                Publisher publisher = new Publisher();
                publisher.setName(bookEditor());
                bookSample.setPublisher(publisher);

                bookSample.setEdition(edition());
                bookSample.setEditionYear(editionYear());
                bookSample.setFormat(editionFormat());
                bookSample.setTotalPages(editionNumberPages());
                bookSample.setBookPrice(editionBookPrice());
                bookSample.setPenaltyPrice(editionPenaltyPrice());
                bookSample.setOriginalLanguage(bookOriginalLanguage());
                bookSample.setEditionLanguage(editionLanguage());
                bookSample.setRare(isRare());

                // Entries validation

                log.info("idBook: [{}] idBookEdition: [{}] idBookSample [{}]", bookSample.getIdBook(), bookSample.getIdBookEdition(), bookSample.getIdBookSample());
                // Entries validation

                bookService.save(bookSample);
                updateTable();
            } catch (Exception exp) {
                JAlertHelper.showError("Erreur de Enregistrement", "Erreur pour faire le enregistrement: " + exp.getMessage());
            }
        });

        jButtonBookDelete.addActionListener(e -> {
            try {
                managerView().actualBookSelectedVenue = jTableBook.getSelectedRow();
                if (jTableBook.getRowCount() > 0 && managerView().actualBookSelectedVenue != null) {
                    String ISBN = (String) jTableBook.getModel().getValueAt(managerView().actualBookSelectedVenue, 0);
                    BookEdition bookEdition = (BookEdition) bookService.getBook(ISBN);
                    bookService.delete(bookEdition);
                    updateTable();
                }
            } catch (Exception exp) {
                exp.printStackTrace();
                JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
            }
        });

        jButtonBookDetails.addActionListener(e -> {
            try {
                managerView().actualBookSelectedVenue = jTableBook.getSelectedRow();
                if (jTableBook.getRowCount() > 0 && managerView().actualBookSelectedVenue != null) {
                    String ISBN = (String) jTableBook.getModel().getValueAt(managerView().actualBookSelectedVenue, 0);
                    BookEdition bookEdition = (BookEdition) bookService.getBook(ISBN);
                    MainFrameController mainFrameController = MainFrameController.getInstance();
                    JDialogBookSample jDialogFavoriteMigration = new JDialogBookSample(mainFrameController.getMainFrameView(), true, bookEdition);
                    jDialogFavoriteMigration.setVisible(true);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
                JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
            }
        });

        jButtonBookReserve.addActionListener(e -> {

        });

        jButtonBookClear.addActionListener(e -> {
            clear();
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

        jTableBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                try {
                    managerView().actualBookSelectedVenue = jTableBook.getSelectedRow();
                    if (jTableBook.getRowCount() > 0 && managerView().actualBookSelectedVenue != null) {
                        managerView().actualBookSelectedVenue = jTableBook.getSelectedRow();
                        String ISBN = (String) jTableBook.getModel().getValueAt(managerView().actualBookSelectedVenue, 0);
                        Book book = bookService.getBook(ISBN);
                        managerView().setBookFields(book, "3");
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                    JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
                }
            }
        });

        jTableBook.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    managerView().actualBookSelectedVenue = jTableBook.getSelectedRow();
                    if (jTableBook.getRowCount() > 0 && managerView().actualBookSelectedVenue != null) {
                        managerView().actualBookSelectedVenue = jTableBook.getSelectedRow();
                        String ISBN = (String) jTableBook.getModel().getValueAt(managerView().actualBookSelectedVenue, 0);
                        Book book = bookService.getBook(ISBN);
                        managerView().setBookFields(book, "3");
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
            managerView().updateBookTable(set);
        } catch (Exception exp) {
            exp.printStackTrace();
            JAlertHelper.showError("Erreur pour remplir table", "Erreur pour creer table: " + exp.getMessage());
        }
    }

    private void updateTable(Set<Book> set) {
        try {
            managerView().updateBookTable(set);
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
            managerView().actualBookSelectedVenue = null;
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
        jTextFieldPenaltyPrice.setText("");
        jTextFieldBookPrice.setText("");
        jTextFieldTotalSamples.setText("");
        jTextFieldOriginalLanguage.setText("");
        jTextFieldEditionLanguage.setText("");

        jCheckBoxBookRare.setSelected(false);
    }

    // Getters & Setters
    public ManagerJPanelView managerView() {
        return managerController.managerView;
    }

    public String bookTitle() {
        return jTextFieldBookTitle.getText();
    }

    public Integer bookYear() {
        return Integer.parseInt(jTextFieldBookYear.getText());
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

    public Integer editionYear() {
        return Integer.parseInt(jTextFieldEditionYear.getText());
    }

    public String editionFormat() {
        return jTextFieldBookFormat.getText();
    }

    public int editionNumberPages() {
        return Integer.parseInt(jTextFieldNumberPages.getText());
    }

    public Double editionPenaltyPrice() {
        return Double.parseDouble(jTextFieldPenaltyPrice.getText());
    }

    public Double editionBookPrice() {
        return Double.parseDouble(jTextFieldBookPrice.getText());
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

    public boolean isRare() {
        return jCheckBoxBookRare.isSelected();
    }
}
