package id.web.herlangga.assistdone.jme.ui.workflow;

import id.web.herlangga.assistdone.jme.ui.*;

/**
 * User interface to help review various Repositories.
 * @author at.herlangga
 */
public interface ReviewView extends View {
	
	/**
	 * Attach Presenter for this View.
	 * @param presenter ReviewPresenter to attach.
	 */
	void attachPresenter(ReviewPresenter presenter);
}
