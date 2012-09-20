package id.web.herlangga.assistdone.jme.ui.workflow;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.stuff.*;

import java.util.*;

public class ProcessPresenter implements Presenter {
	private final ProcessView view;
	private final InBasket inBasket;
	private final CapturedStuffEventProcessor eventProcessor;
	private final CapturedStuffRepository stuffRepository;

	public ProcessPresenter(ProcessView view, InBasket inBasket,
			CapturedStuffEventProcessor eventProcessor,
			CapturedStuffRepository stuffRepository) {
		this.view = view;
		this.inBasket = inBasket;
		this.eventProcessor = eventProcessor;
		this.stuffRepository = stuffRepository;
	}

	public void present() {
		view.clearCollectedStuffs();
		view.addCollectedStuffs(inBasket.collectedStuffs());

		view.attachPresenter(this);
		view.show();
	}

	public void onDoSelected() {
		DoneEvent event = createDoneEvent();
		eventProcessor.process(event);
		
		present();
	}
	
	public void onDeleteSelected() {
		TrashedEvent event = createTrashedEvent();
		eventProcessor.process(event);
	}

	public void onDelegateSelected(DelegatedParty party,
			DeliverableProduct expected) {
		DelegatedEvent event = createDelegatedEvent(party, expected);
		eventProcessor.process(event);
	}

	public void onDeferTimingSelected(Schedule schedule) {
		ScheduledEvent event = createScheduledEvent(schedule);
		eventProcessor.process(event);
	}

	public void onDeferContextSelected(Category category) {
		CategorizedEvent event = createCategorizedEvent(category);
		eventProcessor.process(event);
	}

	private DoneEvent createDoneEvent() {
		Stuff selected = view.selectedCollectedStuff();
		CapturedStuff stuff = stuffRepository.find(selected.id());
		Date now = new Date();

		DoneEvent event = new DoneEvent(stuff, now);
		return event;
	}

	private DelegatedEvent createDelegatedEvent(DelegatedParty party,
			DeliverableProduct expected) {
		Stuff selected = view.selectedCollectedStuff();
		CapturedStuff stuff = stuffRepository.find(selected.id());
		Date now = new Date();

		DelegatedEvent event = new DelegatedEvent(stuff, now, party, expected);
		return event;
	}

	private TrashedEvent createTrashedEvent() {
		Stuff selected = view.selectedCollectedStuff();
		CapturedStuff stuff = stuffRepository.find(selected.id());
		Date now = new Date();

		TrashedEvent event = new TrashedEvent(stuff, now);
		return event;
	}

	private ScheduledEvent createScheduledEvent(Schedule schedule) {
		Stuff selected = view.selectedCollectedStuff();
		CapturedStuff source = stuffRepository.find(selected.id());
		Date now = new Date();

		return new ScheduledEvent(source, now, schedule);
	}

	private CategorizedEvent createCategorizedEvent(Category category) {
		Stuff selected = view.selectedCollectedStuff();
		CapturedStuff source = stuffRepository.find(selected.id());
		Date now = new Date();

		CategorizedEvent event = new CategorizedEvent(source, now, category);
		return event;
	}
}
