package id.web.herlangga.gtd.definition.stuff;

/**
 * Main Object in the Getting Things Done.
 *
 * @author at.herlangga
 */
public interface Stuff {
    /**
     * Get the unique identifier of this Stuff.
     *
     * @return this Stuff identifier.
     */
    long id();

    /**
     * Get description the this Stuff.
     *
     * @return this Stuff description.
     */
    Description description();

    /**
     * Ask what StateType of this Stuff is in.
     *
     * @return StateType of this Stuff.
     */
    StateType stateType();
}
