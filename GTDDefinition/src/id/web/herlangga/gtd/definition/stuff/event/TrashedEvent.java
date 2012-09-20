package id.web.herlangga.gtd.definition.stuff.event;

import id.web.herlangga.gtd.definition.ValueObject;
import id.web.herlangga.gtd.definition.stuff.*;

import java.util.*;

public class TrashedEvent implements Event, ValueObject {
    private final Stuff source;
    private final Date timeStamp;

    public TrashedEvent(Stuff source, Date timeStamp) {
        this.source = source;
        this.timeStamp = createDefensiveCopyOf(timeStamp);
    }

    public Stuff source() {
        return source;
    }

    public Date timeStamp() {
        return createDefensiveCopyOf(timeStamp);
    }

    public TriggerType triggerType() {
        return TriggerType.TRASHING;
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

        TrashedEvent that = (TrashedEvent) o;

        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

        return true;
    }

    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        return result;
    }
}
