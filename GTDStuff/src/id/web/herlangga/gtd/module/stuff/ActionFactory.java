package id.web.herlangga.gtd.module.stuff;

import id.web.herlangga.gtd.definition.stuff.event.*;

import java.util.*;

abstract class ActionFactory {
    private static final Hashtable MAPPING = createMapping();

    static ActionFactory of(TriggerType type) {
        if (!MAPPING.containsKey(type)) {
            throw new IllegalArgumentException("Illegal TriggerType specified: "
                    + type.toString());
        }
        return (ActionFactory) MAPPING.get(type);
    }

    abstract Action createActionFrom(Event event);

    private static class CollectedActionFactory extends ActionFactory {
        Action createActionFrom(Event event) {
            final CollectedEvent result = (CollectedEvent) event;
            final CapturedStuff source = (CapturedStuff) event.source();
            return new Action() {
                public void act() {
                    source.handleCollected(result.description());
                }
            };
        }
    }

    private static class HoldActionFactory extends ActionFactory {
        Action createActionFrom(Event event) {
            final CapturedStuff source = (CapturedStuff) event.source();
            return new Action() {
                public void act() {
                    source.handleHeld();
                }
            };
        }
    }

    private static class DelegatedActionFactory extends ActionFactory {
        Action createActionFrom(Event event) {
            final DelegatedEvent result = (DelegatedEvent) event;
            final CapturedStuff source = (CapturedStuff) event.source();
            return new Action() {
                public void act() {
                    source.handleDelegated(result.appropriateParty(),
                            result.expectedProduct());
                }
            };
        }
    }

    private static class TrashedActionFactory extends ActionFactory {
        Action createActionFrom(Event event) {
            final CapturedStuff source = (CapturedStuff) event.source();
            return new Action() {
                public void act() {
                    source.handleTrashed();
                }
            };
        }
    }

    private static class DoneActionFactory extends ActionFactory {
        Action createActionFrom(Event event) {
            final CapturedStuff source = (CapturedStuff) event.source();
            return new Action() {
                public void act() {
                    source.handleDone();
                }
            };
        }
    }

    private static class ScheduledActionFactory extends ActionFactory {
        Action createActionFrom(Event event) {
            final ScheduledEvent scheduledEvent = (ScheduledEvent) event;
            final CapturedStuff source = (CapturedStuff) event.source();
            return new Action() {
                public void act() {
                    source.handleScheduled(scheduledEvent.schedule());
                }
            };
        }
    }

    private static class CategorizedActionFactory extends ActionFactory {
        Action createActionFrom(Event event) {
            final CategorizedEvent categorizedEvent = (CategorizedEvent) event;
            final CapturedStuff source = (CapturedStuff) event.source();
            return new Action() {
                public void act() {
                    source.handleCategorized(categorizedEvent.category());
                }
            };
        }
    }

    private static Hashtable createMapping() {
        Hashtable mapping = new Hashtable();
        mapping.put(TriggerType.COLLECTING, new CollectedActionFactory());
        mapping.put(TriggerType.HOLDING, new HoldActionFactory());
        mapping.put(TriggerType.DELEGATING, new DelegatedActionFactory());
        mapping.put(TriggerType.TRASHING, new TrashedActionFactory());
        mapping.put(TriggerType.DOING, new DoneActionFactory());
        mapping.put(TriggerType.SCHEDULING, new ScheduledActionFactory());
        mapping.put(TriggerType.CATEGORIZING, new CategorizedActionFactory());

        return mapping;
    }
}
