package id.web.herlangga.gtd.module.stuff;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.*;

import java.util.*;

/**
 * Stuff that has been captured by Getting Things Done (GTD)
 * environment.
 * <p/>
 * Basically every Stuff is CapturedStuff, because Stuff
 * that has never been captured is meaningless to the GTD environment.
 * <p/>
 * Because CapturedStuff is the essentially the Stuff itself,
 * CapturedStuff also represents the lifetime of a Stuff.
 *
 * @author at.herlangga
 */
public class CapturedStuff implements Stuff, Entity {
    private final long id;

    /**
     * The current StateType of this CapturedStuff.
     */
    private StateType stateType;

    /**
     * Collection of assigned Attributes.
     * <p/>
     * In a Stuff lifetime, there's possibility that Stuff will
     * have many Attributes because of State change. In
     * order to "remember" all the Attributes ever assigned to
     * this Stuff, it needs to store all its Attributes.
     */
    private final Hashtable attributes = new Hashtable();

    public CapturedStuff(long id, StateType stateType) {
        this.id = id;
        this.stateType = stateType;
    }

    public long id() {
        return id;
    }

    public StateType stateType() {
        return stateType;
    }

    public Description description() {
        return (Description) attributeOf(AttributeType.DESCRIPTION);
    }

    Hashtable attributes() {
        return attributes;
    }

    void handleCollected(Description description) {
        state().doCollecting(this, description);
    }

    void handleDone() {
        state().doing(this);
    }

    void handleHeld() {
        state().doHolding(this);
    }

    void handleDelegated(DelegatedParty appropriateParty,
                         DeliverableProduct expectedProduct) {
        state().doDelegating(this, appropriateParty, expectedProduct);
    }

    void handleScheduled(Schedule schedule) {
        state().doScheduling(this, schedule);
    }

    void handleCategorized(Category category) {
        state().doCategorizing(this, category);
    }

    void handleTrashed() {
        state().doTrashing(this);
    }

    void updateState(StateType newStateType) {
        stateType = newStateType;
    }

    void handleInvalidatedAction() {
        //TODO: Send notification to the triggering activity
        throw new RuntimeException("Invalid StuffActivity");
    }

    private State state() {
        return (State) STATE_MAPPING.get(stateType);
    }

    private Attribute attributeOf(AttributeType type) {
        if (attributes.containsKey(type)) {
            return (Attribute) attributes.get(type);
        }
        throw new IllegalArgumentException("Stuff " + toString()
                + " doesn't have " + type.toString());
    }

    public boolean hasSameIdentityAs(Entity another) {
        return equals(another);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
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
        return id == other.id;
    }

    public String toString() {
        return "Stuff [id=" + id + ", stateType=" + stateType
                + ", attributes=" + attributes + "]";
    }

    private static final Hashtable STATE_MAPPING = createStateMapping();

    private static Hashtable createStateMapping() {
        Hashtable stateMapping = new Hashtable();
        stateMapping.put(StateType.CAPTURED, new CapturedState());
        stateMapping.put(StateType.COLLECTED, new CollectedState());
        stateMapping.put(StateType.DELEGATED, new DelegatedState());
        stateMapping.put(StateType.SCHEDULED, new ScheduledState());
        stateMapping.put(StateType.CATEGORIZED, new CategorizedState());
        stateMapping.put(StateType.DONE, new DoneState());
        stateMapping.put(StateType.TRASHED, new TrashedState());

        return stateMapping;
    }

    /*
     * Border between good and evil..
     *
     * If you're not in the business of persistence and user interface,
     * please stay away from these two methods.
     */

    public Attribute[] attributes_() {
        Attribute[] attributes = new Attribute[this.attributes.size()];
        int counter = 0;
        Enumeration e = this.attributes.elements();

        while (e.hasMoreElements()) {
            attributes[counter] = (Attribute) e.nextElement();
            counter++;
        }

        return attributes;
    }

    public void addAttribute_(Attribute attribute) {
        attributes.put(attribute.type(), attribute);
    }

}
