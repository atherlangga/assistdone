package id.web.herlangga.assistdone.jme.infrastructure.badulik.stuff;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.module.stuff.*;

public class BadulikCapturedStuffDescriptor implements ObjectReconstitutor,
		ObjectIdExtractor, ObjectStateExtractor {
	private final JSONStuffSerialization serialization;

	public BadulikCapturedStuffDescriptor(JSONStuffSerialization serialization) {
		this.serialization = serialization;
	}

	public Object describe(CapturedStuff stuff) {
		String representation = serialization.serialize(stuff);
		return new CapturedStuffRecord(stuff.id(), representation);
	}

	public CapturedStuff reconstitute(Object object) {
		CapturedStuffRecord record = (CapturedStuffRecord) object;
		return serialization.deserialize(record.representation);
	}

	public Object reconstituteObject(Element objectId, Tuple state) {
		long id = objectId.valueAsLong();
		String representation = state.elementOf(REPRESENTATIION_COLUMN_NAME)
				.valueAsString();
		
		return new CapturedStuffRecord(id, representation);
	}

	public Element extractIdFrom(Object toExtract) {
		CapturedStuffRecord record = (CapturedStuffRecord) toExtract;
		long id = record.id;

		return Element.of(id);
	}

	public Tuple extractStateFrom(Object toExtract) {
		CapturedStuffRecord record = (CapturedStuffRecord) toExtract;
		long id = record.id;
		String representation = record.representation;

		return Tuple
				.buildNew()
				.withSchema(CAPTURED_STUFF_SCHEMA)
				.thenAddField(ID_COLUMN_NAME, Element.of(id))
				.thenAddField(REPRESENTATIION_COLUMN_NAME,
						Element.of(representation)).thenGetResult();

	}

	public Schema schema() {
		return CAPTURED_STUFF_SCHEMA;
	}

	/**
	 * Helper class to encapsulate CapturedStuff for serialization purpose.
	 * 
	 * @author at.herlangga
	 */
	private class CapturedStuffRecord {
		private final long id;
		private final String representation;

		private CapturedStuffRecord(long id, String representation) {
			this.id = id;
			this.representation = representation;
		}
	}

	private static final String ID_COLUMN_NAME = "Id";
	private static final String REPRESENTATIION_COLUMN_NAME = "Representation";

	private static final Schema CAPTURED_STUFF_SCHEMA = Schema.buildNew()
			.thenAddAttribute(ID_COLUMN_NAME, Datatype.LONG)
			.thenAddAttribute(REPRESENTATIION_COLUMN_NAME, Datatype.STRING)
			.thenGetResult();
}
