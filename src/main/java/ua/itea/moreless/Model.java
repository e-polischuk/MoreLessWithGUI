package ua.itea.moreless;

import java.util.*;

/**
 * @author Polischuk on 14.09.2016.
 * 
 */
public class Model implements MLConst {
    // The user's identification key
    private int key;
    // Map fields for users data location in case when the game is concurrent
    private Map<Integer, Integer> secretNums = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> minBounds = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> maxBounds = new HashMap<Integer, Integer>();

    /**
     * The method sets user's identification and initial date
     * 
     * @return key
     */
    public int start() {
	setSecretNumber(++key);
	setMin(key, RAND_MIN);
	setMax(key, RAND_MAX);
	return key;
    }

    /**
     * The method clears the user's date
     * 
     * @param key
     */
    public void finish(int key) {
	secretNums.remove(key);
	minBounds.remove(key);
	maxBounds.remove(key);
    }

    /**
     * In this method an int number is generated randomly from the range (0,100)
     * 
     * @return generated value
     */
    public static int rand() {
	return (int) (Math.random() * (RAND_MAX - RAND_MIN - 1) + RAND_MIN + 1);
    }

    /**
     * In this method randomNumber is compared with
     * 
     * @param key
     * @param inputNumber
     * @return the result message
     */
    public String compare(Integer key, int inputNumber) {
	int randomNumber = getSecretNumber(key);
	if (randomNumber > inputNumber) {
	    setMin(key, inputNumber);
	    return MORE;
	} else if (randomNumber < inputNumber) {
	    setMax(key, inputNumber);
	    return LESS;
	} else {
	    return WINNER.concat(String.valueOf(randomNumber));
	}
    }

    // Utility methods
    public String createInputMessage(Integer key) {
	StringBuffer inputMessage = new StringBuffer(INPUT_INT_DATA);
	inputMessage.append(String.valueOf(getMin(key)));
	inputMessage.append(SPACE);
	inputMessage.append(String.valueOf(getMax(key)));
	inputMessage.append(END);
	return inputMessage.toString();
    }

    public void setSecretNumber(Integer key) {
	secretNums.put(key, rand());
    }

    public int getSecretNumber(Integer key) {
	return secretNums.get(key);
    }

    public void setMin(Integer key, int min) {
	minBounds.put(key, min);
    }

    public int getMin(Integer key) {
	return minBounds.get(key);
    }

    public void setMax(Integer key, int max) {
	maxBounds.put(key, max);
    }

    public int getMax(Integer key) {
	return maxBounds.get(key);
    }

}
