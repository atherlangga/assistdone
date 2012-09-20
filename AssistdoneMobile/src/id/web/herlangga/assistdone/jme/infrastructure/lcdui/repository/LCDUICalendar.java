package id.web.herlangga.assistdone.jme.infrastructure.lcdui.repository;

import java.util.*;

import javax.microedition.lcdui.*;

import com.domainlanguage.time.*;

import id.web.herlangga.assistdone.jme.infrastructure.lcdui.*;
import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.repository.*;
import id.web.herlangga.gtd.module.inventory.Calendar;
import id.web.herlangga.gtd.module.inventory.item.*;

public class LCDUICalendar implements CalendarView, LCDUIScreen {
	private final Calendar calendar;
	private final Display display;
	private final Hashtable commandToCallbackMap = new Hashtable();
	private final Hashtable sequenceNumberToScheduledStuffMap = new Hashtable();

	private final Form datePickerForm = new Form(DATE_PICKER_FORM_LABEL);
	private final DateField datePickerDateField = new DateField(
			DATE_PICKER_DATE_FIELD_LABEL, DateField.DATE);
	private final Command pickDateCommand = new Command(PICK_DATE_LABEL,
			Command.OK, 1);
	private final List scheduledStuffsList = new List(CALENDAR_LABEL,
			List.IMPLICIT);
	private final Command markAsDoneCommand = new Command(MARK_AS_DONE_LABEL,
			Command.ITEM, 1);
	private final Command trashCommand = new Command(TRASH_LABEL, Command.ITEM,
			2);

	private static final String DATE_PICKER_FORM_LABEL = "Pick a Date";
	private static final String DATE_PICKER_DATE_FIELD_LABEL = "Show Scheduled Stuff On: ";
	private static final String PICK_DATE_LABEL = "Pick";
	private static final String CALENDAR_LABEL = "Calendar";
	private static final String MARK_AS_DONE_LABEL = "Mark as Done";
	private static final String TRASH_LABEL = "Trash";

	public LCDUICalendar(Calendar calendar, Display display) {
		this.calendar = calendar;
		this.display = display;
	}

	public void show() {
		datePickerForm.deleteAll();
		datePickerDateField.setDate(new Date());
		datePickerForm.append(datePickerDateField);
		datePickerForm.addCommand(pickDateCommand);
		datePickerForm.setCommandListener(new CommandListener() {
			public void commandAction(Command command, Displayable d) {
				if (command.equals(pickDateCommand)) {
					showScheduledStuffsList();
				}
			}
		});

		display.setCurrent(datePickerForm);
	}

	public void addCommand(Command command, Callback callback) {
		if (!commandToCallbackMap.containsKey(command)) {
			commandToCallbackMap.put(command, callback);
		}
	}

	public ScheduledStuff selectedScheduledStuff() {
		int selectedIndex = scheduledStuffsList.getSelectedIndex();
		return (ScheduledStuff) sequenceNumberToScheduledStuffMap.get(new Integer(
				selectedIndex));
	}

	public void attachPresenter(final CalendarPresenter presenter) {
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

	private void showScheduledStuffsList() {
		refreshScheduledStuffsList();
		embedCommands();
		scheduledStuffsList.setCommandListener(createCommandListener());
		display.setCurrent(scheduledStuffsList);
	}

	private CommandListener createCommandListener() {
		return new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (commandToCallbackMap.containsKey(c)) {
					Callback callback = (Callback) commandToCallbackMap.get(c);
					callback.execute();
				}
			}
		};
	}

	private void embedCommands() {
		Enumeration commandsEnum = commandToCallbackMap.keys();
		while (commandsEnum.hasMoreElements()) {
			scheduledStuffsList
					.addCommand((Command) commandsEnum.nextElement());
		}
	}

	private void refreshScheduledStuffsList() {
		scheduledStuffsList.deleteAll();
		ScheduledStuff[] ss = calendar.scheduledStuffsFor(getSelectedDate());
		int size = ss.length;
		for (int i = 0; i < size; i++) {
			sequenceNumberToScheduledStuffMap.put(new Integer(i), ss[i]);
			scheduledStuffsList.append(ss[i].description().descriptionValue_(),
					null);
		}
	}

	private CalendarDate getSelectedDate() {
		Date selectedDate = datePickerDateField.getDate();
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(selectedDate);
		CalendarDate properDate = CalendarDate
				.from(c.get(java.util.Calendar.YEAR),
						c.get(java.util.Calendar.MONTH),
						c.get(java.util.Calendar.DATE));
		return properDate;
	}

}
