/**
 * 
 */
package ua.itea.moreless;

import javax.swing.JTextField;

/**
 * @author Polischuk
 *
 */
public class ControllerGUI extends Controller {
    private JTextField input;

    public ControllerGUI(Model model, View view) {
	super(model, view);
	ViewGUI gui = (ViewGUI) view;
	input = gui.getInput();

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
	    while (!isInteger(input.getText().trim())) {
		warn(inputMessage);
		input.setText(" ");
	    }
	    intValue = Integer.parseInt(input.getText().trim());
	    input.setText(" ");
	    inRange = intValue > min && intValue < max;
	    if (!inRange) {
		warn(inputMessage);
	    }
	} while (!inRange);

	view.printMessage(OUR_INT.concat(String.valueOf(intValue)));
	return intValue;
    }

    public boolean isInteger(String input) {
	boolean isInt = false;
	if (!input.equals(null)) {
	    char[] symbols = input.toCharArray();
	    int b;
	    for (char ch : symbols) {
		isInt = false;
		b = 48;
		while (!isInt && b <= 58) {
		    if (b == 58)
			return isInt;
		    if (b == (int) ch)
			isInt = true;
		}
	    }
	}

	return isInt;
    }

}
