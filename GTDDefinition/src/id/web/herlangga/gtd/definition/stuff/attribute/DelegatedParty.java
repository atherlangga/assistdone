package id.web.herlangga.gtd.definition.stuff.attribute;

import id.web.herlangga.gtd.definition.*;

public class DelegatedParty implements Attribute {
    private final String contactName;
    private final String contactInformation;

    public DelegatedParty(String contactName, String contactInformation) {
        this.contactName = contactName;
        this.contactInformation = contactInformation;
    }

    public AttributeType type() {
        return AttributeType.DELEGATED_PARTY;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DelegatedParty that = (DelegatedParty) o;

        if (!contactInformation.equals(that.contactInformation)) return false;
        if (!contactName.equals(that.contactName)) return false;

        return true;
    }

    public int hashCode() {
        int result = contactName.hashCode();
        result = 31 * result + contactInformation.hashCode();
        return result;
    }

    public String toString() {
        return "DelegatedParty{" +
                "contactName='" + contactName + '\'' +
                ", contactInformation='" + contactInformation + '\'' +
                '}';
    }

    public String contactIdentifierName_() {
        return contactName;
    }

    public String contactInformationValue_() {
        return contactInformation;
    }

}
