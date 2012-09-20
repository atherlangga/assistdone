package id.web.herlangga.gtd.module.inventory.item;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;

import java.util.*;

/**
 * Stuff that has been described but not yet processed.
 */
public class CollectedStuff extends CapturedStuff {
    private final Date describedDate;

    public CollectedStuff(long id, Description description, Date describedDate) {
        super(id, description);
        this.describedDate = describedDate;
    }

    public StateType stateType() {
        return StateType.COLLECTED;
    }

    public Date describedDate() {
        return describedDate;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }
}
