package ua.itea.moreless;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ua.itea.moreless.MLConst.*;

import org.junit.Test;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author Polischuk
 *
 */
public class ModelTest {
    // Threads amount for check
    public static final int THR = 16;

    @Test
    public void start() {
	final boolean[][] check = new boolean[THR][4];
	final CountDownLatch outlatch = new CountDownLatch(1);
	final int[] keys = new int[THR];
	
	// check "start" method in multithreading
	Thread startMethod = new Thread(new Runnable() {
	    Model model = new Model();
	    CountDownLatch latch = new CountDownLatch(THR);

	    public void run() {
		List<Future<Integer>> tasks = new ArrayList<Future<Integer>>();
		int i = THR;

		/** 
		 * Preparation of the model method for check
		 * in several thread tasks at once with using
		 * Callable interface for check returned keys also
		 */
		while (--i >= 0) {
		    tasks.add(new FutureTask<Integer>(new Callable<Integer>() {
			public Integer call() {
			    int key = model.start();
			    latch.countDown();
			    return key;
			}
		    }));
		}

		// Start prepared tasks and get results
		for (Future<Integer> task : tasks) {
		    try {
			new Thread((Runnable) task).start();
			keys[++i] = task.get();
		    } catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		    }
		}

		// waiting while each of started task were completed
		try {
		    latch.await();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		// collect results
		for (int j = 0; j < THR; j++) {
		    check[j][0] = keys[j] > 0;
		    check[j][1] = model.getSecretNumber(keys[j]) > RAND_MIN && model.getSecretNumber(keys[j]) < RAND_MAX;
		    check[j][2] = model.getMin(keys[j]) == RAND_MIN;
		    check[j][3] = model.getMax(keys[j]) == RAND_MAX;
		}
		
		outlatch.countDown();
	    }
	});

	startMethod.start();
	
	// waiting while the "startMethod" thread was completed
	try {
	    outlatch.await();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
		
	// check results of executed "start" method
	for (int j = 0; j < THR; j++) {
	    assertTrue("Test is failed for key = " + keys[j], check[j][0]);
	    assertTrue("Creating and adding of secret num is failed for key = " + keys[j], check[j][1]);
	    assertTrue("Adding of min_bound is failed for key = " + keys[j], check[j][2]);
	    assertTrue("Adding of max_bound is failed for key = " + keys[j], check[j][3]);
	}

    }
   
    @Test
    public void finish() {
	final boolean[] mapNotEmpty = new boolean[3];
	final boolean[] mapIsEmpty = new boolean[3];
	final CountDownLatch outlatch = new CountDownLatch(1);
	
	// Creating and deleting of map pairs in multithreading
	Thread finishMethod = new Thread(new Runnable() {
	    CountDownLatch latch = new CountDownLatch(THR);

	    @SuppressWarnings("unchecked")
	    public void run() {
		final Model model = new Model();
		
		Class<Model> clMod = (Class<Model>) model.getClass();
		Map<Integer, Integer> secrets = null;
		Map<Integer, Integer> mins = null;
		Map<Integer, Integer> maxs = null;

		try {
		    Field secret = clMod.getDeclaredField("secretNums");
		    Field min = clMod.getDeclaredField("minBounds");
		    Field max = clMod.getDeclaredField("maxBounds");
		    
		    Field[] maps = new Field[] {secret, min, max};
		    
		    AccessibleObject.setAccessible(maps, true);
		    
		    secrets = (Map<Integer, Integer>) secret.get(model);
		    mins = (Map<Integer, Integer>) min.get(model);
		    maxs = (Map<Integer, Integer>) max.get(model);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
		    e.printStackTrace();
		}
		
		final int[] keys = new int[THR];
		
		for (int i = 0; i < THR; i++) {
		    keys[i] = model.start();
		}
		
		// There are maps state before finish:
		mapNotEmpty[0] = !secrets.isEmpty();
		mapNotEmpty[1] = !mins.isEmpty();
		mapNotEmpty[2] = !maxs.isEmpty();
		
		Thread[] threads = new Thread[THR];
		for (int i = 0; i < THR; i++) {
		    final int j = i;
		    threads[i] = new Thread(new Runnable() {
			public void run() {
			    model.finish(keys[j]);
			    latch.countDown();
			}
		    });
		}

		for (Thread thread : threads) {
		    thread.start();
		}

		// waiting while each of started task were completed
		try {
		    latch.await();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		// There are maps state after finish:
		mapIsEmpty[0] = secrets.isEmpty();
		mapIsEmpty[1] = mins.isEmpty();
		mapIsEmpty[2] = maxs.isEmpty();
		
		outlatch.countDown();
	    }
	});

	finishMethod.start();
	
	// waiting while the "finishMethod" thread was completed
	try {
	    outlatch.await();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	
	// check results of executed "finish" method
	assertTrue(mapNotEmpty[0] && mapNotEmpty[1] && mapNotEmpty[2]);
	assertTrue(mapIsEmpty[0] && mapIsEmpty[1] && mapIsEmpty[2]);
	
    }

    @Test
    public void rand() {
	Model model = new Model();
	long limit = (RAND_MAX - RAND_MIN) * (RAND_MAX - RAND_MIN);
	boolean minBound = false;
	boolean maxBound = false;
	
	while (limit-- >= 0) {
	    int randNum = model.rand();
	    assertThat("Range bounds out", randNum, allOf(greaterThan(RAND_MIN), lessThan(RAND_MAX)));
	    if(randNum == RAND_MIN + 1) minBound = true;
	    if(randNum == RAND_MAX - 1) maxBound = true;
	}
	
	assertTrue("Min_bound wasn't achieved", minBound);
	assertTrue("Max_bound wasn't achieved", maxBound);
    }

    @Test
    public void compare() {
	final int secretNumber = (RAND_MAX - RAND_MIN) / 2;
	Model model = new Model() {
	    public int getSecretNumber(int key) {
		return secretNumber;
	    }
	};

	assertThat( model.compare(1, secretNumber - 1), equalTo(MORE));
	assertThat(model.compare(1, secretNumber + 1), equalTo(LESS));
	assertThat(model.compare(1, secretNumber), equalTo(WINNER + secretNumber));

    }

    @Test
    public void createInputMessage() {
	Model model = new Model() {
		public int getMin(int key) {
		    return RAND_MIN;
		}
			
		public int getMax(int key) {
		    return RAND_MAX;
		}
	};
		
	assertThat(INPUT_INT_DATA + RAND_MIN + SPACE + RAND_MAX + END, equalTo(model.createInputMessage(1)));
    }
    
    @Test
    public void isInteger() {
	Model model = new Model();
	assertFalse("null is failed ", model.isInteger(null));
	assertFalse("'' is failed ", model.isInteger(""));
	assertFalse("' 111 ' is failed ", model.isInteger(" 111 "));
	assertFalse("'1 1' is failed ", model.isInteger("1 1"));
	assertFalse("'string' is failed ", model.isInteger("string"));
	assertFalse("'1.11' is failed ", model.isInteger("1.11"));
	assertTrue("'111' is failed ", model.isInteger("111"));
    }

}
