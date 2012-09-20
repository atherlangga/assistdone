package id.web.herlangga.assistdone.jme.ui.repository;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.module.inventory.item.*;

/**
 * View intended to represent Next Action List and Categorized Stuff. 
 * @author at.herlangga
 */
public interface NextActionListView extends View {
	
	/**
	 * Get CategorizedStuff selected by user.
	 * @return selected CategorizedStuff.
	 */
	CategorizedStuff selectedCategorizedStuff();
	
	/**
	 * Add CategorizedStuffs to the View.
	 * @param stuffs CategorizedStuffs to add.
	 */
	void addCategorizedStuffs(CategorizedStuff[] stuffs);
	
	/**
	 * Remove all CategorizedStuff displayed on the screen.
	 */
	void clearCategorizedstuffs();
	
	/**
	 * Attach presenter to this View.
	 * @param presenter NextActionListPresenter to attach.
	 */
	void attachPresenter(NextActionListPresenter presenter);
}
