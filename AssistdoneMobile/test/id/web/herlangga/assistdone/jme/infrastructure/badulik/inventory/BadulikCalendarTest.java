package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.backend.rms.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.inventory.item.*;
import id.web.herlangga.gtd.module.stuff.*;
import id.web.herlangga.gtd.module.stuff.CapturedStuff;

import java.util.*;

import jmunit.framework.cldc11.*;

import com.domainlanguage.time.*;

public class BadulikCalendarTest extends TestCase {
	private RMSStorageManager storageManager = new RMSStorageManager();

	/**
	 * The default constructor. It just transmits the necessary informations to
	 * the superclass.
	 * 
	 * @param totalOfTests
	 *            the total of test methods present in the class.
	 * @param name
	 *            this testcase's name.
	 */
	public BadulikCalendarTest() {
		super(1, "BadulikCalendarTest");
	}

	public void setUp() {
	}

	public void tearDown() {
		storageManager.drop("Calendar");
	}

	public void receiveScheduledEventTest() {
		ObjectStorage storage = storageManager.get("Calendar",
				BadulikCalendar.SCHEDULED_STUFF_SCHEMA);
		BadulikCalendar calendar = new BadulikCalendar(storage);

		CapturedStuffEventProcessor eventProcessor = new CapturedStuffEventProcessor(
				new EventLog() {
					public Event[] load(long arg0) {
						return null;
					}

					public void append(Event arg0) {
					}
				}, calendar);

		CapturedStuff stuff = new CapturedStuff(1, StateType.CAPTURED);

		Description description = new Description("Test");
		CollectedEvent collectedEvent = new CollectedEvent(stuff, new Date(),
				description);
		eventProcessor.process(collectedEvent);

		Schedule schedule = new Schedule(CalendarDate.date(2010, 8, 6));
		ScheduledEvent scheduledEvent = new ScheduledEvent(stuff, new Date(),
				schedule);
		eventProcessor.process(scheduledEvent);

		ScheduledStuff scheduledStuff = calendar
				.scheduledStuffsFor(CalendarDate.date(2010, 8, 6))[0];
		assertEquals(scheduledStuff.id(), 1);
		assertEquals(scheduledStuff.description(), description);
		assertEquals(scheduledStuff.schedule(), schedule);

	}

	/**
	 * This method stores all the test methods invocation. The developer must
	 * implement this method with a switch-case. The cases must start from 0 and
	 * increase in steps of one until the number declared as the total of tests
	 * in the constructor, exclusive. For example, if the total is 3, the cases
	 * must be 0, 1 and 2. In each case, there must be a test method invocation.
	 * 
	 * @param testNumber
	 *            the test to be executed.
	 * @throws Throwable
	 *             anything that the executed test can throw.
	 */
	public void test(int testNumber) throws Throwable {
		switch (testNumber) {
		case 0:
			receiveScheduledEventTest();
			break;
		}
	}

}
