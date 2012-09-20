package id.web.herlangga.gtd.module.inventory;

import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public interface NextActionList {
    public CategorizedStuff[] categorizedStuffs();

    public CategorizedStuff[] categorizedStuffsOf(Category category);
}
