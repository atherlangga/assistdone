package id.web.herlangga.assistdone.jme.ui.repository;

import id.web.herlangga.assistdone.jme.ui.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public interface TicklerFileView extends View {
	
	/**
	 * Add HeldStuffs to shown in this View.
	 * @param heldStuffs
	 */
	void addHeldStuffs(HeldStuff[] heldStuffs);
	
	/**
	 * Get selected HeldStuff.
	 * @return user-selected HeldStuff.
	 */
	HeldStuff selectedHeldStuff();
	
	/**
	 * Clear all HeldStuffs shown in this View.
	 */
	void clearHeldStuffs();
	
	/**
	 * Attach Presenter to this View.
	 * @param presenter TicklerFilePresenter for this View.
	 */
	void attachPresenter(TicklerFilePresenter presenter);
}
