package de.bht.fb6.cg1.colorconverter;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.bht.fb6.cg1.colorconverter.view.ColorSliderPanel;

/**
 * This class cascades {@link javax.swing.event.ChangeListener} and redirects events to registered instances.
 * 
 * @author M. Fischboeck
 *
 */
public class Observer implements ChangeListener {

	/* List, containing all registered ColorSliderPanels */
	private static ArrayList<ColorSliderPanel> panels = 
			new ArrayList<ColorSliderPanel>();
	
	
	/**
	 * Registers a {@link de.bht.fb6.cg1.colorconverter.view.ColorSliderPanel}
	 * A registered panel is being notified upon all changes from other panel's sliders.
	 * @param panel The panel to register with the observer
	 */
	public static void register(final ColorSliderPanel panel) {
		if (!panels.contains(panel))
			panels.add(panel);
	}
	
	
	/**
	 * Unregisters a ColorSliderPanel from being observed
	 * @param panel The panel to unregister
	 */
	public static void unregister(final ColorSliderPanel panel) {
		panels.remove(panel);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Component com = (Component) e.getSource();
		ColorSliderPanel panel = (ColorSliderPanel) com.getParent();
				
		for (final ColorSliderPanel p : panels) {
			p.update(panel.getCurrentColor());			
		}
	}
}
