package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;

import org.json.me.*;

class JSONDescriptionSerializationHelper implements
		JSONStuffAttributeSerializationHelper {
	public JSONObject serialize(Attribute attribute) throws JSONException {
		Description description = (Description) attribute;
		JSONObject inner = new JSONObject();
		inner.put(DESCRIPTION, description.descriptionValue_());

		return inner;
	}

	public Attribute deserialize(JSONObject data) throws JSONException {
		String descriptionValue = data.getString(DESCRIPTION);
		return new Description(descriptionValue);
	}

	private static final String DESCRIPTION = "description";
}
