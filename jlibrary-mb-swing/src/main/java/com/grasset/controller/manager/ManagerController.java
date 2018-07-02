/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.manager;

import com.grasset.controller.Controller;
import com.grasset.view.ManagerJPanelView;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author henrique
 */
public class ManagerController extends Controller {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerController.class);

    // JPanel
    protected final ManagerJPanelView managerView;

    //Controllers
    private ManagerBookController bookController;
    private ManagerClientController clientController;
    private ManagerReservationController reservationController;

    public ManagerController() {
        managerView = new ManagerJPanelView();

        bookController = new ManagerBookController(this);
        clientController = new ManagerClientController(this);
        reservationController = new ManagerReservationController(this);
    }
    
    @Override
    public JPanel getView() {
        return managerView;
    }
}
