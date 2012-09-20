package id.web.herlangga.gtd.module.stuff;

import id.web.herlangga.gtd.definition.stuff.*;

class ScheduledState extends State {
    void doing(CapturedStuff stuff) {
        stuff.updateState(StateType.DONE);
    }

    void doTrashing(CapturedStuff stuff) {
        stuff.updateState(StateType.TRASHED);
    }
}
