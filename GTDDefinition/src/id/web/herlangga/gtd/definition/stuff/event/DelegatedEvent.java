package id.web.herlangga.gtd.definition.stuff.event;

import id.web.herlangga.gtd.definition.ValueObject;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

import java.util.*;

public class DelegatedEvent implements Event, ValueObject {
    private final Stuff source;
    private final Date timeStamp;
    private final DelegatedParty appropriateParty;
    private final DeliverableProduct expectedProduct;

    public DelegatedEvent(
            Stuff source,
            Date timeStamp,
            DelegatedParty appropriateParty,
            DeliverableProduct expectedProduct) {
        this.source = source;
        this.timeStamp = createDefensiveCopyOf(timeStamp);
        this.appropriateParty = appropriateParty;
        this.expectedProduct = expectedProduct;
    }

    public DelegatedParty appropriateParty() {
        return appropriateParty;
    }

    public DeliverableProduct expectedProduct() {
        return expectedProduct;
    }

    public Stuff source() {
        return source;
    }

    public Date timeStamp() {
        return createDefensiveCopyOf(timeStamp);
    }

    public TriggerType triggerType() {
        return TriggerType.DELEGATING;
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

        DelegatedEvent that = (DelegatedEvent) o;

        if (appropriateParty != null ? !appropriateParty.equals(that.appropriateParty) : that.appropriateParty != null)
            return false;
        if (expectedProduct != null ? !expectedProduct.equals(that.expectedProduct) : that.expectedProduct != null)
            return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

        return true;
    }

    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        result = 31 * result + (appropriateParty != null ? appropriateParty.hashCode() : 0);
        result = 31 * result + (expectedProduct != null ? expectedProduct.hashCode() : 0);
        return result;
    }
}
