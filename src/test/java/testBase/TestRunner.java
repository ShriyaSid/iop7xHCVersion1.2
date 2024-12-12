package testBase;

import org.testng.TestNG;


/**
 * @author Shriya Siddarth
   @version 1.2
 */
public class TestRunner {

	
	static TestNG t;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		t=new TestNG();
		t.setTestClasses(new Class[] {TestBase.class});
		t.run();
	}

}
