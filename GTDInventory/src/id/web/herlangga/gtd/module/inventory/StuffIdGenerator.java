package id.web.herlangga.gtd.module.inventory;

/**
 * Object whose primary responsibility is to generate valid Stuff Id.
 */
public interface StuffIdGenerator {

    /**
     * Generate new and valid Stuff Id.
     * @return new Stuff Id
     */
    public long generateStuffId();
}
