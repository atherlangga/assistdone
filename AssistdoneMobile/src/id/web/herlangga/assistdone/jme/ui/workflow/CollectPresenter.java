package id.web.herlangga.assistdone.jme.ui.workflow;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.stuff.*;

import java.util.*;

public class CollectPresenter implements Presenter {
	private final CollectView view;
	private final StuffIdGenerator idGenerator;
	private final CapturedStuffEventProcessor eventProcessor;

	public CollectPresenter(CollectView view, StuffIdGenerator idGenerator,
			CapturedStuffEventProcessor eventProcessor) {
		this.view = view;
		this.idGenerator = idGenerator;
		this.eventProcessor = eventProcessor;
	}

	public void present() {
		view.attachPresenter(this);
		view.show();
	}

	public void onSave(Description description) {
		long newId = idGenerator.generateStuffId();
		CapturedStuff stuff = new CapturedStuff(newId, StateType.CAPTURED);
		Date now = new Date();

		CollectedEvent collectedEvent = new CollectedEvent(stuff,
				now, description);
		eventProcessor.process(collectedEvent);
	}

}
