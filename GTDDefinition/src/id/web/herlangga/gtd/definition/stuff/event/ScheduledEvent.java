package id.web.herlangga.gtd.definition.stuff.event;

import id.web.herlangga.gtd.definition.ValueObject;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

import java.util.*;

public class ScheduledEvent implements Event, ValueObject {
    private final Stuff source;
    private final Date timeStamp;
    private final Schedule schedule;

    public ScheduledEvent(Stuff source, Date timeStamp, Schedule schedule) {
        this.source = source;
        this.timeStamp = createDefensiveCopyOf(timeStamp);
        this.schedule = schedule;
    }

    public Schedule schedule() {
        return schedule;
    }

    public Stuff source() {
        return source;
    }

    public Date timeStamp() {
        return createDefensiveCopyOf(timeStamp);
    }

    public TriggerType triggerType() {
        return TriggerType.SCHEDULING;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }

    private static Date createDefensiveCopyOf(Date date) {
        return new Date(date.getTime());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduledEvent that = (ScheduledEvent) o;

        if (schedule != null ? !schedule.equals(that.schedule) : that.schedule != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

        return true;
    }

    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        return result;
    }
}
