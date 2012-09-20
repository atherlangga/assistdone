package id.web.herlangga.assistdone.jme.infrastructure.lcdui.repository;

import java.util.*;

import javax.microedition.lcdui.*;

import id.web.herlangga.assistdone.jme.infrastructure.lcdui.*;
import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.repository.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class LCDUINextActionListView implements NextActionListView, LCDUIScreen {
	private final Display display;
	private final Hashtable menuMapping = new Hashtable();
	private final Vector categorizedStuffs = new Vector();

	private static final String NEXT_ACTION_LIST_LABEL = "Next Action List";
	private static final String MARK_AS_DONE_LABEL = "Mark as Done";
	private static final String TRASH_LABEL = "Trash";

	private final List categorizedStuffsList = new List(NEXT_ACTION_LIST_LABEL,
			List.IMPLICIT);
	private final Command markAsDoneCommand = new Command(MARK_AS_DONE_LABEL,
			Command.ITEM, 1);
	private final Command trashCommand = new Command(TRASH_LABEL, Command.ITEM,
			2);

	public LCDUINextActionListView(Display display) {
		this.display = display;
	}

	public CategorizedStuff selectedCategorizedStuff() {
		int selectedIndex = categorizedStuffsList.getSelectedIndex();
		CategorizedStuff selected = (CategorizedStuff) categorizedStuffs
				.elementAt(selectedIndex);

		return selected;
	}

	public void show() {
		Enumeration commandsEnum = menuMapping.keys();
		while (commandsEnum.hasMoreElements()) {
			Command c = (Command) commandsEnum.nextElement();
			categorizedStuffsList.addCommand(c);
		}

		categorizedStuffsList.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (menuMapping.containsKey(c)) {
					Callback callback = (Callback) menuMapping.get(c);
					callback.execute();
				}
			}
		});

		display.setCurrent(categorizedStuffsList);
	}

	public void addCommand(Command command, Callback callback) {
		if (!menuMapping.containsKey(command)) {
			menuMapping.put(command, callback);
		}
	}

	public void addCategorizedStuffs(CategorizedStuff[] stuffs) {
		int size = stuffs.length;
		for (int i = 0; i < size; i++) {
			CategorizedStuff current = stuffs[i];
			if (!categorizedStuffs.contains(current)) {
				categorizedStuffs.addElement(current);
				categorizedStuffsList.append(
						current.description().descriptionValue_(), null);
			}
		}
	}

	public void clearCategorizedstuffs() {
		categorizedStuffsList.deleteAll();
		categorizedStuffs.removeAllElements();
	}

	public void attachPresenter(final NextActionListPresenter presenter) {
		addCommand(markAsDoneCommand, new Callback() {
			public void execute() {
				presenter.onMarkAsDoneSelected();
			}
		});

		addCommand(trashCommand, new Callback() {
			public void execute() {
				presenter.onTrashSelected();
			}
		});
	}

}
