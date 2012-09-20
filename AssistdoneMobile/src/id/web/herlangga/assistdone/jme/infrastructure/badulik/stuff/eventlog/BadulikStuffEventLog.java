package id.web.herlangga.assistdone.jme.infrastructure.badulik.stuff.eventlog;

import id.web.herlangga.badulik.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;

public class BadulikStuffEventLog implements EventLog {
	private ObjectStorage storage;
	private BadulikStuffEventLogDescriptor descriptor;

	public BadulikStuffEventLog(ObjectStorage storage,
			BadulikStuffEventLogDescriptor descriptor) {
		this.storage = storage;
		this.descriptor = descriptor;
	}

	public void append(Event event) {
		long id = storage.nextSequenceNumber();
		storage.save(descriptor.pack(id, event), descriptor, descriptor);
	}

	public Event[] load(long stuffId) {
		Object[] eventObjects = fetchEventObjectsWithId(stuffId);
		return castToEvents(eventObjects);
	}

	private Event[] castToEvents(Object[] eventObjects) {
		int totalEventObjects = eventObjects.length;
		Event[] events = new Event[totalEventObjects];
		for (int i = 0; i < totalEventObjects; i++) {
			events[i] = (Event) eventObjects[i];
		}

		return events;
	}

	private Object[] fetchEventObjectsWithId(final long stuffId) {
		Object[] eventObjects = storage.findAll(new ObjectFilter() {
			public boolean matches(Object object) {
				Event event = (Event) object;
				return (event.source().id() == stuffId);
			}
		}, descriptor);
		return eventObjects;
	}

}
