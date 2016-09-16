package ua.itea.moreless;

import java.util.Scanner;

/**
 * @author Polischuk on 14.09.2016.
 * 
 */
public class Controller {
    Model model;
    View view;

 // Constructor
    public Controller(Model model, View view) {
        this.model  = model;
        this.view = view;
    }
    
    /**
     * This is the work method that processes users requests
     */
    public void processUser() {
		Communicable communicator = new ScanReceiver(new Scanner(System.in), view);
		model.setCommunicator(communicator);
		model.playWithUser(view);
    }

}
