package id.web.herlangga.gtd.module.inventory.item;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;

public class HeldStuff extends CapturedStuff {
    
    public HeldStuff(long id, Description description) {
        super(id, description);
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }

    public StateType stateType() {
        return StateType.HELD;
    }
}
