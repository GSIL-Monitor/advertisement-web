package com.yuanshanbao.ms.charts;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.yuanshanbao.ms.model.other.TimeChartValue;

/**
 * 时间曲线图
 * 
 * @author rnjin
 *
 */
public class TimeChartService extends ChartPanel implements Runnable {
	
	private static final long serialVersionUID = -915447147756100985L;

	private static TimeSeries timeSeries; 
	
	public TimeChartService(String chartContent, String title, String yaxisName, List<TimeChartValue> valueList) {  
		super(createChart(chartContent, title, yaxisName, valueList));  
	}  
      
	private static JFreeChart createChart(String chartContent, String title, String yaxisName, List<TimeChartValue> valueList) {  
		//创建时序图对象  
		timeSeries = new TimeSeries(chartContent, Day.class); 
		
		if (valueList != null && valueList.size() > 0) {
			for (TimeChartValue value : valueList) {
				Day day = new Day(value.getDay(), value.getMonth(), value.getYear());
				timeSeries.add(day, value.getValue());
			}
		}
		
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);  
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, "时间(天)", yaxisName, timeseriescollection, true, true, false);  
		XYPlot xyplot = jfreechart.getXYPlot();
		DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
		dateaxis.setDateFormatOverride(sfd);
		xyplot.setDomainAxis(dateaxis);
		//纵坐标设定  
		ValueAxis valueaxis = xyplot.getDomainAxis();  
		//自动设置数据轴数据范围  
		valueaxis.setAutoRange(true);  
		//数据轴固定数据范围 30s  
		valueaxis.setFixedAutoRange(30000D);  
  
		valueaxis = xyplot.getRangeAxis();  
		
		return jfreechart;  
	}  
  
	public void run() {  
    }  

    /** 
     * @param args 
     */  
    public static void main(String[] args) {
    	JFrame frame=new JFrame("Test Chart");
    	
    	List<TimeChartValue> valueList = new LinkedList<TimeChartValue>();
    	
    	TimeChartValue value1 = new TimeChartValue();
    	value1.setDateStr("2013-07-13");
    	value1.setValue(100);
    	value1.setFormat("yyyy-MM-dd");
    	
    	TimeChartValue value2 = new TimeChartValue();
    	value2.setDateStr("2013-07-14");
    	value2.setValue(300);
    	value2.setFormat("yyyy-MM-dd");
    	
    	valueList.add(value1);
    	valueList.add(value2);
    	
    	TimeChartService rtcp=new TimeChartService("日期", "异常帐号数量趋势图", "异常帐号数量", valueList);
    	frame.getContentPane().add(rtcp, BorderLayout.CENTER);
    	frame.pack();
    	frame.setVisible(true);
    	(new Thread(rtcp)).start();
    	frame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent windowevent) {
    			System.exit(0);  
    		}
    	});
	}  
}