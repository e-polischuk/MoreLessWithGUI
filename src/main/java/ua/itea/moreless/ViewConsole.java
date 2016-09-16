package ua.itea.moreless;

/**
 * @author Polischuk on 14.09.2016.
 * 
 */
public class ViewConsole implements View {

    public void printMessage(String message){
        System.out.println(Thread.currentThread().getName() + " - " + message);
    }
    
}