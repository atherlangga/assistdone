package id.web.herlangga.gtd.definition.stuff.event;

import id.web.herlangga.gtd.definition.stuff.*;

import java.util.*;

/**
 * The result doing something to Stuff in the GTD environment.
 *
 * @author at.herlangga
 */
public interface Event {

    /**
     * Get the TriggerType causing this Event.
     *
     * @return TriggerType of the Event.
     */
    TriggerType triggerType();

    /**
     * Get Stuff causing this Event.
     *
     * @return source of the Event.
     */
    Stuff source();

    /**
     * Get the time stamp of this Event.
     *
     * @return time stamp of this Event.
     */
    Date timeStamp();
}