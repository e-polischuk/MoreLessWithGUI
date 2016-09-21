package ua.itea.moreless;

/**
 * Very simple implementation
 * 
 */
public class ViewConsole implements View {

    public void printMessage(String message) {
	System.out.println(Thread.currentThread().getName() + " - " + message);
    }

}