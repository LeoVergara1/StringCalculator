import java.util.regex.Pattern
class StringCalculatorTest extends GroovyTestCase{

	void testNull() {
		StringCalculator obj = new StringCalculator()
		int result = obj.add(null)
		assert result == 0
	}
	void testEmpty() {
		StringCalculator obj = new StringCalculator()
		int result = obj.add("")
		assert result == 0
	}
	void testOneNumber(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("1")
		assert result == 1
		result = obj.add("3")
		assert result == 3 
	}
	void testTwoNumber(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("1,2")
		assert result == 3
		result = obj.add("3,9")
		assert result == 12 
	}
	void testAnyNumber(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("1,2,3,4,5")
		assert result == 15
		result = obj.add("3,9,4,20,10,2")
		assert result == 48 
	}
	void testAnyNumberOtherSeparator(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("1\n2,3\n4,5")
		assert result == 15
		result = obj.add("3,9\n4,20\n10\n2")
		assert result == 48
	}
	void testSeparator(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("//;\n1;2;3;4;5")
		assert result == 15
		result = obj.add("//=\n3=9=4=20=10=2")
		assert result == 48	
	}

	void testNegativeNumbersNotAllowed(){
		StringCalculator obj = new StringCalculator()
		def message = shouldFail {
			obj.add("1,-2,3,-4,5,-6,-7,8")
		}
		assert message == "No se aceptan números negativos [-2, -4, -6, -7]"
	}
	void testNumberbigger(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("//;\n1;2;3;4;5;1001")
		assert result == 15
		result = obj.add("//=\n3=9=4=20=10=2=3000")
		assert result == 48	
	}
	void testDelimitersAnylength(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("//[***]\n1***2***3")
		assert result == 6
		result = obj.add("//=\n3=9=4=20=10=2;3000")
		assert result == 48	
	}
	void testMultipleDelimiters(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("//[*][%]\n1*2%3*4")
		assert result == 10
	}

	void testMultipleDelimitersMultipleSizes(){
		StringCalculator obj = new StringCalculator()
		int result = obj.add("//[**][%%%]\n1**2%%%3**4")
		assert result == 10
	}

}

class StringCalculator{	
	int add(String numbers){
		if (!numbers){	
			return 0
		}
		if(numbers.size()>1 && !Pattern.compile(/-\d/).matcher(numbers)){
			numbers = numbers.replaceAll(/[^,\d]+/,",").replaceFirst(/^,*/,"").replaceAll(/,\d{4}+/,"")
			def lista = numbers.split(",").collect() {it.toInteger()}
			return lista.sum()
		}
		if(Pattern.compile(/-\d/).matcher(numbers)){
 			ArrayList stringNegatives = (numbers =~ /-\d/).collect(){it} // (numbers =~ /-\d/) Recolección de numeros negativos
 			throw new Exception("No se aceptan números negativos ${stringNegatives}")
		}	
	   numbers.toInteger()
	} 
}