package id.web.herlangga.assistdone.jme.ui.repository;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public interface WaitingForListView extends View {
	
	/**
	 * Add DelegatedStuffs to this View.
	 * @param delegatedStuffs array of DelegatedStuff to show.
	 */
	void addDelegatedStuffs(DelegatedStuff[] delegatedStuffs);
	
	/**
	 * Get DelegatedStuff selected by user.
	 * @return user-selected DelegatedStuff.
	 */
	DelegatedStuff selectedDelegatedStuff();
	
	/**
	 * Clear DelegatedStuffs shown in this View.
	 */
	void clearDelegatedStuffs();
	
	/**
	 * Attach presenter for this View.
	 * @param presenter WaitingForListPresenter to attach.
	 */
	void attachPresenter(WaitingForListPresenter presenter);
}
