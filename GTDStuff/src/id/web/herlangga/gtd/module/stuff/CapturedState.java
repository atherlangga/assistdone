package id.web.herlangga.gtd.module.stuff;

import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

class CapturedState extends State {
    void doCollecting(CapturedStuff stuff, Description description) {
        stuff.attributes().put(AttributeType.DESCRIPTION, description);
        stuff.updateState(StateType.COLLECTED);
    }

}
