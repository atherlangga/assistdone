package id.web.herlangga.gtd.definition.stuff.event.party;

import id.web.herlangga.gtd.definition.stuff.event.*;

/**
 * Log contains interesting Events.
 *
 * @author at.herlangga
 */
public interface EventLog {

    /**
     * Append Event to the log.
     *
     * @param event The Event that just happened.
     */
    void append(Event event);

    /**
     * Load Event for particular Stuff.
     *
     * @param id Stuff ID to load its Event.
     * @return array of Event for particular Stuff.
     */
    Event[] load(long id);
}
