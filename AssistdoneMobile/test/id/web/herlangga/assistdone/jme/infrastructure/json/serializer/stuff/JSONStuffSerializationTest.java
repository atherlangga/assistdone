package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff;

import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.module.stuff.*;
import jmunit.framework.cldc11.*;

import com.domainlanguage.time.*;

public class JSONStuffSerializationTest extends TestCase {

	/**
	 * The default constructor. It just transmits the necessary informations to
	 * the superclass.
	 * 
	 * @param totalOfTests
	 *            the total of test methods present in the class.
	 * @param name
	 *            this testcase's name.
	 */
	public JSONStuffSerializationTest() {
		super(1, "JSONStuffSerializationTest");
	}

	public void serializeThenDeserializeTest() {
		JSONStuffSerialization serialization = new JSONStuffSerialization(
				new JSONStuffAttributeSerialization());
		CapturedStuff cs = new CapturedStuff(1, StateType.CAPTURED);
		cs.addAttribute_(new Description("Stuff serialization test"));
		cs.addAttribute_(new Schedule(CalendarDate.from(2010, 7, 1)));
		String result = serialization.serialize(cs);
		
		CapturedStuff deserialized = serialization.deserialize(result);
		assertEquals(cs.attributes_()[0], deserialized.attributes_()[0]);
		assertEquals(cs.attributes_()[1], deserialized.attributes_()[1]);
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
			serializeThenDeserializeTest();
			break;
		}
	}

}
