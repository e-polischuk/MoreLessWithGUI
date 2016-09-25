package ua.itea.moreless;

import java.util.*;

/**
 * @author Polischuk on 14.09.2016.
 * 
 */
public class Model implements MLConst {
    // The user's identification key
    private int key;
    // Map fields for users data location provide multithreading
    private Map<Integer, Integer> secretNums = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> minBounds = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> maxBounds = new HashMap<Integer, Integer>();

    /**
     * The method sets user's identification and initial date
     * 
     * @return key
     */
    public synchronized int start() {
	setSecretNumber(++key);
	setMin(key, RAND_MIN);
	setMax(key, RAND_MAX);
	
	//check information
	System.out.println(Thread.currentThread().getName() + " - was started with key = " + key + " and secret = " + getSecretNumber(key));
	
	return key;
    }

    /**
     * The method clears the user's date
     * 
     * @param key
     */
    public synchronized void finish(int key) {
	//check information
	System.out.println(Thread.currentThread().getName() + " was finished with - secrets: " + secretNums + "; mins: " + minBounds + "; maxs: " + maxBounds);
	
	secretNums.remove(key);
	minBounds.remove(key);
	maxBounds.remove(key);
    }

    /**
     * In this method an int number is generated randomly from the range (0,100)
     * 
     * @return generated value
     */
    public synchronized int rand() {
	return (int) (Math.random() * (RAND_MAX - RAND_MIN - 1) + RAND_MIN + 1);
    }

    /**
     * In this method randomNumber is compared with
     * 
     * @param key
     * @param inputNumber
     * @return the result message
     */
    public synchronized String compare(int key, int inputNumber) {
	int randomNumber = getSecretNumber(key);
	if (randomNumber > inputNumber) {
	    setMin(key, inputNumber);
	    return MORE;
	} else if (randomNumber < inputNumber) {
	    setMax(key, inputNumber);
	    return LESS;
	} else {
	    return WINNER + randomNumber;
	}
    }

    // Utility methods
    public synchronized String createInputMessage(int key) {
	StringBuffer inputMessage = new StringBuffer(INPUT_INT_DATA);
	inputMessage.append(String.valueOf(getMin(key)));
	inputMessage.append(SPACE);
	inputMessage.append(String.valueOf(getMax(key)));
	inputMessage.append(END);
	return inputMessage.toString();
    }
    
    public synchronized boolean isInteger(String input) {
	boolean isInt = false;
	if (input != null && !input.isEmpty()) {
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
		    b++;
		}
	    }
	}
	return isInt;
    }

    public synchronized void setSecretNumber(int key) {
	secretNums.put(key, rand());
    }

    public synchronized int getSecretNumber(int key) {
	return secretNums.get(key);
    }

    public synchronized void setMin(int key, int min) {
	minBounds.put(key, min);
    }

    public synchronized int getMin(int key) {
	return minBounds.get(key);
    }

    public synchronized void setMax(int key, int max) {
	maxBounds.put(key, max);
    }

    public synchronized int getMax(int key) {
	return maxBounds.get(key);
    }

}
