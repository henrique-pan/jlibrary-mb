/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.client;

import com.grasset.controller.Controller;
import com.grasset.view.ClientJPanelView;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author henrique
 */
public class ClientController extends Controller {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    
    // JPanel
    protected final ClientJPanelView clientView;

    // Controllers
    private ClientInfoController infoController;
    private ClientBookController bookController;

    public ClientController() {
        clientView = new ClientJPanelView();
        infoController = new ClientInfoController(this);

    }
    
    @Override
    public JPanel getView() {
        return clientView;
    }
    
}
