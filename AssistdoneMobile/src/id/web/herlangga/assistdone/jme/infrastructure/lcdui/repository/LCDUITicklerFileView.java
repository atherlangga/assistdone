package id.web.herlangga.assistdone.jme.infrastructure.lcdui.repository;

import java.util.*;

import javax.microedition.lcdui.*;

import id.web.herlangga.assistdone.jme.infrastructure.lcdui.*;
import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.repository.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class LCDUITicklerFileView implements TicklerFileView, LCDUIScreen {
	private final Display display;
	private final Hashtable commandToCallbackCache = new Hashtable();
	private final Vector heldStuffsCache = new Vector();

	private final List heldStuffsList = new List(TICKLER_FILE_LABEL,
			List.IMPLICIT);
	private final Command trashCommand = new Command(TRASH_LABEL, Command.OK, 1);

	private static final String TICKLER_FILE_LABEL = "Tickler File";
	private static final String TRASH_LABEL = "Trash";

	public LCDUITicklerFileView(Display display) {
		this.display = display;
	}

	public void show() {
		Enumeration commandsEnum = commandToCallbackCache.keys();
		while (commandsEnum.hasMoreElements()) {
			Command c = (Command) commandsEnum.nextElement();
			heldStuffsList.addCommand(c);
		}
		
		Enumeration heldStuffsEnum = heldStuffsCache.elements();
		while (heldStuffsEnum.hasMoreElements()) {
			HeldStuff hs = (HeldStuff) heldStuffsEnum.nextElement();
			heldStuffsList.append(hs.description().descriptionValue_(), null);
		}

		heldStuffsList.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (commandToCallbackCache.containsKey(c)) {
					Callback selected = (Callback) commandToCallbackCache
							.get(c);
					selected.execute();
				}
			}
		});

		display.setCurrent(heldStuffsList);
	}

	public void addCommand(Command command, Callback callback) {
		if (!commandToCallbackCache.containsKey(command)) {
			commandToCallbackCache.put(command, callback);
		}
	}

	public void addHeldStuffs(HeldStuff[] heldStuffs) {
		int size = heldStuffs.length;
		for (int i = 0; i < size; i++) {
			HeldStuff current = heldStuffs[i];
			if (!heldStuffsCache.contains(current)) {
				heldStuffsCache.addElement(current);
			}
		}
	}

	public HeldStuff selectedHeldStuff() {
		int selectedIndex = heldStuffsList.getSelectedIndex();
		HeldStuff selected = (HeldStuff) heldStuffsCache
				.elementAt(selectedIndex);

		return selected;
	}

	public void clearHeldStuffs() {
		heldStuffsList.deleteAll();
		heldStuffsCache.removeAllElements();
	}

	public void attachPresenter(final TicklerFilePresenter ticklerFilePresenter) {
		addCommand(trashCommand, new Callback() {
			public void execute() {
				ticklerFilePresenter.onTrashSelected();
			}
		});
	}

}
