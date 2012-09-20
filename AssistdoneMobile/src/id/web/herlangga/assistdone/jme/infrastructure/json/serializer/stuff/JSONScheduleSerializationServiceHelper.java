package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.attribute.*;

import org.json.me.*;

import com.domainlanguage.time.*;

class JSONScheduleSerializationServiceHelper implements
		JSONStuffAttributeSerializationHelper {

	public JSONObject serialize(Attribute attribute) throws JSONException {
		Schedule schedule = (Schedule) attribute;

		JSONObject representation = new JSONObject();
		representation.put(START_DATE_YEAR, schedule.startDate().yearAsInt());
		representation.put(START_DATE_MONTH, schedule.startDate().monthAsInt());
		representation.put(START_DATE_DAY, schedule.startDate().dayAsInt());
		representation.put(CLOCK_TIME_HOUR, schedule.time().hour().value());
		representation.put(CLOCK_TIME_MINUTE, schedule.time().minute().value());
		representation.put(DURATION, schedule.durationInMinutes());
		representation.put(INTERVAL, schedule.intervalInDays());
		representation.put(END_DATE_YEAR, schedule.endDate().yearAsInt());
		representation.put(END_DATE_MONTH, schedule.endDate().monthAsInt());
		representation.put(END_DATE_DAY, schedule.endDate().dayAsInt());

		return representation;
	}

	public Attribute deserialize(JSONObject data) throws JSONException {
		CalendarDate startDate = CalendarDate.date(
				data.getInt(START_DATE_YEAR), data.getInt(START_DATE_MONTH),
				data.getInt(START_DATE_DAY));
		TimeOfDay time = TimeOfDay.hourAndMinute(data.getInt(CLOCK_TIME_HOUR),
				data.getInt(CLOCK_TIME_MINUTE));
		int durationInMinutes = data.getInt(DURATION);
		int intervalInDays = data.getInt(INTERVAL);
		CalendarDate endDate = CalendarDate.date(data.getInt(END_DATE_YEAR),
				data.getInt(END_DATE_MONTH), data.getInt(END_DATE_DAY));

		return new Schedule(startDate, time, durationInMinutes, intervalInDays,
				endDate);
	}

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
