package id.web.herlangga.assistdone.jme.ui.workflow;

import id.web.herlangga.assistdone.jme.ui.*;


/**
 * User interface for collecting stuff.
 * 
 * @author angga
 * 
 */
public interface CollectView extends View {
	
	/**
	 * Attach {@link CollectPresenter} to this {@link CollectView} to be
	 * forwarded with user requests.
	 * 
	 * @param presenter
	 *            {@link CollectPresenter} to receive user requests.
	 */
	void attachPresenter(CollectPresenter presenter);
}
