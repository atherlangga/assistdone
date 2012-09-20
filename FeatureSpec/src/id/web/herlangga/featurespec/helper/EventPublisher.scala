package id.web.herlangga.featurespec.helper

import id.web.herlangga.gtd.definition.stuff.event.party.EventSubscriber
import id.web.herlangga.gtd.definition.stuff.event.Event

/**
 * @author at.herlangga
 */

object EventPublisher extends EventSubscriber {
  private var subscribers = List[EventSubscriber]()

  def register(newSubscriber: EventSubscriber) = {
    subscribers = newSubscriber :: subscribers
  }

  def resetSubscribers() = {
    subscribers = List[EventSubscriber]()
  }

  def receive(event: Event) = {
    subscribers foreach (subscriber => subscriber receive event)
  }
}