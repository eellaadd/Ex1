package myMath;

//import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.function.Predicate;

//import javax.management.RuntimeErrorException;

import org.knowm.xchart.QuickChart;
//import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

//import javafx.scene.chart.XYChartBuilder;
import myMath.Monom;

public class Polynom implements Polynom_able{


	ArrayList<Monom> list = new ArrayList<>();

	public Polynom()
	{
		list.add(new Monom("0"));
	} 

	public Polynom(String s)
	{
		this();
		Polynom p = init_from_string(s);
		Iterator<Monom> it= p.iteretor();
		while(it.hasNext())
		{
			Monom m = it.next();
			this.add(m);
		}
	}

	public Polynom(Polynom ot)
	{
		this();
		Iterator<Monom> it= ot.iteretor();
		while(it.hasNext())
		{
			Monom m = it.next();
			this.add(m);
		}
	}

	@Override
	public double f(double x) 
	{
		double sum = 0;
		for (int i = 0; i < this.list.size(); i++) 
		{
			sum += this.list.get(i).f(x);
		}
		return sum;
	}

	/**
	 * adds another polynom p1 to given polynom.
	 * @param p1 polynom to be added.
	 */
	@Override
	public void add(Polynom_able p1) 
	{
		Iterator<Monom> it = p1.iteretor();
		while(it.hasNext()) {
			Monom x = it.next();
			this.add(x);
		}
		removeZero();
	}

	/**
	 * Adds to the current polynom the given monom m1.
	 * @param m1 Monom to be added.
	 */
	@Override
	public void add(Monom m1)
	{
		boolean added = false;
		Monom_Comperator cmp = new Monom_Comperator();
		for (int i = 0; i < this.list.size(); i++) 
		{
			if (this.list.get(i).get_power()==m1.get_power())
			{
				this.list.get(i).add(m1);
				added = true;
			}
		}
		if(!added)
		{
			list.add(m1);
			list.sort(cmp);
		}
		removeZero();
	}
	/**
	 * Subtracts from the current polynom the given polynom p1.
	 * @param p1 Polynom to be subtracted.
	 */
	@Override
	public void substract(Polynom_able p1)
	{
		Polynom temp = new Polynom();
		Iterator<Monom> it = p1.iteretor();
		while(it.hasNext())
		{
			Monom x = it.next();
			Monom mNew = new Monom(-1 * x.get_coefficient(), x.get_power());
			temp.add(mNew);
		}
		this.add(temp);
		it = p1.iteretor();
		while(it.hasNext()) 
		{
			Monom x = it.next();
			if (x.isZero()) this.list.remove(x);
		}
		removeZero();
	}
	/**
	 * Subtracts from the current polynom the given monom m1.
	 * @param m1 Monom to be subtracted.
	 */
	public void substract(Monom m1)
	{
		Polynom temp = new Polynom();
		Monom mNew = new Monom(-1 * m1.get_coefficient(), m1.get_power());
		temp.add(mNew);
		this.add(temp);
		Iterator<Monom> it = this.iteretor();
		while(it.hasNext()) 
		{
			Monom x = it.next();
			if (x.isZero()) this.list.remove(x);
		}
		removeZero();
	}
	/**
	 * Multiplies the current polynom by a given polynom p1.
	 * @param p1 Polynom to be multiplied by.
	 */
	@Override
	public void multiply(Polynom_able p1)
	{
		Polynom sum = new Polynom();
		Iterator<Monom> it1 = this.iteretor();
		Iterator<Monom> it2 = p1.iteretor();
		while(it1.hasNext()) 
		{
			Monom m1 = it1.next();
			while(it2.hasNext())
			{
				Monom m2 = it2.next();
				Monom m3 = new Monom(m1.get_coefficient(),m1.get_power());
				m3.multiply(m2);
				sum.add(m3);
			}
			it2 = p1.iteretor();
		}
		Polynom copy = new Polynom();
		copy.add(this);

		this.substract(copy);
		this.add(sum);
		removeZero();
	}

	/**
	 * Checks whether the current polynom is equal to a given polynom p1.
	 * Two polynoms are considered equal if their coefficients and powers are equal.
	 * @param p1 Polynom to be compared to.
	 * @return True if the polynoms are equal, false otherwise.
	 */
	@Override
	public boolean equals(Polynom_able p1)
	{
		Iterator<Monom> it1 = this.iteretor();
		Iterator<Monom> it2 = p1.iteretor();
		while(it1.hasNext() || it2.hasNext())
		{
			if (it1.hasNext() && !it2.hasNext() || !it1.hasNext() && it2.hasNext())
				return false;
			Monom m1 = it1.next();
			Monom m2 = it2.next();
			if (!(m1.equals(m2)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the current polynom's coefficient is zero.
	 * @return True if the polynom is a zero polynom, false otherwise.
	 */
	@Override
	public boolean isZero()
	{
		Polynom zero = new Polynom("0");
		return this.equals(zero);
	}

	public void removeZero() 
	{
		//		Iterator<Monom> it = this.iteretor();
		//		Monom last = new Monom();
		//		while(it.hasNext()) {
		//			last = it.next();
		//		}
		//		if (last.isZero()) {
		//			it.remove();
		//		}
	}

	/**
	 * This function uses the Bisection Method to find a root of the polynom between x0 and x1.
	 * https://en.wikipedia.org/wiki/Bisection_method
	 * @param x0 The left boundary of the range.
	 * @param x1 The right boundary of the range.
	 * @param eps The maximum distance between x0 and x1 we will allow. The function will stop when x0 and x1 get at least this close.
	 * @return The x value of the root.
	 */
	@Override
	public double root(double x0, double x1, double eps)
	{

		double y0 = f(x0);
		double y1 = f(x1);

		if(y0 * y1 > 0)
			System.err.println("f(x0) and f(x1) should have different signs");

		double x_diff = Math.abs(x1- x0);
		double y_diff = Math.abs(f(x0) - f(x1));

		if (x_diff > eps || y_diff > eps) {
			double x_mid = (x0 + x1) / 2;
			double y_mid = f(x_mid);

			if(y0 * y_mid <= 0)
				return root(x0, x_mid, eps);
			else
				return root(x_mid, x1, eps);
		}

		return x0;
	}

	/**
	 * Creates a copy of the current polynom.
	 * @return A copy of the polynom.
	 */
	@Override
	public Polynom_able copy()
	{
		Polynom copy = new Polynom();

		Iterator<Monom> it = this.iteretor();
		while(it.hasNext())
		{
			Monom x = it.next();
			Monom mNew = new Monom(x.get_coefficient(), x.get_power());
			copy.add(mNew);
		}
		return copy;
	}

	/**
	 * Creates a new polynom which is the derivative of the current polynom.
	 * @return The derived polynom.
	 */
	@Override
	public Polynom_able derivative() 
	{
		Polynom der = new Polynom();
		Iterator<Monom> it = this.iteretor();
		while (it.hasNext()) {
			Monom curr = it.next(); // Current Monom
			Monom cder = curr.derivative(); // Derivative of current Monom
			der.add(cder);
		}
		return der;
	}

	/**
	 * Computes the Riemann sum of the current polynom between x0 and x1.
	 * https://en.wikipedia.org/wiki/Riemann_sum
	 * @param x0 The left boundary of the range.
	 * @param x1 The right boundary of the range.
	 * @param eps The width of one rectangle.
	 * @return The computed area value.
	 */
	@Override
	public double area(double x0, double x1, double eps)
	{
		if (x1 < x0) {
			throw new RuntimeException("X0 must be smaller than X1");
		}
		double sum=0;
		for (double i = x0; i <= x1; i=i+eps) 
		{
			if (f(i) > 0 && f(i+eps) > 0) {
				sum += eps * Math.max(f(i), f(i+eps));
			}
		}
		return sum;
	}

	/**
	 * @return An iterator to the arraylist the contains the monoms that make up the polynom.
	 */
	@Override
	public Iterator<Monom> iteretor() 
	{
		return this.list.iterator();
	}

	/**
	 * Converts the current polynom to a string.
	 * @return A string version of the current polynom.
	 */
	public String toString()
	{
		String ans;
		if (isZero())
		{
			ans = "0";
		}
		else
		{
			ans = list.get(0).toString();
			for (int i = 1; i < list.size(); i++)
			{
				Monom curr_monom = list.get(i);
				String curr_monom_string = curr_monom.toString();
				if (!curr_monom_string.equals("0"))
				{
					if (curr_monom.get_coefficient() > 0)
					{
						ans = ans + " + " + curr_monom_string;
					}
					else
					{
						ans = ans + " - " + curr_monom_string.substring(1);
					}
				}
			}
		}
		return ans;
	}
	/**
	 * Draws the graph of the polynom for x values from x0 to x1
	 * Marks the Maximum and Minimum points on the graph
	 * Calculates the area above the polynom and below the x axis, and prints it to the console
	 * We used the open-source xchart library to draw the function
	 * https://github.com/knowm/XChart
	 * @param x0 beginning of the drawing range
	 * @param x1 ending of the drawing range
	 */
	public void draw(double x0, double x1)
	{
		double[][] values = getValues(x0,x1);
		double[] xData = values[0];
		double[] yData = values[1];

		double max_y = yData[0];
		double min_y = yData[0];
		for (int i = 1; i < yData.length; i++)
		{
			if (yData[i] > max_y) max_y = yData[i];
			if (yData[i] < min_y) min_y = yData[i];
		}
		double y_range = Math.abs(max_y - min_y);

		double[] xAxisXData = { x0, x1 };
		double[] xAxisYData = { 0, 0 };

		double[] yAxisXData = { 0, 0 };
		double[] yAxisYData = { min_y - y_range*0.2, max_y + y_range*0.2};

		XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", this.toString(), xData, yData);


		XYSeries xAxis = chart.addSeries("X Axis", xAxisXData, xAxisYData);
		XYSeries yAxis = chart.addSeries("Y Axis", yAxisXData, yAxisYData);
		xAxis.setLineColor(XChartSeriesColors.BLACK);
		xAxis.setMarker(SeriesMarkers.NONE);
		xAxis.setLineStyle(SeriesLines.SOLID);
		yAxis.setLineColor(XChartSeriesColors.BLACK);
		yAxis.setMarker(SeriesMarkers.NONE);
		yAxis.setLineStyle(SeriesLines.SOLID);

		ArrayList<Double> maxMinPoints = getMaxMinX(xData, yData, y_range);
		for (int i = 0; i < maxMinPoints.size(); i++)
		{
			double[] x = {maxMinPoints.get(i)};
			double[] y = {f(maxMinPoints.get(i))};
			chart.addSeries("("+x[0]+","+y[0]+")", x, y)
			.setMarker(SeriesMarkers.CIRCLE)
			.setMarkerColor(XChartSeriesColors.RED)
			.setLineColor(XChartSeriesColors.LIGHT_GREY);
		}

		// Show it
		//new SwingWrapper(chart).displayChart();


		Polynom inverse = new Polynom(this);
		inverse.multiply(new Polynom("-1"));
		double area = inverse.area(x0, x1, 0.01);
		System.out.println("The area under the X axis and above the function: " + area);
	}
	private double[][] getValues(double x0, double x1)
	{
		int num_points = 1000;
		double length = x1 - x0;
		double[] xData = new double[num_points+1];
		double[] yData = new double[num_points+1];
		xData[0] = x0;
		xData[num_points] = x1;
		for (int i = 1; i < xData.length; i++)
		{
			xData[i] = xData[i-1] + length/num_points;
		}
		for (int i = 0; i < yData.length; i++)
		{
			yData[i] = f(xData[i]);
		}
		double[][] values = new double[2][num_points];
		values[0] = xData;
		values[1] = yData;

		return values;

	}
	private ArrayList<Double> getMaxMinX(double[] xData, double[] yData, double y_range)
	{
		double eps = y_range*0.001;
		double min_x_dist = xData.length*0.001;
		ArrayList<Double> points = new ArrayList<Double>();
		Polynom derivative = (Polynom) derivative();
		double maxima_x = Integer.MIN_VALUE;
		for (int i = 0; i < yData.length; i++) {
			double curr_x = xData[i];
			if ( (derivative.f(curr_x) >= -eps && derivative.f(curr_x) <= eps)
					&& ((derivative.f(curr_x-1) > 0 && derivative.f(curr_x+1) < 0)
							|| (derivative.f(curr_x-1) < 0 && derivative.f(curr_x+1) > 0)) )
			{
				if (i == 0) 
				{
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
	/**
	 * A helper function for the constructor that gets a string.
	 * @param s String to be converted into a polynom.
	 * @return A polynom constructed from the string.
	 */
	private Polynom init_from_string(String s)
	{
		String tmp_sign = "+";
		if (s == null)
		{
			throw new RuntimeException("String is empty");
		}
		String[] string_monoms = s.split(" "); //["3x^2", "+", "%", "2x", "+", "5"]
		Polynom pnew = new Polynom();
		for (int i = 0; i < string_monoms.length; i++)
		{
			String s_monom = string_monoms[i];
			if(s_monom.equals("+") || s_monom.equals("-"))
			{
				tmp_sign = s_monom;
			}
			if (!s_monom.equals("+") &&  !s_monom.equals("-")){
				try {
					Monom m = new Monom(s_monom);
					if (!m.isZero() && tmp_sign.equals("+"))
						pnew.add(m);
					if (!m.isZero() && tmp_sign.equals("-"))
						pnew.substract(m);
				}
				catch(Exception ex) 
				{
					System.err.println(ex);
				}
			}
		}
		return pnew;
	}

}