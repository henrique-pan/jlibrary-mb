/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.manager;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.grasset.AuthorService;
import com.grasset.AuthorServiceImpl;
import com.grasset.BookEditionService;
import com.grasset.BookEditionServiceImpl;
import com.grasset.BookSampleService;
import com.grasset.BookSampleServiceImpl;
import com.grasset.BookService;
import com.grasset.BookServiceImpl;
import com.grasset.PublisherService;
import com.grasset.PublisherServiceImpl;
import com.grasset.book.Author;
import com.grasset.book.Book;
import com.grasset.book.BookEdition;
import com.grasset.book.BookSample;
import com.grasset.book.Publisher;
import com.grasset.view.ManagerJPanelView;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.MonetaryAmountDecimalFormatBuilder;
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
    private final JTextField jTextFieldPenaltyPrice;
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
    
    private PublisherService publisherService;
    private AuthorService authorService;
    private BookService bookService;
    private BookEditionService bookEditionService;
    private BookSampleService bookSampleService;
    
    //private static CurrencyUnit DOLLAR = (CurrencyUnit) MonetaryAmountDecimalFormatBuilder.of(Locale.CANADA);

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
        jTextFieldSampleCode = managerView().getjTextFieldSampleCode();
        jCheckBoxBookRare = managerView().getjCheckBoxBookRare();
        jButtonBookSave = managerView().getjButtonBookSave();
        jButtonBookDelete = managerView().getjButtonBookDelete();
        jButtonBookDetails = managerView().getjButtonBookDetails();
        jButtonBookReserve = managerView().getjButtonBookReserve();
        jButtonBookClear = managerView().getjButtonBookClear();
        
        jTextFieldBookSearch = managerView().getjTextFieldBookSearch();
        
        publisherService = new PublisherServiceImpl();
        authorService = new AuthorServiceImpl();
        bookService = new BookServiceImpl();
        bookEditionService = new BookEditionServiceImpl();
        bookSampleService = new BookSampleServiceImpl();
        
        setEvents();
    }
    
    private void setEvents() {
        // Button Events
        setButtonEvents();
    }
    
    private void setButtonEvents() {
        jButtonBookSave.addActionListener(e -> {
        		Author author = new Author();
        		Book book = new Book();
        		BookEdition bookEdition = new BookEdition();
        		BookSample bookSample = new BookSample();
        		Publisher publisher = new Publisher();
        		ManagerHelper mh = new ManagerHelper();
        		
        		//BOOK
        		book.setTitle(bookTitle());
        		if(mh.yearValidation(bookYear()))
        			book.setBookYear(Integer.parseInt(bookYear()));
        		book.setOriginalLanguage(bookOriginalLanguage());
        		
        		int idBook = bookService.save(book);
        		
        		//BOOKEDITION
        		bookEdition.setIdBook(idBook);
        		bookEdition.setTitle(bookTitle());
        		
        		if(mh.yearValidation(bookYear()))
        			bookEdition.setBookYear(Integer.parseInt(bookYear()));
        		
        		//AUTHOR
    			String[] authors = bookAuthors().split(",");
    			Set<Author> hsAuthor = new HashSet<>();
    			for(int i = 0; i < authors.length; i++) {
    				author.setName(authors[i]);
    				author = authorService.save(author);
    				hsAuthor.add(author);
    			}
    			bookEdition.setAuthors(hsAuthor);
        		
    			if(mh.isbnValidation(bookISBN()))
    				bookEdition.setISBN(bookISBN());
        		
    			//PUBLISHER
        		publisher.setName(bookEditor());
        		publisher = publisherService.save(publisher);
        		bookEdition.setPublisher(publisher);
        		
        		bookEdition.setEdition(edition());
        		
        		if(mh.yearValidation(editionYear()))
        			bookEdition.setEditionYear(Integer.parseInt(editionYear()));
        		
        		bookEdition.setFormat(editionFormat());
        		bookEdition.setTotalPages(editionNumberPages());
        		
        		//bookEdition.setPenaltyPrice(Money.of(editionPenaltyPrice(), DOLLAR));
        		//bookEdition.setBookPrice(Money.of(editionBookPrice(), DOLLAR));
        		
        		bookEdition.setOriginalLanguage(bookOriginalLanguage());
        		bookEdition.setEditionLanguage(editionLanguage());
        	
        		int idBookEdition = bookEditionService.save(bookEdition);
        		
        		bookEdition.setIdBookEdition(idBookEdition);
        		
        		//BOOKSAMPLE
        		bookSample.setIdBookEdition(bookEdition.getIdBookEdition());
        		bookSample.setCodeSample(sampleCode());

        		bookSampleService.save(bookSample);
            
        });
        
        jButtonBookDelete.addActionListener(e -> {
            
        });
        
        jButtonBookDetails.addActionListener(e -> {
            
        });
        
        jButtonBookReserve.addActionListener(e -> {
            
        });
        
        jButtonBookClear.addActionListener(e -> {
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
            jTextFieldSampleCode.setText("");
            jCheckBoxBookRare.setSelected(false);
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
    
    public String bookAuthors() {
        return jTextFieldBookAuthors.getText();
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

    public BigDecimal editionPenaltyPrice() {
        return new BigDecimal(jTextFieldPenaltyPrice.getText());
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
