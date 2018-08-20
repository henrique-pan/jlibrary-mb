package com.grasset.controller.manager;

import com.grasset.view.alerts.JAlertHelper;

public class ManagerHelper {
	
	private final int YEAR_NUMBER = 4;
	
	boolean yearValidation(String yearValidation) {
		StringBuilder sb = new StringBuilder();
		char [] arrCharYearValidation = yearValidation.toCharArray();
		
		for(int i = 0; i < yearValidation.length(); i++) {
			if(Character.isDigit(arrCharYearValidation[i]) && (yearValidation.length() == YEAR_NUMBER)) {
				sb.append(i);
			} else {
				JAlertHelper.showError("Error de validation", "L'année doit contenir 4 chiffres. Ex.: 1999 (AAAA).");
				return false;
			}
		}
		return true;
	}

	boolean isbnValidation(String bookISBN) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

}
