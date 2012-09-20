package id.web.herlangga.featurespec

import helper._
import org.scalatest.{GivenWhenThen, FeatureSpec}
import java.util.Date
import id.web.herlangga.gtd.definition.stuff.{Description, StateType}
import id.web.herlangga.gtd.definition.stuff.event.CollectedEvent
import id.web.herlangga.gtd.module.stuff.{CapturedStuffEventProcessor, CapturedStuff}
import org.scalatest.matchers.ShouldMatchers

class CollectionPhaseFeatureSpec extends FeatureSpec with GivenWhenThen with ShouldMatchers with InventoryFactory {
  val eventProcessor = new CapturedStuffEventProcessor(EventLogDummy, EventPublisher)

  feature("GTD user captures stuffs") {

    info("As a GTD user")
    info("I want to capture my 'incomplete' stuff")
    info("So that I can concentrate on the 'now'")

    scenario("In-basket has been set up to retrieve captured stuff") {

      given("A captured stuff")
      val stuff = new CapturedStuff(1, StateType.CAPTURED)

      and("An in-basket")
      val inBasket = createInBasket
      EventPublisher register inBasket

      when("I have collected and described the stuff")
      val collectedEvent = new CollectedEvent(stuff, new Date, new Description("description"))
      eventProcessor process (collectedEvent)

      then("The in-basket should automatically contains the stuff")
      inBasket.collectedStuffs exists (_.id equals stuff.id) should be === true
    }
  }
}
