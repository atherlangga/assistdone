package id.web.herlangga.featurespec

import com.domainlanguage.time.CalendarDate

import helper._

import id.web.herlangga.gtd.module.stuff._
import id.web.herlangga.gtd.definition.stuff._
import id.web.herlangga.gtd.definition.stuff.attribute._
import id.web.herlangga.gtd.definition.stuff.event._

import java.util.Date

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class ProcessAndOrganizePhaseFeatureSpec
        extends FeatureSpec
                with GivenWhenThen
                with BeforeAndAfterEach
                with ShouldMatchers
                with InventoryFactory {
  val eventProcessor = new CapturedStuffEventProcessor(EventLogDummy, EventPublisher)

  override def afterEach() = {
    EventPublisher resetSubscribers
  }

  feature("GTD User processes stuff") {

    info("As a GTD User")
    info("I want to process my stuffs")
    info("So that it's easy for me to organize")

    scenario("Trashing useless stuff") {

      given("A stuff in in-basket")
      val stuff = new CapturedStuff(1, StateType.CAPTURED)
      val inBasket = createInBasket
      val collectedEvent = new CollectedEvent(
        stuff, new Date(), new Description("description"))
      EventPublisher register inBasket
      eventProcessor process collectedEvent

      and("A trash can")
      val trashCan = createTrashCan
      EventPublisher register trashCan

      when("I decided to trash the stuff")
      val trashedEvent = new TrashedEvent(stuff, new Date())
      eventProcessor process trashedEvent

      then("The trash should contains the trashed stuff")
      trashCan.trashedStuffs exists (_.id equals stuff.id) should be === true

      and("In-basket should no longer contains the trashed collected stuff")
      inBasket.collectedStuffs exists (_.id equals stuff.id) should be === false
    }

    scenario("Holding stuff to be reviewed someday") {
      given("A stuff in in-basket")
      val stuff = new CapturedStuff(1, StateType.CAPTURED)
      val inBasket = createInBasket
      val collectedEvent = new CollectedEvent(
        stuff, new Date(), new Description("description"))
      EventPublisher register inBasket
      eventProcessor process collectedEvent

      and("A tickler file")
      val ticklerFile = createTicklerFile
      EventPublisher register ticklerFile

      when("I decided to hold the stuff to reviewed someday")
      val heldEvent = new HeldEvent(stuff, new Date())
      eventProcessor process heldEvent

      then("The stuff should appear in my tickler file")
      ticklerFile.heldStuffs exists (_.id equals stuff.id) should be === true
    }

    scenario("Doing less-than-two-minute action") {
      given("A stuff in in-basket")
      val stuff = new CapturedStuff(1, StateType.CAPTURED)
      val inBasket = createInBasket
      val collectedEvent = new CollectedEvent(
        stuff, new Date(), new Description("description"))
      EventPublisher register inBasket
      eventProcessor process collectedEvent

      and("A done-log")
      val doneLog = createDoneLog
      EventPublisher register doneLog

      when("I decided to do the stuff right away")
      val doneEvent = new DoneEvent(stuff, new Date())
      eventProcessor process doneEvent

      then("The stuff should be listed in done-log")
      doneLog.doneStuffs exists (_.id equals stuff.id) should be === true
    }

    scenario("Handing off stuff that can be delegated") {
      given("A stuff in in-basket")
      val stuff = new CapturedStuff(1, StateType.CAPTURED)
      val collectedEvent = new CollectedEvent(
        stuff, new Date(), new Description("description"))
      val inBasket = createInBasket
      EventPublisher register inBasket
      eventProcessor process collectedEvent

      and("Another (slighty unlucky) party better than me to do the stuff")
      val appropriateParty = new DelegatedParty("someone", "someone@somewhere")

      and("A demand of finished product")
      val finishedProduct = new DeliverableProduct("finished product")

      and("A waiting-for list")
      val waitingForList = createWaitingForList
      EventPublisher register waitingForList

      when("I decided to delegate it")
      val delegatedEvent = new DelegatedEvent(
        stuff, new Date(), appropriateParty, finishedProduct)
      eventProcessor process delegatedEvent

      then("The stuff should be listed in waiting-for list")
      waitingForList.delegatedStuffs exists (_.id equals stuff.id) should be === true
    }

    scenario("Deferring action that must be done in specific time") {
      given("A stuff in in-basket")
      val stuff = new CapturedStuff(1, StateType.CAPTURED)
      val collectedEvent = new CollectedEvent(
        stuff, new Date(), new Description("description"))
      val inBasket = createInBasket
      EventPublisher register inBasket
      eventProcessor process collectedEvent

      and("A calendar")
      val calendar = createCalendar
      EventPublisher register calendar

      when("I know that it must be done in specific time")
      val schedule = new Schedule(CalendarDate.date(2010, 4, 26))
      val scheduledEvent = new ScheduledEvent(stuff, new Date(), schedule)
      eventProcessor process scheduledEvent

      then("The stuff should be contained in calendar")
      calendar.scheduledStuffs exists (_.id equals stuff.id) should be === true
    }

    scenario("Deferring action that can't be done right now") {
      given("A stuff in in-basket")
      val stuff = new CapturedStuff(1, StateType.CAPTURED)
      val collectedEvent = new CollectedEvent(
        stuff, new Date(), new Description("description"))
      val inBasket = createInBasket
      EventPublisher register inBasket
      eventProcessor process collectedEvent

      and("A next-action list")
      val nextActionList = createNextActionList
      EventPublisher register nextActionList

      when("I decided to defer it because I can't do it right now")
      val category = new Category("Some category")
      val categorizedEvent = new CategorizedEvent(stuff, new Date, category)
      eventProcessor process categorizedEvent

      then("The stuff should be listed in next-action list")
      nextActionList.categorizedStuffs exists (_.id equals stuff.id) should be === true
    }

  }
}