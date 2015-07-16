package de.bht.fb6.cg1.imagetweak.service;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import de.bht.fb6.cg1.imagetweak.command.ICommand;
import de.bht.fb6.cg1.imagetweak.command.OpenFileCommand;
import de.bht.fb6.cg1.imagetweak.model.DimensionModel;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import de.bht.fb6.cg1.imagetweak.plugins.BilinearResizer2;
import de.bht.fb6.cg1.imagetweak.plugins.BlurPlugin;
import de.bht.fb6.cg1.imagetweak.plugins.BrightnessPlugin;
import de.bht.fb6.cg1.imagetweak.plugins.CMYComponentRemover;
import de.bht.fb6.cg1.imagetweak.plugins.NearestNeighborResizer;
import de.bht.fb6.cg1.imagetweak.plugins.RGBComponentRemover;
import de.bht.fb6.cg1.imagetweak.plugins.YUVComponentRemover;

import de.bht.fb6.cg1.imagetweak.ui.ImageTweakUI;
import de.bht.fb6.cg1.imagetweak.ui.Dialog.BCDialog;
import de.bht.fb6.cg1.imagetweak.ui.Dialog.CMYRemoverDialog;
import de.bht.fb6.cg1.imagetweak.ui.Dialog.HistogramDialog;
import de.bht.fb6.cg1.imagetweak.ui.Dialog.RGBRemoverDialog;
import de.bht.fb6.cg1.imagetweak.ui.Dialog.ResizeDialog;
import de.bht.fb6.cg1.imagetweak.ui.Dialog.YUVRemoverDialog;

/**
 * Management class that handles click actions and executes plugins on the image
 * @author M. Fischboeck
 *
 */

public class Observer implements ActionListener {

	private ImageModel		model;

	private ImageTweakUI	userInterface;
	
	/**
	 * Default constructor
	 */
	public Observer() {
		this.model = new ImageModel();
	}
	
	/**
	 * Constructor specifying the userinterface to apply changes to
	 * @param userInterface The userinterface to apply changes to
	 */
	public Observer(ImageTweakUI userInterface) {
		this.userInterface = userInterface;
	}
	
	/**
	 * Returns the drawing canvas of the underlying image model
	 * @return The canvas the image get's drawn to
	 */
	public Canvas getCanvas() {
		return this.model.getImage().getCanvas();
	}
	
	/**
	 * Sets the user interface to apply changes to
	 * @param userInterface The userinterface to apply changes to
	 */
	public void setUserInterface(ImageTweakUI userInterface) {
		this.userInterface = userInterface;
	}
	
	/**
	 * Executes all changes based on the underlying model
	 */
	public void executeChanges() {
		if (model.getRGBRemovals().isChanged()) {
			RGBComponentRemover remover = new RGBComponentRemover(this.model);
			remover.run("");
			model.getRGBRemovals().setChanged(false);
		}
		
		if (model.getCMYRemovals().isChanged()) {
			CMYComponentRemover remover = new CMYComponentRemover(this.model);
			remover.run("");
			model.getCMYRemovals().setChanged(false);
		}
		
		if (model.getYUVRemovals().isChanged()) {
			YUVComponentRemover remover = new YUVComponentRemover(this.model);
			remover.run("");
			model.getYUVRemovals().setChanged(false);
		}
		
		if (model.getBrightnessAndContrast().isBrightnessChanged()) {
			BrightnessPlugin plugin = new BrightnessPlugin(this.model);
			plugin.run("");
			model.getBrightnessAndContrast().setBrightnessChanged(false);
		}
		
		if (model.getDimensions().isChanged()) {
			if (model.getDimensions().getAlgorithm() == DimensionModel.RESIZE_NN) {
				NearestNeighborResizer plugin = new NearestNeighborResizer(this.model);
				plugin.run("");
			} 
			
			if (model.getDimensions().getAlgorithm() == DimensionModel.RESIZE_BILINEAR) {
				BilinearResizer2 plugin = new BilinearResizer2(this.model);
				plugin.run("");
			}
		}
			
		
		this.userInterface.updateSurface(model);
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o = arg0.getSource();
		
		if (o instanceof JMenuItem) {
			JMenuItem item = (JMenuItem) o;
			System.out.println("Registered action on item ["+item.getText()+"]");
			
			if (item.getText().equals("Open...")) {
				ICommand cmd = new OpenFileCommand();
				cmd.execute(model);
				this.userInterface.updateSurface(this.model);
			}
			
			if (item.getActionCommand().equals("Close")) {
				this.userInterface.closeApplication();
			}
			
			if (item.getText().equals("RGB Remover")) {
				RGBRemoverDialog dialog = new RGBRemoverDialog(model, this);
				dialog.setVisible(true);
			}
			
			if (item.getActionCommand().equals("CMY Remover")) {
				CMYRemoverDialog dialog = new CMYRemoverDialog(model, this);
				dialog.setVisible(true);
			}
			
			if (item.getActionCommand().equals("YUV Remover")) {
				YUVRemoverDialog dialog = new YUVRemoverDialog(model, this);
				dialog.setVisible(true);
			}
			
			if (item.getActionCommand().equals("BrightnessAndContrast")) {
				BCDialog dialog = new BCDialog(model, this);
				dialog.setVisible(true);
			}
			
			if (item.getActionCommand().equals("Resize")) {
				ResizeDialog dialog = new ResizeDialog(model, this);
				dialog.setVisible(true);
			}
			
			if (item.getActionCommand().equals("Histogram")) {
				HistogramDialog dialog = new HistogramDialog(model, this);
				dialog.setVisible(true);
			}
			
			if (item.getActionCommand().equals("applyBlur")) {
				BlurPlugin plugin = new BlurPlugin(model);
				plugin.run("");
				this.userInterface.updateSurface(model);
			}
		}
		
		
		if (o instanceof JButton) {
			JButton btn = (JButton) o;
			System.out.println("Registered button press on parent: " + btn.getParent().getClass().getSimpleName());
		}
		
	}
	
	/**
	 * Updates the drawing surface with the new image stored in the model
	 */
	public void updateSurface() {
		this.userInterface.updateSurface(this.model);
	}
}
