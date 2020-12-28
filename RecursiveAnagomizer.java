package anagramizer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RecursiveAnagomizer {

	//A method that parses the valid word file to a list of Strings. You may use a loop in the parsing method.

	public List<String> parseFile() throws FileNotFoundException {
		//Create List
		List <String> result = new ArrayList<>();

		//Create try catch to scan file, if not found throw exception
		try (Scanner sc = new Scanner((Readable) new FileReader("twl06.txt"))){
			while (sc.hasNext()) {
				result.add(sc.nextLine());
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		finally {
			System.out.println("Parsing file terminated.");
		}
		return result;
	}

	/* A nonrecursive method that prints the input String, makes the first call to the
	 * recursive anagramizer method, sends the result to the filter method, and prints 
	 * the filtered result. You may use this code:*/

	@SuppressWarnings("unused")
	private void anagramize(String inString) throws FileNotFoundException {
		System.out.println("input string: " + inString);
		String l = filter(anagramizeRecursive(inString.toLowerCase()));
		System.out.println("Anagrams: " + l);
	}

	/* A recursive method that takes a String as its argument and returns a list of 
	 * Strings which includes all anagrams of the String. This method will contain a 
	 * loop that generates each possible substring consisting of all but one character 
	 * in the input String, ie the substring that omits the first letter, then the 
	 * substring that omits the second letter, etc. Within the loop, the method calls itself 
	 * recursively for each substring. For each String in the list returned by the recursive call, 
	 * add the omitted character back to the end and add the String to the list to be returned. 
	 * When the first instance of this method returns, the list will contain all anagrams of the 
	 * original input String. It may help to work this out with a pencil and paper for a very short 
	 * string (like "abc".) The most straightforward base case (termination condition for the recursion) 
	 * is to return a list containing only a new empty String if the input String has length 0. If you 
	 * want a challenge, try replacing this algorithm with one that uses only recursion, with no loops.*/

	public static List<String> anagramizeRecursive(String stringInput) {
		//Create the List of data type String which will be returned
		List<String> anagramsString = new ArrayList<String>();
		
		//Base case as stated 
		if (stringInput.length() == 0) return (List<String>) anagramsString;

		//Return value at index 0
		Character ch = stringInput.charAt(0);
		
		//stringInput has to be greater than 1, 0 will return nothing
		if (stringInput.length() > 1) {
			stringInput = stringInput.substring(1);
			
			//Create a list with stringInput
			List<String> permSet = anagramizeRecursive(stringInput);
			
			//Iterate through List
			for (String stringIterate : permSet) {
				for (int i = 0; i <= stringIterate.length(); i++) {
					
					//Add the omitted character back to the end and add the String to the list to be returned
					anagramsString.add(stringIterate.substring(0, i) + ch + stringIterate.substring(i));

				}
			}
		}
		else {
			anagramsString.add(stringInput);
		}
		//Return list
		return (List<String>) anagramsString;
	}

	/* A nonrecursive method that starts the recursion for the filtering step. This method will take a list 
	 * of Strings, consisting of the anagrams, as its argument. Use a loop that takes each String in the list, 
	 * converts it to an array of Strings using String's split() method with a blank space as the argument, 
	 * and then uses the array to provide values for a list of Strings. The result of this will be a list of 
	 * Strings in which each String is a word from the anagram. Still inside the loop, call the recursive filter 
	 * method for each of these Strings. In each case when it receives a non-null String as the return value for 
	 * the recursive filter method, it will add the String to the list which it returns. */

	//Reference: https://beginnersbook.com/2015/05/java-string-to-arraylist-conversion/

	//This method will take a list of String, consisting of the anagrams as its arg
	public ArrayList<String> nonRecursive (List<String> listOfAnagrams) throws FileNotFoundException{
		//Create List that will be returned
		List <String> results = new ArrayList<String>();
		//Use a loop that takes each string in the list
		for (String stringOfList: listOfAnagrams) {
			//Converts it to an array of String using split()
			String [] split = stringOfList.split(" ");
			//Uses the array to provide values for a list of strings
			//List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
			List <String> listOfStrings = (ArrayList<String>) Arrays.asList(split);
			//Inside the loop, call the recursive filter method for each of these (listOfStrings) listOfStrings
			String str = filter(listOfStrings);
			//When it (str) receives a non-null String, it will add the String to the list which it returns (results)
			if(str != null) {
				results.add(str);
			}
		}
		return (ArrayList<String>) results;
	}

	/* A recursive filter method that takes a list of Strings and returns the following:
	 * - if all of the Strings in the list are contained in the list of valid words, return a single String made 
	 *   up of the Strings in the order in which they appear in the list
	 * - if any of the Strings in the list do not appear in the list of valid words, return null. This should be 
	 *   much more common than the first case.
	 * If a list is received for which the last String is in the word list, this method should recursively 
	 * call itself on a list consisting of all but the last word. If the recursive call returns a String (not null), 
	 * add the last word back to the end of the String and return it. This method should not contain any loops.*/
	
	public String filter(List<String> anagrams) throws FileNotFoundException {
		//If ALL of the strings in the list are contained in the list of valid words, return a single String
		if (parseFile().contains(anagrams.get(0))) {
			String returnedString = anagrams.get(0);
			return returnedString;
		}
		
		if (!(parseFile().contains(anagrams.get(0)))) return null;

		//String for first element
		String first = anagrams.get(0);
		anagrams.remove(0);
		//String for last element
		String last = filter(anagrams);
		//If the recursive call returns a string not null, add the last word back to the end of the String and return it
		if (last==null) {
			return null;
		}
		return first + " " + last;
	}

	public static void main(String[] args) {

		System.out.println(anagramizeRecursive("abc"));
		System.out.println(anagramizeRecursive("fat cat"));
//		System.out.println(anagramizeRecursive("way cool"));

		}


}


