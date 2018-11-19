package myMath;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * Creates a simple Chart using QuickChart
 */

public class Example0 {
 
  public static void main(String[] args) throws Exception {
	Polynom func = new Polynom("0.2x^4 - 1.5x^3 + 3x^2 - x - 5");
	double x0 = -2;
	double x1 = 6;
	double[][] values = getValues(func, x0,x1);
    double[] xData = values[0];
    double[] yData = values[1];
    
    double[] xAxisXData = { x0, x1 };
    double[] xAxisYData = { 0, 0 };
    
    double[] yAxisXData = { 0, 0 };
    double[] yAxisYData = { -20, 20 };
 
    // Create Chart
    XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
    chart.addSeries("X Axis", xAxisXData, xAxisYData);
    chart.addSeries("Y Axis", yAxisXData, yAxisYData);
    ArrayList<Double> maxMinPoints = getMaxMinX(xData, yData, func);
    for (int i = 0; i < maxMinPoints.size(); i++) {
    	double[] x = {maxMinPoints.get(i)};
    	double[] y = {func.f(maxMinPoints.get(i))};
    	chart.addSeries("("+x[0]+","+y[0]+")", x, y);
	}
 
    // Show it
    new SwingWrapper(chart).displayChart();
 
  }
  
  public static double[][] getValues(Polynom func, double x0, double x1)
  {
	  int num_points = 10000;
	  double length = x1 - x0;
	  double[] xData = new double[num_points+1];
	  double[] yData = new double[num_points+1];
	  xData[0] = x0;
	  xData[num_points] = x1;
	  for (int i = 1; i < xData.length; i++) {
		xData[i] = xData[i-1] + length/num_points;
	  }
	  for (int i = 0; i < yData.length; i++) {
		yData[i] = f(func, xData[i]);
	  }
	  double[][] values = new double[2][num_points];
	  values[0] = xData;
	  values[1] = yData;
	  
	  return values;
	  
  }
  
  public static double f(Polynom func, double x) {
	  return func.f(x);
  }
  
  public static ArrayList<Double> getMaxMinX(double[] xData, double[] yData, Polynom func) {
	  double eps = 0.0015;
	  double min_x_dist = 0.01;
	  ArrayList<Double> points = new ArrayList<Double>();
	  Polynom derivative = (Polynom) func.derivative();
	  double maxima_x = 0;
	  for (int i = 0; i < yData.length; i++) {
		  double curr_x = xData[i];
		  if (derivative.f(curr_x) >= -eps && derivative.f(curr_x) <= eps)
		  {
			  if (i == 0) {
				  points.add(curr_x);
				  maxima_x = curr_x;
				  
			  }
			  else if (i > 0 && Math.abs(maxima_x - curr_x) > min_x_dist)
			  {
				  points.add(curr_x);
				  maxima_x = curr_x;
			  }
		  }
	  }
	  return points;
  }
  
}