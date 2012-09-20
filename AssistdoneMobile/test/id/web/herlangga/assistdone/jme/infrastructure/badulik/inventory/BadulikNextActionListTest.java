package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import java.util.*;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.backend.rms.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.stuff.*;
import jmunit.framework.cldc11.*;

public class BadulikNextActionListTest extends TestCase {

	private RMSStorageManager storageManager;

	/**
	 * The default constructor. It just transmits the necessary informations to
	 * the superclass.
	 * 
	 * @param totalOfTests
	 *            the total of test methods present in the class.
	 * @param name
	 *            this testcase's name.
	 */
	public BadulikNextActionListTest() {
		super(1, "BadulikNextActionListTest");
		storageManager = new RMSStorageManager();
	}

	public void tearDown() {
		storageManager.drop("NextActionListTest");
	}

	public void receiveCategorizedEventTest() {
		BadulikCategorizedStuffDescriptor descriptor = new BadulikCategorizedStuffDescriptor();
		ObjectStorage storage = storageManager.get("NextActionListTest",
				descriptor.schema());
		BadulikNextActionList nextActionList = new BadulikNextActionList(
				storage, descriptor);

		CapturedStuffEventProcessor eventProcessor = new CapturedStuffEventProcessor(
				new EventLog() {
					public Event[] load(long arg0) {
						return null;
					}

					public void append(Event arg0) {
					}
				}, nextActionList);

		CapturedStuff cs1 = new CapturedStuff(1, StateType.CAPTURED);
		Description description = new Description("Test");
		CollectedEvent collectedEvent = new CollectedEvent(cs1, new Date(),
				description);
		eventProcessor.process(collectedEvent);

		Category category = new Category("Test");
		CategorizedEvent categorizedEvent = new CategorizedEvent(cs1,
				new Date(), category);

		eventProcessor.process(categorizedEvent);
		assertTrue(nextActionList.categorizedStuffs().length == 1);
		assertTrue(nextActionList.categorizedStuffs()[0].id() == 1);
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
			receiveCategorizedEventTest();
			break;
		}
	}

}
