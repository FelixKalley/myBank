package com.example.mybank.helpers;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.example.mybank.R;
import com.example.mybank.R.color;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RelativeLayout;

public class CategoriesChart {

	int category1, category2, category3, category4, category5, category6, category7, category8, category9;
	
	private GraphicalView mChartView2;
	static int count=9;

	 
	
	 
	 public CategoriesChart( int cat1, int cat2, int cat3, int cat4, int cat5, int cat6, int cat7, int cat8, int cat9) {
		this.category1 = cat1;
		this.category2 = cat2;
		this.category3 = cat3;
		this.category4 = cat4;
		this.category5 = cat5;
		this.category6 = cat6;
		this.category7 = cat7;
		this.category8 = cat8;
		this.category9 = cat9;
	}


	public Intent execute(Context context,RelativeLayout parent) {
	  
	  int[] colors = new int[count];
	  int[] Mycolors = new int[] { 
			  context.getResources().getColor(R.color.Android_Dark_Red), 
			  context.getResources().getColor(R.color.Android_Red), 
			  context.getResources().getColor(R.color.Android_Orange), 
			  context.getResources().getColor(R.color.Android_Dark_Orange), 
			  context.getResources().getColor(R.color.Android_Dark_Green), 
			  context.getResources().getColor(R.color.Android_Green),
			  context.getResources().getColor(R.color.Android_Purple),
			  context.getResources().getColor(R.color.Android_Dark_Purple), 
			  context.getResources().getColor(R.color.Android_Dark_Blue) 
	  };
	  
	  for(int i=0;i<count;i++) {
	   colors[i]=Mycolors[i];
	  }
	  
	  
	  DefaultRenderer renderer = buildCategoryRenderer(colors);
	   renderer.setPanEnabled(false);
	   renderer.setLabelsColor(Color.WHITE);
	   renderer.setShowLabels(true);
	   renderer.setLabelsTextSize(20);
	   renderer.setLegendTextSize(20);
	   renderer.setChartTitle("Anzahl Ausgaben/Kategorie");
	   renderer.setChartTitleTextSize(40);
	   
	   
	   
	  CategorySeries categorySeries = new CategorySeries("Ausgaben - Kategorien");
	  categorySeries.add("Freizeit", category1);
	  categorySeries.add("Geschaeftlich", category2);
	  categorySeries.add("Haushalt", category3);
	  categorySeries.add("Privat", category4);
	  categorySeries.add("Schule", category5);
	  categorySeries.add("Sonstiges", category6);
	  categorySeries.add("Sport", category7);
	  categorySeries.add("Studium", category8);
	  categorySeries.add("Wohnung", category9);
	  
	  
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
