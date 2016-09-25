package ua.itea.moreless;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static ua.itea.moreless.MLConst.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;
import org.junit.Test;

public class ControllerConsoleTest {

    @Test
    public void inputIntValue() {
	int min = RAND_MIN;
	int max = RAND_MAX;
	int intValue = (max - min) / 2;

	View view = mock(ViewConsole.class);
	Model model = mock(Model.class);

	Controller controller = new ControllerConsole(model, view);
	
	String input = String.valueOf(intValue);
	InputStream in = new ByteArrayInputStream(input.getBytes());
	try {
	    Class<ControllerConsole> contrClass = ControllerConsole.class;
	    Field sc = contrClass.getDeclaredField("sc");
	    sc.setAccessible(true);
	    sc.set(controller, new Scanner(in));
	} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
	    e.printStackTrace();
	}

	assertThat(controller.inputIntValue(INPUT_INT_DATA, min, max), equalTo(intValue));
    }

}
