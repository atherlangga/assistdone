package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class BadulikCategorizedStuffDescriptor implements ObjectReconstitutor,
		ObjectIdExtractor, ObjectStateExtractor {

	private class CategorizedStuffRecord {
		private long id;
		private Description description;
		private Category category;

		private CategorizedStuffRecord(long id, Description description,
				Category category) {
			this.id = id;
			this.description = description;
			this.category = category;
		}
	}

	private static final String ID_COLUMN_NAME = "Id";
	private static final String DESCRIPTION_COLUMN_NAME = "Description";
	private static final String CATEGORY_COLUMN_NAME = "Category";

	private static final Schema CATEGORIZED_STUFF_SCHEMA = Schema.buildNew()
			.thenAddAttribute(ID_COLUMN_NAME, Datatype.LONG)
			.thenAddAttribute(DESCRIPTION_COLUMN_NAME, Datatype.STRING)
			.thenAddAttribute(CATEGORY_COLUMN_NAME, Datatype.STRING)
			.thenGetResult();

	public Object describe(long id, Description description, Category category) {
		return new CategorizedStuffRecord(id, description, category);
	}

	public CategorizedStuff reconstitute(Object rawObject) {
		CategorizedStuffRecord record = (CategorizedStuffRecord) rawObject;

		return new CategorizedStuff(record.id, record.description,
				record.category);
	}

	public Object reconstituteObject(Element objectId, Tuple state) {
		long id = objectId.valueAsLong();
		String descriptionValue = state.elementOf(DESCRIPTION_COLUMN_NAME)
				.valueAsString();
		String categoryName = state.elementOf(CATEGORY_COLUMN_NAME)
				.valueAsString();

		return new CategorizedStuffRecord(id,
				new Description(descriptionValue), new Category(categoryName));
	}

	public Element extractIdFrom(Object toExtract) {
		CategorizedStuffRecord record = (CategorizedStuffRecord) toExtract;
		return Element.of(record.id);
	}

	public Tuple extractStateFrom(Object toExtract) {
		CategorizedStuffRecord record = (CategorizedStuffRecord) toExtract;
		String desriptionValue = record.description.descriptionValue_();
		String categoryName = record.category.categoryName_();

		return Tuple
				.buildNew()
				.withSchema(CATEGORIZED_STUFF_SCHEMA)
				.thenAddField(ID_COLUMN_NAME, Element.of(record.id))
				.thenAddField(DESCRIPTION_COLUMN_NAME,
						Element.of(desriptionValue))
				.thenAddField(CATEGORY_COLUMN_NAME, Element.of(categoryName))
				.thenGetResult();
	}
	
	public Schema schema() {
		return CATEGORIZED_STUFF_SCHEMA;
	}

}
