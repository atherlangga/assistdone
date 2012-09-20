package id.web.herlangga.gtd.definition.stuff.attribute;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.TimeOfDay;
import id.web.herlangga.gtd.definition.ValueObject;

import java.util.Enumeration;

public class Schedule implements Attribute {
    private final CalendarDate startDate;
    private final TimeOfDay time;
    private final int durationInMinutes;
    private final int intervalInDays;
    private final CalendarDate endDate;

    public Schedule(CalendarDate date) {
        this(date, TimeOfDay.hourAndMinute(0, 0), ONE_DAY_IN_MINUTES,
                0, date);
    }

    public Schedule(CalendarDate date, TimeOfDay time) {
        this(date, time, ONE_DAY_IN_MINUTES, 0, date);
    }

    public Schedule(CalendarDate date, TimeOfDay time, int durationInMinutes) {
        this(date, time, durationInMinutes, 0, date);
    }

    public Schedule(CalendarDate startDate, CalendarDate endDate) {
        this(startDate, TimeOfDay.hourAndMinute(0, 0), ONE_DAY_IN_MINUTES,
                0, endDate);
    }

    public Schedule(
            CalendarDate startDate, TimeOfDay time, int durationInMinutes,
            int intervalInDays, CalendarDate endDate) {
        this.startDate = startDate;
        this.time = time;
        this.durationInMinutes = durationInMinutes;
        this.intervalInDays = intervalInDays;
        this.endDate = endDate;
    }

    public CalendarDate date() {
        return startDate();
    }

    public CalendarDate startDate() {
        return startDate;
    }

    public TimeOfDay time() {
        return time;
    }

    public int durationInMinutes() {
        return durationInMinutes;
    }

    public int intervalInDays() {
        return intervalInDays;
    }

    public CalendarDate endDate() {
        return endDate;
    }

    public boolean isActiveAt(CalendarDate someDay) {
        Enumeration enumeration = startDate.through(endDate).daysIterator();
        while (enumeration.hasMoreElements()) {
            CalendarDate current = (CalendarDate) enumeration.nextElement();
            if (someDay.equals(current)) {
                return true;
            }
        }

        return false;
    }

    public AttributeType type() {
        return AttributeType.SCHEDULE;
    }

    public boolean hasSameValueAs(ValueObject another) {
        return equals(another);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (durationInMinutes != schedule.durationInMinutes) return false;
        if (intervalInDays != schedule.intervalInDays) return false;
        if (endDate != null ? !endDate.equals(schedule.endDate) : schedule.endDate != null) return false;
        if (startDate != null ? !startDate.equals(schedule.startDate) : schedule.startDate != null) return false;
        if (time != null ? !time.equals(schedule.time) : schedule.time != null) return false;

        return true;
    }

    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + durationInMinutes;
        result = 31 * result + intervalInDays;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    private static int ONE_HOUR_IN_MINUTES = 60;
    private static int ONE_DAY_IN_MINUTES = 24 * ONE_HOUR_IN_MINUTES;
}
