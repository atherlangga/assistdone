package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.event.*;

import org.json.me.*;

/**
 * Helper for Event Serialization process.
 * 
 * In order to serialize many types of Event, Event Serialization Service will
 * need help to serialize many specific types of Event. This interface's sole
 * purpose is to help that process.
 * 
 * Every specific Event type gets its own helper. So, in order to maintain
 * abstraction, the price that must be paid is one has to cast Event to its
 * concrete type.
 * 
 * @author at.herlangga
 * 
 */
interface JSONEventSerializationHelper {
	/**
	 * Serialize particular Event to JSONObject.
	 * 
	 * Method that wants to implement this must know what the exact type of the
	 * Event parameter. Therefore, the method must first casts the Event to its
	 * concrete type.
	 * 
	 * @param event
	 *            Event to serialize.
	 * @param stuffSerializer
	 *            Stuff serialization service to delegate the job of
	 *            serialization of concrete Stuff to.
	 * @return JSONObject as representation of Event.
	 * @throws JSONException
	 *             The exception will be thrown when the serialization process
	 *             failed.
	 */
	JSONObject serialize(Event event, JSONStuffSerialization stuffSerializer)
			throws JSONException;

	/**
	 * De-serialize representation of Event from JSONObject.
	 * 
	 * @param data
	 *            representation of Event in its JSONObject form.
	 * @param stuffSerializer
	 *            Stuff serialization service to delegate the job of
	 *            de-serialization of concrete Stuff to.
	 * @return Concrete Event.
	 * @throws JSONException
	 *             if de-serialization process failed.
	 */
	Event deserialize(JSONObject data, JSONStuffSerialization stuffSerializer)
			throws JSONException;
}
