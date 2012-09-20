package id.web.herlangga.assistdone.jme.ui.repository;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.stuff.*;

import java.util.*;

public class TicklerFilePresenter implements Presenter {
	private final TicklerFileView view;
	private final TicklerFile ticklerFile;
	private final CapturedStuffEventProcessor eventProcessor;
	private final CapturedStuffRepository stuffRepository;

	public TicklerFilePresenter(TicklerFileView view, TicklerFile ticklerFile,
			CapturedStuffEventProcessor eventProcessor,
			CapturedStuffRepository stuffRepository) {
		this.view = view;
		this.ticklerFile = ticklerFile;
		this.eventProcessor = eventProcessor;
		this.stuffRepository = stuffRepository;
	}

	public void present() {
		view.clearHeldStuffs();
		view.addHeldStuffs(ticklerFile.heldStuffs());
		view.attachPresenter(this);
		view.show();
	}
	
	public void onTrashSelected() {
		CapturedStuff source = fetchCapturedStuffFromSelectedHeldStuff();
		Date now = new Date();
		
		TrashedEvent trashedEvent = new TrashedEvent(source, now);
		eventProcessor.process(trashedEvent);
	}
	
	private CapturedStuff fetchCapturedStuffFromSelectedHeldStuff() {
		long selectedStuffId = view.selectedHeldStuff().id();
		CapturedStuff stuff = stuffRepository.find(selectedStuffId);
		
		return stuff;
	}

}
