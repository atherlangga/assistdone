package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.attribute.*;

import org.json.me.*;

interface JSONStuffAttributeSerializationHelper {
	JSONObject serialize(Attribute attribute) throws JSONException;

	Attribute deserialize(JSONObject data) throws JSONException;
}
