package id.web.herlangga.gtd.definition.stuff.attribute;

import id.web.herlangga.gtd.definition.*;

/**
 * Attribute of a Stuff.
 *
 * @author at.herlangga
 */
public interface Attribute extends ValueObject {

    /**
     * Get the type of this Attribute.
     *
     * @return Type of this Attribute.
     */
    public AttributeType type();
}
