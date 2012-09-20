package id.web.herlangga.featurespec

import com.domainlanguage.time.CalendarDate
import id.web.herlangga.gtd.definition.stuff.Description
import id.web.herlangga.gtd.definition.stuff.attribute.{DelegatedParty, DeliverableProduct, Category, Schedule}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{GivenWhenThen, FeatureSpec}
import id.web.herlangga.gtd.module.inventory.item.{HeldStuff, DelegatedStuff, CategorizedStuff, ScheduledStuff}

class ReviewPhaseFeatureSpec
        extends FeatureSpec
        with GivenWhenThen
        with ShouldMatchers
        with InventoryFactory {

  feature("GTD User reviews stuff") {

    info("As a GTD User")
    info("I want to review my inventories periodically")
    info("So that I can keep my stuffs under control")

    scenario("Daily calendar review") {
      given("Some date")
      val someDate = CalendarDate.date(2010, 5, 16)

      and("A calendar")
      val calendar = createCalendar

      and("I have entered tasks for today")
      val description = new Description("Today's schedule")
      val schedule = new Schedule(someDate)
      val scheduledStuff = new ScheduledStuff(1, description, schedule)
      calendar add scheduledStuff

      when("I want to review today's stuffs")
      val stuffsForToday = calendar scheduledStuffsFor someDate

      then("Calendar should show me the list of stuffs I have to do")
      stuffsForToday exists (ss => ss.schedule equals schedule) should be === true
    }

    scenario("Daily next-action list review") {
      given("My current context")
      val context = new Category("At office")

      and("A next-action list")
      val nextActionList = createNextActionList

      and("I have entered deferred action")
      val description = new Description("Something to be done at the office")
      val categorizedStuff = new CategorizedStuff(1, description, context)
      nextActionList add categorizedStuff

      when("I want to review my stuffs for current context")
      val stuffsForContext = nextActionList categorizedStuffsOf context

      then("Next-action list should show them")
      stuffsForContext exists (cs => cs.category equals context) should be === true
    }

    scenario("Weekly waiting list review") {
      given("A waiting-list")
      val waitingForList = createWaitingForList

      and("I have entered delegated stuff")
      val description = new Description("Delegated stuff")
      val party = new DelegatedParty("Someone unfortunate", "someone@somewhere")
      val product = new DeliverableProduct("Something I need")
      val delegatedStuff = new DelegatedStuff(1, description, party, product)
      waitingForList add delegatedStuff

      when("I want to review my delegated stuffs")
      val stuffsForSomeParty = waitingForList delegatedStuffsFor party

      then("The waiting list should list them")
      stuffsForSomeParty exists (ds => ds.appropriateParty equals party) should be === true
    }

    scenario("Weekly-reviewing someday/maybe list, then decided to delete it") {
      given("A tickler file")
      val ticklerFile = createTicklerFile

      and("A pending stuff")
      val heldStuff = new HeldStuff(1, new Description("Held Stuff"))

      when("I'm not interested in it anymore")
      ticklerFile remove heldStuff.id

      then("The tickler file should not contains the stuff anymore")
      ticklerFile.heldStuffs exists (_.id equals heldStuff.id) should be === false
    }
  }
}