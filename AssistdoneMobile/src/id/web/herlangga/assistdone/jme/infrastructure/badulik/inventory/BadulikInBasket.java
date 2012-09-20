package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class BadulikInBasket implements InBasket, StuffIdGenerator,
		EventSubscriber {
	private final ObjectStorage storage;
	private final BadulikCollectedStuffDescriptor descriptor;

	public BadulikInBasket(ObjectStorage storage,
			BadulikCollectedStuffDescriptor descriptor) {
		this.storage = storage;
		this.descriptor = descriptor;
	}

	public CollectedStuff[] collectedStuffs() {
		Object[] records = fetchAllRecords();
		CollectedStuff[] result = reconstitute(records);

		return result;
	}

	public void receive(Event event) {
		if (event.triggerType() == TriggerType.COLLECTING) {
			insertIntoRepository((CollectedEvent) event);
		} else {
			removeFromRepository(event);
		}
	}

	public long generateStuffId() {
		return storage.nextSequenceNumber();
	}

	private CollectedStuff[] reconstitute(Object[] records) {
		int totalRecords = records.length;
		CollectedStuff[] result = new CollectedStuff[totalRecords];
		for (int i = 0; i < totalRecords; i++) {
			result[i] = descriptor.reconstitute(records[i]);
		}
		return result;
	}

	private Object[] fetchAllRecords() {
		Object[] records = storage.findAll(new ObjectFilter() {
			public boolean matches(Object object) {
				return true;
			}
		}, descriptor);
		return records;
	}

	private void insertIntoRepository(CollectedEvent event) {
		Object collectedStuffObject = descriptor.describe(event.source().id(),
				event.description(), event.timeStamp());
		storage.save(collectedStuffObject, descriptor, descriptor);
	}

	private void removeFromRepository(Event event) {
		Element objectId = Element.of(event.source().id());
		if (storage.contains(objectId)) {
			storage.remove(objectId);
		}
	}

}
