package id.web.herlangga.assistdone.jme.ui.workflow;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.assistdone.jme.ui.repository.*;

public class ReviewPresenter implements Presenter {
	private final ReviewView view;
	private final CalendarPresenter calendarPresenter;
	private final NextActionListPresenter nextActionListPresenter;
	private final WaitingForListPresenter waitingForListPresenter;
	private final TicklerFilePresenter ticklerFilePresenter;

	public ReviewPresenter(ReviewView view, CalendarPresenter calendarPresenter,
			NextActionListPresenter nextActionListPresenter,
			WaitingForListPresenter waitingForListPresenter,
			TicklerFilePresenter ticklerFilePresenter) {
		this.view = view;
		this.calendarPresenter = calendarPresenter;
		this.nextActionListPresenter = nextActionListPresenter;
		this.waitingForListPresenter = waitingForListPresenter;
		this.ticklerFilePresenter = ticklerFilePresenter;
	}

	public void present() {
		view.attachPresenter(this);
		view.show();
	}

	public void onCalendarSelected() {
		calendarPresenter.present();
	}

	public void onNextActionListSelected() {
		nextActionListPresenter.present();
	}

	public void onWaitingForListSelected() {
		waitingForListPresenter.present();
	}

	public void onTicklerFileSelected() {
		ticklerFilePresenter.present();
	}

}
