package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.stuff.*;

import java.util.*;

import org.json.me.*;

class JSONCollectedEventSerializationHelper implements
		JSONEventSerializationHelper {
	public JSONObject serialize(Event event,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		JSONObject result = new JSONObject();
		CollectedEvent collectedEvent = (CollectedEvent) event;

		result.put(SOURCE, stuffSerializer.serialize((CapturedStuff) event.source()));
		result.put(TIME_STAMP, collectedEvent.timeStamp().getTime());
		result.put(DESCRIPTION, collectedEvent.description()
				.descriptionValue_());

		return result;
	}

	public Event deserialize(JSONObject data,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		CapturedStuff source = stuffSerializer.deserialize(data.getString(SOURCE));
		Date timeStamp = new Date(data.getLong(TIME_STAMP));
		Description description = new Description(data.getString(DESCRIPTION));

		return new CollectedEvent(source, timeStamp, description);
	}

	private static final String SOURCE = "source";
	private static final String TIME_STAMP = "time_stamp";
	private static final String DESCRIPTION = "description";
}
