package com.grasset.controller;

import com.grasset.controller.client.ClientController;
import com.grasset.controller.admin.AdminController;
import com.grasset.controller.manager.ManagerController;
import com.grasset.view.MainJFrameView;

public class MainFrameController {

    private final MainJFrameView mainFrameView;

    // Singleton
    private static MainFrameController mainFrameController;
    
    public static MainFrameController getInstance() {
        if(mainFrameController == null) {
            mainFrameController = new MainFrameController();
        }
        return mainFrameController;
    }
    
    private MainFrameController() {        
        mainFrameView = new MainJFrameView();        
    }

    public void startMainFrame() {        
        showLoginView();
        mainFrameView.setVisible(true);
    }
    
    public void showLoginView() {
        Controller controller = new LoginController();
        mainFrameView.addPanel(controller.getView());
    }
    
    public void showAdminView() {
        Controller controller = new AdminController();
        mainFrameView.addPanel(controller.getView());
    }
    
    public void showManagerView() {
        Controller controller = new ManagerController();
        mainFrameView.addPanel(controller.getView());
    }
    
    public void showUserView() {
        Controller controller = new ClientController();
        mainFrameView.addPanel(controller.getView());
    }
    
}
