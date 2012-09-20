package id.web.herlangga.gtd.definition.stuff.event;

import id.web.herlangga.gtd.definition.ValueObject;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

import java.util.*;

public class CategorizedEvent implements Event, ValueObject {
    private final Stuff source;
    private final Date timeStamp;
    private final Category category;

    public CategorizedEvent(Stuff source, Date timeStamp, Category category) {
        this.source = source;
        this.timeStamp = createDefensiveCopyOf(timeStamp);
        this.category = category;
    }

    public Category category() {
        return category;
    }

    public Stuff source() {
        return source;
    }

    public Date timeStamp() {
        return createDefensiveCopyOf(timeStamp);
    }

    public TriggerType triggerType() {
        return TriggerType.CATEGORIZING;
    }

    private static Date createDefensiveCopyOf(Date date) {
        return new Date(date.getTime());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategorizedEvent that = (CategorizedEvent) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

        return true;
    }

    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);

    }
}
