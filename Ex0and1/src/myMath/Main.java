package myMath;

public class Main {

	public static void main(String[] args)
	{
		
		Monom m1 = new Monom(8,3);
		Monom m2 = new Monom(7,5);
		Monom m3 = new Monom(9,8);
		System.out.println(m1);
		System.out.println(m1.derivative());
		m2.multiply(m3);
		System.out.println(m2);
		System.out.println("-------------");
		
		Polynom p1 = new Polynom();
		p1.add(m1);
		p1.add(m2);
		p1.add(m3);
		System.out.println(p1);

	}

}
