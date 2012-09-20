package id.web.herlangga.gtd.definition.stuff.event.party;

import id.web.herlangga.gtd.definition.stuff.event.*;

/**
 * Party interested in Event.
 *
 * @author at.herlangga
 */
public interface EventSubscriber {

    /**
     * Receive notification about something happened in the system because of
     * particular Event.
     *
     * @param event The Event causing system-state changed.
     */
    void receive(Event event);
}
