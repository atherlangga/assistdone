package id.web.herlangga.gtd.definition.stuff.event;

import java.util.*;

/**
 * Type of Event trigger.
 *
 * @author at.herlangga
 */
public class TriggerType {
    private final String typeName;

    private TriggerType(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        return "id.web.herlangga.gtd.definition.activity.TriggerType{" +
                "typeName='" + typeName + '\'' +
                '}';
    }

    private static final String COLLECTING_IDENTIFIER = "Collecting";
    private static final String DOING_IDENTIFIER = "Doing";
    private static final String HOLDING_IDENTIFIER = "Holding";
    private static final String DELEGATING_IDENTIFIER = "Delegating";
    private static final String SCHEDULING_IDENTIFIER = "Scheduling";
    private static final String CATEGORIZING_IDENTIFIER = "Categorizing";
    private static final String TRASHING_IDENTIFIER = "Trashing";

    public static final TriggerType COLLECTING = new TriggerType(
            COLLECTING_IDENTIFIER);
    public static final TriggerType DOING = new TriggerType(
            DOING_IDENTIFIER);
    public static final TriggerType HOLDING = new TriggerType(HOLDING_IDENTIFIER);
    public static final TriggerType DELEGATING = new TriggerType(
            DELEGATING_IDENTIFIER);
    public static final TriggerType SCHEDULING = new TriggerType(
            SCHEDULING_IDENTIFIER);
    public static final TriggerType CATEGORIZING = new TriggerType(
            CATEGORIZING_IDENTIFIER);
    public static final TriggerType TRASHING = new TriggerType(
            TRASHING_IDENTIFIER);

    private static final Hashtable TYPE_MAPPING = createTypeMapping();

    private static Hashtable createTypeMapping() {
        Hashtable mapping = new Hashtable();
        mapping.put(COLLECTING_IDENTIFIER, COLLECTING);
        mapping.put(DOING_IDENTIFIER, DOING);
        mapping.put(HOLDING_IDENTIFIER, HOLDING);
        mapping.put(DELEGATING_IDENTIFIER, DELEGATING);
        mapping.put(SCHEDULING_IDENTIFIER, SCHEDULING);
        mapping.put(CATEGORIZING_IDENTIFIER, CATEGORIZING);
        mapping.put(TRASHING_IDENTIFIER, TRASHING);

        return mapping;
    }

    public static TriggerType of_(String typeName) {
        if (!TYPE_MAPPING.containsKey(typeName)) {
            throw new IllegalArgumentException(
                    "Invalid type name specified: " + typeName);
        }

        return (TriggerType) TYPE_MAPPING.get(typeName);
    }

    public String name_() {
        return typeName;
    }
}
