package de.bht.fb6.cg1.imagetweak.command;

import de.bht.fb6.cg1.imagetweak.model.ImageModel;


/**
 * Interface to define a command executed by the observer
 * @author M. Fischboeck
 *
 */
public interface ICommand {
	
	/**
	 * Interface method to execute the command
	 * @param model The image model the command is executed on
	 */
	public void execute(final ImageModel model);
}
