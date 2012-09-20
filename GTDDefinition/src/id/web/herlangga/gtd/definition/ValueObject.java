package id.web.herlangga.gtd.definition;

/**
 * The Value Object of the domain.
 */
public interface ValueObject {

    /**
     * Determines whether this ValueObject has the same value compared to
     * another.
     *
     * @param another Another ValueObject to compare to.
     * @return true if the two has the same value, else false.
     */
    public boolean hasSameValueAs(ValueObject another);
}
