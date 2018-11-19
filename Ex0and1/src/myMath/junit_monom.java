package myMath;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class junit_monom {

	@Test
	void testMonomDoubleIntCtor() {
		Monom m = new Monom(2,4);
		if (m.get_coefficient() != 2 && m.get_power() != 4)
			fail("Coefficient and power are incorrect.");
		if (m.get_coefficient() != 2) 
			fail("Coefficient is incorrect.");
		if (m.get_power() != 4) 
			fail("Power is incorrect.");
	}
	
	@Test
	void testMonomCopyCtor() {
		Monom m0 = new Monom(2,4);
		Monom m1 = new Monom(m0);
		if (!m0.equals(m1))
			fail("m0 and m1 should be equal, copy ctor doesn't work.");
	}
	
	@Test
	void testMonomStringCtor1() {
		Monom m0 = new Monom(2,4);
		Monom m1 = new Monom("2x^4");
		if (!m0.equals(m1))
			fail("Monom is: " + m1 + " should be: " + m0);
	}
	
	@Test
	void testMonomStringCtor2() {
		Monom m0 = new Monom(-2,4);
		Monom m1 = new Monom("-2x^4");
		if (!m0.equals(m1))
			fail("Monom is: " + m1 + " should be: " + m0);
	}
	
	@Test
	void testMonomStringCtor3() {
		Monom m0 = new Monom(0,4);
		Monom m1 = new Monom("0");
		if (!m0.equals(m1))
			fail("Monom is: " + m1 + " should be: " + m0);
	}
	
	@Test
	void testMonomStringCtor4() {
		Monom m0 = new Monom(2,0);
		Monom m1 = new Monom("2");
		if (!m0.equals(m1))
			fail("Monom is: " + m1 + " should be: " + m0);
	}
	
	@Test
	void testMonomStringCtor5() {
		String[] bad_strings = {null, "", "a", "3^2", "x^a", "++","--","@$&%","**","//","+x","*x","/x","%x"};
		assertThrows(Exception.class,
	            ()->{
	            	for (int i = 0; i < bad_strings.length; i++) {
	            		new Monom(bad_strings[i]);
					}
	            });
		
	}
	
	@Test
	void testMonomStringCtor6() {
		String[] good_strings = {"0", "3", "x", "3x", "3x^2","-x","-3"};
		try {
			for (int i = 0; i < good_strings.length; i++) {
        		new Monom(good_strings[i]);
			}
		}
		catch(Exception ex) {
			System.err.println(ex);
			fail("String should not throw exception ");
		}
		
	}
	
	@Test
	void testMonomValueAtX() {
		Monom m = new Monom(3,2);
		double x = 2;
		double ans = 12;
		if (m.f(x) != ans) {
			fail("f(2) should equal 12.0, equals " + m.f(x));
		}
	}
	
	@Test
	void testDerivative() {
		Monom m = new Monom(-3,2);
		Monom md = m.derivative();
		if (md.get_coefficient() != -6 || md.get_power() != 1) {
			fail("Derivative should be -6.0x, but is " + md);
		}
	}
	
	@Test
	void testAdd() {
		Monom m0 = new Monom(2,3);
		Monom m1 = new Monom(4,3);
		Monom m2=new Monom(m0);
		m2.add(m1);
		if(m2.get_coefficient()!= m0.get_coefficient()+m1.get_coefficient() && m2.get_power()!=m0.get_power()) {
			fail("Coefficient should be 6.0 , but is " + m2.get_coefficient() + " Power should be 3 , but is " + m2.get_power());
		}
		if(m2.get_coefficient()!= m0.get_coefficient()+m1.get_coefficient()) {
			fail("Coefficient should be 6.0 , but is " + m2.get_coefficient());
		}
		if(m2.get_power()!=m0.get_power()) {
			fail("Power should be 3 , but is " + m2.get_power());
		}
	}
	
	@Test
	void testSubstract1() {
		Monom m0 = new Monom(2,3);
		Monom m1 = new Monom(4,3);
		Monom m2 = new Monom("-2x^3");
		m0.substract(m1);
		if (!m0.equals(m2)) {
			fail("Monom should be " + m2 + " but is " + m0);
		}
	}
	
	@Test
	void testSubstract2() {
		Monom m0 = new Monom(2,3);
		Monom m1 = new Monom(-4,3);
		Monom m2 = new Monom("6x^3");
		m0.substract(m1);
		if (!m0.equals(m2)) {
			fail("Monom should be " + m2 + " but is " + m0);
		}
	}
	
	@Test
	void testMultiply() {
		Monom m0 = new Monom(2,3);
		Monom m1 = new Monom(4,5);
		Monom m2=new Monom(m0);
		m2.multiply(m1);
	
		if(m2.get_coefficient()!= m0.get_coefficient()*m1.get_coefficient() && m2.get_power()!=m0.get_power()+m1.get_power()) {
			fail("Coefficient should be 8 , but is " + m2.get_coefficient() + " Power should be 8 , but is " + m2.get_power());
		}
		if(m2.get_coefficient()!= m0.get_coefficient()*m1.get_coefficient()) {
			fail("Coefficient should be 8 , but is " + m2.get_coefficient());
		}
		if(m2.get_power()!=m0.get_power()+m1.get_power()) {
			fail("Power should be 8 , but is " + m2.get_power());
		}
		
	}
	
	@Test
	void testEquals() {
		Monom m0 = new Monom(2,3);
		Monom m1 = new Monom(m0);
		if (m0.get_coefficient() != m1.get_coefficient() && m0.get_power() != m1.get_power()) {
			fail("Coefficient should be 2, but is " + m1.get_coefficient() + " Power should be 3, but is " + m1.get_power());
		}
		if (m0.get_coefficient() != m1.get_coefficient()) {
			fail("Coefficient should be 2, but is " + m1.get_coefficient());
		}
		if (m0.get_power() != m1.get_power()) {
			fail("Power should be 3, but is " + m1.get_power());
		}
	}
	
	@Test
	void testIsZero1() {
		Monom m = new Monom(0,0);
		if (!m.isZero()) {
			fail("Monom should be zero");
		}
	}
	
	@Test
	void testIsZero2() {
		Monom m = new Monom(0,4);
		if (!m.isZero()) {
			fail("Monom should be zero");
		}
	}
	
	@Test
	void testToString1() {
		Monom m0 = new Monom(3,7);
		String m0_string = m0.toString();
		Monom m1 = new Monom(m0_string);
		if (!m0.equals(m1)) {
			fail("m1 should be "+ m0 + " but is " + m1);
		}
	}
	
	@Test
	void testToString2() {
		Monom m0 = new Monom(-3,7);
		String m0_string = m0.toString();
		Monom m1 = new Monom(m0_string);
		if (!m0.equals(m1)) {
			fail("m1 should be "+ m0 + " but is " + m1);
		}
	}
	
	@Test
	void testToString3() {
		Monom m0 = new Monom(1,1);
		String m0_string = m0.toString();
		Monom m1 = new Monom(m0_string);
		if (!m0.equals(m1)) {
			fail("m1 should be "+ m0 + " but is " + m1);
		}
	}

}
