package ua.itea.moreless;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Model model = new Model();
        View view = new ViewConsole();
        Controller controller = new Controller(model, view);
        
        controller.processUser();
    }
}
