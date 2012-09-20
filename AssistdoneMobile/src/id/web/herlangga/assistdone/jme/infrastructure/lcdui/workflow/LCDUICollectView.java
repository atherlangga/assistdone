package id.web.herlangga.assistdone.jme.infrastructure.lcdui.workflow;

import id.web.herlangga.assistdone.jme.infrastructure.lcdui.*;
import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.workflow.*;
import id.web.herlangga.gtd.definition.stuff.*;

import java.util.*;

import javax.microedition.lcdui.*;

public class LCDUICollectView implements CollectView, LCDUIScreen {
	private final Display display;
	private final Hashtable menuMapping = new Hashtable();
	private final TextField descriptionTextField = new TextField(
			DESCRIPTION_LABEL, INITIAL_DESCRIPTION, 127, TextField.ANY);
	private final Command saveCommand = new Command(SAVE_LABEL, Command.ITEM, 1);
	private final Form collectForm = new Form(COLLECT_FORM_TITLE);

	public LCDUICollectView(Display display) {
		this.display = display;
	}

	public void addCommand(Command command, Callback callback) {
		if (!menuMapping.containsKey(command)) {
			menuMapping.put(command, callback);
		}
	}

	public void attachPresenter(final CollectPresenter presenter) {
		descriptionTextField.addCommand(saveCommand);
		descriptionTextField.setItemCommandListener(new ItemCommandListener() {
			public void commandAction(Command selectedCommand, Item item) {
				if (selectedCommand == saveCommand) {
					String descriptionValue = descriptionTextField.getString();
					Description description = new Description(descriptionValue);
					presenter.onSave(description);

					descriptionTextField.setString(INITIAL_DESCRIPTION);
				}
			}
		});

		collectForm.append(descriptionTextField);
	}

	public void show() {
		embedCommands();
		configureCommandListener();
		display.setCurrent(collectForm);
	}

	private void configureCommandListener() {
		collectForm.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (menuMapping.containsKey(c)) {
					Callback callback = (Callback) menuMapping.get(c);
					callback.execute();
				}
			}
		});
	}

	private void embedCommands() {
		Enumeration commandsEnumeration = menuMapping.keys();
		while (commandsEnumeration.hasMoreElements()) {
			Command c = (Command) commandsEnumeration.nextElement();
			collectForm.addCommand(c);
		}
	}

	private static final String COLLECT_FORM_TITLE = "Collect";
	private static final String DESCRIPTION_LABEL = "What's on your mind?";
	private static final String INITIAL_DESCRIPTION = "";
	private static final String SAVE_LABEL = "Save";
}
