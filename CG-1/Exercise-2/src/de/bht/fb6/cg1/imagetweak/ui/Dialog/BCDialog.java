package de.bht.fb6.cg1.imagetweak.ui.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import javax.swing.JLabel;

import de.bht.fb6.cg1.imagetweak.model.BCModel;
import de.bht.fb6.cg1.imagetweak.model.BCModel.Channel;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import de.bht.fb6.cg1.imagetweak.service.Observer;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


/**
 * Dialog for changing Brightness and Contrast
 * Note: Contrast is currently not implemented
 * @author M. Fischboeck
 *
 */
public class BCDialog extends JDialog implements ActionListener, MouseListener {


	private static final long serialVersionUID = -5866895932400327816L;


	private final JPanel contentPanel = new JPanel();


	private ImageModel	model;
	private Observer	observer;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * Create the dialog
	 */
	public BCDialog(final ImageModel model, final Observer observer) {
		
		this.model = model;
		this.observer = observer;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JSlider slider = new JSlider();
		slider.setValue(0);
		slider.setMaximum(50);
		slider.setMinimum(-50);
		slider.addMouseListener(this);
		slider.setBounds(152, 86, 284, 16);
		slider.setName("Brightness");
		contentPanel.add(slider);
		
		JSlider slider_1 = new JSlider();
		slider_1.setValue(0);
		slider_1.setMaximum(50);
		slider_1.setMinimum(-50);
		slider_1.addMouseListener(this);
		slider_1.setBounds(152, 141, 284, 16);
		slider_1.setName("Contrast");
		//contentPanel.add(slider_1);
		
		JLabel lblChangeBrightnessAnd = new JLabel("Change Brightness and Contrast of the Image");
		lblChangeBrightnessAnd.setBounds(12, 12, 424, 15);
		contentPanel.add(lblChangeBrightnessAnd);
		
		JLabel lblBrightness = new JLabel("Brightness");
		lblBrightness.setBounds(12, 87, 122, 15);
		contentPanel.add(lblBrightness);
		
		JLabel lblContrast = new JLabel("Contrast");
		lblContrast.setBounds(12, 141, 70, 15);
		//contentPanel.add(lblContrast);
		
		JLabel lblChannels = new JLabel("Channels");
		lblChannels.setBounds(12, 217, 61, 16);
		contentPanel.add(lblChannels);
		
		JRadioButton rdbtnRgb = new JRadioButton("RGB");
		rdbtnRgb.setSelected(true);
		buttonGroup.add(rdbtnRgb);
		rdbtnRgb.setBounds(152, 210, 70, 23);
		rdbtnRgb.setActionCommand(("selectRGB"));
		rdbtnRgb.addActionListener(this);
		contentPanel.add(rdbtnRgb);
		
		JRadioButton rdbtnHsv = new JRadioButton("HSV");
		buttonGroup.add(rdbtnHsv);
		rdbtnHsv.setBounds(234, 210, 70, 23);
		rdbtnHsv.addActionListener(this);
		rdbtnHsv.setActionCommand("selectHSV");
		contentPanel.add(rdbtnHsv);
		
		JRadioButton rdbtnYuv = new JRadioButton("YUV");
		buttonGroup.add(rdbtnYuv);
		rdbtnYuv.setBounds(316, 210, 70, 23);
		rdbtnYuv.setActionCommand("selectYUV");
		rdbtnYuv.addActionListener(this);
		contentPanel.add(rdbtnYuv);
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

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Cancel")) {
			if (this.model.getStack().size() > 0) {
				this.model.setImage(this.model.getStack().pop());
			}
			this.dispose();
			this.observer.updateSurface();
			return;
		}
		
		if (e.getActionCommand().equals("OK")) {
			this.observer.executeChanges();
			this.dispose();
			
			if (this.model.getStack().size() > 0) {
				this.model.getStack().pop();
			} 
		}
		
		if (e.getActionCommand().equals("selectRGB")) {
			this.model.getBrightnessAndContrast().setChannels(Channel.RGB);
		}
		
		if (e.getActionCommand().equals("selectHSV")) {
			this.model.getBrightnessAndContrast().setChannels(Channel.HSV);
		}
		
		if (e.getActionCommand().equals("selectYUV")) {
			this.model.getBrightnessAndContrast().setChannels(Channel.YUV);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Object object = e.getSource();
		if (object instanceof JSlider) {
			JSlider slider = (JSlider) object;
			
			BCModel bc = this.model.getBrightnessAndContrast();
			if (slider.getName().equals("Brightness")) {
				bc.setBrightness(slider.getValue());
			}
			
			if (slider.getName().equals("Contrast")) {
				bc.setContrast(slider.getValue());
			}
		}
		
		this.observer.executeChanges();
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
