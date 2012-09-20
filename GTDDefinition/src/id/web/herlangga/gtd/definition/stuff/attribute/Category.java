package id.web.herlangga.gtd.definition.stuff.attribute;

import id.web.herlangga.gtd.definition.*;

public class Category implements Attribute {
    private final String category;

    public Category(String category) {
        this.category = category;
    }

    public AttributeType type() {
        return AttributeType.CATEGORY;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category1 = (Category) o;

        if (!category.equals(category1.category)) return false;

        return true;
    }

    public int hashCode() {
        return category.hashCode();
    }

    public String toString() {
        return "Category{" +
                "category='" + category + '\'' +
                '}';
    }

    public String categoryName_() {
        return category;
    }
}
