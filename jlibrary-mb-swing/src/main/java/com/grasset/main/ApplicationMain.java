package com.grasset.main;

import com.grasset.controller.MainFrameController;

public class ApplicationMain {

    public static void main(String[] args) {
        MainFrameController mainFrameController = MainFrameController.getInstance();
        mainFrameController.startMainFrame();
    }

}
