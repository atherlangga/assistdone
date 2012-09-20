package id.web.herlangga.gtd.module.inventory.item;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

/**
 * Stuff that has been processed and delegated to
 * DelegatedParty to expect its DeliverableProduct.
 */
public class DelegatedStuff extends CapturedStuff {
    private final DelegatedParty appropriateParty;
    private final DeliverableProduct expectedProduct;

    public DelegatedStuff(long id, Description description,
                          DelegatedParty appropriateParty, DeliverableProduct expectedProduct) {
        super(id, description);
        this.appropriateParty = appropriateParty;
        this.expectedProduct = expectedProduct;
    }

    public StateType stateType() {
        return StateType.DELEGATED;
    }

    public DelegatedParty appropriateParty() {
        return appropriateParty;
    }

    public DeliverableProduct expectedProduct() {
        return expectedProduct;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }
}
