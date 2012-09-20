package id.web.herlangga.assistdone.jme.infrastructure.badulik.stuff.eventlog;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event.*;
import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.event.*;

public class BadulikStuffEventLogDescriptor implements ObjectReconstitutor,
		ObjectIdExtractor, ObjectStateExtractor {
	private final JSONEventSerialization serialization;

	public BadulikStuffEventLogDescriptor(JSONEventSerialization serialization) {
		this.serialization = serialization;
	}

	/**
	 * Pack id and Event to ease serialization.
	 * 
	 * @param id
	 *            Id of the Event.
	 * @param event
	 *            the Event.
	 * @return Object ready to be serialized.
	 */
	public Object pack(long id, Event event) {
		return new StuffEventLogRecord(id, serialization.serialize(event));
	}

	public Object reconstituteObject(Element objectId, Tuple data) {
		String event = data.elementOf(EVENT_COLUMN_NAME).valueAsString();
		return serialization.deserialize(event);
	}

	public Element extractIdFrom(Object toExtract) {
		StuffEventLogRecord record = (StuffEventLogRecord) toExtract;
		return Element.of(record.id);
	}

	public Tuple extractStateFrom(Object toExtract) {
		StuffEventLogRecord record = (StuffEventLogRecord) toExtract;
		return Tuple.buildNew().withSchema(STUFF_EVENT_LOG_SCHEMA)
				.thenAddField(EVENT_COLUMN_NAME, Element.of(record.event))
				.thenGetResult();
	}

	public Schema schema() {
		return STUFF_EVENT_LOG_SCHEMA;
	}

	private class StuffEventLogRecord {
		private final long id;
		private final String event;

		private StuffEventLogRecord(long id, String event) {
			this.id = id;
			this.event = event;
		}
	}

	private static final String EVENT_COLUMN_NAME = "Event";
	private static final Schema STUFF_EVENT_LOG_SCHEMA = Schema.buildNew()
			.thenAddAttribute(EVENT_COLUMN_NAME, Datatype.STRING)
			.thenGetResult();

}
