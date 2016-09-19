package ua.itea.moreless;

import java.util.Scanner;

/**
 * @author Polischuk on 14.09.2016.
 * 
 */
public class ControllerConsole extends Controller {
    Scanner sc;

    // Constructor
    public ControllerConsole(Model model, View view) {
	super(model, view);
	sc = new Scanner(System.in);
    }

    /**
     * Override utility method
     * 
     * @param inputMessage
     * @param min
     * @param max
     * @return
     */
    @Override
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
	    if (!inRange) {
		warn(inputMessage);
	    }
	} while (!inRange);

	view.printMessage(OUR_INT.concat(String.valueOf(intValue)));
	return intValue;
    }

}
