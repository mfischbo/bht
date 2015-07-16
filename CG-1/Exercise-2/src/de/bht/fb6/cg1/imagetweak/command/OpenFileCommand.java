package de.bht.fb6.cg1.imagetweak.command;

import ij.IJ;
import ij.ImagePlus;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;


/**
 * Command class that opens a new file
 * @author M. Fischboeck
 *
 */
public class OpenFileCommand implements ICommand {

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.imagetweak.command.ICommand#execute(de.bht.fb6.cg1.imagetweak.model.ImageModel)
	 */
	public void execute(final ImageModel model) {
		IJ.open();
		ImagePlus img = IJ.getImage();
		img = img.flatten();
		model.setImage(img);
	}
}
