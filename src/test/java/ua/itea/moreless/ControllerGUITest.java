package ua.itea.moreless;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static ua.itea.moreless.MLConst.*;

import org.junit.Test;

public class ControllerGUITest {

    @Test
    public void inputIntValue() {
	int min = RAND_MIN;
	int max = RAND_MAX;
	int intValue = (max - min) / 2;

	Model model = new Model();
	ViewGUI view = mock(ViewGUI.class);
	
	when(view.getInputLine()).thenReturn(String.valueOf(intValue));

	Controller controller = new ControllerGUI(model, view);

	assertThat(controller.inputIntValue(INPUT_INT_DATA, min, max), equalTo(intValue));
    }

}
