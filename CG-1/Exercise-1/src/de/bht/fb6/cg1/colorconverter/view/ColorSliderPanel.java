package de.bht.fb6.cg1.colorconverter.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import de.bht.fb6.cg1.colorconverter.Observer;
import de.bht.fb6.cg1.colorconverter.model.IColor;

/**
 * Class that represents a widget for a generic color selector.
 * The widget contains labels, sliders and textfields for the 
 * represented color. The amount of sliders depends on the 
 * color's amount of parameters.
 * 
 * @author M. Fischboeck
 *
 */
public class ColorSliderPanel extends JPanel {

	private static final long serialVersionUID = -2500232292374378962L;
	
	/** Reference to the Canvas to draw to */
	private Canvas	surface;
	
	/** The observer observing all registered instances of ColorSlidePanel */
	private Observer	observer;
	
	/** The underlying color model */
	private IColor	color;
	
	/** References to all sliders */
	private JSlider[] 	sliders;
	
	/** References to all text fields */
	private JTextField[] textFields;
	
	/**
	 * Constructs a new ColorSliderPanel for the specified {@link de.bht.fb6.cg1.colorconverter.model.IColor}	
	 * @param color The color to be represented by the panel
	 */
	public ColorSliderPanel(final IColor color) {
	
		super();
		
		final int[] components = color.getParameters();
	
		this.observer = new Observer();
		this.color = color;
		
		sliders = new JSlider[color.getParameters().length];
		textFields = new JTextField[color.getParameters().length];
		
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new GridLayout(components.length, components.length));
		this.setSize(200, 50);
			
		for (int i=0; i < components.length; i++) {
			
			// Set the labels
			final char id = color.getIdentifiers()[i];
			final JLabel label = new JLabel(("" + id).toUpperCase());
			this.add(label);
			
			// Set the slider
			final JSlider slider = new JSlider(0, 255, components[i]);
			slider.addChangeListener(this.observer);
			this.add(slider);
			this.sliders[i] = slider;
			
			
			// Set the text field
			final JTextField field = new JTextField();
			final String value = Integer.toString(components[i]);
			field.setText(value);
			field.setEnabled(false);
			this.add(field);
			this.textFields[i] = field;
		}
		
		Observer.register(this);
	}
	
	
	/**
	 * Sets the canvas surface to draw on
	 * @param canvas
	 */
	public void setCanvas(final Canvas canvas) {
		this.surface = canvas;
	}
	
	
	/**
	 * Updates the panels state to represent the specified color
	 * @param newColor The new color to be displayed by the panel
	 */
	public void update(final IColor newColor) {
				
		if (newColor.equals(this.color)) {
			
			final int[] params = new int[this.sliders.length];
			for (int i=0; i < this.sliders.length; i++) {
				params[i] = (this.sliders[i].getValue());
				
				// update the text label
				this.textFields[i].setText("" + this.sliders[i].getValue());
			}
			
			this.color.setParameters(params);
			final int[] rgb = this.color.toRGB();
			this.surface.setBackground(new Color(rgb[0], rgb[1], rgb[2]));
			
		} else {
			
			this.color.fromRGB(newColor.toRGB());
			final int[] params = this.color.getParameters();
			
			for (int i=0; i < params.length; i++) {
				this.sliders[i].removeChangeListener(this.observer);
				this.sliders[i].setValue(params[i]);
				this.textFields[i].setText("" + params[i]);
				this.sliders[i].addChangeListener(this.observer);
			}
		}
	}
	
	/**
	 * Returns the current color that is represented by the panel
	 * @return The color that is represented by the panel
	 */
	public IColor getCurrentColor() {
		return this.color;
	}
}
