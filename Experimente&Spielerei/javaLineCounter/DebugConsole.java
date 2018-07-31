package javaLineCounter;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DebugConsole extends JDialog implements Runnable
{
	private static final long serialVersionUID = 5673308756593988158L;
	private final JScrollPane scrollPane;
	private final JTextArea textArea;

	public static void main(final String[] args)
	{
		new DebugConsole().setVisible(true);
		;
	}

	public DebugConsole()
	{
		scrollPane = new JScrollPane();
		add(scrollPane);
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.BLACK);
		textArea.setRows(10);
		textArea.setEditable(false);
		textArea.setColumns(50);

		pack();
	}

	public void toConsole(final String msg)
	{
		textArea.append(msg + "\r\n");
		textArea.setCaretPosition(textArea.getText().length());
		System.out.println(msg);
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub

	}
}
