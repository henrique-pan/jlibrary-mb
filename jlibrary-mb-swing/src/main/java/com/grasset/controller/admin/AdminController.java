/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.admin;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.grasset.ManagerUserServiceImpl;
import com.grasset.ManagerUserService;
import com.grasset.SystemUserService;
import com.grasset.SystemUserServiceImpl;
import com.grasset.controller.Controller;
import com.grasset.controller.LoginController;
import com.grasset.user.ManagerUser;
import com.grasset.view.AdminJPanelView;
import com.grasset.view.alerts.JAlertHelper;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author henrique
 */
@Slf4j
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
    
    //Service
    private final ManagerUserService manageService;

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
        
        manageService = new ManagerUserServiceImpl();
        
        setEvents();
    }

    private void setEvents() {
        // Button Events
        setButtonEvents();
    }
    
    private void setButtonEvents() {
        jButtonSave.addActionListener(e -> {
        		log.info("Start save process.");
        		
        		ManagerUser managerUser = new ManagerUser();
        		
        		managerUser.setCode(adminCode());
        		managerUser.setName(adminName());
        		managerUser.setLastName(adminLastName());
        		managerUser.setActive(adminStatus());
        		
        		// Entries validation
        		if(managerUser.getCode() == null || managerUser.getCode().isEmpty()) {
        			JAlertHelper.showError("Erreur de Code", "Le code de l'utilisateur est obligatoire.");
                return;
        		}
        		
        		if(managerUser.getName() == null || managerUser.getName().isEmpty()) {
        			JAlertHelper.showError("Erreur de Prénom", "Le Prénom de l'utilisateur est obligatoire.");
                return;
        		}
        		
        		if(managerUser.getLastName() == null || managerUser.getLastName().isEmpty()) {
        			JAlertHelper.showError("Erreur de Nom de famille", "Le Nom de famille de l'utilisateur est obligatoire.");
                return;
        		}
        		log.info("Code: [{}] Name: [{}] LastName [{}] Active [{}]", managerUser.getCode(), managerUser.getName(), managerUser.getLastName(), managerUser.isActive());
        		// Entries validation
        		
        		try {
        			manageService.save(managerUser);
        			
        		} catch (Exception exp) {
					JAlertHelper.showError("Erreur de Enregistrement", "Erreur pour faire le enregistrement: " + exp.getMessage());
			}
        		
        });
        
        jButtonDelete.addActionListener(e -> {
	    		log.info("Start delete process.");
			
			ManagerUser managerUser = new ManagerUser();
			
			managerUser.setCode(adminCode());
			managerUser.setName(adminName());
			managerUser.setLastName(adminLastName());
			managerUser.setActive(adminStatus());
			
			// Entries validation
			if(managerUser.getCode() == null || managerUser.getCode().isEmpty()) {
				JAlertHelper.showError("Erreur de Code", "Le code de l'utilisateur est obligatoire.");
	        return;
			}
			
			if(managerUser.getName() == null || managerUser.getName().isEmpty()) {
				JAlertHelper.showError("Erreur de Prénom", "Le Prénom de l'utilisateur est obligatoire.");
	        return;
			}
			
			if(managerUser.getLastName() == null || managerUser.getLastName().isEmpty()) {
				JAlertHelper.showError("Erreur de Nom de famille", "Le Nom de famille de l'utilisateur est obligatoire.");
	        return;
			}
			log.info("Code: [{}] Name: [{}] LastName [{}] Active [{}]", managerUser.getCode(), managerUser.getName(), managerUser.getLastName(), managerUser.isActive());
			// Entries validation
			
			try {
				manageService.delete(managerUser);
				
			} catch (Exception exp) {
				JAlertHelper.showError("Erreur de Enlèvement", "Erreur pour faire le Enlèvement: " + exp.getMessage());
			}
        });
        
        jButtonClear.addActionListener(e -> {
        		jTextFieldCode.setText("");
        		jTextFieldLastName.setText("");
        		jTextFieldName.setText("");
        		jTextFieldSearch.setText("");
        		jCheckBoxStatus.setSelected(false);        		
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
