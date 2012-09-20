package id.web.herlangga.gtd.module.inventory.item;

import id.web.herlangga.gtd.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

public class CategorizedStuff extends CapturedStuff {
    private Category category;

    public CategorizedStuff(long id, Description description, Category category) {
        super(id, description);
        this.category = category;
    }

    public Category category() {
        return category;
    }

    public StateType stateType() {
        return StateType.CATEGORIZED;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another); 
    }
}
