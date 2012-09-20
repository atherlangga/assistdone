package id.web.herlangga.assistdone.jme.infrastructure.lcdui;

import id.web.herlangga.assistdone.jme.ui.*;

import javax.microedition.lcdui.*;

public interface LCDUIScreen {
	
	/**
	 * Add LCDUI Command to the Screen.
	 * 
	 * @param command LCDUI Command to add.
	 * @param callback Callback to execute when command is selected.
	 */
	void addCommand(Command command, Callback callback);
}
