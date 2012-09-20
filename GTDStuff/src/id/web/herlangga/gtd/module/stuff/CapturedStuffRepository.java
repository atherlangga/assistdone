package id.web.herlangga.gtd.module.stuff;

/**
 * {@link CapturedStuff} storage.
 *
 * @author angga
 */
public interface CapturedStuffRepository {

    /**
     * Find {@link CapturedStuff} based on its ID.
     *
     * @param id Unique ID.
     * @return Found {@link CapturedStuff} whose ID is specified.
     */
    public CapturedStuff find(long id);

}
