package id.web.herlangga.assistdone.jme.infrastructure.lcdui.workflow;

import id.web.herlangga.assistdone.jme.infrastructure.lcdui.*;
import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.workflow.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.module.inventory.item.*;

import java.util.*;

import javax.microedition.lcdui.*;

import com.domainlanguage.time.*;

public class LCDUIProcessView implements ProcessView, LCDUIScreen {
	private final Display display;
	private final Hashtable menuMapping = new Hashtable();
	private final Vector collectedStuffs = new Vector();

	private final List stuffList = new List(PROCESS_LIST_TITLE, Choice.IMPLICIT);
	private final Command doCommand = new Command(DO_LABEL, Command.ITEM, 1);
	private final Command deleteCommand = new Command(DELETE_LABEL,
			Command.ITEM, 2);
	private final Command delegateCommand = new Command(DELEGATE_LABEL,
			Command.ITEM, 3);
	private final Command scheduleCommand = new Command(SCHEDULE_LABEL,
			Command.ITEM, 4);
	private final Command categorizeCommand = new Command(CATEGORIZE_LABEL,
			Command.ITEM, 5);

	public LCDUIProcessView(Display display) {
		this.display = display;
	}

	public void addCollectedStuffs(CollectedStuff[] collectedStuffs) {
		int size = collectedStuffs.length;
		for (int i = 0; i < size; i++) {
			addCollectedStuff(collectedStuffs[i]);
		}
	}

	public void clearCollectedStuffs() {
		stuffList.deleteAll();
		collectedStuffs.removeAllElements();
	}

	public void attachPresenter(final ProcessPresenter presenter) {
		addCommand(doCommand, new Callback() {
			public void execute() {
				presenter.onDoSelected();
			}
		});

		addCommand(deleteCommand, new Callback() {
			public void execute() {
				presenter.onDeleteSelected();
			}
		});

		addCommand(delegateCommand, new Callback() {
			public void execute() {
				final DelegationForm f = new DelegationForm(display);
				f.show(new Callback() {
					public void execute() {
						DelegatedParty party = f.appropriateParty();
						DeliverableProduct expected = f.expectedProduct();

						presenter.onDelegateSelected(party, expected);
						presenter.present();
					}
				}, new Callback() {
					public void execute() {
						presenter.present();
					}
				});
			}
		});

		addCommand(scheduleCommand, new Callback() {
			public void execute() {
				final ScheduleForm f = new ScheduleForm(display);
				f.show(new Callback() {
					public void execute() {
						Schedule schedule = f.schedule();

						presenter.onDeferTimingSelected(schedule);
						presenter.present();
					}
				}, new Callback() {
					public void execute() {
						presenter.present();
					}
				});
			}
		});

		addCommand(categorizeCommand, new Callback() {
			public void execute() {
				final ContextForm f = new ContextForm(display);
				f.show(new Callback() {
					public void execute() {
						Category c = f.category();

						presenter.onDeferContextSelected(c);
						presenter.present();
					}
				}, new Callback() {
					public void execute() {
						presenter.present();
					}
				});
			}
		});
	}

	public CollectedStuff selectedCollectedStuff() {
		int selectedIndex = stuffList.getSelectedIndex();
		CollectedStuff selected = (CollectedStuff) collectedStuffs
				.elementAt(selectedIndex);

		return selected;
	}

	public void show() {
		embedCommands();
		configureCommandListener();
		display.setCurrent(stuffList);
	}

	public void addCommand(Command command, Callback callback) {
		if (!menuMapping.containsKey(command)) {
			menuMapping.put(command, callback);
		}
	}

	private void addCollectedStuff(CollectedStuff collectedStuff) {
		if (!collectedStuffs.contains(collectedStuff)) {
			stuffList.append(collectedStuff.description().descriptionValue_(),
					null);
			collectedStuffs.addElement(collectedStuff);
		}
	}

	private void embedCommands() {
		Enumeration commandsEnumaration = menuMapping.keys();
		while (commandsEnumaration.hasMoreElements()) {
			Command c = (Command) commandsEnumaration.nextElement();
			stuffList.addCommand(c);
		}
	}

	private void configureCommandListener() {
		stuffList.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (menuMapping.containsKey(c)) {
					Callback callback = (Callback) menuMapping.get(c);
					callback.execute();
				}
			}
		});
	}

	private static final String PROCESS_LIST_TITLE = "Process";
	private static final String DO_LABEL = "Do";
	private static final String DELETE_LABEL = "Delete";
	private static final String DELEGATE_LABEL = "Delegate";
	private static final String SCHEDULE_LABEL = "Schedule";
	private static final String CATEGORIZE_LABEL = "Categorize";

	/**
	 * Form to gather required parameter for delegating stuff.
	 * 
	 * @author at.herlangga
	 */
	private class DelegationForm {
		private final Display display;

		DelegationForm(Display display) {
			this.display = display;
		}

		private void show(final Callback okCallback,
				final Callback cancelCallback) {
			delegationForm.append(contactNameTextField);
			delegationForm.append(contactInformationTextField);
			delegationForm.append(productTextField);
			delegationForm.addCommand(saveCommand);
			delegationForm.addCommand(cancelCommand);
			delegationForm.setCommandListener(createCommandListener(okCallback,
					cancelCallback));

			display.setCurrent(delegationForm);
		}

		private DelegatedParty appropriateParty() {
			String contactName = contactNameTextField.getString();
			String contactInformation = contactInformationTextField.getString();
			return new DelegatedParty(contactName, contactInformation);
		}

		private DeliverableProduct expectedProduct() {
			return new DeliverableProduct(productTextField.getString());
		}

		private CommandListener createCommandListener(
				final Callback okCallback, final Callback cancelCallback) {
			return new CommandListener() {
				public void commandAction(Command c, Displayable d) {
					if (c.equals(saveCommand)) {
						okCallback.execute();
					} else if (c.equals(cancelCommand)) {
						cancelCallback.execute();
					}
				}
			};
		}

		private final TextField contactNameTextField = new TextField(
				"Contact name:", "", 255, TextField.ANY);
		private final TextField contactInformationTextField = new TextField(
				"Contact information:", "", 255, TextField.ANY);
		private final TextField productTextField = new TextField(
				"Expected product:", "", 255, TextField.ANY);
		private final Command saveCommand = new Command("Save", Command.OK, 1);
		private final Command cancelCommand = new Command("Cancel",
				Command.CANCEL, 2);
		private final Form delegationForm = new Form("Delegation Form");

	}

	/**
	 * Form to gather information about Schedule.
	 * 
	 * @author at.herlangga
	 */
	private class ScheduleForm {
		private final Display display;

		public ScheduleForm(Display display) {
			this.display = display;
		}

		public Schedule schedule() {
			Date selectedDate = scheduleDateField.getDate();
			Calendar.getInstance().setTime(selectedDate);
			int date = Calendar.getInstance().get(Calendar.DATE);
			int month = Calendar.getInstance().get(Calendar.MONTH);
			int year = Calendar.getInstance().get(Calendar.YEAR);

			return new Schedule(CalendarDate.date(year, month, date));
		}

		private void show(final Callback okCallback,
				final Callback cancelCallback) {
			scheduleDateField.setDate(new Date());
			scheduleForm.append(scheduleDateField);
			scheduleForm.addCommand(saveCommand);
			scheduleForm.addCommand(cancelCommand);
			scheduleForm.setCommandListener(createCommandListener(okCallback,
					cancelCallback));

			display.setCurrent(scheduleForm);
		}

		private CommandListener createCommandListener(
				final Callback okCallback, final Callback cancelCallback) {
			return new CommandListener() {
				public void commandAction(Command c, Displayable d) {
					if (c.equals(saveCommand)) {
						okCallback.execute();
					} else if (c.equals(cancelCommand)) {
						cancelCallback.execute();
					}
				}
			};
		}

		private final DateField scheduleDateField = new DateField("Start Date",
				DateField.DATE);
		private final Command saveCommand = new Command("Save", Command.OK, 1);
		private final Command cancelCommand = new Command("Cancel",
				Command.CANCEL, 2);
		private final Form scheduleForm = new Form("Schedule Form");
	}

	/**
	 * Form to gather information about Context.
	 * 
	 * @author at.herlangga
	 */
	private class ContextForm {
		private final Display display;

		public ContextForm(Display display) {
			this.display = display;
		}

		public Category category() {
			return new Category(categoryTextField.getString());
		}

		private void show(final Callback okCallback,
				final Callback cancelCallback) {
			contextForm.append(categoryTextField);
			contextForm.addCommand(saveCommand);
			contextForm.addCommand(cancelCommand);
			contextForm.setCommandListener(new CommandListener() {
				public void commandAction(Command c, Displayable d) {
					if (c.equals(saveCommand)) {
						okCallback.execute();
					} else if (c.equals(cancelCommand)) {
						cancelCallback.execute();
					}
				}
			});

			display.setCurrent(contextForm);
		}

		private final TextField categoryTextField = new TextField("Category",
				"", 255, TextField.ANY);
		private final Command saveCommand = new Command("Save", Command.OK, 1);
		private final Command cancelCommand = new Command("Cancel",
				Command.CANCEL, 2);
		private final Form contextForm = new Form("Context Form");
	}

}
