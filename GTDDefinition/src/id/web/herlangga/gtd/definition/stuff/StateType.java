package id.web.herlangga.gtd.definition.stuff;

import java.util.*;

/**
 * StateType of Stuff.
 * <p/>
 * There are several defined StateTypes for Stuff in the GTD environment which
 * revolves around Stuff. This class contains those enumeration.
 *
 * @author at.herlangga
 */
public class StateType {
    private final String typeName;

    private StateType(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        return "StateType{" +
                "typeName='" + typeName + '\'' +
                '}';
    }

    private static final String PREFIX = "Stuff";
    private static final String CAPTURED_IDENTIFIER = PREFIX + "Captured";
    private static final String COLLECTED_IDENTIFIER = PREFIX + "Collected";
    private static final String DONE_IDENTIFIER = PREFIX + "Done";
    private static final String HELD_IDENTIFIER = PREFIX + "Held";
    private static final String DELEGATED_IDENTIFIER = PREFIX + "Delegated";
    private static final String SCHEDULED_IDENTIFIER = PREFIX + "Scheduled";
    private static final String CATEGORIZED_IDENTIFIER = PREFIX + "Categorized";
    private static final String TRASHED_IDENTIFIER = PREFIX + "Trashed";

    /**
     * StateType of every Stuff that has been captured into the GTD
     * Environment.
     */
    public static final StateType CAPTURED = new StateType(
            CAPTURED_IDENTIFIER);

    /**
     * StateType of a Stuff that just has been captured but not yet processed.
     */
    public static final StateType COLLECTED = new StateType(
            COLLECTED_IDENTIFIER);

    /**
     * StateType of a Stuff that has been done.
     */
    public static final StateType DONE = new StateType(DONE_IDENTIFIER);

    /**
     * StateType of a Stuff that have been held.
     */
    public static final StateType HELD = new StateType(HELD_IDENTIFIER);

    /**
     * StateType of a Stuff that has been delegated to be done by someone else.
     */
    public static final StateType DELEGATED = new StateType(
            DELEGATED_IDENTIFIER);

    /**
     * StateType of a Stuff that can only be done at specified time.
     */
    public static final StateType SCHEDULED = new StateType(
            SCHEDULED_IDENTIFIER);

    /**
     * StateType of a Stuff that can only be done when certain category met.
     */
    public static final StateType CATEGORIZED = new StateType(
            CATEGORIZED_IDENTIFIER);

    /**
     * StateType of a Stuff that no longer interesting.
     */
    public static final StateType TRASHED = new StateType(TRASHED_IDENTIFIER);

    private static final Hashtable TYPE_MAPPING = createTypeMapping();

    private static Hashtable createTypeMapping() {
        Hashtable mapping = new Hashtable();
        mapping.put(CAPTURED_IDENTIFIER, CAPTURED);
        mapping.put(COLLECTED_IDENTIFIER, COLLECTED);
        mapping.put(DONE_IDENTIFIER, DONE);
        mapping.put(HELD_IDENTIFIER, HELD);
        mapping.put(DELEGATED_IDENTIFIER, DELEGATED);
        mapping.put(SCHEDULED_IDENTIFIER, SCHEDULED);
        mapping.put(CATEGORIZED_IDENTIFIER, CATEGORIZED);
        mapping.put(TRASHED_IDENTIFIER, TRASHED);

        return mapping;
    }

    public static StateType of_(String typeName) {
        if (!TYPE_MAPPING.containsKey(typeName)) {
            throw new IllegalArgumentException("Illegal type name specified: "
                    + typeName);
        }
        return (StateType) TYPE_MAPPING.get(typeName);
    }

    public String name_() {
        return typeName;
    }

}
