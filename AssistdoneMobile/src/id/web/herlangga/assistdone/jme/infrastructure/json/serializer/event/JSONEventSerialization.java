package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.event.*;

import java.util.*;

import org.json.me.*;

public class JSONEventSerialization {
	private final JSONStuffSerialization stuffSerialization;

	public JSONEventSerialization(JSONStuffSerialization stuffSerialization) {
		this.stuffSerialization = stuffSerialization;
	}

	public String serialize(Event event) {
		JSONObject outer = new JSONObject();
		JSONObject inner = new JSONObject();
		JSONEventSerializationHelper helper = serializationHelperOf(event
				.triggerType().name_());

		try {
			outer.put(STUFF_EVENT, inner);
			inner.put(TYPE, event.triggerType().name_());
			inner.put(EVENT, helper.serialize(event, stuffSerialization));

			return outer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Cannot serialize StuffEvent " + event);
	}

	public Event deserialize(String representation) {
		try {
			JSONObject outer = new JSONObject(representation);
			JSONObject inner = outer.getJSONObject(STUFF_EVENT);

			String typeName = inner.getString(TYPE);
			JSONEventSerializationHelper helper = serializationHelperOf(typeName);

			return helper.deserialize(inner.getJSONObject(EVENT),
					stuffSerialization);
		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Cannot deserialize " + representation);
	}

	private JSONEventSerializationHelper serializationHelperOf(String typeName) {
		if (!MAPPING.containsKey(typeName)) {
			throw new IllegalArgumentException("Serialization helper for "
					+ typeName + " doesn't exist");
		}

		return (JSONEventSerializationHelper) MAPPING.get(typeName);
	}

	private static final Hashtable MAPPING = createMapping();

	private static Hashtable createMapping() {
		Hashtable mapping = new Hashtable();
		mapping.put(TriggerType.COLLECTING.name_(),
				new JSONCollectedEventSerializationHelper());
		mapping.put(TriggerType.DELEGATING.name_(),
				new JSONDelegatedEventSerializationHelper());
		mapping.put(TriggerType.SCHEDULING.name_(),
				new JSONScheduledEventSerializationHelper());
		mapping.put(TriggerType.DOING.name_(),
				new JSONDoneEventSerializationHelper());
		mapping.put(TriggerType.TRASHING.name_(),
				new JSONTrashedEventSerializationHelper());

		return mapping;
	}

	private static final String STUFF_EVENT = "stuff_event";
	private static final String TYPE = "type";
	private static final String EVENT = "event";
}
