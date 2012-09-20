package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.attribute.*;

import org.json.me.*;

class JSONDelegatedPartySerializationHelper implements
		JSONStuffAttributeSerializationHelper {
	public JSONObject serialize(Attribute attribute) throws JSONException {
		DelegatedParty delegatedParty = (DelegatedParty) attribute;
		JSONObject result = new JSONObject();
		result.put(CONTACT_IDENTIFIER, delegatedParty.contactIdentifierName_());
		result.put(CONTACT_INFORMATION, delegatedParty
				.contactInformationValue_());

		return result;
	}

	public Attribute deserialize(JSONObject data) throws JSONException {
		String contactIdentifer = data.getString(CONTACT_IDENTIFIER);
		String contactInformation = data.getString(CONTACT_INFORMATION);

		return new DelegatedParty(contactIdentifer, contactInformation);
	}

	private static final String CONTACT_IDENTIFIER = "contact_identifer";
	private static final String CONTACT_INFORMATION = "contact_information";
}
