package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.module.stuff.*;

import org.json.me.*;

public class JSONStuffSerialization {
	private final JSONStuffAttributeSerialization attributeSerializer;

	public JSONStuffSerialization(JSONStuffAttributeSerialization attributeSerializer) {
		this.attributeSerializer = attributeSerializer;
	}

	public String serialize(CapturedStuff stuff) {
		try {
			JSONObject outer = new JSONObject();
			JSONObject inner = new JSONObject();

			outer.put(STUFF, inner);
			inner.put(ID, stuff.id());
			inner.put(STATE_TYPE, stuff.stateType().name_());
			inner.put(ATTRIBUTES, attributesToJSONArray(stuff.attributes_()));

			return outer.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Cannot serialize Stuff " + stuff);
	}

	public CapturedStuff deserialize(String representation) {
		try {
			JSONObject outer = new JSONObject(representation);
			JSONObject inner = outer.getJSONObject(STUFF);

			long id = inner.getLong(ID);
			StateType stateType = StateType.of_(inner
					.getString(STATE_TYPE));
			CapturedStuff stuff = new CapturedStuff(id, stateType);

			Attribute[] attributes = attributesFromJSONArray(inner
					.getJSONArray(ATTRIBUTES));
			int totalAttributes = attributes.length;
			for (int i = 0; i < totalAttributes; i++) {
				stuff.addAttribute_(attributes[i]);
			}

			return stuff;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Cannot deserialize Stuff representation "
				+ representation);
	}

	private JSONArray attributesToJSONArray(Attribute[] attributes) {
		JSONArray result = new JSONArray();

		int totalAttributes = attributes.length;
		for (int i = 0; i < totalAttributes; i++) {
			result.put(attributeSerializer.serialize(attributes[i]));
		}

		return result;
	}

	private Attribute[] attributesFromJSONArray(JSONArray attributes) {
		Attribute[] result = new Attribute[attributes.length()];

		try {
			int totalAttributes = attributes.length();
			for (int i = 0; i < totalAttributes; i++) {
				JSONObject attributeRepresentation = attributes
						.getJSONObject(i);
				Attribute attribute = attributeSerializer
						.deserialize(attributeRepresentation);
				result[i] = attribute;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	private static final String STUFF = "stuff";
	private static final String ID = "id";
	private static final String STATE_TYPE = "state_type";
	private static final String ATTRIBUTES = "attributes";
}
