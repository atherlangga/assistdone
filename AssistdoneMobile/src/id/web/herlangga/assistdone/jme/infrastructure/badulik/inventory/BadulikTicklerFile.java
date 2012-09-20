package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class BadulikTicklerFile implements TicklerFile, EventSubscriber {
	private final ObjectStorage storage;
	private final BadulikHeldStuffDescriptor heldStuffDescriptor;

	public BadulikTicklerFile(ObjectStorage storage,
			BadulikHeldStuffDescriptor heldStuffDescriptor) {
		this.storage = storage;
		this.heldStuffDescriptor = heldStuffDescriptor;
	}

	public void receive(Event event) {
		if (event.triggerType().equals(TriggerType.HOLDING)) {
			Object heldStuffObject = heldStuffDescriptor.describe(event
					.source().id(), event.source().description());
			storage.save(heldStuffObject, heldStuffDescriptor,
					heldStuffDescriptor);
		} else {
			Element stuffId = Element.of(event.source().id());
			if (storage.contains(stuffId)) {
				storage.remove(stuffId);
			}
		}
	}

	public HeldStuff[] heldStuffs() {
		Object[] records = storage.findAll(findAllObjectFilter(),
				heldStuffDescriptor);
		HeldStuff[] result = cast(records);

		return result;
	}

	private ObjectFilter findAllObjectFilter() {
		return new ObjectFilter() {
			public boolean matches(Object object) {
				return true;
			}
		};
	}

	private HeldStuff[] cast(Object[] records) {
		int size = records.length;
		HeldStuff[] result = new HeldStuff[size];
		for (int i = 0; i < size; i++) {
			HeldStuff current = (HeldStuff) heldStuffDescriptor
					.reconstitute(records[i]);
			result[i] = current;
		}

		return result;
	}

}
