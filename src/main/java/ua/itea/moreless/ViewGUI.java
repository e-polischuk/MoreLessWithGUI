package ua.itea.moreless;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//import javax.swing.JTextPane;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.StyledDocument;

public class ViewGUI implements View {
    private static int order;
    private JTextArea display;
    private JTextField input;
//    private StyledDocument doc;
    SimpleAttributeSet keyWord;

    public ViewGUI() {
	JFrame view = new JFrame(NAME + ++order);
	view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	BorderLayout layout = new BorderLayout();
	JPanel background = new JPanel(layout);
	background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//	JTextPane display = new JTextPane();
	display = new JTextArea("");
	display.setEditable(false);
//	display.setText("original text");
//	doc = new DefaultStyledDocument();
//	keyWord = new SimpleAttributeSet();
//	try {
//	    doc.insertString(0, "Start of text\n", keyWord);
//	} catch (BadLocationException e1) {
//	    e1.printStackTrace();
//	}
//	display.setStyledDocument(doc);
	
	
//	doc = display.getStyledDocument();;
	JScrollPane dsp = new JScrollPane(display);
	dsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	JTextField input = new JTextField();
	input.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		JTextField in = (JTextField) e.getSource();
		printMessage(in.getText());
		in.setText(" ");
	    }

	});
	JLabel label = new JLabel("INPUT and ENTER:");
	GridLayout grid = new GridLayout(2, 1);
	JPanel gp = new JPanel(grid);
	gp.add(label);
	gp.add(input);
	background.add(BorderLayout.CENTER, display);
	background.add(BorderLayout.SOUTH, gp);
	view.getContentPane().add(background);
	view.setBounds(50, 50, 300, 300);
	view.setVisible(true);

    }

    public void printMessage(String message) {
	display.setText(display.getText() + LN + message);
//	try {
//	    doc = display.getStyledDocument();
//	    doc.insertString(doc.getLength(), LN + display.getText() + LN + message, keyWord);
//	    display.setStyledDocument(doc);
//	    display.repaint();
//	    
//	} catch (BadLocationException e) {
//	    e.printStackTrace();
//	}
	
    }

    public JTextField getInput() {
        return input;
    }

}
