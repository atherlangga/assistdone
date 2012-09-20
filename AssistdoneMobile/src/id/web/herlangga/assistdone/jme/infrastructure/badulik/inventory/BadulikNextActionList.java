package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class BadulikNextActionList implements NextActionList, EventSubscriber {
	private final ObjectStorage storage;
	private final BadulikCategorizedStuffDescriptor descriptor;

	public BadulikNextActionList(ObjectStorage storage,
			BadulikCategorizedStuffDescriptor descriptor) {
		this.storage = storage;
		this.descriptor = descriptor;
	}

	public void receive(Event event) {
		if (event.triggerType().equals(TriggerType.CATEGORIZING)) {
			CategorizedEvent he = (CategorizedEvent) event;
			Object categorizedStuffObject = descriptor.describe(he.source()
					.id(), he.source().description(), he.category());
			storage.save(categorizedStuffObject, descriptor, descriptor);
		} else {
			Element stuffId = Element.of(event.source().id());
			if (storage.contains(stuffId)) {
				storage.remove(stuffId);
			}
		}
	}

	public CategorizedStuff[] categorizedStuffs() {
		Object[] records = storage.findAll(objectFilter(), descriptor);
		CategorizedStuff[] result = cast(records);

		return result;
	}

	public CategorizedStuff[] categorizedStuffsOf(final Category category) {
		Object[] matchedRecords = storage.findAll(objectFilterBy(category),
				descriptor);
		CategorizedStuff[] result = cast(matchedRecords);

		return result;
	}

	private ObjectFilter objectFilter() {
		return new ObjectFilter() {
			public boolean matches(Object object) {
				return true;
			}
		};
	}

	private ObjectFilter objectFilterBy(final Category category) {
		return new ObjectFilter() {
			public boolean matches(Object object) {
				CategorizedStuff cs = (CategorizedStuff) object;
				if (cs.category().hasSameValueAs(category)) {
					return true;
				}
				return false;
			}
		};
	}

	private CategorizedStuff[] cast(Object[] records) {
		int size = records.length;
		CategorizedStuff[] result = new CategorizedStuff[size];
		for (int i = 0; i < size; i++) {
			result[i] = descriptor.reconstitute(records[i]);
		}

		return result;
	}

}
