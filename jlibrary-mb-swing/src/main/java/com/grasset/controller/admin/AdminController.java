/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.admin;

import com.grasset.controller.Controller;
import com.grasset.view.AdminJPanelView;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author henrique
 */
public class AdminController extends Controller {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    // JPanel
    private final AdminJPanelView adminView;
    
    // JComponents
    private final JTextField jTextFieldCode;
    private final JTextField jTextFieldName;
    private final JTextField jTextFieldLastName;
    private final JCheckBox jCheckBoxStatus;
    private final JButton jButtonSave;
    private final JButton jButtonDelete;
    private final JButton jButtonClear;  
    private final JTextField jTextFieldSearch;

    public AdminController() {
        adminView = new AdminJPanelView();
        
        jTextFieldCode = adminView.getjTextFieldCode();
        jTextFieldName = adminView.getjTextFieldName();
        jTextFieldLastName = adminView.getjTextFieldLastName();
        jCheckBoxStatus = adminView.getjCheckBoxStatus();
        jButtonSave = adminView.getjButtonSave();
        jButtonDelete = adminView.getjButtonDelete();
        jButtonClear = adminView.getjButtonClear();
        jTextFieldSearch = adminView.getjTextFieldSearch();
        
        setEvents();
    }

    private void setEvents() {
        // Button Events
        setButtonEvents();
    }
    
    private void setButtonEvents() {
        jButtonSave.addActionListener(e -> {
            
        });
        
        jButtonDelete.addActionListener(e -> {
            
        });
        
        jButtonClear.addActionListener(e -> {
            
        });
    }
    
    // Getters & Setters
    @Override
    public AdminJPanelView getView() {
        return adminView;
    }
    
    public String adminCode() {
        return jTextFieldCode.getText();
    }
    
    public String adminName() {
        return jTextFieldName.getText();
    }
    
    public String adminLastName() {
        return jTextFieldLastName.getText();
    }
    
    public boolean adminStatus() {
        return jCheckBoxStatus.isSelected();
    }
}
