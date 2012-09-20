package id.web.herlangga.assistdone.jme.infrastructure.badulik.stuff;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.stuff.*;

public class BadulikCapturedStuffRepository implements CapturedStuffRepository,
		EventSubscriber {
	private final ObjectStorage storage;
	private final BadulikCapturedStuffDescriptor descriptor;

	public BadulikCapturedStuffRepository(ObjectStorage storage,
			BadulikCapturedStuffDescriptor descriptor) {
		this.storage = storage;
		this.descriptor = descriptor;
	}

	public void save(CapturedStuff stuff) {
		Object capturedStuffObject = descriptor.describe(stuff);
		storage.save(capturedStuffObject, descriptor, descriptor);
	}

	public CapturedStuff find(long id) {
		Object capturedStuffObject = storage.find(Element.of(id), descriptor);
		return descriptor.reconstitute(capturedStuffObject);
	}

	public void receive(Event event) {
		if (event.triggerType().equals(TriggerType.COLLECTING)) {
			save((CapturedStuff) event.source());
		}
	}

}
