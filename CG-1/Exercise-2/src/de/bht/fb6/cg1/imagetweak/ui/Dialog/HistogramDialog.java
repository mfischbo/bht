package de.bht.fb6.cg1.imagetweak.ui.Dialog;

import ij.process.ImageProcessor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;

import de.bht.fb6.cg1.imagetweak.model.HistogramOptions.Channel;
import de.bht.fb6.cg1.imagetweak.model.HSVColor;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import de.bht.fb6.cg1.imagetweak.model.YUVColor;
import de.bht.fb6.cg1.imagetweak.service.Observer;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

/**
 * Dialog showing all options for Histogram generation
 * As the Histogram will be opened in a separate Dialog window, the generation
 * of the diagram will take place in this class
 * @author M. Fischboeck
 *
 */
public class HistogramDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -8522383505410613888L;
	private final JPanel contentPanel = new JPanel();

	private final ImageModel model;
	
	private SimpleHistogramDataset	dataset;
	private ChartPanel		panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	

	
	/**
	 * Create the dialog specifying the image model and the observer class
	 * @param model The model to be used generating the diagram
	 * @param observer The observer to be notified on events
	 */
	public HistogramDialog(ImageModel model, Observer observer) {
		
		this.model = model;
		
		this.dataset = new SimpleHistogramDataset(255);
		for (int i=0; i < 256; i++) {
			this.dataset.addBin(new SimpleHistogramBin(i, i+1, true, false));
		}
		updateDataset();
		
		
		
		JFreeChart chart = ChartFactory.createHistogram("Histogram", "values", "brightness", dataset, PlotOrientation.VERTICAL, false, false, false);
		chart.setBackgroundPaint(Color.white);
		this.panel = new ChartPanel(chart);
		this.panel.setBounds(12, 12, 554, 300);
		contentPanel.add(this.panel);
		
		setBounds(100, 100, 580, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblChannels = new JLabel("Channels");
		lblChannels.setBounds(12, 314, 70, 15);
		contentPanel.add(lblChannels);
		
		JRadioButton rdbtnRgb = new JRadioButton("RGB");
		rdbtnRgb.setSelected(true);
		rdbtnRgb.setActionCommand("selectRGB");
		rdbtnRgb.addActionListener(this);
		buttonGroup.add(rdbtnRgb);
		rdbtnRgb.setBounds(88, 309, 70, 23);
		contentPanel.add(rdbtnRgb);
		
		JRadioButton rdbtnHsv = new JRadioButton("HSV");
		rdbtnHsv.setActionCommand("selectHSV");
		rdbtnHsv.addActionListener(this);
		buttonGroup.add(rdbtnHsv);
		rdbtnHsv.setBounds(170, 309, 70, 23);
		contentPanel.add(rdbtnHsv);
		
		JRadioButton rdbtnYuv = new JRadioButton("YUV");
		rdbtnYuv.setActionCommand("selectYUV");
		rdbtnYuv.addActionListener(this);
		buttonGroup.add(rdbtnYuv);
		rdbtnYuv.setBounds(252, 309, 141, 23);
		contentPanel.add(rdbtnYuv);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Close");
				okButton.setActionCommand("Close");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
	
		if (source instanceof JButton) {
			JButton c = (JButton) e.getSource();
			if (c.getActionCommand().equals("Close")) {
				this.dispose();
				return;
			}
		}
		
		if (source instanceof JRadioButton) {
			String actionCommand = ((JRadioButton) source).getActionCommand();
			if (actionCommand.equals("selectRGB")) {
				this.model.getHistogramOptions().setHistogramChannel(Channel.RGB);
			} else if (actionCommand.equals("selectHSV")) {
				this.model.getHistogramOptions().setHistogramChannel(Channel.HSV);
			} else if (actionCommand.equals("selectYUV")) {
				this.model.getHistogramOptions().setHistogramChannel(Channel.YUV);
			}
			updateDataset();
			
		}
	}


	/**
	 * Updates the dataset for histogram generation 
	 */
	private void updateDataset() {
		
		ImageProcessor proc = this.model.getImage().getProcessor();
		int[] pixels = (int[]) proc.getPixels();
		
		this.dataset.clearObservations();
		Channel selected = this.model.getHistogramOptions().getHistogramChannel();
		
		for (int x=0; x < pixels.length; ++x) {
			
			int r = (pixels[x] & 0x00FF0000) >> 16;
			int g = (pixels[x] & 0x0000FF00) >> 8;
			int b = (pixels[x] & 0x000000FF);
			
			int val = 0;
			
			if (selected == Channel.RGB) 
				val = (r + g + b) / 3;
			
			if (selected == Channel.HSV) {
				HSVColor hsv = new HSVColor(0, 0, 0);
				hsv.fromRGB(new int[] {r, g, b});
				val = hsv.getParameters()[2];
			}
			
			if (selected == Channel.YUV) {
				YUVColor yuv = new YUVColor(0, 0, 0);
				yuv.fromRGB(new int[] {r, g, b});
				val = yuv.getParameters()[0];
			}
			
			this.dataset.addObservation(val);
		}
	}
}
