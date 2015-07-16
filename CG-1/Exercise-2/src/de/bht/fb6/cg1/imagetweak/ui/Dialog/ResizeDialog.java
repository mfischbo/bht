package de.bht.fb6.cg1.imagetweak.ui.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import de.bht.fb6.cg1.imagetweak.model.DimensionModel;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import de.bht.fb6.cg1.imagetweak.service.Observer;
import javax.swing.ButtonGroup;
import javax.swing.SpinnerNumberModel;

/**
 * Dialog to be displayed when the user resizes the image.
 * The dialog has internal workings to calculate the correct size when
 * the keep aspect ration checkbox is enabled
 * 
 * @author M. Fischboeck
 *
 */
public class ResizeDialog extends JDialog implements ActionListener, ChangeListener {


	private static final long serialVersionUID = -2256624093309760444L;
	
	private final JPanel contentPanel = new JPanel();

	private ImageModel model;
	private Observer observer;
	
	
	private final JSpinner	w_spinner;
	private final JSpinner  h_spinner;
	private final JCheckBox	keepAspect;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JRadioButton rdbtnBilinear;
	private final JRadioButton rdbtnNN;
	
	
	/**
	 * Constructor specifying the model to work on an the observer to notify
	 * @param model The model to work on
	 * @param observer The observer to notify on changes
	 */
	public ResizeDialog(final ImageModel model, final Observer observer) {
		
		this.model = model;
		this.observer = observer;
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblResizeTheImage = new JLabel("Resize the Image");
		lblResizeTheImage.setBounds(12, 12, 424, 15);
		contentPanel.add(lblResizeTheImage);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(12, 68, 70, 15);
		contentPanel.add(lblWidth);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(12, 95, 70, 15);
		contentPanel.add(lblHeight);
		
		this.w_spinner = new JSpinner();
		w_spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		this.w_spinner.setBounds(85, 66, 65, 20);
		this.w_spinner.setValue(this.model.getImage().getWidth());
		this.w_spinner.addChangeListener(this);
		contentPanel.add(this.w_spinner);
		
		this.h_spinner = new JSpinner();
		h_spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		this.h_spinner.setBounds(85, 93, 65, 20);
		this.h_spinner.setValue(this.model.getImage().getHeight());
		this.h_spinner.addChangeListener(this);
		contentPanel.add(this.h_spinner);
		
		this.keepAspect = new JCheckBox("Keep Aspect");
		this.keepAspect.setBounds(158, 79, 129, 23);
		this.keepAspect.setSelected(true);
		contentPanel.add(this.keepAspect);
		
		rdbtnBilinear = new JRadioButton("Bilinear");
		rdbtnBilinear.setSelected(true);
		buttonGroup.add(rdbtnBilinear);
		rdbtnBilinear.setBounds(12, 134, 149, 23);
		contentPanel.add(rdbtnBilinear);
		
		rdbtnNN = new JRadioButton("Nearest Neighbor");
		buttonGroup.add(rdbtnNN);
		rdbtnNN.setBounds(12, 161, 200, 23);
		contentPanel.add(rdbtnNN);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			
			if (button.getActionCommand().equals("Cancel")) {
				this.dispose();
				return;
			}
			
			if (button.getActionCommand().equals("OK")) {
				int w = (Integer) this.w_spinner.getValue();
				int h = (Integer) this.h_spinner.getValue();
				
				this.model.getDimensions().setWidth(w);
				this.model.getDimensions().setHeight(h);
				
				if (this.rdbtnBilinear.isSelected())
					this.model.getDimensions().setAlgorithm(DimensionModel.RESIZE_BILINEAR);
				else
					this.model.getDimensions().setAlgorithm(DimensionModel.RESIZE_NN);
				
				this.observer.executeChanges();
				this.dispose();
				return;
			}
		}
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		
		boolean aspect = this.keepAspect.isSelected();
		if (!aspect)
			return;
		
		Object source = e.getSource();
		DimensionModel dm = this.model.getDimensions();
		
		if (source.equals(this.w_spinner)) {
			
			int w = (Integer) ((JSpinner) source).getValue();
			int h = dm.getHeight();
	
			float ch = (w * 100.f) / dm.getWidth();
			h = (int) (h * (ch / 100));
			
			this.h_spinner.removeChangeListener(this);
			this.h_spinner.setValue(h);
			this.h_spinner.addChangeListener(this);			
		}
		
		
		
		if (source.equals(this.h_spinner)) {
			
			int w = dm.getWidth();
			int h = (Integer) (this.h_spinner).getValue();
			
			float cw = (h * 100.f) / dm.getHeight();
			w = (int) (w * (cw / 100));
			
			this.w_spinner.removeChangeListener(this);
			this.w_spinner.setValue(w);
			this.w_spinner.addChangeListener(this);
		}
	}
}
