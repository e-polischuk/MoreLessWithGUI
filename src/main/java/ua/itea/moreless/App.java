package ua.itea.moreless;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.concurrent.CountDownLatch;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Run class for the multithreaded game "More-Less"
 *
 * @author Polischuk
 */
public class App {
    private JFrame frame = new JFrame("More-Less"); 

    public static void main(String[] args) {
	final CountDownLatch latch = new CountDownLatch(4);
	final JFrame app = new App().getFrame();
	
	EventQueue.invokeLater(new Runnable() {
	    
	    public void run() {
		Model model = new Model();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(1, 3);
		JPanel background = new JPanel(grid);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		ViewGUI view1 = new ViewGUI();
		ViewGUI view2 = new ViewGUI();
		ViewGUI view3 = new ViewGUI();

		background.add(view1.getPanel());
		background.add(view2.getPanel());
		background.add(view3.getPanel());
		app.getContentPane().add(background);

		Controller controller1 = new ControllerGUI(model, view1);
		Controller controller2 = new ControllerGUI(model, view2);
		Controller controller3 = new ControllerGUI(model, view3);
		Controller controller = new ControllerConsole(model, new ViewConsole());
		
		controller1.setLatch(latch);
		controller2.setLatch(latch);
		controller3.setLatch(latch);
		controller.setLatch(latch);

		app.setBounds(50, 50, 1200, 600);
		app.setVisible(true);

		Thread first = new Thread(controller1); //(ControllerGUI)
		Thread second = new Thread(controller2);
		Thread third = new Thread(controller3);
		Thread console = new Thread(controller); //(ControllerConsole) 

		first.setName("View_1");
		second.setName("View_2");
		third.setName("View_3");
		console.setName("Console");		
		
		first.start();
		second.start();
		third.start();
		console.start();
		
	    }
	    
	});
	
	try {
	    latch.await();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	
	app.dispose();
    }
    
    public JFrame getFrame() {
	return frame;
    }

}
