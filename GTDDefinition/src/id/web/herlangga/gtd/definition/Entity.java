package id.web.herlangga.gtd.definition;

/**
 * The Entity of a domain.
 *
 * @author at.herlangga
 */
public interface Entity {

    /**
     * Determine whether this Entity has the same identity compared to another.
     *
     * @param another Another Entity to compare to.
     * @return true if the two has the same identity, else false.
     */
    public boolean hasSameIdentityAs(Entity another);
}
