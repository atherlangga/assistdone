package id.web.herlangga.assistdone.jme.infrastructure.lcdui.repository;

import id.web.herlangga.assistdone.jme.infrastructure.lcdui.*;
import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.repository.*;
import id.web.herlangga.gtd.module.inventory.item.*;

import java.util.*;

import javax.microedition.lcdui.*;

public class LCDUIWaitingForListView implements WaitingForListView, LCDUIScreen {
	private final Display display;
	private final Hashtable menuMapping = new Hashtable();
	private final Vector delegatedStuffs = new Vector();

	private final List delegatedStuffList = new List(WAITING_FOR_LIST_TITLE,
			Choice.IMPLICIT);
	private final Command markAsDeliveredCommand = new Command(
			MARK_AS_DELIVERED_LABEL, Command.ITEM, 1);
	private final Command trashCommand = new Command(TRASH_LABEL, Command.ITEM,
			2);

	public LCDUIWaitingForListView(Display display) {
		this.display = display;
	}

	public void show() {
		embedCommands();
		configureCommandListener();
		display.setCurrent(delegatedStuffList);
	}

	private void configureCommandListener() {
		delegatedStuffList.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (menuMapping.containsKey(c)) {
					Callback callback = (Callback) menuMapping.get(c);
					callback.execute();
				}
			}
		});
	}

	private void embedCommands() {
		Enumeration commandsEnum = menuMapping.keys();
		while (commandsEnum.hasMoreElements()) {
			Command c = (Command) commandsEnum.nextElement();
			delegatedStuffList.addCommand(c);
		}
	}

	public void addDelegatedStuffs(DelegatedStuff[] newDelegatedStuffs) {
		int size = newDelegatedStuffs.length;
		for (int i = 0; i < size; i++) {
			DelegatedStuff current = newDelegatedStuffs[i];
			if (!delegatedStuffs.contains(current)) {
				delegatedStuffs.addElement(current);
				delegatedStuffList.append(current.description()
						.descriptionValue_(), null);
			}
		}
	}

	public DelegatedStuff selectedDelegatedStuff() {
		int selectedIndex = delegatedStuffList.getSelectedIndex();
		return (DelegatedStuff) delegatedStuffs.elementAt(selectedIndex);
	}

	public void clearDelegatedStuffs() {
		delegatedStuffList.deleteAll();
		delegatedStuffs.removeAllElements();
	}

	public void attachPresenter(final WaitingForListPresenter presenter) {
		addCommand(markAsDeliveredCommand, new Callback() {
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

	public void addCommand(Command command, Callback callback) {
		if (!menuMapping.contains(command)) {
			menuMapping.put(command, callback);
		}
	}

	private static final String WAITING_FOR_LIST_TITLE = "Waiting-for List";
	private static final String MARK_AS_DELIVERED_LABEL = "Mark as delivered";
	private static final String TRASH_LABEL = "Trash";

}
