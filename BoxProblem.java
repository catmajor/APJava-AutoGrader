import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
// Add imports here
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.lang.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.regex.*;
public class BoxProblem extends _test_runnerTestSuite {
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final InputStream originalIn = System.in;
	private ByteArrayInputStream inputStream; 
	private String testOutput(String input) {
	  outputStream.reset();
	  String[] args = new String[0];
	  inputStream = new ByteArrayInputStream(input.getBytes());
	  System.setIn(inputStream);
	  Main.main(args);
	  String output = outputStream.toString().trim();
	  return output;
	}
	private void boxOutputValid(int input) {
	  final int originalInput = input;
	  String output = testOutput(""+input+"\n");
	  Pattern numPattern = Pattern.compile("\\d+");
	  Matcher outputMatches = numPattern.matcher(output);
	  ArrayList<Integer> outputNums = new ArrayList<Integer>();
	  while(outputMatches.find()) {
	    String match = output.substring(outputMatches.start(), outputMatches.end());
	    outputNums.add(Integer.parseInt(match));
	  }
	  ArrayList<Integer> intsToCheck = new ArrayList<Integer>();
	  intsToCheck.add(input/50);
	  input%=50;
	  intsToCheck.add(input/20);
	  input%=20;
	  intsToCheck.add(input/5);
	  input%=5;
	  intsToCheck.add(input);
	  String concatOutputNums = "|";
	  String concatIntsToCheck = "|";
	  for (Integer value:outputNums) {
	    concatOutputNums += value.toString() + "|";
	  }
	  for (Integer value:intsToCheck) {
	    concatIntsToCheck += value.toString() + "|";
	  }
	  if (!concatOutputNums.contains(concatIntsToCheck)) {
	    throw new RuntimeException(
	      "(Fails) for input of " + originalInput + " boxes.\n" +
	      "Expected output for input of " + originalInput +
	      " should contain the values " + 
	      intsToCheck.get(0) + ", " + 
	      intsToCheck.get(1) + ", " +
	      intsToCheck.get(2) + ", " +
	      intsToCheck.get(3) +
	      ".\n" +
	      "Actual output:\n" +
	      output + "\n----------------"
	    );
	  }
	  return;
	}
	@Before
	public void setUp() {
	  // Add setup code here
	  System.setOut(new PrintStream(outputStream));
	  System.setIn(inputStream);
	}
	@After
	public void tearDown() {
	  // Add teardown code here
	  System.setOut(originalOut);
	  System.setIn(originalIn);
	}
	@Test
	public void IOTest () {
	  // Enter code here
	  boolean testFail = false;
	  int randomTestCase = (int)(Math.random()*100); //0-99 Test Case
	  ArrayList<Integer> testCases = new ArrayList<Integer>();
	  testCases.add(0);
	  testCases.add(1);
	  testCases.add(4);
	  testCases.add(21);
	  testCases.add(50);
	  testCases.add(139);
	  testCases.add(140);
	  testCases.add(149);
	  testCases.add(150);
	  testCases.add(1098);
	  testCases.add(1234123);
	  testCases.add(randomTestCase);
	  String fails = "\n----------------\n";
	  for (Integer testCase:testCases) {
	    try {
	      boxOutputValid(testCase.intValue());
	    } catch (Exception e) {
	      testFail = true;
	      fails += e.getMessage() + "\n";
	      continue;
	    }
	    fails +=  "(Passes) for input of " + testCase.toString() + " boxes \n----------------\n";
	  }
	  if (testFail) {
	    fail(fails);
	  }
	}
	@Test
	public void AllLinesAreLessThan80Chars () {
	Path filePath = Paths.get("Main.java"); // Replace with the actual path to your source code file
	    try {
	        String fileContent = Files.readString(filePath);
	        String[] lines = fileContent.split("\\r?\\n");
	        ArrayList<Integer> failLines = new ArrayList<Integer>();
	        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
	            String line = lines[lineNumber];
	            if (line.length() > 80) {
	                failLines.add(lineNumber + 1);
	            }
	        }
	        if (failLines.size()!=0) {
	          String failText = "Exceeds 80 chars at lines:";
	          for (Integer failNum:failLines) {
	            failText += (" " + failNum + ",");
	          }
	          failText = failText.substring(0, failText.length()-1);
	          fail(failText);
	        }
	    } catch (Exception e) {
	        fail("Problem reading the file: " + e.getMessage());
	    }
	}
	@Test
	public void ContainsComment () {

	  // Enter code here
	  Path filePath = Paths.get("Main.java");
	  String fileContent="";
	  try {
	    fileContent = Files.readString(filePath);
	  } catch (Exception e) {
	    fail("Problem Finding Instructor.java " + e);
	  }

	  fileContent = fileContent.replaceAll("\\s+","");
	  assertTrue(fileContent.contains("//"));

	}
	@Test
	public void CorrectIndents () {
	  //multi-line comments, parantheses
	  Path filePath = Paths.get("Main.java"); // Replace with the actual path to your source code file
	    try {
	      String fileContent = Files.readString(filePath);
	      String[] lines = fileContent.split("\\r?\\n");
	      int braceCount = 0;
	      ArrayList<Integer> failLines = new ArrayList<Integer>();
	      int expectedIndentation = 0;
	      for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
	        int consecutiveSlash = 0;
	        String line = lines[lineNumber].replace("\t", "    ");
	        if (line.trim().length() == 0) continue;
	        int parCounter = 0;
	        for(int pIndex = 0; pIndex<line.length(); pIndex++){
	          if (line.charAt(pIndex)=='/') consecutiveSlash++;
	          else consecutiveSlash = 0;
	          if (consecutiveSlash==2) {
	            break;
	          }
	          if(line.charAt(pIndex)=='('){
	            parCounter++;
	          }
	          if(line.charAt(pIndex)==')'){
	            parCounter--;
	          }
	        }
	        if (line.contains("}")) {
	          braceCount--;
	          if (braceCount < 0) {
	            fail("Mismatched braces before line " + (lineNumber + 1));
	          }
	        }
	        if(parCounter < 0){
	          braceCount--;
	        }
	        expectedIndentation = braceCount * 4;
	        int spaceCounter = 0;
	        for(int c = 0; c<line.length(); c++){
	          if(line.substring(c,c+1).equals(" ")){
	            spaceCounter++;
	          }
	          else {
	            c = line.length();
	          }
	        }
	        if (spaceCounter != expectedIndentation) {
	          failLines.add((lineNumber + 1));
	        }
	        if(line.contains("{")){
	          braceCount++;
	        }
	        if(parCounter > 0){
	          braceCount++;
	        }
	        
	      }
	      if (braceCount != 0) {
	        fail("Mismatched braces at the end of the file");
	      }
	      if (failLines.size()!=0) {
	        String failText = "Incorrect indents at lines:";
	        for(Integer lineNum: failLines) {
	          failText += (" "+ lineNum + ",");
	        }
	        failText = failText.substring(0, failText.length()-1);
	        fail(failText);
	      }
	    } catch (Exception e) {
	      fail("Problem reading the file: " + e.getMessage());
	    }
	}
	@Test
	public void ScannerTest () {
	  Path filePath = Paths.get("Main.java");
	  String fileContent="";
	  try {
	    fileContent = Files.readString(filePath);
	    String[] lines = fileContent.split("\\r?\\n");
	    String a = "";
	    int scannerLine = -1;
	    for(int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
	      lines[lineNumber] = lines[lineNumber].replaceAll("\\s", "");
	      if(lines[lineNumber].length() > 7 && lines[lineNumber].substring(0, 7).equals("Scanner")){
	        int eqind = lines[lineNumber].indexOf("=");
	        a = lines[lineNumber].substring(7, eqind);
	        scannerLine = lineNumber;
	        break;
	      }
	    }
	    if(scannerLine == -1){
	      fail("Scanner not initialized");
	    }
	    for(int q = scannerLine; q<lines.length; q++){
	      if(lines[q].trim().startsWith(a+".close()")){
	        return;
	      }
	    }
	    fail("Scanner not closed");
	  } catch (Exception e) {
	    fail("Problem Finding Instructor.java " + e);
	  }
	}
}