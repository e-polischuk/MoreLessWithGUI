package ua.itea.moreless;

/**
 * @author Polischuk
 *
 */
public class ControllerGUI extends Controller {
    private ViewGUI view;

    public ControllerGUI(Model model, View view) {
	super(model, view);
	this.view = (ViewGUI) view;
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
	    view.printMessage(inputMessage);
	    while (!model.isInteger(view.getInputLine())) {
		view.printMessage(WRONG_INPUT_DATA);
		view.printMessage(inputMessage);
	    }
	    intValue = Integer.parseInt(view.getInputLine());
	    inRange = intValue > min && intValue < max;
	    if (!inRange) {
		view.printMessage(WRONG_INPUT_DATA);
	    }
	} while (!inRange);

	view.printMessage(OUR_INT + intValue);
	return intValue;
    }
    
}
