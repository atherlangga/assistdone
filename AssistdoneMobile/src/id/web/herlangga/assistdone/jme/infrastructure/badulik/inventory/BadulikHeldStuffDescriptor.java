package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class BadulikHeldStuffDescriptor implements ObjectReconstitutor,
		ObjectIdExtractor, ObjectStateExtractor {

	/**
	 * Record manifestation in database.
	 * 
	 * @author angga
	 */
	private class HeldStuffRecord {
		private long id;
		private Description description;

		private HeldStuffRecord(long id, Description description) {
			this.id = id;
			this.description = description;
		}
	}

	private static final String ID_COLUMN_NAME = "Id";
	private static final String DESCRIPTION_COLUMN_NAME = "Description";
	
	private Schema HELD_STUFF_SCHEMA = Schema.buildNew()
			.thenAddAttribute(ID_COLUMN_NAME, Datatype.LONG)
			.thenAddAttribute(DESCRIPTION_COLUMN_NAME, Datatype.STRING)
			.thenGetResult();

	public Object describe(long id, Description description) {
		return new HeldStuffRecord(id, description);
	}

	public HeldStuff reconstitute(Object object) {
		HeldStuffRecord record = (HeldStuffRecord) object;
		HeldStuff heldStuff = new HeldStuff(record.id, record.description);

		return heldStuff;
	}

	public Object reconstituteObject(Element objectId, Tuple state) {
		long id = objectId.valueAsLong();
		String descriptionValue = state.elementOf(DESCRIPTION_COLUMN_NAME)
				.valueAsString();
		return new HeldStuffRecord(id, new Description(descriptionValue));
	}

	public Element extractIdFrom(Object toExtract) {
		HeldStuffRecord record = (HeldStuffRecord) toExtract;
		return Element.of(record.id);
	}

	public Tuple extractStateFrom(Object toExtract) {
		HeldStuffRecord record = (HeldStuffRecord) toExtract;
		long id = record.id;
		String descriptionValue = record.description.descriptionValue_();

		return Tuple
				.buildNew()
				.withSchema(HELD_STUFF_SCHEMA)
				.thenAddField(ID_COLUMN_NAME, Element.of(id))
				.thenAddField(DESCRIPTION_COLUMN_NAME,
						Element.of(descriptionValue)).thenGetResult();
	}

	public Schema schema() {
		return HELD_STUFF_SCHEMA;
	}

}
