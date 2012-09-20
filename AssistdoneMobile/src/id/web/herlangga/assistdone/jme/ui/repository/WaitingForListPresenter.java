package id.web.herlangga.assistdone.jme.ui.repository;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.stuff.*;

import java.util.*;

public class WaitingForListPresenter implements Presenter {
	private final WaitingForListView view;
	private final WaitingForList waitingForList;
	private final CapturedStuffRepository stuffRepository;
	private final CapturedStuffEventProcessor eventProcessor;

	public WaitingForListPresenter(WaitingForListView view,
			WaitingForList waitingForList,
			CapturedStuffRepository stuffRepository,
			CapturedStuffEventProcessor eventProcessor) {
		this.view = view;
		this.waitingForList = waitingForList;
		this.stuffRepository = stuffRepository;
		this.eventProcessor = eventProcessor;
	}

	public void present() {
		view.clearDelegatedStuffs();
		view.addDelegatedStuffs(waitingForList.delegatedStuffs());
		view.attachPresenter(this);
		view.show();
	}

	public void onMarkAsDoneSelected() {
		CapturedStuff stuff = getAssociatedCapturedStuff();
		Date now = new Date();

		DoneEvent doneEvent = new DoneEvent(stuff, now);
		eventProcessor.process(doneEvent);
	}

	public void onTrashSelected() {
		CapturedStuff stuff = getAssociatedCapturedStuff();
		Date now = new Date();

		TrashedEvent trashedEvent = new TrashedEvent(stuff, now);
		eventProcessor.process(trashedEvent);
	}

	private CapturedStuff getAssociatedCapturedStuff() {
		long selectedDelegatedStuffId = view.selectedDelegatedStuff().id();
		CapturedStuff capturedStuff = stuffRepository
				.find(selectedDelegatedStuffId);

		return capturedStuff;
	}

}
