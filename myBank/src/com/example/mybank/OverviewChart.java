package com.example.mybank;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RelativeLayout;

public class OverviewChart {

	int balance;
	int expense;
	private GraphicalView mChartView2;
	private GraphicalView mChartView3;
	static int count=2;
	 
	int[] Mycolors = new int[] { Color.GRAY, Color.parseColor("#CC66CC") };
	 
	 public OverviewChart(int balance2, int expense2) {
		this.balance = balance2;
		this.expense = expense2;
	}


	public Intent execute(Context context,RelativeLayout parent) {
	  int balanceForChart = balance;
	  int expenseForChart = expense;
	  int[] colors = new int[count];
	  
	  for(int i=0;i<count;i++) {
	   colors[i]=Mycolors[i];
	  }
	  
	  
	  DefaultRenderer renderer = buildCategoryRenderer(colors);
	   renderer.setPanEnabled(false);
	   renderer.setLabelsColor(Color.WHITE);
	   renderer.setShowLabels(true);
	   renderer.setLabelsTextSize(18);
	   renderer.setLegendTextSize(20);
	   renderer.setChartTitle("Balance - Ausgaben");
	   renderer.setChartTitleTextSize(32);
	   
	   
	   
	  CategorySeries categorySeries = new CategorySeries("Balance-Ausgaben");
	  categorySeries.add("Balance", balanceForChart);
	  categorySeries.add("Expenses", expenseForChart);
	  
	  mChartView2=ChartFactory.getPieChartView(context, categorySeries,renderer);
	  parent.addView(mChartView2);
	  
	  
	  return ChartFactory.getPieChartIntent(context, categorySeries, renderer,null);
	 }
	 
	 
	 protected DefaultRenderer buildCategoryRenderer(int[] colors) {
	  DefaultRenderer renderer = new DefaultRenderer();
	  for (int color : colors) {
	  SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	  r.setColor(color);
	  renderer.addSeriesRenderer(r);
	  
	  }
	  return renderer;
	  }
	
	
	
	
	
}