package ua.itea.moreless;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ViewGUI extends JPanel implements View {
    private static int num;
    private JPanel panel;
    private JTextArea display;
    private JTextField field;
    private String inputLine;

    public ViewGUI() {
	display = new JTextArea(AD);
	display.setEditable(false);
	JScrollPane dsp = new JScrollPane(display);
	dsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	field = new JTextField("");
	field.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		JTextField f = (JTextField) e.getSource();
		inputLine = f.getText().trim();
		f.setText(EMPTY);
		proceed(true);
	    }
	});

	JPanel south = new JPanel(new GridLayout(2, 1));
	south.add(new JLabel(LB));
	south.add(field);

	panel = new JPanel(new BorderLayout());
	panel.add(BorderLayout.NORTH, new JLabel(VIEW + ++num, 0));
	panel.add(BorderLayout.CENTER, dsp);
	panel.add(BorderLayout.SOUTH, south);

	inputLine = EMPTY;
    }

    public JPanel getPanel() {
	return panel;
    }

    public void printMessage(String message) {
	display.setText(display.getText() + LN + message);
	if (message.contains(INPUT_INT_DATA)) {
	    proceed(false);
	}
    }
    

    public synchronized void proceed(boolean isInput) {
	if(isInput) notify();
	else
	    try {
		wait();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
    }

    public String getInputLine() {
	return inputLine;
    }

}
