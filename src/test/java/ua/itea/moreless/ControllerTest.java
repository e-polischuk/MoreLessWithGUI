package ua.itea.moreless;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static ua.itea.moreless.MLConst.*;

import org.junit.Test;

public class ControllerTest {
    
    @Test
    public void run() {
	final String[] check = new String[3];
	int secretNumber = (RAND_MAX - RAND_MIN)/8;
	Model model = mock(Model.class);
	View view = new View() {
	    int i = 0;
	    @Override
	    public void printMessage(String message) {
		check[i++] = message;
		System.out.println(message);
		
	    }
	};
	Controller controller = new Controller(model, view) {
	    @Override
	    public int inputIntValue(String inputMessage, int min, int max) {
		if(inputMessage.equals(INPUT_INT_DATA)) return (max - min)/2;
		else return -1;
	    }
	};
	
	when(model.start()).thenReturn(1);
	when(model.getMin(1)).thenReturn(RAND_MIN);
	when(model.getMax(1)).thenReturn(RAND_MAX).thenReturn((RAND_MAX - RAND_MIN)/2).thenReturn((RAND_MAX - RAND_MIN)/4);
	when(model.createInputMessage(1)).thenReturn(INPUT_INT_DATA);
	when(model.compare(1, (RAND_MAX - RAND_MIN)/2)).thenReturn(LESS);
	when(model.compare(1, (RAND_MAX - RAND_MIN)/4)).thenReturn(LESS);
	when(model.compare(1, secretNumber)).thenReturn(WINNER + secretNumber);
	when(model.getSecretNumber(1)).thenReturn(secretNumber);
	
	controller.run();
	
	assertThat(check[0], equalTo(LESS));
	assertThat(check[1], equalTo(LESS));
	assertThat(check[2], equalTo(WINNER + secretNumber));

    }

}
