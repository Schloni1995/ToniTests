package javaLineCounter;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class HotKeyManager implements KeyEventDispatcher
{
	private final Gui gui;

	public HotKeyManager(final Gui gui)
	{
		this.gui = gui;
	}

	@Override
	public boolean dispatchKeyEvent(final KeyEvent evt)
	{
		// if ((evt.isControlDown() && (evt.getKeyCode() == KeyEvent.VK_A)) &&
		// evt.getID() == KeyEvent.KEY_PRESSED)
		// {
		//
		// }

		if ((evt.getKeyCode() == KeyEvent.VK_F1) && (evt.getID() == KeyEvent.KEY_PRESSED))
			gui.setDebugConsole(new DebugConsole());
		return false;
	}
}
