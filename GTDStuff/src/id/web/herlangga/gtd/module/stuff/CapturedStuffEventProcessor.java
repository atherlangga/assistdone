package id.web.herlangga.gtd.module.stuff;

import id.web.herlangga.gtd.definition.stuff.event.Event;
import id.web.herlangga.gtd.definition.stuff.event.party.EventLog;
import id.web.herlangga.gtd.definition.stuff.event.party.EventSubscriber;

public class CapturedStuffEventProcessor {
    private final EventLog log;
    private final EventSubscriber subscriber;

    public CapturedStuffEventProcessor(EventLog log,
                                       EventSubscriber subscriber) {
        this.log = log;
        this.subscriber = subscriber;

    }

    /**
     * Tells the universe that an Event just happens
     * after making sure the Event is logged.
     *
     * @param event Event caused by user's action.
     */
    public void process(Event event) {
        Action action = extractActionFrom(event);
        action.act();
        log.append(event);
        subscriber.receive(event);
    }

    private Action extractActionFrom(Event event) {
        ActionFactory factory = ActionFactory.of(event.triggerType());
        return factory.createActionFrom(event);
    }
}
