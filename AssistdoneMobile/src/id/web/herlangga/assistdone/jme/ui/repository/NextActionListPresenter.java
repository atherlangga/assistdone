package id.web.herlangga.assistdone.jme.ui.repository;

import java.util.*;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.stuff.*;

public class NextActionListPresenter implements Presenter {
	private final NextActionListView view;
	private final NextActionList nextActionList;
	private final CapturedStuffRepository stuffRepository;
	private final CapturedStuffEventProcessor eventProcessor;

	public NextActionListPresenter(NextActionListView view,
			NextActionList nextActionList,
			CapturedStuffRepository stuffRepository,
			CapturedStuffEventProcessor eventProcessor) {
		this.view = view;
		this.nextActionList = nextActionList;
		this.stuffRepository = stuffRepository;
		this.eventProcessor = eventProcessor;
	}

	public void present() {
		view.clearCategorizedstuffs();
		view.addCategorizedStuffs(nextActionList.categorizedStuffs());
		view.attachPresenter(this);
		view.show();
	}

	public void onMarkAsDoneSelected() {
		long id = view.selectedCategorizedStuff().id();
		CapturedStuff stuff = stuffRepository.find(id);
		Date now = new Date();
		
		DoneEvent doneEvent = new DoneEvent(stuff, now);
		eventProcessor.process(doneEvent);
	}

	public void onTrashSelected() {
		long id = view.selectedCategorizedStuff().id();
		CapturedStuff stuff = stuffRepository.find(id);
		Date now = new Date();
		
		TrashedEvent trashedEvent = new TrashedEvent(stuff, now);
		eventProcessor.process(trashedEvent);
	}

}
