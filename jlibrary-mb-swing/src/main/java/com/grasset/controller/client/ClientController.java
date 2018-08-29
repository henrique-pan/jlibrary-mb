/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller.client;

import com.grasset.controller.Controller;
import com.grasset.view.ClientJPanelView;
import javax.swing.JPanel;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author henrique
 */
@Slf4j
public class ClientController extends Controller {
    
    // JPanel
    protected final ClientJPanelView clientView;

    // Controllers
    private ClientInfoController infoController;
    private ClientBookController bookController;

    public ClientController() {
        clientView = new ClientJPanelView();
        infoController = new ClientInfoController(this);
        bookController = new ClientBookController(this);
    }
    
    @Override
    public JPanel getView() {
        return clientView;
    }
    
}
