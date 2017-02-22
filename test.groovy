class MyTest extends GroovyTestCase {

	void testSomeOperation(){
		assert 1+2+3+4+5 == 3*5
	}

	void testSomeOtherOperation(){
		String s = "hello world"
		assertFalse s.size() < 3
	}

	void testSomeClass(){
		SomeClass object = new SomeClass()
		assertNotNull object
	}

}

class SomeClass {
	
}