package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.stuff.*;

import java.util.*;

import org.json.me.*;

public class JSONDelegatedEventSerializationHelper implements
		JSONEventSerializationHelper {

	public JSONObject serialize(Event event, JSONStuffSerialization stuffSerializer)
			throws JSONException {
		JSONObject representation = new JSONObject();
		DelegatedEvent delegatedEvent = (DelegatedEvent) event;

		representation.put(SOURCE, stuffSerializer
				.serialize((CapturedStuff) delegatedEvent.source()));
		representation.put(TIME_STAMP, delegatedEvent.timeStamp().getTime());
		representation.put(CONTACT_IDENTIFIER, delegatedEvent
				.appropriateParty().contactIdentifierName_());
		representation.put(CONTACT_INFORMATION, delegatedEvent
				.appropriateParty().contactInformationValue_());
		representation.put(EXPECTED_PRODUCT, delegatedEvent.expectedProduct()
				.productName_());

		return representation;
	}

	public Event deserialize(JSONObject data,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		CapturedStuff source = stuffSerializer.deserialize(data
				.getString(SOURCE));
		Date timeStamp = new Date(data.getLong(TIME_STAMP));
		DelegatedParty party = new DelegatedParty(data
				.getString(CONTACT_IDENTIFIER), data
				.getString(CONTACT_INFORMATION));
		DeliverableProduct product = new DeliverableProduct(data
				.getString(EXPECTED_PRODUCT));

		return new DelegatedEvent(source, timeStamp, party, product);
	}

	private static final String SOURCE = "source";
	private static final String TIME_STAMP = "time_stamp";
	private static final String CONTACT_IDENTIFIER = "contact_identifier";
	private static final String CONTACT_INFORMATION = "contact_information";
	private static final String EXPECTED_PRODUCT = "expected_product";
}
