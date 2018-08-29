package com.grasset.controller;

import com.grasset.controller.client.ClientController;
import com.grasset.controller.admin.AdminController;
import com.grasset.controller.manager.ManagerController;
import com.grasset.env.CurrentSystemUser;
import com.grasset.view.MainJFrameView;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@Slf4j
public class MainFrameController {

    private final MainJFrameView mainFrameView;
    private final JMenuBar jMenuBar;
    private final JMenuItem jMenuItemLogout;

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
        jMenuBar = mainFrameView.getJMenuBar();
        jMenuItemLogout = mainFrameView.getjMenuItemLogout();

        setMenuEvents();
    }

    private void setMenuEvents() {
        jMenuItemLogout.addActionListener(event -> {
            CurrentSystemUser.set(null);
            CurrentSystemUser.setClient(null);
            CurrentSystemUser.setManagerUser(null);

            showLoginView();
        });
    }

    public void startMainFrame() {        
        showLoginView();
        jMenuBar.setVisible(false);
        mainFrameView.setVisible(true);
    }
    
    public void showLoginView() {
        Controller controller = new LoginController();
        mainFrameView.addPanel(controller.getView());
        
        jMenuBar.setVisible(false);
    }
    
    public void showAdminView() {
        Controller controller = new AdminController();
        mainFrameView.addPanel(controller.getView());
        
        jMenuBar.setVisible(true);
    }
    
    public void showManagerView() {
        Controller controller = new ManagerController();
        mainFrameView.addPanel(controller.getView());
        
        jMenuBar.setVisible(true);
    }
    
    public void showClientView() {
        Controller controller = new ClientController();
        mainFrameView.addPanel(controller.getView());
        
        jMenuBar.setVisible(true);
    }

    public MainJFrameView getMainFrameView() {
        return mainFrameView;
    }
}
