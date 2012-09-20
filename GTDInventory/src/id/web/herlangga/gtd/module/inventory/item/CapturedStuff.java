package id.web.herlangga.gtd.module.inventory.item;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;

/**
 * Stuff captured by GTD user.
 * <p/>
 * Once Stuff is collected, it begin its journey in the GTD system.
 */
abstract class CapturedStuff implements Stuff, ValueObject {
    private final long id;
    private final Description description;

    CapturedStuff(long id, Description description) {
        this.id = id;
        this.description = description;
    }

    public long id() {
        return id;
    }

    public Description description() {
        return description;
    }

    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CapturedStuff other = (CapturedStuff) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
