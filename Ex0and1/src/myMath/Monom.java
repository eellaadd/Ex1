
package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{

	private double _coefficient;
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

	public Monom(String s)
	{
		Monom ms = init_from_string(s);
		this.set_coefficient(ms.get_coefficient());
		this.set_power(ms.get_power());
	}

	//****************** Private Methods and Data *****************

	private static Monom init_from_string(String s)
	{
		if(s == null)
			throw new RuntimeException("String is null");

		if(s.isEmpty())
			throw new RuntimeException("String is null");

		double a = 0;
		int b = 0;

		String in = s.toLowerCase();
		int ind_x = in.indexOf("x");
		int ind_p = in.indexOf("^");

		if(ind_x < 0)
		{
			try
			{
				a = Double.parseDouble(in);
			}
			catch(Exception ex)
			{
				System.out.println(ex);
			}
		}
		else 
		{
			if (ind_p < 0)
			{
				try 
				{
					if (in.endsWith("x"))
					{
						String coef = in.substring(0, ind_x);
						if (!coef.isEmpty())
						{
							try
							{
								a = Double.parseDouble(coef);
								b = 1;
							}
							catch(Exception ex)
							{
								System.out.println(ex);
							}
						}
						else
						{
							a = b = 1;
						}
					}
					else 
					{
						throw new RuntimeException("Invalid string, x should be the last char");
					}
				}
				catch(Exception ex) 
				{
					System.out.println(ex);
				}
			}
			else 
			{
				if (ind_p == ind_x + 1)
				{
					String pow = in.substring(ind_p + 1);
					if (pow.isEmpty()) 
					{
						throw new RuntimeException("Invalid string, nothing after '^'");
					}
					else
					{
						try 
						{
							b = Integer.parseInt(pow);
						}
						catch(Exception ex) {
							System.out.println(ex);
						}
					}
					String coef = in.substring(0, ind_x);
					if (!coef.isEmpty())
					{
						try 
						{
							a = Double.parseDouble(coef);
						}
						catch(Exception ex)
						{
							System.out.println(ex);
						}
					}
					else 
					{
						a = 1;
					}
				}
				else 
				{
					throw new RuntimeException("Invalid string, 'x...^' ");
				}
			}
		}
		return new Monom(a,b);
	}

	private void set_coefficient(double a)
	{
		this._coefficient = a;
	}

	private void set_power(int p)
	{
		this._power = p;
	}
}
