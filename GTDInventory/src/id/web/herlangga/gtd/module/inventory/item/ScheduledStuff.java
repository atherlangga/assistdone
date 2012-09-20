package id.web.herlangga.gtd.module.inventory.item;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

public class ScheduledStuff extends CapturedStuff {
    private Schedule schedule;

    public ScheduledStuff(long id, Description description, Schedule schedule) {
        super(id, description);
        this.schedule = schedule;
    }

    public Schedule schedule() {
        return schedule;
    }

    public StateType stateType() {
        return StateType.SCHEDULED;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }
}
