package ua.itea.moreless;

import static org.junit.Assert.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class ViewGUITest {
    
    @Test
    public void proceed() {
	final int steps = 10;
	final List<Integer> results = new ArrayList<Integer>();
	final ViewGUI view = new ViewGUI();
	final CountDownLatch latch = new CountDownLatch(2);	
	
	Thread first = new Thread(new Runnable() {
	    @Override
	    public void run() {
		view.proceed(false);
		for(int i = 0; i < steps; i++) {
		    results.add(1);
		}
		latch.countDown();
	    }
	});
	
	Thread second = new Thread(new Runnable() {
	    @Override
	    public void run() {
		for(int i = 0; i < steps; i++) {
		    results.add(2);
		}
		view.proceed(true);
		latch.countDown();
	    }
	    
	});
	
	first.start();
	second.start();
	
	try {
	    latch.await();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	 
	System.out.println(results);
	
	int i = 0;
	for(Integer id : results) {
	    if(i++ < steps) assertTrue(id == 2);
	    else assertTrue(id == 1);
	}
	assertTrue(i == 2 * steps);  
    }

}
