package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.stuff.*;

import java.util.*;

import org.json.me.*;

import com.domainlanguage.time.*;

class JSONScheduledEventSerializationHelper implements
		JSONEventSerializationHelper {

	public JSONObject serialize(Event event,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		JSONObject representation = new JSONObject();
		ScheduledEvent scheduledEvent = (ScheduledEvent) event;

		representation.put(SOURCE, stuffSerializer
				.serialize((CapturedStuff) scheduledEvent.source()));
		representation.put(TIME_STAMP, scheduledEvent.timeStamp().getTime());
		representation.put(START_DATE_YEAR, scheduledEvent.schedule()
				.startDate().yearAsInt());
		representation.put(START_DATE_MONTH, scheduledEvent.schedule()
				.startDate().monthAsInt());
		representation.put(START_DATE_DAY, scheduledEvent.schedule()
				.startDate().dayAsInt());
		representation.put(CLOCK_TIME_HOUR, scheduledEvent.schedule().time()
				.hour().value());
		representation.put(CLOCK_TIME_MINUTE, scheduledEvent.schedule().time()
				.minute().value());
		representation.put(DURATION, scheduledEvent.schedule()
				.durationInMinutes());
		representation
				.put(INTERVAL, scheduledEvent.schedule().intervalInDays());
		representation.put(END_DATE_YEAR, scheduledEvent.schedule().endDate()
				.yearAsInt());
		representation.put(END_DATE_MONTH, scheduledEvent.schedule().endDate()
				.monthAsInt());
		representation.put(END_DATE_DAY, scheduledEvent.schedule().endDate()
				.dayAsInt());

		return representation;
	}

	public Event deserialize(JSONObject data,
			JSONStuffSerialization stuffSerializer) throws JSONException {
		CapturedStuff source = stuffSerializer.deserialize(data
				.getString(SOURCE));
		Date timeStamp = new Date(data.getLong(TIME_STAMP));

		CalendarDate startDate = CalendarDate.date(
				data.getInt(START_DATE_YEAR), data.getInt(START_DATE_MONTH),
				data.getInt(START_DATE_DAY));
		TimeOfDay time = TimeOfDay.hourAndMinute(data.getInt(CLOCK_TIME_HOUR),
				data.getInt(CLOCK_TIME_MINUTE));
		int durationInMinutes = data.getInt(DURATION);
		int intervalInDays = data.getInt(INTERVAL);
		CalendarDate endDate = CalendarDate.date(data.getInt(END_DATE_YEAR),
				data.getInt(END_DATE_MONTH), data.getInt(END_DATE_DAY));

		Schedule schedule = new Schedule(startDate, time, durationInMinutes,
				intervalInDays, endDate);
		return new ScheduledEvent(source, timeStamp, schedule);
	}

	private static final String SOURCE = "source";
	private static final String TIME_STAMP = "time_stamp";
	private static final String START_DATE_YEAR = "start_date_year";
	private static final String START_DATE_MONTH = "start_date_month";
	private static final String START_DATE_DAY = "start_date_day";
	private static final String CLOCK_TIME_HOUR = "clock_time_hour";
	private static final String CLOCK_TIME_MINUTE = "clock_time_minute";
	private static final String DURATION = "duration";
	private static final String INTERVAL = "interval";
	private static final String END_DATE_YEAR = "end_date_year";
	private static final String END_DATE_MONTH = "end_date_month";
	private static final String END_DATE_DAY = "end_date_day";
}
