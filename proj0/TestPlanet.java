public class TestPlanet{

	/**
	 * Test Planet class
	 */
	public static void main(String[] args){
		checkTestPlanet();
	}

	/** 
	 * Run the process to check if the Planet class works as expected
	 */
	public static void checkTestPlanet(){
		Planet p1 = new Planet(0, 0, 0, 0, 5e6, "jupiter.gif");
		Planet p2 = new Planet(3, 4, 1, 2, 5e7, "jupiter.gif");
		
		checkEquals(p1.calcForceExertedBy(p2), 667, "calcForceExertedBy()", 0.01);

	}

	/** Find out if the expected value and the given value are equal
	 * 
	 * @param expected   	the expected value of the result
	 * @param actual 	the value you give
	 * @param label 	the name of the test class
	 * @param eqs		tolerance for the double comparison
	 */
	public static void checkEquals(double expected, double actual,
			String label, double eqs){
		if (Math.abs(expected - actual) <= Math.max(actual, expected) * eqs){
			System.out.println("PASS: " + label + ": expected " + expected +
					" and you give " + actual);
		} else {
			System.out.println("FAIL: " + label + ": exepcted " + expected + 
					" and you give " + actual);
		}
	}
}

