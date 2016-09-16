package ua.itea.moreless;

import java.util.Scanner;

/**
 * @author Polischuk on 14.09.2016.
 * 
 */
public class ScanReceiver implements Communicable {
	Scanner sc;
	View view;
	
	ScanReceiver(Scanner sc, View view) {
		this.sc = sc;
		this.view = view;
	}
	
	// The Utility methods
    public int inputIntValue(String inputMessage, int min, int max) {
        int intValue;
        boolean inRange;
		
        do {
            while (!sc.hasNextInt()) {
                warn(inputMessage);
                sc.next();
            }
            intValue = sc.nextInt();
            inRange = intValue > min && intValue < max;
            if(!inRange) {
                warn(inputMessage);
            }
        } while(!inRange);
		
		view.printMessage(OUR_INT.concat(String.valueOf(intValue)));
        return intValue;
    }

    private void warn(String inputMessage) {
        view.printMessage(WRONG_INPUT_DATA);
        view.printMessage(inputMessage);
    }
}
