/**
 * The abstract controller contains the common methods and fields
 */
package ua.itea.moreless;

public abstract class Controller implements MLConst {
    protected Model model;
    protected View view;

    // Constructor
    public Controller(Model model, View view) {
	this.model = model;
	this.view = view;
    }

    /**
     * The work method that processes users requests
     */
    public void processUser() {
	int key = model.start();

	int inputNumber;
	String message;
	do {
	    message = model.createInputMessage(key);
	    view.printMessage(message);
	    inputNumber = inputIntValue(message, model.getMin(key), model.getMax(key));
	    message = model.compare(key, inputNumber);
	    view.printMessage(message);
	} while (inputNumber != model.getSecretNumber(key));
	
	model.finish(key);

    }

    
    /**
     * Utility method
     * 
     * @param inputMessage
     * @param min
     * @param max
     * @return
     */
    public abstract int inputIntValue(String inputMessage, int min, int max);

    public void warn(String inputMessage) {
	view.printMessage(WRONG_INPUT_DATA);
	view.printMessage(inputMessage);
    }

}
