/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.controller;

import javax.swing.JPanel;

/**
 *
 * @author henrique
 */
public abstract class Controller {
    
    // Main Frame Controller
    protected final MainFrameController mainController;
    
    public Controller() {
        mainController = MainFrameController.getInstance();
    }

    public abstract JPanel getView();    
    
}
