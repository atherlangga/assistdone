package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import java.util.*;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.stuff.*;

import org.json.me.*;

class JSONDoneEventSerializationHelper implements JSONEventSerializationHelper {
	private static final String SOURCE = "source";
	private static final String TIME_STAMP = "time_stamp";

	public JSONObject serialize(Event event,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		JSONObject representation = new JSONObject();
		DoneEvent doneEvent = (DoneEvent) event;

		representation.put(SOURCE,
				stuffSerializer.serialize((CapturedStuff) doneEvent.source()));
		representation.put(TIME_STAMP, doneEvent.timeStamp().getTime());

		return representation;
	}

	public Event deserialize(JSONObject data,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		CapturedStuff source = stuffSerializer.deserialize(data.getString(SOURCE));
		Date timeStamp = new Date(data.getLong(TIME_STAMP));
		DoneEvent event = new DoneEvent(source, timeStamp);
		
		return event;
	}

}
