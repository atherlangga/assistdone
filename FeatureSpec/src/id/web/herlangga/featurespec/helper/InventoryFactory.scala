package id.web.herlangga.featurespec

import id.web.herlangga.gtd.definition.stuff.event.party.EventSubscriber
import id.web.herlangga.gtd.definition.stuff.Stuff
import id.web.herlangga.gtd.definition.stuff.event._
import id.web.herlangga.gtd.module.inventory._
import id.web.herlangga.gtd.module.inventory.item._
import com.domainlanguage.time.CalendarDate
import id.web.herlangga.gtd.definition.stuff.attribute._

/**
 * @author at.herlangga
 */

trait InventoryFactory {
  trait GenericRepository[S <: Stuff] {
    private var buffer = List[S]()

    protected def stuffs(implicit m: ClassManifest[S]): Array[S] = {
      buffer.toArray
    }

    def add(stuff: S) = {
      buffer = stuff :: buffer
    }

    def remove(stuffId: Long) = {
      buffer = buffer remove (stuff => stuff.id equals stuffId)
    }
  }
  
  def createInBasket = {
    new InBasket with EventSubscriber with GenericRepository[CollectedStuff] {

      def collectedStuffs = {
        stuffs
      }

      def receive(event: Event) = {
        event match {
          case e: CollectedEvent => {
            add(new CollectedStuff(event.source.id, event.source.description, e.timeStamp))
          }
          case _ => {
            remove(event.source.id)
          }
        }
      }

    }
  }

  def createTrashCan = {
    new TrashCan with EventSubscriber with GenericRepository[TrashedStuff] {
      def trashedStuffs = {
        stuffs
      }
      
      def receive(event: Event) = {
        event match {
          case e: TrashedEvent => {
            add(new TrashedStuff(event.source.id, event.source.description))
          }
          case _ => {
            remove(event.source.id)
          }
        }
      }

    }
  }

  def createTicklerFile = {
    new TicklerFile with EventSubscriber with GenericRepository[HeldStuff] {
      def heldStuffs = {
        stuffs
      }

      def receive(event: Event) = {
        event match {
          case e: HeldEvent => {
            add(new HeldStuff(event.source.id, event.source.description))
          }
          case _ => {
            remove(event.source.id)
          }
        }
      }
    }
  }

  def createDoneLog = {
    new DoneLog with EventSubscriber with GenericRepository[DoneStuff] {
      def doneStuffs = {
        stuffs
      }

      def receive(event: Event) = {
        event match {
          case e: DoneEvent => {
            add(new DoneStuff(event.source.id, event.source.description))
          }
          case _ => {
            remove(event.source.id)
          }
        }
      }
    }

  }

  def createWaitingForList = {
    new WaitingForList with EventSubscriber with GenericRepository[DelegatedStuff] {
      def delegatedStuffs = {
        stuffs
      }

      def delegatedStuffsFor(appropriateParty: DelegatedParty) = {
        delegatedStuffs filter (ds => ds.appropriateParty == appropriateParty)
      }

      def receive(event: Event) = {
        event match {
          case e: DelegatedEvent => {
            add(new DelegatedStuff(event.source.id, event.source.description, e.appropriateParty, e.expectedProduct))
          }
          case _ => {
            remove(event.source.id)
          }
        }
      }
    }
  }

  def createCalendar = {
    new Calendar with EventSubscriber with GenericRepository[ScheduledStuff] {
      def scheduledStuffs = {
        stuffs
      }

      def scheduledStuffsFor(date: CalendarDate) = {
        stuffs filter (ss => ss.schedule isActiveAt date)
      }

      def receive(event: Event) = {
        event match {
          case e: ScheduledEvent => {
            add(new ScheduledStuff(event.source.id, event.source.description, e.schedule))
          }
          case _ => {
            remove(event.source.id)
          }
        }
      }
    }
  }

  def createNextActionList = {
    new NextActionList with EventSubscriber with GenericRepository[CategorizedStuff] {
      def categorizedStuffs = {
        stuffs
      }

      def categorizedStuffsOf(category: Category) = {
        stuffs filter (_.category equals category)
      }

      def receive(event: Event) = {
        event match {
          case e: CategorizedEvent => {
            add(new CategorizedStuff(event.source.id, event.source.description, e.category))
          }
          case _ => {
            remove(event.source.id)
          }
        }
      }

    }
  }

}