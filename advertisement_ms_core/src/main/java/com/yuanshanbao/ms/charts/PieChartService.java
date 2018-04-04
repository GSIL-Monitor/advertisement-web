package com.yuanshanbao.ms.charts;

import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.yuanshanbao.ms.model.other.PieChartValue;

/**
 * 饼状图
 * 
 * @author rnjin
 *
 */
public class PieChartService extends ApplicationFrame{
	private static final long serialVersionUID = 4990250251675085213L;
	
	public PieChartService(String title, List<PieChartValue> valueList) {
		super(title);
		setContentPane(createDemoPanel(title, valueList));
	}
	
	public static void main(String[] args){
		List<PieChartValue> valueList = new LinkedList<PieChartValue>();
		
		PieChartValue pcv1 = new PieChartValue();
		pcv1.setName("测试1");
		pcv1.setValue(20D);
		PieChartValue pcv2 = new PieChartValue();
		pcv2.setName("测试2");
		pcv2.setValue(30D);
		PieChartValue pcv3 = new PieChartValue();
		pcv3.setName("测试3");
		pcv3.setValue(40D);
		
		valueList.add(pcv1);
		valueList.add(pcv2);
		valueList.add(pcv3);
		
		PieChartService fjc = new PieChartService("测试饼图", valueList);
		
		fjc.pack();  
		RefineryUtilities.centerFrameOnScreen(fjc);  
		fjc.setVisible(true);  
	}  
		 
	//生成饼图数据集对象  
	public static PieDataset createDataset(List<PieChartValue> valueList){  
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		if (valueList != null && valueList.size() > 0) {
			for (PieChartValue pcv : valueList) {
				defaultpiedataset.setValue(pcv.getName(), pcv.getValue());
			}
		}
		 
		return defaultpiedataset;
	}  
		 
	//生成图表主对象JFreeChart  
	public static JFreeChart createChart(String title, PieDataset piedataset){  
		//定义图表对象  
		JFreeChart jfreechart = ChartFactory.createPieChart(title, piedataset, true, true, false);  
		//获得图表显示对象  
		PiePlot pieplot = (PiePlot)jfreechart.getPlot();  
		//设置图表标签字体  
		pieplot.setLabelFont(new Font("SansSerif",Font.BOLD,12));  
		pieplot.setNoDataMessage("No data available");  
		pieplot.setCircular(true);  
		pieplot.setLabelGap(0.01D);//间距  
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				("{0}: ({2})"), NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%"))); 
		 
		return jfreechart;  
	}  
		 
	//生成显示图表的面板  
	public static JPanel createDemoPanel(String title, List<PieChartValue> valueList){  
		JFreeChart jfreechart = createChart(title, createDataset(valueList));
		return new ChartPanel(jfreechart);  
	}  
}
