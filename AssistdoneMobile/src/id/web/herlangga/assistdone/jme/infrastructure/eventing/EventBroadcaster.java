package id.web.herlangga.assistdone.jme.infrastructure.eventing;

import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;

import java.util.*;

/**
 * Event announcer on every StuffEvent that just happened. Its jobs is to bridge
 * communication between the real broadcaster and several other subscribers.
 * 
 * @author at.herlangga
 * 
 */
public class EventBroadcaster implements EventSubscriber {
	private final Vector subscribers = new Vector();

	public void receive(Event result) {
		// FIXME Make this asynchronous
		Enumeration subscribersEnumeration = subscribers.elements();
		while (subscribersEnumeration.hasMoreElements()) {
			EventSubscriber subscriber = (EventSubscriber) subscribersEnumeration
					.nextElement();
			subscriber.receive(result);
		}
	}

	public void addSubscriber(EventSubscriber subscriber) {
		if (!subscribers.contains(subscriber)) {
			subscribers.addElement(subscriber);
		}
	}

}
