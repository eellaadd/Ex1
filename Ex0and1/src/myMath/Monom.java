
package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{

	private double _coefficient; // מקדם
	private int _power; 

	public Monom()
	{
		this.set_coefficient(0);
		this.set_coefficient(0);
	}

	public Monom(double a, int b)
	{
		if(b<0) throw new RuntimeException("Power has to be non-negative");
		if(a==0) b = 0;

		this.set_coefficient(a);
		this.set_power(b);
	}
	//private static Monom init_from_string(String s)
	//	public Monom(String s)


	public Monom(Monom ot) 
	{
		this(ot.get_coefficient(), ot.get_power());
	}

	public Monom derivative()
	{
		double a = 0;
		int b = 0;

		if(isZero() && this.get_power() != 0)
			b = this.get_power()-1;

		else if(!isZero() && this.get_power() != 0 ) 
		{
			a = this.get_coefficient()*this.get_power();
			b = this.get_power()-1;
		}

		return new Monom(a,b);
	}

	public boolean equals(Monom m2)
	{
		return this.get_coefficient() == m2.get_coefficient() && this.get_power() == m2.get_power();
	}

	// ***************** add your code below **********************

	public int get_power()
	{
		return this._power;
	}

	public double get_coefficient()
	{
		return this._coefficient;
	}

	public void add(Monom m)
	{
		if(m.get_power() == this.get_power())
			this.set_coefficient(m.get_coefficient()+this.get_coefficient());
		else
			throw new RuntimeException("Powers of the monoms should be equal");
	}

	public void multiply(Monom x)
	{
		this.set_coefficient(x.get_coefficient()*this.get_coefficient());
		this.set_power(x.get_power()+this.get_power());
	}

	public void substract(Monom m)
	{
		if(m.get_power() == this.get_power())
			this.set_coefficient(this.get_coefficient()-m.get_coefficient());
		else
			throw new RuntimeException("Powers of the monoms should be equal");
	}

	@Override
	public double f(double x)
	{
		return this.get_coefficient()*Math.pow(x, this.get_power());
	}

	public boolean isZero()
	{
		return this.get_coefficient() == 0;
	}
	
	public String toString()
	{
		double a = this.get_coefficient();
		int b = this.get_power();
		
		if(a==0) return "0";
		if(b==0) return "" + a;
		if(a==1)
		{
			if(b==0) return "1";
			if(b==1) return "x";
			else return "x^" + b;
		}
		
		if(b==1 && a!=0 && a!=1) return "" + a +"x";
		else return a +"x^" +b;
	}

	//****************** Private Methods and Data *****************


	private void set_coefficient(double a)
	{
		this._coefficient = a;
	}

	private void set_power(int p)
	{
		this._power = p;
	}
	

}
