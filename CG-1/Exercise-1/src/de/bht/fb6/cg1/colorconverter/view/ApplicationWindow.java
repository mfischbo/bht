package de.bht.fb6.cg1.colorconverter.view;

import java.awt.Canvas;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.lwjgl.opengl.Display;

import de.bht.fb6.cg1.colorconverter.model.CMYColor;
import de.bht.fb6.cg1.colorconverter.model.HSVColor;
import de.bht.fb6.cg1.colorconverter.model.RGBColor;
import de.bht.fb6.cg1.colorconverter.model.YUVColor;

/**
 * Main application window.
 * This class represents the applications main view and contains
 * all {@link ColorSliderPanel}'s as well as a canvas that is used by
 * the LWJGL Library to draw on.
 * 
 * @author M. Fischboeck
 *
 */

public class ApplicationWindow extends JFrame implements WindowStateListener {

	private static final long serialVersionUID = 1L;

	/**
	 * The canvas to draw on
	 */
	private Canvas			  cCanvas;
	
	/**
	 * States if the window has received a close event
	 */
	private boolean			isApplicationClosed;
	
	/**
	 * The panel holding the reference values for the current color selection
	 */
	private ColorSliderPanel	reference;
	
	/**
	 * Default constructor
	 */
	public ApplicationWindow() {
		
		// create the application window
		super("Color Converter");
		this.setSize(640, 480);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
		
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		
		// create the canvas to draw on
		cCanvas = new Canvas() {

			private static final long serialVersionUID = 7937678349135627185L;

			/* called when the canvas is ready */
			public void addNotify() {
				super.addNotify();
				initLWJGL();
			}
			
			/* called when the canvas is about to be destroyed */
			public void removeNotify() {
				shutdownLWJGL();
				super.removeNotify();
			}
		};
		cCanvas.setSize(640, 400);
		cCanvas.setFocusable(true);
		cCanvas.setIgnoreRepaint(true);
		this.add(cCanvas, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		
		// create a ColorSliderPanel for RGB
		final RGBColor rgb = new RGBColor(0, 0, 0);
		final ColorSliderPanel rgbPan = new ColorSliderPanel(rgb);
		rgbPan.setCanvas(cCanvas);
		c.gridy++;
		this.add(rgbPan, c);
		this.reference = rgbPan;
		
		// create a ColorSliderPanel for YUV
		final YUVColor yuv = new YUVColor(0, 0, 0);
		final ColorSliderPanel yuvPanel = new ColorSliderPanel(yuv);
		yuvPanel.setCanvas(cCanvas);
		c.gridy++;
		this.add(yuvPanel,  c);
		
		// create a ColorSliderPanel for CMY
		final CMYColor cmy = new CMYColor(0, 0, 0);
		final ColorSliderPanel cmyPanel = new ColorSliderPanel(cmy);
		cmyPanel.setCanvas(cCanvas);
		c.gridy++;
		this.add(cmyPanel, c);
		
		// create a ColorSliderPanel for HSV
		final HSVColor hsv = new HSVColor(0, 0, 0);
		final ColorSliderPanel hsvPanel = new ColorSliderPanel(hsv);
		hsvPanel.setCanvas(cCanvas);
		c.gridy++;
		this.add(hsvPanel, c);
		
		
		this.pack();
		
		this.isApplicationClosed = false;
		this.addWindowStateListener(this);
	}	
	
	
	/**
	 * The applications main loop 
	 */
	public void mainLoop() {
		
		OpenGlView superVision = new OpenGlView();
		superVision.register(new RGBCube());
		
		while (!this.isApplicationClosed) {
				superVision.renderFrame(this.reference.getCurrentColor());	
		}
	}

	
	/**
	 * Initialize the LWJGL engine
	 */
	private void initLWJGL() {
		try {
			Display.setParent(this.cCanvas);
			Display.create();
			OpenGlView.init();
		} catch (Exception pq) {
			pq.printStackTrace();
		}
	}
	
	/**
	 * Friendly shutdown the LWJGL engine
	 */
	private void shutdownLWJGL() {
		try {
			Display.destroy();
		} catch (Exception pq) {
			pq.printStackTrace();
		}
	}


	/*
	 * (non-Javadoc)
	 * @see java.awt.event.WindowStateListener#windowStateChanged(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowStateChanged(WindowEvent e) {
		if (e.getNewState() == WindowEvent.WINDOW_CLOSING || e.getNewState() == WindowEvent.WINDOW_CLOSED) {
			this.isApplicationClosed = true;
		}
	}
}
