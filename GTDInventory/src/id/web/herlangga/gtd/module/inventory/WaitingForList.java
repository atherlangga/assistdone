package id.web.herlangga.gtd.module.inventory;

import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.module.inventory.item.*;

/**
 * Inventory for keeping DelegatedStuffs.
 *
 * @author angga
 */
public interface WaitingForList {
    public DelegatedStuff[] delegatedStuffs();

    public DelegatedStuff[] delegatedStuffsFor(DelegatedParty party);
}
