package id.web.herlangga.assistdone.jme.infrastructure.badulik.inventory;

import java.util.*;

import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.backend.rms.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.definition.stuff.event.party.*;
import id.web.herlangga.gtd.module.stuff.*;
import jmunit.framework.cldc11.*;

public class BadulikTicklerFileTest extends TestCase {
	private RMSStorageManager storageManager;

	/**
	 * The default constructor. It just transmits the necessary informations to
	 * the superclass.
	 * 
	 * @param totalOfTests the total of test methods present in the class.
	 * @param name this testcase's name.
	 */
	public BadulikTicklerFileTest() {
		super(1, "BadulikTicklerFileTest");
		this.storageManager = new RMSStorageManager();
	}

	/**
	 * A empty method used by the framework to initialize the tests. If there's
	 * 5 test methods, the setUp is called 5 times, one for each method. The
	 * setUp occurs before the method's execution, so the developer can use it
	 * to any necessary initialization. It's necessary to override it, however.
	 * 
	 * @throws Throwable anything that the initialization can throw.
	 */
	public void setUp() throws Throwable {
	}

	/**
	 * A empty method used by the framework to release resources used by the
	 * tests. If there's 5 test methods, the tearDown is called 5 times, one for
	 * each method. The tearDown occurs after the method's execution, so the
	 * developer can use it to close something used in the test, like a
	 * nputStream or the RMS. It's necessary to override it, however.
	 */
	public void tearDown() {
		storageManager.drop("TicklerFileTest");
	}
	
	public void receiveHeldEventTest() {
		BadulikHeldStuffDescriptor heldStuffDescriptor = new BadulikHeldStuffDescriptor();
		ObjectStorage storage = storageManager.get("TicklerFileTest", heldStuffDescriptor.schema());
		BadulikTicklerFile ticklerFile = new BadulikTicklerFile(storage, heldStuffDescriptor);
		
		CapturedStuffEventProcessor eventProcessor = new CapturedStuffEventProcessor(
				new EventLog() {
					public Event[] load(long arg0) {
						return null;
					}

					public void append(Event arg0) {
					}
				}, ticklerFile);

		CapturedStuff stuff = new CapturedStuff(1, StateType.CAPTURED);
		Description description = new Description("Test");
		CollectedEvent collectedEvent = new CollectedEvent(stuff, new Date(),
				description);
		eventProcessor.process(collectedEvent);

		HeldEvent heldEvent = new HeldEvent(stuff, new Date());
		eventProcessor.process(heldEvent);
		
		assertTrue(ticklerFile.heldStuffs().length == 1);
	}

	/**
	 * This method stores all the test methods invocation. The developer must
	 * implement this method with a switch-case. The cases must start from 0 and
	 * increase in steps of one until the number declared as the total of tests
	 * in the constructor, exclusive. For example, if the total is 3, the cases
	 * must be 0, 1 and 2. In each case, there must be a test method invocation.
	 * 
	 * @param testNumber the test to be executed.
	 * @throws Throwable anything that the executed test can throw.
	 */
	public void test(int testNumber) throws Throwable {
		switch (testNumber) {
		case 0:
			receiveHeldEventTest();
			break;
		}
	}

}
