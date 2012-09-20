package id.web.herlangga.gtd.module.stuff;

import id.web.herlangga.gtd.definition.stuff.*;

class DoneState extends State {
    void doTrashing(CapturedStuff stuff) {
        stuff.updateState(StateType.TRASHED);
    }

}
