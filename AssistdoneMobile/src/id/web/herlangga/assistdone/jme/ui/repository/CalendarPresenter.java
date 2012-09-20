package id.web.herlangga.assistdone.jme.ui.repository;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.stuff.*;

public class CalendarPresenter implements Presenter {
	private final CalendarView view;
	private final CapturedStuffEventProcessor eventProcessor;
	private final CapturedStuffRepository stuffRepository;

	public CalendarPresenter(CalendarView view,
			CapturedStuffEventProcessor eventProcessor,
			CapturedStuffRepository stuffRepository) {
		this.view = view;
		this.eventProcessor = eventProcessor;
		this.stuffRepository = stuffRepository;
	}

	public void present() {
		view.attachPresenter(this);
		view.show();
	}

	public void onMarkAsDoneSelected() {
		CapturedStuff stuff = getCapturedStuffFromSelectedScheduledStuff();
		java.util.Date now = new java.util.Date();

		DoneEvent doneEvent = new DoneEvent(stuff, now);
		eventProcessor.process(doneEvent);
	}

	public void onTrashSelected() {
		CapturedStuff stuff = getCapturedStuffFromSelectedScheduledStuff();
		java.util.Date now = new java.util.Date();

		TrashedEvent trashedEvent = new TrashedEvent(stuff, now);
		eventProcessor.process(trashedEvent);
	}

	private CapturedStuff getCapturedStuffFromSelectedScheduledStuff() {
		long selectedScheduledStuffId = view.selectedScheduledStuff().id();
		CapturedStuff capturedStuff = stuffRepository
				.find(selectedScheduledStuffId);

		return capturedStuff;
	}

}
