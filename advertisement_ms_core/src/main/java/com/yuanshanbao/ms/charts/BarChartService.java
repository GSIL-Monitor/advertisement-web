package com.yuanshanbao.ms.charts;

import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * 柱状图
 * 
 * @author rnjin
 *
 */
public class BarChartService extends ApplicationFrame {
	private static final long serialVersionUID = -8254724147171291988L;

	public BarChartService(String title) {
		super(title);
		this.setContentPane(createPanel());
	}

	public static CategoryDataset createDataSet() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.setValue(10, "aa", "管理人员");
		dataset.setValue(15, "bb", "市场人员");
		dataset.setValue(35, "cc", "开发人员");
		dataset.setValue(12, "dd", "其他人员");
		return dataset;
	}

	public static JFreeChart createJFreeChart(CategoryDataset dataset) {
		/**
		 * 构建JFreeChart
		 */
		JFreeChart jfreeChart = ChartFactory.createBarChart3D("某公司组织结构图",
				"人员分布", "人员数量", dataset, PlotOrientation.VERTICAL, true, false,
				false);
		/**
		 * 设置JFreeChart的属性
		 */
		jfreeChart.setTitle(new TextTitle("某公司组织构图", new Font("宋体", Font.BOLD
				+ Font.ITALIC, 20)));
		CategoryPlot plot = (CategoryPlot) jfreeChart.getPlot();
		CategoryAxis categoryAxis = plot.getDomainAxis();
		categoryAxis.setLabelFont(new Font("仿宋", Font.ROMAN_BASELINE, 12));
		return jfreeChart;
	}

	public static JPanel createPanel() {
		JFreeChart chart = createJFreeChart(createDataSet());
		return new ChartPanel(chart);
	}

	public static void main(String[] args) {
		BarChartService chart = new BarChartService("某公司组织结构图");
		chart.pack();
		chart.setVisible(true);
	}
}