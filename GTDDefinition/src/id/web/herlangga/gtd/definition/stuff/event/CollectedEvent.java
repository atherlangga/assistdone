package id.web.herlangga.gtd.definition.stuff.event;

import id.web.herlangga.gtd.definition.ValueObject;
import id.web.herlangga.gtd.definition.stuff.*;

import java.util.*;

public class CollectedEvent implements Event, ValueObject {
    private final Stuff source;
    private final Date timeStamp;
    private final Description description;

    public CollectedEvent(
            Stuff source, Date timeStamp,
            Description description) {
        this.timeStamp = createDefensiveCopyOf(timeStamp);
        this.source = source;
        this.description = description;
    }

    public Description description() {
        return description;
    }

    public Stuff source() {
        return source;
    }

    public Date timeStamp() {
        return createDefensiveCopyOf(timeStamp);
    }

    public TriggerType triggerType() {
        return TriggerType.COLLECTING;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }

    private static Date createDefensiveCopyOf(Date timeStamp) {
        return new Date(timeStamp.getTime());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectedEvent that = (CollectedEvent) o;

        if (!description.equals(that.description)) return false;
        if (!source.equals(that.source)) return false;
        if (!timeStamp.equals(that.timeStamp)) return false;

        return true;
    }

    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + timeStamp.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
