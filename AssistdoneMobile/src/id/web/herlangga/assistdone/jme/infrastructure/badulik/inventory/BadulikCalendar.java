package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import com.domainlanguage.time.*;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.definition.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.inventory.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public class BadulikCalendar implements Calendar, EventSubscriber {
	private final ObjectStorage storage;
	private final BadulikScheduledStuffDescriptor descriptor = new BadulikScheduledStuffDescriptor();

	public BadulikCalendar(ObjectStorage storage) {
		this.storage = storage;
	}

	public void receive(Event event) {
		if (event.triggerType().equals(TriggerType.SCHEDULING)) {
			ScheduledEvent scheduledEvent = (ScheduledEvent) event;
			Object scheduledStuffObject = descriptor.describe(scheduledEvent
					.source().id(), scheduledEvent.source().description(),
					scheduledEvent.schedule());
			storage.save(scheduledStuffObject, descriptor, descriptor);
		} else {
			Element objectId = Element.of(event.source().id());
			if (storage.contains(objectId)) {
				storage.remove(objectId);
			}
		}
	}

	public ScheduledStuff[] scheduledStuffs() {
		Object[] records = storage.findAll(filterNothing(), descriptor);
		ScheduledStuff[] scheduledStuffs = castFrom(records);

		return scheduledStuffs;
	}

	public ScheduledStuff[] scheduledStuffsFor(CalendarDate date) {
		Object[] matchingRecords = storage.findAll(filterByDate(date),
				descriptor);
		ScheduledStuff[] matchingScheduledStuffs = castFrom(matchingRecords);

		return matchingScheduledStuffs;
	}

	public ScheduledStuff[] castFrom(Object[] records) {
		int size = records.length;
		ScheduledStuff[] result = new ScheduledStuff[size];
		for (int i = 0; i < size; i++) {
			ScheduledStuff current = descriptor.reconstitute(records[i]);
			result[i] = current;
		}

		return result;
	}

	private ObjectFilter filterNothing() {
		return new ObjectFilter() {
			public boolean matches(Object object) {
				return true;
			}
		};
	}

	private ObjectFilter filterByDate(final CalendarDate date) {
		return new ObjectFilter() {
			public boolean matches(Object object) {
				ScheduledStuff scheduledStuff = descriptor.reconstitute(object);
				if (scheduledStuff.schedule().date().equals(date)) {
					return true;
				}
				return false;
			}
		};
	}

	private static final String ID_COLUMN_NAME = "Id";
	private static final String DESCRIPTION_COLUMN_NAME = "Description";
	private static final String START_DATE_YEAR_COLUMN_NAME = "StartDateYear";
	private static final String START_DATE_MONTH_COLUMN_NAME = "StartDateMonth";
	private static final String START_DATE_DAY_COLUMN_NAME = "StartDateDay";
	private static final String HOUR_COLUMN_NAME = "StartDateHour";
	private static final String MINUTE_COLUMN_NAME = "StartDateMinute";
	private static final String DURATION_COLUMN_NAME = "DurationInMinutes";
	private static final String INTERVAL_COLUMN_NAME = "IntervalInDays";
	private static final String END_DATE_YEAR_COLUMN_NAME = "EndDateYear";
	private static final String END_DATE_MONTH_COLUMN_NAME = "EndDateMonth";
	private static final String END_DATE_DAY_COLUMN_NAME = "EndDateDay";

	public static final Schema SCHEDULED_STUFF_SCHEMA = Schema.buildNew()
			.thenAddAttribute(ID_COLUMN_NAME, Datatype.LONG)
			.thenAddAttribute(DESCRIPTION_COLUMN_NAME, Datatype.STRING)
			.thenAddAttribute(START_DATE_YEAR_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(START_DATE_MONTH_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(START_DATE_DAY_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(HOUR_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(MINUTE_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(DURATION_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(INTERVAL_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(END_DATE_YEAR_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(END_DATE_MONTH_COLUMN_NAME, Datatype.INT)
			.thenAddAttribute(END_DATE_DAY_COLUMN_NAME, Datatype.INT)
			.thenGetResult();

	private class BadulikScheduledStuffDescriptor implements
			ObjectReconstitutor, ObjectIdExtractor, ObjectStateExtractor {

		private class ScheduledStuffRecord {
			private long id;
			private Description description;
			private Schedule schedule;

			private ScheduledStuffRecord(long id, Description description,
					Schedule schedule) {
				this.id = id;
				this.description = description;
				this.schedule = schedule;
			}
		}

		private Object describe(long id, Description description,
				Schedule schedule) {
			return new ScheduledStuffRecord(id, description, schedule);
		}

		private ScheduledStuff reconstitute(Object o) {
			ScheduledStuffRecord record = (ScheduledStuffRecord) o;
			return new ScheduledStuff(record.id, record.description,
					record.schedule);
		}

		public Object reconstituteObject(Element objectId, Tuple state) {
			long id = objectId.valueAsLong();
			String descriptionValue = state.elementOf(DESCRIPTION_COLUMN_NAME)
					.valueAsString();
			int startDateYear = state.elementOf(START_DATE_YEAR_COLUMN_NAME)
					.valueAsInt();
			int startDateMonth = state.elementOf(START_DATE_MONTH_COLUMN_NAME)
					.valueAsInt();
			int startDateDay = state.elementOf(START_DATE_DAY_COLUMN_NAME)
					.valueAsInt();
			int startDateHour = state.elementOf(HOUR_COLUMN_NAME).valueAsInt();
			int startDateMinute = state.elementOf(MINUTE_COLUMN_NAME)
					.valueAsInt();
			int durationInMinutes = state.elementOf(DURATION_COLUMN_NAME)
					.valueAsInt();
			int intervalInDays = state.elementOf(INTERVAL_COLUMN_NAME)
					.valueAsInt();
			int endDateYear = state.elementOf(END_DATE_YEAR_COLUMN_NAME)
					.valueAsInt();
			int endDateMonth = state.elementOf(END_DATE_MONTH_COLUMN_NAME)
					.valueAsInt();
			int endDateDay = state.elementOf(END_DATE_DAY_COLUMN_NAME)
					.valueAsInt();

			Description description = new Description(descriptionValue);
			Schedule schedule = new Schedule(CalendarDate.date(startDateYear,
					startDateMonth, startDateDay), TimeOfDay.hourAndMinute(
					startDateHour, startDateMinute), durationInMinutes,
					intervalInDays, CalendarDate.date(endDateYear,
							endDateMonth, endDateDay));

			return new ScheduledStuffRecord(id, description, schedule);
		}

		public Element extractIdFrom(Object toExtract) {
			ScheduledStuffRecord record = (ScheduledStuffRecord) toExtract;
			return Element.of(record.id);
		}

		public Tuple extractStateFrom(Object toExtract) {
			ScheduledStuffRecord record = (ScheduledStuffRecord) toExtract;

			long id = record.id;
			String descriptionValue = record.description.descriptionValue_();
			int startDateYear = record.schedule.startDate().yearAsInt();
			int startDateMonth = record.schedule.startDate().monthAsInt();
			int startDateDay = record.schedule.startDate().dayAsInt();
			int startDateHour = record.schedule.time().hour().value();
			int startDateMinute = record.schedule.time().minute().value();
			int durationInMinutes = record.schedule.durationInMinutes();
			int intervalInDays = record.schedule.intervalInDays();
			int endDateYear = record.schedule.endDate().yearAsInt();
			int endDateMonth = record.schedule.endDate().monthAsInt();
			int endDateDay = record.schedule.endDate().dayAsInt();

			return Tuple
					.buildNew()
					.withSchema(SCHEDULED_STUFF_SCHEMA)
					.thenAddField(ID_COLUMN_NAME, Element.of(id))
					.thenAddField(DESCRIPTION_COLUMN_NAME,
							Element.of(descriptionValue))
					.thenAddField(START_DATE_YEAR_COLUMN_NAME,
							Element.of(startDateYear))
					.thenAddField(START_DATE_MONTH_COLUMN_NAME,
							Element.of(startDateMonth))
					.thenAddField(START_DATE_DAY_COLUMN_NAME,
							Element.of(startDateDay))
					.thenAddField(HOUR_COLUMN_NAME, Element.of(startDateHour))
					.thenAddField(MINUTE_COLUMN_NAME,
							Element.of(startDateMinute))
					.thenAddField(DURATION_COLUMN_NAME,
							Element.of(durationInMinutes))
					.thenAddField(INTERVAL_COLUMN_NAME,
							Element.of(intervalInDays))
					.thenAddField(END_DATE_YEAR_COLUMN_NAME,
							Element.of(endDateYear))
					.thenAddField(END_DATE_MONTH_COLUMN_NAME,
							Element.of(endDateMonth))
					.thenAddField(END_DATE_DAY_COLUMN_NAME,
							Element.of(endDateDay)).thenGetResult();
		}

	}

}
