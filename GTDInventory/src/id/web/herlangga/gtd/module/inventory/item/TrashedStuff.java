package id.web.herlangga.gtd.module.inventory.item;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;

public class TrashedStuff extends CapturedStuff {

    public TrashedStuff(long id, Description description) {
        super(id, description);
    }

    public StateType stateType() {
        return StateType.TRASHED;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }
}
