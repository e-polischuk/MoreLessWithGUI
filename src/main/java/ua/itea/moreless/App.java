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
        View view = new ViewGUI();
        Controller controller = new ControllerGUI(model, view);
        
        controller.processUser();
//	int i = 0;
//	for(int b = 45; b <= 60; b++) System.out.println(++i + ") " + ((char) b));
//	System.out.println(++i + ") " + ((int) '1'));
//	System.out.println(++i + ") " + ((int) '2'));
//	System.out.println(++i + ") " + ((int) '3'));
//	System.out.println(++i + ") " + ((int) '4'));
//	System.out.println(++i + ") " + ((int) '5'));
	
    }
}
