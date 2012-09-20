package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import java.util.*;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.stuff.*;

import org.json.me.*;

class JSONHeldEventSerializationHelper implements JSONEventSerializationHelper {
	private static final String SOURCE = "source";
	private static final String TIME_STAMP = "time_stamp";

	public JSONObject serialize(Event event,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		JSONObject result = new JSONObject();
		HeldEvent heldEvent = (HeldEvent) event;

		result.put(SOURCE,
				stuffSerializer.serialize((CapturedStuff) heldEvent.source()));
		result.put(TIME_STAMP, heldEvent.timeStamp().getTime());

		return result;
	}

	public Event deserialize(JSONObject data,
			JSONStuffSerialization stuffSerializer) throws JSONException {

		CapturedStuff source = stuffSerializer.deserialize(data.getString(SOURCE));
		Date timeStamp = new Date(data.getLong(TIME_STAMP));
		HeldEvent event = new HeldEvent(source, timeStamp);
		
		return event;
	}

}
