package id.web.herlangga.featurespec.helper

import id.web.herlangga.gtd.definition.stuff.event.party.EventLog
import id.web.herlangga.gtd.definition.stuff.event.Event

/**
 * @author at.herlangga
 */

object EventLogDummy extends EventLog {
  def append(event: Event) = {}
  def load(id: Long) = {
    scala.Array()
  }
}