package id.web.herlangga.assistdone.jme.infrastructure.lcdui.workflow;

import id.web.herlangga.assistdone.jme.infrastructure.lcdui.*;
import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.workflow.*;

import java.util.*;

import javax.microedition.lcdui.*;

/**
 * {@link ReviewView} implementation for LCDUI
 * @author at.herlangga
 */
public class LCDUIReviewView implements ReviewView, LCDUIScreen {
	private final Display display;
	private final Hashtable menuMapping = new Hashtable();

	private final List repositoryList = new List(REVIEW_LABEL,
			Choice.IMPLICIT);
	private final Command selectCommand = new Command(SELECT_LABEL, Command.OK,
			1);

	public LCDUIReviewView(Display display) {
		this.display = display;
	}

	public void attachPresenter(final ReviewPresenter presenter) {
		repositoryList.deleteAll();
		repositoryList.append(CALENDAR_LABEL, null);
		repositoryList.append(NEXT_ACTION_LIST_LABEL, null);
		repositoryList.append(WAITING_FOR_LIST_LABEL, null);
		repositoryList.append(TICKLER_FILE_LABEL, null);
		
		addCommand(selectCommand, new Callback() {
			public void execute() {
				int selectedIndex = repositoryList.getSelectedIndex();
				String selectedName = repositoryList.getString(selectedIndex);
				
				if (selectedName.equals(CALENDAR_LABEL)) {
					presenter.onCalendarSelected();
				} else if (selectedName.equals(NEXT_ACTION_LIST_LABEL)) {
					presenter.onNextActionListSelected();
				} else if (selectedName.equals(WAITING_FOR_LIST_LABEL)) {
					presenter.onWaitingForListSelected();
				} else if (selectedName.equals(TICKLER_FILE_LABEL)) {
					presenter.onTicklerFileSelected();
				}
			}
		});
	}
	
	public void addCommand(Command command, Callback callback) {
		if (!menuMapping.containsKey(command)) {
			menuMapping.put(command, callback);
		}
	}

	public void show() {
		embedCommands();
		configureCommandListener();
		display.setCurrent(repositoryList);
	}
	
	private void embedCommands() {
		Enumeration commandsEnumaration = menuMapping.keys();
		while (commandsEnumaration.hasMoreElements()) {
			Command c = (Command) commandsEnumaration.nextElement();
			repositoryList.addCommand(c);
		}
	}

	private void configureCommandListener() {
		repositoryList.setSelectCommand(selectCommand);
		repositoryList.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (menuMapping.containsKey(c)) {
					Callback callback = (Callback) menuMapping.get(c);
					callback.execute();
				}
			}
		});
	}

	private static final String REVIEW_LABEL = "Review";
	private static final String SELECT_LABEL = "Select";
	private static final String CALENDAR_LABEL = "Calendar";
	private static final String NEXT_ACTION_LIST_LABEL = "Next Action List";
	private static final String WAITING_FOR_LIST_LABEL = "Waiting-For List";
	private static final String TICKLER_FILE_LABEL = "Tickler File";

}
