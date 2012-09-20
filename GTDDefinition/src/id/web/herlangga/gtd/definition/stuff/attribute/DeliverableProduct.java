package id.web.herlangga.gtd.definition.stuff.attribute;

import id.web.herlangga.gtd.definition.*;

public class DeliverableProduct implements Attribute {
    private final String product;

    public DeliverableProduct(String product) {
        this.product = product;
    }

    public AttributeType type() {
        return AttributeType.DELIVERABLE_PRODUCT;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliverableProduct that = (DeliverableProduct) o;

        if (!product.equals(that.product)) return false;

        return true;
    }

    public int hashCode() {
        return product.hashCode();
    }

    public String toString() {
        return "DeliverableProduct{" +
                "product='" + product + '\'' +
                '}';
    }

    public String productName_() {
        return product;
    }

}
