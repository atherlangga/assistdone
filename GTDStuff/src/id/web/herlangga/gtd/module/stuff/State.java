package id.web.herlangga.gtd.module.stuff;

import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

/**
 * Provides CapturedStuff correct behavior for State
 * change.
 * <p/>
 * Because its nature to determine which next State for the parent
 * CapturedStuff is valid, State also has role as handler for
 * action that will causing State transition for its
 * parent CapturedStuff.
 * <p/>
 * In order to avoid duplication, this abstract class gives default
 * action handling implementation. The default implementation is to
 * invalidate every action and delegate its invalidation to
 * the CapturedStuff. Therefore, subclasses of this State should
 * provide correct overriding behavior.
 *
 * @author at.herlangga
 */
abstract class State {
    void doCollecting(CapturedStuff stuff, Description description) {
        stuff.handleInvalidatedAction();
    }

    void doing(CapturedStuff stuff) {
        stuff.handleInvalidatedAction();
    }

    void doHolding(CapturedStuff stuff) {
        stuff.handleInvalidatedAction();
    }

    void doDelegating(CapturedStuff stuff,
                      DelegatedParty appropriateParty,
                      DeliverableProduct expectedProduct) {
        stuff.handleInvalidatedAction();
    }

    void doScheduling(CapturedStuff stuff, Schedule schedule) {
        stuff.handleInvalidatedAction();
    }

    void doCategorizing(CapturedStuff stuff, Category category) {
        stuff.handleInvalidatedAction();
    }

    void doTrashing(CapturedStuff stuff) {
        stuff.handleInvalidatedAction();
    }
}
