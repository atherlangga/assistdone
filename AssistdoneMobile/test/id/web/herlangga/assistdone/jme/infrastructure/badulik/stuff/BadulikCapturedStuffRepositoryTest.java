package id.web.herlangga.assistdone.jme.infrastructure.badulik.stuff;

import com.domainlanguage.time.*;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.badulik.*;
import id.web.herlangga.badulik.backend.rms.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.module.stuff.*;
import jmunit.framework.cldc11.*;

public class BadulikCapturedStuffRepositoryTest extends TestCase {
	private final ObjectStorageManager storageManager;
	private final BadulikCapturedStuffRepository repository;

	private static final String REPOSITORY_NAME = "CapturedStuffRepositoryTest";

	/**
	 * The default constructor. It just transmits the necessary informations to
	 * the superclass.
	 * 
	 * @param totalOfTests
	 *            the total of test methods present in the class.
	 * @param name
	 *            this testcase's name.
	 */
	public BadulikCapturedStuffRepositoryTest() {
		super(1, "BadulikCapturedStuffRepositoryTest");
		storageManager = new RMSStorageManager();
		BadulikCapturedStuffDescriptor descriptor = new BadulikCapturedStuffDescriptor(
				new JSONStuffSerialization(
						new JSONStuffAttributeSerialization()));
		repository = new BadulikCapturedStuffRepository(storageManager.get(
				REPOSITORY_NAME, descriptor.schema()), descriptor);
	}

	public void tearDown() {
		storageManager.drop(REPOSITORY_NAME);
	}

	public void saveThenFindTest() {
		CapturedStuff cs = new CapturedStuff(1, StateType.SCHEDULED);
		cs.addAttribute_(new Description("First captured stuff"));
		cs.addAttribute_(new Schedule(CalendarDate.date(2010, 8, 1)));
		repository.save(cs);

		CapturedStuff resurrected = repository.find(1);

		assertEquals(cs.attributes_().length, resurrected.attributes_().length);
		assertEquals(cs.attributes_()[0], resurrected.attributes_()[0]);
		assertEquals(cs.attributes_()[1], resurrected.attributes_()[1]);
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
			saveThenFindTest();
			break;
		}
	}

}
