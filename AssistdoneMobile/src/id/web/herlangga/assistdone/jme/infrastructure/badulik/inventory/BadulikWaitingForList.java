package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class BadulikWaitingForList implements WaitingForList, EventSubscriber {
	private final ObjectStorage storage;
	private final BadulikDelegatedStuffDescriptor descriptor;

	public BadulikWaitingForList(ObjectStorage storage,
			BadulikDelegatedStuffDescriptor descriptor) {
		this.storage = storage;
		this.descriptor = descriptor;
	}

	public DelegatedStuff[] delegatedStuffs() {
		Object[] records = storage.findAll(new ObjectFilter() {
			public boolean matches(Object object) {
				return true;
			}
		}, descriptor);

		return cast(records);
	}

	public DelegatedStuff[] delegatedStuffsFor(final DelegatedParty party) {
		Object[] matchingRecords = storage.findAll(new ObjectFilter() {
			public boolean matches(Object object) {
				DelegatedStuff ds = (DelegatedStuff) object;
				if (ds.appropriateParty().equals(party)) {
					return true;
				}
				return false;
			}
		}, descriptor);

		return cast(matchingRecords);
	}

	public void receive(Event event) {
		if (event.triggerType().equals(TriggerType.DELEGATING)) {
			DelegatedEvent de = (DelegatedEvent) event;
			Object delegatedStuffObject = descriptor.describe(de.source().id(),
					de.source().description(), de.appropriateParty(),
					de.expectedProduct());
			storage.save(delegatedStuffObject, descriptor, descriptor);
		} else {
			Element stuffId = Element.of(event.source().id());
			if (storage.contains(stuffId)) {
				storage.remove(stuffId);
			}
		}
	}

	private DelegatedStuff[] cast(Object[] records) {
		int totalRecords = records.length;
		DelegatedStuff[] delegatedStuffs = new DelegatedStuff[totalRecords];
		for (int i = 0; i < totalRecords; i++) {
			delegatedStuffs[i] = descriptor.reconstitute(records[i]);
		}

		return delegatedStuffs;
	}
}
