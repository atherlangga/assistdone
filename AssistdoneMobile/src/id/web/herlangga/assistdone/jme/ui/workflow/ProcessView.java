package id.web.herlangga.assistdone.jme.ui.workflow;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.module.inventory.item.*;

/**
 * User interface for processing CollectedStuff.
 * 
 * @author at.herlangga
 */
public interface ProcessView extends View {
	
	/**
	 * Add CollectedStuffs to the View.
	 * @param collectedStuffs array of CollectedStuff to add. 
	 */
	void addCollectedStuffs(CollectedStuff[] collectedStuffs);
	
	/**
	 * Clear content of the View.
	 */
	void clearCollectedStuffs();
	
	/**
	 * Attach presenter to manage the View.
	 * @param presenter ProcessPresenter to manage the View. 
	 */
	void attachPresenter(ProcessPresenter presenter);
	
	/**
	 * Get user-selected CollectedStuff.
	 * @return selected CollectedStuff.
	 */
	CollectedStuff selectedCollectedStuff();
}
