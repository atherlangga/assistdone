package id.web.herlangga.assistdone.jme.ui.repository;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.module.inventory.item.*;

/**
 * View intended to display Calendar and Scheduled Stuff representation.
 * @author at.herlangga
 *
 */
public interface CalendarView extends View {
	
	/**
	 * Get selected ScheduledStuff.
	 * @return user-selected ScheduledStuff.
	 */
	ScheduledStuff selectedScheduledStuff();
	
	/**
	 * Attach Presenter to this View.
	 * @param presenter CalendarPresenter to attach.
	 */
	void attachPresenter(CalendarPresenter presenter);
}
