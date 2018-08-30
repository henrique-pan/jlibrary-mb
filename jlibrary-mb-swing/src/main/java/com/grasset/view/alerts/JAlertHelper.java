/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grasset.view.alerts;

import lombok.extern.slf4j.Slf4j;

import javax.swing.JOptionPane;

/**
 * @author henrique
 */
@Slf4j
public class JAlertHelper {

    public static void showInfo(String title, String message) {
        log.info("{}: {}", title, message);
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(String title, String message) {
        log.error("{}: {}", title, message);
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static int showYesOrNoMessage(String title, String message) {
        int n = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        return n;
    }

}
