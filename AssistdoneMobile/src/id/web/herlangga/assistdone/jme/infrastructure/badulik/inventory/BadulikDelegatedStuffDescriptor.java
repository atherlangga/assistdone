package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class BadulikDelegatedStuffDescriptor implements ObjectReconstitutor,
		ObjectIdExtractor, ObjectStateExtractor {
	
	public Object describe(long id, Description description,
			DelegatedParty appropriateParty, DeliverableProduct expectedProduct) {
		return new DelegatedStuffRecord(id, description, appropriateParty,
				expectedProduct);
	}

	public DelegatedStuff reconstitute(Object object) {
		DelegatedStuffRecord record = (DelegatedStuffRecord) object;
		return new DelegatedStuff(record.id, record.description,
				record.appropriateParty, record.expectedProduct);
	}
	
	public Schema schema() {
		return DELEGATED_STUFF_SCHEMA;
	}

	public Object reconstituteObject(Element objectId, Tuple state) {
		long id = objectId.valueAsLong();
		String descriptionValue = state.elementOf(DESCRIPTION_COLUMN_NAME)
				.valueAsString();
		String contactNameValue = state.elementOf(CONTACT_NAME_VALUE)
				.valueAsString();
		String contactInformationValue = state.elementOf(
				CONTACT_INFORMATION_VALUE).valueAsString();
		String expectedProductValue = state.elementOf(
				EXPECTED_PRODUCT_COLUMN_NAME).valueAsString();

		Description description = new Description(descriptionValue);
		DelegatedParty appropriateParty = new DelegatedParty(contactNameValue,
				contactInformationValue);
		DeliverableProduct expectedProduct = new DeliverableProduct(
				expectedProductValue);

		return new DelegatedStuffRecord(id, description, appropriateParty,
				expectedProduct);
	}

	public Element extractIdFrom(Object toExtract) {
		DelegatedStuffRecord record = (DelegatedStuffRecord) toExtract;
		return Element.of(record.id);
	}

	public Tuple extractStateFrom(Object toExtract) {
		DelegatedStuffRecord record = (DelegatedStuffRecord) toExtract;
		long id = record.id;
		String descriptionValue = record.description.descriptionValue_();
		String contactName = record.appropriateParty.contactIdentifierName_();
		String contactInformation = record.appropriateParty
				.contactInformationValue_();
		String productName = record.expectedProduct.productName_();

		return Tuple.buildNew().withSchema(DELEGATED_STUFF_SCHEMA)
				.thenAddField(ID_COLUMN_NAME, Element.of(id)).thenAddField(
						DESCRIPTION_COLUMN_NAME, Element.of(descriptionValue))
				.thenAddField(CONTACT_NAME_VALUE, Element.of(contactName))
				.thenAddField(CONTACT_INFORMATION_VALUE,
						Element.of(contactInformation)).thenAddField(
						EXPECTED_PRODUCT_COLUMN_NAME, Element.of(productName))
				.thenGetResult();
	}

	private class DelegatedStuffRecord {
		private final long id;
		private final Description description;
		private final DelegatedParty appropriateParty;
		private final DeliverableProduct expectedProduct;

		public DelegatedStuffRecord(long id, Description description,
				DelegatedParty appropriateParty,
				DeliverableProduct expectedProduct) {
			this.id = id;
			this.description = description;
			this.appropriateParty = appropriateParty;
			this.expectedProduct = expectedProduct;
		}
	}

	private static final String ID_COLUMN_NAME = "Id";
	private static final String DESCRIPTION_COLUMN_NAME = "Description";
	private static final String CONTACT_NAME_VALUE = "ContactName";
	private static final String CONTACT_INFORMATION_VALUE = "ContactInformation";
	private static final String EXPECTED_PRODUCT_COLUMN_NAME = "ExpectedProduct";

	private static final Schema DELEGATED_STUFF_SCHEMA = Schema.buildNew()
			.thenAddAttribute(ID_COLUMN_NAME, Datatype.LONG).thenAddAttribute(
					DESCRIPTION_COLUMN_NAME, Datatype.STRING).thenAddAttribute(
					CONTACT_NAME_VALUE, Datatype.STRING).thenAddAttribute(
					CONTACT_INFORMATION_VALUE, Datatype.STRING)
			.thenAddAttribute(EXPECTED_PRODUCT_COLUMN_NAME, Datatype.STRING)
			.thenGetResult();
}
