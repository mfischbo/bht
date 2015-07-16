package de.bht.fb6.cg1.imagetweak.ui;

import ij.gui.ImageCanvas;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import de.bht.fb6.cg1.imagetweak.service.Observer;

import java.awt.Canvas;

/**
 * Main application entry point class
 * @author M. Fischboeck
 *
 */
public class ImageTweakUI {

	private JFrame frmImageTweakUi;

	private Observer observer;
	
	private static final int CANVAS_WIDTH = 990;
	private static final int CANVAS_HEIGHT= 680;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageTweakUI window = new ImageTweakUI();
					window.frmImageTweakUi.setVisible(true);
					window.frmImageTweakUi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ImageTweakUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.observer = new Observer();
		this.observer.setUserInterface(this);
		
		frmImageTweakUi = new JFrame();
		frmImageTweakUi.setTitle("Image Tweak UI");
		frmImageTweakUi.setBounds(100, 100, 1024, 768);
		frmImageTweakUi.getContentPane().setLayout(null);
		frmImageTweakUi.getContentPane().setSize(300, 650);
		
		
		Canvas canvas = new Canvas();
		canvas.setBounds(10, 10, CANVAS_WIDTH, CANVAS_HEIGHT);
		frmImageTweakUi.getContentPane().add(canvas);
		
		
		JMenuBar menuBar = new JMenuBar();
		frmImageTweakUi.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(this.observer);
		mnFile.add(mntmOpen);
		
		/*
		JMenuItem mntmSaveAs = new JMenuItem("Save as ...");
		mntmSaveAs.addActionListener(this.observer);
		mnFile.add(mntmSaveAs);
		*/
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(this.observer);
		mnFile.add(mntmClose);
		
		JMenu mnPlugins = new JMenu("Plugins");
		menuBar.add(mnPlugins);
		
		JMenuItem mntmRgbRemover = new JMenuItem("RGB Remover");
		mnPlugins.add(mntmRgbRemover);
		mntmRgbRemover.addActionListener(observer);
		
		JMenuItem mntmCmyremover = new JMenuItem("CMY Remover");
		mnPlugins.add(mntmCmyremover);
		mntmCmyremover.addActionListener(observer);
		
		JMenuItem mntmYuvRemover = new JMenuItem("YUV Remover");
		mnPlugins.add(mntmYuvRemover);
		mntmYuvRemover.addActionListener(observer);
		
		JMenuItem mntmBrightnessContrast = new JMenuItem("Brightness & Contrast");
		mntmBrightnessContrast.setActionCommand("BrightnessAndContrast");
		mnPlugins.add(mntmBrightnessContrast);
		mntmBrightnessContrast.addActionListener(observer);
		
		JMenuItem mntmResize = new JMenuItem("Resize");
		mnPlugins.add(mntmResize);
		mntmResize.addActionListener(observer);
		
		JMenuItem mntmHistogram = new JMenuItem("Histogram");
		mnPlugins.add(mntmHistogram);
		mntmHistogram.addActionListener(observer);
		
		JMenuItem mntmBlur = new JMenuItem("Blur");
		mntmBlur.setActionCommand("applyBlur");
		mnPlugins.add(mntmBlur);
		mntmBlur.addActionListener(observer);
	}
	
	/**
	 * Updates the drawing surface with the underlying image
	 * @param model The model holding the image to be drawn
	 */
	public void updateSurface(final ImageModel model) {
		Component[] cmt = frmImageTweakUi.getContentPane().getComponents();
		for (final Component c : cmt) {
			if (c instanceof Canvas) {
				frmImageTweakUi.getContentPane().remove(c);
				ImageCanvas cnv = new ImageCanvas(model.getImage());
				cnv.setBounds(10, 10, CANVAS_WIDTH, CANVAS_HEIGHT);
				frmImageTweakUi.getContentPane().add(cnv);
			}
		}
	}
	
	/**
	 * Quits the application
	 */
	public void closeApplication() {
		frmImageTweakUi.dispose();
		System.exit(0);
	}
}
