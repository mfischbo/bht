package de.bht.fb6.cg1.imagetweak.ui.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import de.bht.fb6.cg1.imagetweak.model.CMYRemovals;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import de.bht.fb6.cg1.imagetweak.service.Observer;

/**
 * Dialog to be displayed for the {@link de.bht.fb6.cg1.imagetweak.plugins.RGBComponentRemover}.
 * All changes made in the dialog are reflected to {@link de.bht.fb6.cg1.imagetweak.model.ImageModel}.
 * After user confirmation the observer executes the changes that must be made to the
 * underlying image.
 * @author M. Fischboeck
 *
 */

public class CMYRemoverDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -5338957279589322836L;
	
	private static final String OK_BTN_LABEL = "OK";
	private static final String CANCEL_BTN_LABEL = "Cancel";
	
	private final JPanel contentPanel = new JPanel();

	private final JCheckBox	chckbxC;
	private final JCheckBox	chckbxM;
	private final JCheckBox	chckbxY;
	
	private final CMYRemovals	removals;
	
	private final Observer	observer;
	
	/**
	 * Creates the dialog with with a model and observer to take actions.
	 * @param model The underlying model reflected by the dialog
	 * @param observer The observer executing changes in the model
	 */
	public CMYRemoverDialog(final ImageModel model, final Observer observer) {
		
		this.removals = model.getCMYRemovals();
		this.observer = observer;
		
		setTitle("CMY Channel Remover");
		setBounds(100, 100, 450, 212);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		chckbxC = new JCheckBox("Cyan");
		chckbxC.setBounds(8, 54, 129, 23);
		contentPanel.add(chckbxC);
		chckbxC.setEnabled(!removals.isCyan());
		chckbxC.setSelected(removals.isCyan());
		
		chckbxM = new JCheckBox("Magenta");
		chckbxM.setBounds(8, 77, 129, 23);
		contentPanel.add(chckbxM);
		chckbxM.setEnabled(!removals.isMagenta());
		chckbxM.setSelected(removals.isMagenta());
		
		chckbxY = new JCheckBox("Yellow");
		chckbxY.setBounds(8, 99, 129, 23);
		contentPanel.add(chckbxY);
		chckbxY.setEnabled(!removals.isYellow());
		chckbxY.setSelected(removals.isYellow());
		
		JLabel lblRemovesTheSelected = new JLabel("Removes the selected color channels from the image");
		lblRemovesTheSelected.setBounds(8, 12, 428, 34);
		contentPanel.add(lblRemovesTheSelected);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(OK_BTN_LABEL);
				okButton.setActionCommand(OK_BTN_LABEL);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
			}
			{
				JButton cancelButton = new JButton(CANCEL_BTN_LABEL);
				cancelButton.setActionCommand(CANCEL_BTN_LABEL);
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// handle clicks on the cancel button
		if (arg0.getActionCommand().equals(CANCEL_BTN_LABEL)) {
			this.dispose();
			return;
		}
		
		// handle clicks on the ok button
		if (arg0.getActionCommand().equals(OK_BTN_LABEL)) {
			removals.setCyan(chckbxC.isSelected() );
			removals.setMagenta(chckbxM.isSelected());
			removals.setYellow(chckbxY.isSelected());
			
			observer.executeChanges();
			this.dispose();
		}
	}
}