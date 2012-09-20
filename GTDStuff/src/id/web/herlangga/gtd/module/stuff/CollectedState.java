package id.web.herlangga.gtd.module.stuff;

import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

class CollectedState extends State {
    void doing(CapturedStuff stuff) {
        stuff.updateState(StateType.DONE);
    }

    void doHolding(CapturedStuff stuff) {
        stuff.updateState(StateType.HELD);
    }

    void doDelegating(CapturedStuff stuff,
                      DelegatedParty appropriateParty,
                      DeliverableProduct expectedProduct) {
        stuff.attributes().put(AttributeType.DELEGATED_PARTY, appropriateParty);
        stuff.attributes().put(AttributeType.DELIVERABLE_PRODUCT, expectedProduct);
        stuff.updateState(StateType.DELEGATED);
    }

    void doScheduling(CapturedStuff stuff, Schedule schedule) {
        stuff.attributes().put(AttributeType.SCHEDULE, schedule);
        stuff.updateState(StateType.SCHEDULED);
    }

    void doCategorizing(CapturedStuff stuff, Category category) {
        stuff.attributes().put(AttributeType.CATEGORY, category);
        stuff.updateState(StateType.CATEGORIZED);
    }

    void doTrashing(CapturedStuff stuff) {
        stuff.updateState(StateType.TRASHED);
    }

}
