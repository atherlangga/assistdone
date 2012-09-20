package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.attribute.*;

import org.json.me.*;

class JSONCategorySerializationHelper implements
		JSONStuffAttributeSerializationHelper {

	public JSONObject serialize(Attribute attribute) throws JSONException {
		Category category = (Category) attribute;
		JSONObject inner = new JSONObject();
		inner.put(CATEGORY_KEY_IDENTIFIER, category.categoryName_());
		
		return inner;
	}

	public Attribute deserialize(JSONObject data) throws JSONException {
		String categoryName = data.getString(CATEGORY_KEY_IDENTIFIER);
		return new Category(categoryName);
	}

	private static final String CATEGORY_KEY_IDENTIFIER = "Category";
}
