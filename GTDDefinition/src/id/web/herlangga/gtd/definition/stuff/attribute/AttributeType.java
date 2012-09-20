package id.web.herlangga.gtd.definition.stuff.attribute;

import java.util.*;

public class AttributeType {
    private final String typeName;

    private AttributeType(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        return "StuffAttributeType [typeName=" + typeName + "]";
    }

    private static final String DESCRIPTION_IDENTIFIER = "Description";
    private static final String DELEGATED_PARTY_IDENTIFIER = "DelegatedParty";
    private static final String DELIVERABLE_PRODUCT_IDENTIFIER =
            "DeliverableProduct";
    private static final String CATEGORY_IDENTIFIER = "Category";
    private static final String SCHEDULE_IDENTIFIER = "Schedule";

    public static final AttributeType DESCRIPTION = new AttributeType(
            DESCRIPTION_IDENTIFIER);
    public static final AttributeType DELEGATED_PARTY = new AttributeType(
            DELEGATED_PARTY_IDENTIFIER);
    public static final AttributeType DELIVERABLE_PRODUCT = new AttributeType(
            DELIVERABLE_PRODUCT_IDENTIFIER);
    public static final AttributeType CATEGORY = new AttributeType(
            CATEGORY_IDENTIFIER);
    public static final AttributeType SCHEDULE = new AttributeType(
            SCHEDULE_IDENTIFIER);

    private static final Hashtable TYPE_MAPPING = createTypeMapping();

    private static Hashtable createTypeMapping() {
        Hashtable mapping = new Hashtable();
        mapping.put(DESCRIPTION_IDENTIFIER, DESCRIPTION);
        mapping.put(DELEGATED_PARTY_IDENTIFIER, DELEGATED_PARTY);
        mapping.put(DELIVERABLE_PRODUCT_IDENTIFIER, DELIVERABLE_PRODUCT);
        mapping.put(CATEGORY_IDENTIFIER, CATEGORY);
        mapping.put(SCHEDULE_IDENTIFIER, SCHEDULE);

        return mapping;
    }

    public static AttributeType of_(String typeName) {
        if (!TYPE_MAPPING.containsKey(typeName)) {
            throw new IllegalArgumentException("Illegal type specified: "
                    + typeName);
        }
        return (AttributeType) TYPE_MAPPING.get(typeName);
    }

    public String name_() {
        return typeName;
    }
}
