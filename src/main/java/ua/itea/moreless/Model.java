package ua.itea.moreless;

import java.util.*;

/**
 * @author Polischuk on 14.09.2016.
 * 
 */
public class Model implements Communicable {
	private Communicable communicator;
	private Map<View, Integer> randomUsers = new HashMap<View, Integer>();
	private Map<View, Integer> minUsers = new HashMap<View, Integer>();
	private Map<View, Integer> maxUsers = new HashMap<View, Integer>();
	
	/**
     * The model main work method
     */
	public void playWithUser(View view) {
        String message;
		int inputNumber;
		setRandom(view);
		setMin(view, RAND_MIN);
		setMax(view, RAND_MAX);
		
        do {
			message = createInputMessage(view);
			view.printMessage(message);
            inputNumber = inputIntValue(message, getMin(view), getMax(view));
            message = proceed(view, inputNumber);
            view.printMessage(message);
        } while (inputNumber != getRandom(view));
        
        randomUsers.remove(view);
		minUsers.remove(view);
		maxUsers.remove(view);
    }

	/**
     * In this method an int number is generated randomly from the range (0,100)
     * @return generated value
     */
    public static int rand() {
        return (int) (Math.random() * (RAND_MAX - RAND_MIN - 1) + RAND_MIN + 1);
    }

    /**
     * In this method randomNumber is compared with
     * @param inputNumber
     * @return the result message
     */
    public String proceed(View view, int inputNumber) {
        int randomNumber = getRandom(view);
		if(randomNumber > inputNumber) {
			setMin(view, inputNumber);
            return MORE;
		} else if(randomNumber < inputNumber) {
			setMax(view, inputNumber);
            return MORE;
		} else {
            return WINNER.concat(String.valueOf(randomNumber));
        }
    }
	
	public void setRandom(View view) {
		randomUsers.put(view, rand());
	}
	
	public int getRandom(View view) {
		return randomUsers.get(view);
	}
	
	public void setMin(View view, int min) {
		minUsers.put(view, min);
	}
	
	public int getMin(View view) {
		return minUsers.get(view);
	}
	
	public void setMax(View view, int max) {
		maxUsers.put(view, max);
	}
	
	public int getMax(View view) {
		return maxUsers.get(view);
	}
	
	public void setCommunicator(Communicable communicator) {
		this.communicator = communicator;
	}
	
	public Communicable getCommunicator() {
		return communicator;
	}

    // Utility methods	
	public String createInputMessage(View view) {
		StringBuffer inputMessage = new StringBuffer(INPUT_INT_DATA);
		inputMessage.append(String.valueOf(getMin(view)));
		inputMessage.append(SPACE);
		inputMessage.append(String.valueOf(getMax(view)));
		inputMessage.append(END);
		return inputMessage.toString();
	}
		
	public int inputIntValue(String inputMessage, int min, int max) {
		return communicator.inputIntValue(inputMessage, min, max);
	}

}
