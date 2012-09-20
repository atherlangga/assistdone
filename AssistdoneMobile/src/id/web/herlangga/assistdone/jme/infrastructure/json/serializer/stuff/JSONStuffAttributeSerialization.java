package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.attribute.*;

import java.util.*;

import org.json.me.*;

public class JSONStuffAttributeSerialization {
	public JSONObject serialize(Attribute attribute) {
		JSONStuffAttributeSerializationHelper serializer = getHelperFor(attribute
				.type());

		try {
			JSONObject inner = serializer.serialize(attribute);
			inner.put(TYPE_KEY_IDENTIFIER, attribute.type().name_());

			return inner;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Cannot serialize " + attribute);
	}

	public Attribute deserialize(JSONObject data) {
		try {
			String typeName = data.getString(TYPE_KEY_IDENTIFIER);
			AttributeType type = AttributeType.of_(typeName);
			JSONStuffAttributeSerializationHelper serializer = getHelperFor(type);

			return serializer.deserialize(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Cannot deserialize " + data);
	}

	private JSONStuffAttributeSerializationHelper getHelperFor(
			AttributeType type) {
		if (!SERIALIZATION_HELPER_MAPPING.containsKey(type)) {
			throw new RuntimeException("Attribute serialization helper for "
					+ type.name_() + " doesn't exist");
		}

		return (JSONStuffAttributeSerializationHelper) SERIALIZATION_HELPER_MAPPING
				.get(type);
	}

	private static final Hashtable SERIALIZATION_HELPER_MAPPING = createSerializationHelperMapping();

	private static Hashtable createSerializationHelperMapping() {
		Hashtable mapping = new Hashtable();
		mapping.put(AttributeType.DESCRIPTION,
				new JSONDescriptionSerializationHelper());
		mapping.put(AttributeType.DELEGATED_PARTY,
				new JSONDelegatedPartySerializationHelper());
		mapping.put(AttributeType.DELIVERABLE_PRODUCT,
				new JSONDeliverableProductSerializationHelper());
		mapping.put(AttributeType.SCHEDULE,
				new JSONScheduleSerializationServiceHelper());
		mapping.put(AttributeType.CATEGORY,
				new JSONCategorySerializationHelper());

		return mapping;
	}

	private static final String TYPE_KEY_IDENTIFIER = "type";
}
