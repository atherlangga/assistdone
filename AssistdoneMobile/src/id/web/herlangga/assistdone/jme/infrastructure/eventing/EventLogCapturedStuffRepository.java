package id.web.herlangga.assistdone.jme.infrastructure.eventing;

import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.stuff.*;

public class EventLogCapturedStuffRepository implements CapturedStuffRepository {
	private final EventLog eventLog;

	public EventLogCapturedStuffRepository(EventLog eventLog) {
		this.eventLog = eventLog;
	}

	public CapturedStuff find(long id) {
		Event[] recordedEvents = eventLog.load(id);

		if (recordedEvents.length < 1) {
			throw new IllegalArgumentException(
					"No events recorded for Stuff with id " + id);
		}

		Event lastEvent = getLastEventOf(recordedEvents);
		return (CapturedStuff) lastEvent.source();
	}

	private Event getLastEventOf(Event[] recordedEvents) {
		int totalEvents = recordedEvents.length;
		Event lastEvent = recordedEvents[0];
		for (int i = 0; i < totalEvents; i++) {
			if (recordedEvents[i].timeStamp().getTime() > lastEvent.timeStamp()
					.getTime()) {
				lastEvent = recordedEvents[i];
			}
		}
		return lastEvent;
	}
}
