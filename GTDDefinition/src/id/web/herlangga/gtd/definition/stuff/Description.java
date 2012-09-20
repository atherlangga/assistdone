package id.web.herlangga.gtd.definition.stuff;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

public class Description implements Attribute {
    private final String description;

    public Description(String description) {
        this.description = description;
    }

    public AttributeType type() {
        return AttributeType.DESCRIPTION;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Description that = (Description) o;

        if (!description.equals(that.description)) return false;

        return true;
    }

    public int hashCode() {
        return description.hashCode();
    }

    public String toString() {
        return "Description{" +
                "description='" + description + '\'' +
                '}';
    }

    public String descriptionValue_() {
        return description;
    }

}
