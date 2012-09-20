package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.module.inventory.item.*;

import java.util.*;

public class BadulikCollectedStuffDescriptor implements ObjectReconstitutor,
		ObjectIdExtractor, ObjectStateExtractor {

	/**
	 * Describes attributes of a CollectedStuff into meaningful Object to be
	 * kept in Repository.
	 * 
	 * @param id
	 *            CollectedStuff id.
	 * @param description
	 *            CollectedStuff description.
	 * @param collectedDate
	 *            CollectedStuff collected date.
	 * @return Object to be kept in Repository.
	 */
	public Object describe(long id, Description description, Date collectedDate) {
		return new CollectedStuffRecord(id, description, collectedDate);
	}

	/**
	 * Reconstitutes CollectedStuff from arbitrary Object kept in the
	 * Repository.
	 * 
	 * @param object
	 *            Object to be converted into CollectedStuff.
	 * @return reconstituted CollectedStuff.
	 */
	public CollectedStuff reconstitute(Object object) {
		CollectedStuffRecord record = (CollectedStuffRecord) object;
		return new CollectedStuff(record.id, record.description,
				record.collectedDate);
	}

	public Object reconstituteObject(Element objectId, Tuple state) {
		long id = objectId.valueAsLong();
		Description description = new Description(state.elementOf(
				DESCRIPTION_COLUMN_NAME).valueAsString());
		Date collectedDate = state.elementOf(COLLECTED_DATE_COLUMN_NAME)
				.valueAsDate();

		return new CollectedStuffRecord(id, description, collectedDate);
	}

	public Element extractIdFrom(Object toExtract) {
		CollectedStuffRecord record = (CollectedStuffRecord) toExtract;
		long id = record.id;

		return Element.of(id);
	}

	public Tuple extractStateFrom(Object toExtract) {
		CollectedStuffRecord record = (CollectedStuffRecord) toExtract;
		long id = record.id;
		String descriptionValue = record.description.descriptionValue_();
		Date collectedDate = record.collectedDate;

		return Tuple
				.buildNew()
				.withSchema(COLLECTED_STUFF_SCHEMA)
				.thenAddField(ID_COLUMN_NAME, Element.of(id))
				.thenAddField(DESCRIPTION_COLUMN_NAME,
						Element.of(descriptionValue))
				.thenAddField(COLLECTED_DATE_COLUMN_NAME,
						Element.of(collectedDate)).thenGetResult();
	}

	public Schema schema() {
		return COLLECTED_STUFF_SCHEMA;
	}

	private class CollectedStuffRecord {
		private final long id;
		private final Description description;
		private final Date collectedDate;

		private CollectedStuffRecord(long id, Description description,
				Date collectedDate) {
			this.id = id;
			this.description = description;
			this.collectedDate = collectedDate;
		}
	}

	private static final String ID_COLUMN_NAME = "Id";
	private static final String DESCRIPTION_COLUMN_NAME = "Description";
	private static final String COLLECTED_DATE_COLUMN_NAME = "CollectedDate";

	private static final Schema COLLECTED_STUFF_SCHEMA = Schema.buildNew()
			.thenAddAttribute(ID_COLUMN_NAME, Datatype.LONG)
			.thenAddAttribute(DESCRIPTION_COLUMN_NAME, Datatype.STRING)
			.thenAddAttribute(COLLECTED_DATE_COLUMN_NAME, Datatype.DATE)
			.thenGetResult();

}
