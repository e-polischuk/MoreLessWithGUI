package ua.itea.moreless;

/**
 * The abstract controller contains the common methods and fields
 * 
 */
public abstract class Controller implements Runnable, MLConst {
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
    public void run() {
	int key = model.start();
	int inputNumber;
	String message;
	do {
	    message = model.createInputMessage(key);
	    inputNumber = inputIntValue(message, model.getMin(key), model.getMax(key));
	    message = model.compare(key, inputNumber);
	    view.printMessage(message);
	} while (inputNumber != model.getSecretNumber(key));

	model.finish(key);

    }

    // Utility method declaration
    public abstract int inputIntValue(String inputMessage, int min, int max);

}
