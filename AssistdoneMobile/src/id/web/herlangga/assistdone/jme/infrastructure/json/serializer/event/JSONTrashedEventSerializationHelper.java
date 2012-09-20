package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import java.util.*;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.stuff.*;

import org.json.me.*;

class JSONTrashedEventSerializationHelper implements
		JSONEventSerializationHelper {
	private static final String SOURCE = "source";
	private static final String TIME_STAMP = "time_stamp";

	public JSONObject serialize(Event event,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		JSONObject representation = new JSONObject();
		TrashedEvent trashedEvent = (TrashedEvent) event;

		representation.put(SOURCE, stuffSerializer
				.serialize((CapturedStuff) trashedEvent.source()));
		representation.put(TIME_STAMP, trashedEvent.timeStamp().getTime());

		return representation;
	}

	public Event deserialize(JSONObject data,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		CapturedStuff source = stuffSerializer.deserialize(data.getString(SOURCE));
		Date timeStamp = new Date(data.getLong(TIME_STAMP));
		TrashedEvent event = new TrashedEvent(source, timeStamp);
		
		return event;
	}

}
