package id.web.herlangga.assistdone.jme.infrastructure.json.serializer.event;

import java.util.*;

import org.json.me.*;

import id.web.herlangga.assistdone.jme.infrastructure.json.serializer.stuff.*;
import id.web.herlangga.gtd.definition.stuff.*;
import id.web.herlangga.gtd.definition.stuff.attribute.*;
import id.web.herlangga.gtd.definition.stuff.event.*;
import id.web.herlangga.gtd.module.stuff.*;
import jmunit.framework.cldc11.*;

public class JSONHeldEventSerializationHelperTest extends TestCase {

	/**
	 * The default constructor. It just transmits the necessary informations to
	 * the superclass.
	 * 
	 * @param totalOfTests
	 *            the total of test methods present in the class.
	 * @param name
	 *            this testcase's name.
	 */
	public JSONHeldEventSerializationHelperTest() {
		super(1, "JSONHeldEventSerializationHelperTest");
	}

	public void serializeThenDeserializeTest() {
		JSONStuffSerialization stuffSerialization = new JSONStuffSerialization(
				new JSONStuffAttributeSerialization());
		JSONHeldEventSerializationHelper serializationHelper = new JSONHeldEventSerializationHelper();
		
		CapturedStuff cs = new CapturedStuff(1, StateType.CATEGORIZED);
		cs.addAttribute_(new Description("HeldStuffSerializationTest"));
		cs.addAttribute_(new Category("Test"));
		
		HeldEvent event = new HeldEvent(cs, new Date());
		try {
			JSONObject result = serializationHelper.serialize(event, stuffSerialization);
			HeldEvent resurrected = (HeldEvent) serializationHelper.deserialize(result, stuffSerialization);
			
			assertEquals(event, resurrected);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
