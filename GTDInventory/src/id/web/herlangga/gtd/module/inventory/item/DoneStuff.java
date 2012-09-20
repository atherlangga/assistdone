package id.web.herlangga.gtd.module.inventory.item;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;

public class DoneStuff extends CapturedStuff {
    public DoneStuff(long id, Description description) {
        super(id, description);
    }

    public StateType stateType() {
        return StateType.DONE;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }
}
