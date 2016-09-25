package ua.itea.moreless;

import java.util.concurrent.CountDownLatch;

/**
 * The abstract controller contains the common methods and fields
 * 
 */
public abstract class Controller implements Runnable, MLConst {
    protected Model model;
    protected View view;
    protected CountDownLatch latch;

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
	if (latch != null) latch.countDown();
    }

    // Utility method declaration
    public abstract int inputIntValue(String inputMessage, int min, int max);

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

}
