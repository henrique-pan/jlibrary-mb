package com.grasset.main;

import com.grasset.controller.MainFrameController;
import com.grasset.db.DomainLoader;

public class ApplicationMain {

    public static void main(String[] args) {
        config();

        MainFrameController mainFrameController = MainFrameController.getInstance();
        mainFrameController.startMainFrame();
    }

    private static void config() {
        DomainLoader domainLoader = new DomainLoader();
        domainLoader.loadDomainTables();
    }

}
