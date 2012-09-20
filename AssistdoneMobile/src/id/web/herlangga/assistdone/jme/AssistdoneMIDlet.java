package id.web.herlangga.assistdone.jme;

import id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory.*;
import id.web.herlangga.assistdone.jme.infrastructure.badulik.stuff.eventlog.*;
import id.web.herlangga.assistdone.jme.infrastructure.eventing.*;
import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event.*;
import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.assistdone.jme.infrastructure.lcdui.repository.*;
import id.web.herlangga.assistdone.jme.infrastructure.lcdui.workflow.*;
import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.repository.*;
import id.web.herlangga.assistdone.jme.ui.workflow.*;
import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.backend.rms.*;
import id.web.herlangga.gtd.module.stuff.*;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class AssistdoneMIDlet extends MIDlet {
	/**
	 * Helper class to help exit from this application.
	 * 
	 * @author at.herlangga
	 */
	private class ExitCommand implements Callback {
		public void execute() {
			try {
				destroyApp(true);
			} catch (MIDletStateChangeException e) {
				e.printStackTrace();
			}
			notifyDestroyed();
		}
	}

	protected void startApp() throws MIDletStateChangeException {
		// Get display
		Display display = Display.getDisplay(this);

		// Create Event Broadcaster
		EventBroadcaster broadcaster = new EventBroadcaster();
		// Create Repository Manager
		ObjectStorageManager storageManager = new RMSStorageManager();

		// Create Event Serialization
		JSONEventSerialization eventSerializer = new JSONEventSerialization(
				new JSONStuffSerialization(
						new JSONStuffAttributeSerialization()));
		BadulikStuffEventLogDescriptor eventLogDescriptor = new BadulikStuffEventLogDescriptor(
				eventSerializer);
		BadulikStuffEventLog eventLog = new BadulikStuffEventLog(
				storageManager
						.get("StuffEventLog", eventLogDescriptor.schema()),
				eventLogDescriptor);

		// Create Event Processor
		CapturedStuffEventProcessor eventProcessor = new CapturedStuffEventProcessor(
				eventLog, broadcaster);

		// Create Stuff Repository
		CapturedStuffRepository stuffRepository = new EventLogCapturedStuffRepository(
				eventLog);

		// Create Collected Stuff Descriptor
		BadulikCollectedStuffDescriptor collectedStuffDescriptor = new BadulikCollectedStuffDescriptor();
		// Create InBasket
		BadulikInBasket inBasket = new BadulikInBasket(storageManager.get(
				"InBasket", collectedStuffDescriptor.schema()),
				collectedStuffDescriptor);

		// Create Delegated Stuff Descriptor
		BadulikDelegatedStuffDescriptor delegatedStuffDescriptor = new BadulikDelegatedStuffDescriptor();
		// Create WaitingForList
		BadulikWaitingForList waitingForList = new BadulikWaitingForList(
				storageManager.get("WaitingForList",
						delegatedStuffDescriptor.schema()),
				delegatedStuffDescriptor);

		BadulikCategorizedStuffDescriptor categorizedStuffDescriptor = new BadulikCategorizedStuffDescriptor();
		BadulikNextActionList nextActionList = new BadulikNextActionList(
				storageManager.get("NextActionList",
						categorizedStuffDescriptor.schema()),
				categorizedStuffDescriptor);

		BadulikHeldStuffDescriptor heldStuffDescriptor = new BadulikHeldStuffDescriptor();
		BadulikTicklerFile ticklerFile = new BadulikTicklerFile(
				storageManager.get("TicklerFile", heldStuffDescriptor.schema()),
				heldStuffDescriptor);

		BadulikCalendar calendar = new BadulikCalendar(storageManager.get(
				"Calendar", BadulikCalendar.SCHEDULED_STUFF_SCHEMA));

		final LCDUINextActionListView nextActionListView = new LCDUINextActionListView(
				display);
		final NextActionListPresenter nextActionListPresenter = new NextActionListPresenter(
				nextActionListView, nextActionList, stuffRepository,
				eventProcessor);

		final LCDUIWaitingForListView waitingForListView = new LCDUIWaitingForListView(
				display);
		final WaitingForListPresenter waitingForListPresenter = new WaitingForListPresenter(
				waitingForListView, waitingForList, stuffRepository,
				eventProcessor);

		final LCDUITicklerFileView ticklerFileView = new LCDUITicklerFileView(
				display);
		final TicklerFilePresenter ticklerFilePresenter = new TicklerFilePresenter(
				ticklerFileView, ticklerFile, eventProcessor, stuffRepository);

		final LCDUICalendar calendarView = new LCDUICalendar(calendar, display);
		final CalendarPresenter calendarPresenter = new CalendarPresenter(
				calendarView, eventProcessor, stuffRepository);

		// Create View and Presenter for Collecting
		final LCDUICollectView collectView = new LCDUICollectView(display);
		final CollectPresenter collectPresenter = new CollectPresenter(
				collectView, inBasket, eventProcessor);

		// Create View and Presenter for Processing
		final LCDUIProcessView processView = new LCDUIProcessView(display);
		final ProcessPresenter processPresenter = new ProcessPresenter(
				processView, inBasket, eventProcessor, stuffRepository);

		// Create View and Presenter for Reviewing
		final LCDUIReviewView reviewView = new LCDUIReviewView(display);
		final ReviewPresenter reviewPresenter = new ReviewPresenter(reviewView,
				calendarPresenter, nextActionListPresenter,
				waitingForListPresenter, ticklerFilePresenter);

		// Create helper for exiting application.
		Callback exitApp = new ExitCommand();

		// Set up subscribers
		broadcaster.addSubscriber(inBasket);
		broadcaster.addSubscriber(waitingForList);
		broadcaster.addSubscriber(nextActionList);
		broadcaster.addSubscriber(calendar);
		broadcaster.addSubscriber(ticklerFile);

		// Set up menu for Collect View
		collectView.addCommand(PROCESS_COMMAND, new Callback() {
			public void execute() {
				processPresenter.present();
			}
		});
		collectView.addCommand(REVIEW_COMMAND, new Callback() {
			public void execute() {
				reviewPresenter.present();
			}
		});
		collectView.addCommand(EXIT_COMMAND, exitApp);

		// Set up menu for Process View
		processView.addCommand(BACK_COMMAND, new Callback() {
			public void execute() {
				collectView.show();
			}
		});
		processView.addCommand(EXIT_COMMAND, exitApp);

		// Set up menu for Review View
		reviewView.addCommand(BACK_COMMAND, new Callback() {
			public void execute() {
				collectView.show();
			}
		});
		reviewView.addCommand(EXIT_COMMAND, exitApp);

		// Set up menu for Waiting For List View
		waitingForListView.addCommand(BACK_COMMAND, new Callback() {
			public void execute() {
				reviewView.show();
			}
		});
		
		calendarView.addCommand(BACK_COMMAND, new Callback() {
			public void execute() {
				reviewView.show();
			}
		});
		
		nextActionListView.addCommand(BACK_COMMAND, new Callback() {
			public void execute() {
				reviewView.show();
			}
		});
		
		ticklerFileView.addCommand(BACK_COMMAND, new Callback() {
			public void execute() {
				reviewView.show();
			}
		});

		// Start the engine..
		collectPresenter.present();
	}

	protected void pauseApp() {
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	private static final String BACK_LABEL = "Back";
	private static final String EXIT_LABEL = "Exit";
	private static final String PROCESS_LABEL = "Process";
	private static final String REVIEW_LABEL = "Review";

	private static final Command BACK_COMMAND = new Command(BACK_LABEL,
			Command.BACK, 2);
	private static final Command EXIT_COMMAND = new Command(EXIT_LABEL,
			Command.EXIT, 1);
	private static final Command PROCESS_COMMAND = new Command(PROCESS_LABEL,
			Command.SCREEN, 10);
	private static final Command REVIEW_COMMAND = new Command(REVIEW_LABEL,
			Command.SCREEN, 11);

}
