package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.attribute.*;

import org.json.me.*;

class JSONDeliverableProductSerializationHelper implements
		JSONStuffAttributeSerializationHelper {

	public Attribute deserialize(JSONObject data) throws JSONException {
		String productName = (String) data.getString(PRODUCT_NAME);
		return new DeliverableProduct(productName);
	}

	public JSONObject serialize(Attribute attribute) throws JSONException {
		DeliverableProduct product = (DeliverableProduct) attribute;
		JSONObject inner = new JSONObject();
		inner.put(PRODUCT_NAME, product.productName_());
		return inner;
	}
	
	private static final String PRODUCT_NAME = "product_name";

}
