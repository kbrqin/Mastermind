import java.util.Scanner;
import java.io.*;
/*
 * Kim Guo
 * ICS3U1-02
 * December 9, 2020 - December 17, 2020
 * ISP: Mastermind
 */

public class Mastermind {
	static final String[] COLOURS= {"B", "G", "Y", "W", "P", "R"}; //blue, green, yellow, white, purple, red
	
	/**
	 * Write the parameters given into a file in the format of a Mastermind board.
	 * @param strGuesses is a string array of the code breaker's guesses
	 * @param strFeedback is a string array of the code maker's feedback for each guess
	 * @param hiddenCode is the code that the code breaker is trying to guess
	 * @param intGame is the game number
	 * @throws Exception
	 */
	public static void writeToFile(String[] strGuesses, String[] strFeedback, String hiddenCode, int intGame) throws Exception{
		File board=new File("decodingBoard"+intGame+".txt"); //creating a file object to store the decoding board for the respective game
		PrintWriter outToBoard=new PrintWriter(board); //creating a printwriter to write to the file that stores the decoding board
		if(intGame%2==1) { //if the game number is odd (it's a game where the user is the code breaker) - the hidden code will be pointed towards the opponent (at the top of the board)
			//The next few lines create the area for the hidden code
			outToBoard.println("========================================"); //outputting a border to make the output look like a board
			for(int i=0;i<hiddenCode.length();i++) { //looping through the hidden code
				outToBoard.print(hiddenCode.charAt(i)+"\t"); //outputting the characters of the hidden code to the file, each character separated by a tab
			}
			outToBoard.println("<- CODE"); //specifying that the line holds the hidden code
			outToBoard.println("========================================"); //outputting a border to make the output look like a board
			outToBoard.println("========================================"); //outputting a border to make the output look like a board
			
			//The next few lines create the lines for the decoding board where the guesses go
			for(int i=strGuesses.length-1;i>=0;i--) { //looping through each guess
				//The code breaker's guess
				if(strGuesses[i].length()==0) { //if the guess is empty (if there hasn't been a guess yet)
					for(int j=0;j<4;j++) { //looping through
						outToBoard.print("O"+"\t"); //output "O"s to represent unfilled pegs, separated by tabs
					}
				}
				else { //if the guess is not empty (if there has been a guess)
					for(int j=0;j<strGuesses[i].length();j++) { //looping through each character in the guess
						outToBoard.print(strGuesses[i].charAt(j)+"\t"); //outputting the characters of the hidden code to the file, each character separated by a tab
					}
				}
				
				//The feedback for the respective guess
				for(int j=0;j<strFeedback[i].length();j++) { //outputting the feedback for the guess on the same line as the guess
					outToBoard.print(strFeedback[i].charAt(j)+" "); //outputting the characters of the feedback separated by spaces
				}
				outToBoard.println(""); //outputting a line break
				outToBoard.println("========================================"); //outputting a border to make the output look like a board
			}
		}
		else { //if the game number is even (if it's a game where the user is the code maker) - the hidden code will be pointed towards the user (at the bottom of the board)
			//The next few lines create the lines for the decoding board where the guesses go
			outToBoard.println("========================================"); //outputting a border to make the output look like a board
			for(int i=0;i<strGuesses.length;i++) { //looping through each guess
								
				if(strGuesses[i].length()==0) { //if the guess is empty (if there hasn't been a guess yet)
					for(int j=0;j<4;j++) outToBoard.print("O"+"\t"); //output "O"s to represent unfilled pegs, separated by tabs
				}
				else {
					for(int j=0;j<strGuesses[i].length();j++) { //looping through each character in the guess
						outToBoard.print(strGuesses[i].charAt(j)+"\t"); //output an "O" to represent an unfilled peg
					}
				}
				//The feedback for the respective guess
				for(int j=0;j<strFeedback[i].length();j++) outToBoard.print(strFeedback[i].charAt(j)+" "); //outputting each character of the feedback, separated by a space
				outToBoard.println(""); //outputting a line break
				outToBoard.println("========================================"); //outputting a border to make the output look like a board
			}
			//The next few lines create the area for the hidden code
			outToBoard.println("========================================"); //outputting a border to make the output look like a board
			for(int i=0;i<hiddenCode.length();i++) outToBoard.print(hiddenCode.charAt(i)+"\t"); //outputting the characters of the hidden code, separated by tabs
			outToBoard.println("<- CODE"); //specifying that the characters on the respective line is the hidden code
			outToBoard.println("========================================"); //outputting a border to make the output look like a board
		}
		outToBoard.close(); //closing the file
	}
	
	/**
	 * Append the number of points for the code maker into the file.
	 * @param intPoints is the number of points that the code maker got in the current game.
	 * @param intGame is the game number 
	 * @throws Exception
	 */
	public static void writeToFile(int intPoints, int intGame) throws Exception{
		File pointsFile=new File("points.txt"); //creating a file object for the points file
		PrintWriter outToPoints=new PrintWriter(new FileWriter(pointsFile, true)); //creating a printwriter that would append to the points file
		outToPoints.println("------------"); //writing a border for user friendliness in the file and the console
		outToPoints.println("Game "+intGame+":"+"\n"); //writing the game number and a line break for user friendliness
		if(intGame%2==1) {//if the game number is odd (it's a game where the user is the code breaker) 
			outToPoints.println("Computer: "+intPoints+" point(s)"); //writing the number of points for the computer
		}
		else { //if the game number is even (it's a game where the user is the code maker)
			outToPoints.println("You: "+intPoints+" point(s)"); //writing the number of points for the user
		}
		outToPoints.close(); //closing the points file
	}
	
	/**
	 * Wipe the points file.
	 * @throws Exception
	 */
	public static void wipePointsFile() throws Exception{
		File pointsFile=new File("points.txt"); //creating a file object for the points file
		PrintWriter outToPoints=new PrintWriter(pointsFile); //create a printwriter that would write to the file
		outToPoints.print(""); //write an empty line without terminating the line (this wipes the file)
		outToPoints.close(); //close the points file
	}
	
	/**
	 * Output the board in the file whose name corresponds with the game number
	 * @param intGame is the game number
	 * @throws Exception
	 */
	public static void outputBoard(int intGame) throws Exception{
		File board=new File("decodingBoard"+intGame+".txt"); //creating a file object for the decoding board corresponding to the game number
		Scanner boardScan=new Scanner(board); //creating a scanner that would read from the file
		while(boardScan.hasNext()) { //reading from the file
			String strLine=boardScan.nextLine(); //reading each line from the file and storing it in a string variable strLine
			System.out.println(strLine); //outputting each line to the console
		}
		boardScan.close(); //closing the scanner for the board
	}
	
	/**
	 * Read from the points file and output the number of points for each player to the console.
	 * @throws Exception
	 */
	public static void outputPoints() throws Exception{
		File pointsFile=new File("points.txt"); //creating a file object for the points file
		Scanner pointsScan=new Scanner(pointsFile); //creating a scanner that would read from the file
		while(pointsScan.hasNext()) { //reading from the points file
			String strLine=pointsScan.nextLine(); //reading each line of the file and storing it in a string variable strLine 
			System.out.println(strLine); //outputting the line to the console
		}
		pointsScan.close(); //closing the scanner for the points file
	}

	/**
	 * Create a randomly generated 4-character code.
	 * @return a randomly generated 4-character code
	 */
	public static String createCode() {
		String strCode=""; //creating a string that holds the code and initializing it to an empty string
		for(int i=0;i<4;i++) { //looping 4 times
			int colourIndex=(int)(Math.random()*COLOURS.length); //randomly generating a number for the index of the COLOURS array
			strCode+=COLOURS[colourIndex]; //concatenating the value of COLOURS at index colourIndex to strCode
		}
		return strCode; //return the code
	}
	
	/**
	 * Check whether the code is valid (if it is four characters and only uses the available characters, it is valid).
	 * @param code is a string containing the hidden code
	 * @return true if the code is valid, false if it is invalid
	 */
	public static boolean codeIsValid(String code) {
		if(code.length()!=4) return false; //if the code is not 4 characters long, it is invalid. return false
		else{ //if the length of the code is 4 characters
			for(int i=0;i<code.length();i++) { //looping through the code
				if("BGYWPR".indexOf((int) code.charAt(i))==-1) { //if the character of the code is not a valid character
					return false; //return false
				}
			}
		}
		return true; //if the code is 4 characters long and doesn't contain any invalid characters, it is valid. return true
	}

	/**
	 * Check whether the feedback is valid (if it is four characters and only uses the available characters, it is valid).
	 * @param feedback is a string containing the hidden code
	 * @return true if the code is valid, false if it is invalid
	 */
	public static boolean feedbackIsValid(String feedback) {
		if(feedback.length()!=4) return false; //if the feedback is not 4 characters long, it is invalid. return false
		else{ //if length of the feedback is 4 characters
			for(int i=0;i<feedback.length();i++) { //looping through the feedback
				if("BWX".indexOf((int) feedback.charAt(i))==-1) { //if the character of the feedback string is not a valid character	
					return false; //return false
				}
			}
		}
		return true; //if the code is 4 characters long and doesn't contain any invalid characters, it is valid. return true
	}
	
	/**
	 * Give feedback to the code breaker's guess using the characters 'B', 'W', or 'X' depending on whether the position and colour are correct.
	 * @param strGuess is the code breaker's guess
	 * @param strCode is the code maker's hidden code
	 * @return a string of four elements containing the characters 'B', 'W', or 'X' depending on whether the position and colour are correct.
	 */
	public static String feedback(String strGuess, String strCode) {
		String code=strCode, guess=strGuess; //creating two string variables to store the values of strCode and strGuess so the original values remain unchanged
		String strFeedback=""; //creating a string that will store the feedback for the guess
		for(int i=0;i<guess.length();i++) { //1st loop through the guess 
			if(guess.charAt(i)==code.charAt(i)) { //if the position and colour are the same				
				strFeedback+="B"; //the feedback should be a "B". concatenate it to strFeedback
				guess=guess.substring(0,i)+"-"+guess.substring(i+1); //in the guess string, replace the character that is being worked with with a "-" to avoid extra 'B's
				code=code.substring(0,i)+"_"+code.substring(i+1); //in the code string, replace the character that is being worked with with a "_" to avoid extra 'B's
			}
		}
				
		for(int i=0;i<guess.length();i++) { //second loop through the guess
			if(guess.charAt(i)!=code.charAt(i) && code.indexOf(guess.charAt(i))!=-1) {	 //if the colour is in the hidden code but the position is incorrect		
				char letter=guess.charAt(i); //create a char variable to hold the character that is being worked with
				guess=guess.substring(0,guess.indexOf(letter))+"-"+guess.substring(guess.indexOf(letter)+1); //in the guess string, replace the character that is being worked with with a "-" to avoid extra 'B's
				code=code.substring(0,code.indexOf(letter))+"_"+code.substring(code.indexOf(letter)+1); //in the code string, replace the character that is being worked with with a "_" to avoid extra 'B's
				strFeedback+="W";  //concatenate a "W" to strFeedback
			}
		}
		
		if(strFeedback.length()<4) { //if the length of strFeedback is not 4 characters
			int feedbackLength=strFeedback.length(); //store the current length of the feedback string in an integer variable feedbackLength
			for(int i=0;i<4-feedbackLength;i++) { //looping the number of times needed to make strFeedback 4 characters long
				strFeedback+="X"; //concatenating an "X" to strFeedback (the position and colour were both incorrect)
			}
		}
				
		return strFeedback; //return the feedback string
	}
	
	/**
	 * Output that it is the user's turn.
	 */
	public static void userTurn() {
		System.out.println("---"+"\n"+"Your turn!"); //output that it's the user's turn with a border before it for user friendliness
	}
	
	/**
	 * Output that it is the computer's turn.
	 */
	public static void computersTurn() {
		System.out.println("---"+"\n"+"Computer's turn!"); //output that it's the computer's turn with a border before it for user friendliness
	}
	
	/**
	 * Output the description of the game and how to play it to the console.
	 */
	public static void outputGameDesc() {
		System.out.println("Game description:"); //specifing that the next few lines will be the game description
		System.out.println(""); //outputting an empty line for user friendliness
		System.out.println("Welcome to Mastermind! This is a game that requires two players: a code maker and a code breaker. You and the computer will "+"\n"
				+ "take turns being the code maker and the code breaker. The code maker creates a four-character code using the characters 'B'(blue), 'G' (green),"+"\n"
				+ "'Y' (yellow), 'W' (white), 'P' (purple), and 'R' (red), and the code breaker has 10 tries to guess the code that the code maker creates. After"+"\n"
				+ "each guess, the code maker will give the code breaker feedback. The code maker will type a 'B' (black) for each character that is the correct colour"+"\n"
				+ "and is in the correct position, a 'W' (white) for each character that is the correct colour but is in the wrong position, and an 'X' for each "+"\n"
				+ "character that is the wrong colour and the wrong position. The feedback code should prioritize 'B's first, then 'W's, and finally 'X's."+"\n"
				+ "You will play an even number of games. After each game, if the code breaker guessed the correct code,"+"\n"
				+ "the code maker will get the number of points corresponding with the number of guesses the code breaker took. If the code breaker was unable to "+"\n"
				+ "guess the correct code, the code maker would get 11 points. At the end of the games, if you got the highest number of points, you win!"+"\n"
				+ "You will be trusted to provide the correct feedback!"+"\n"+"\n"
				+ "Note: The code and feedback should not contain any spaces."+"\n"+"\n"
				+ "You will decide how many games you want to play before beginning the game. You must play an even number of games to ensure that "+"\n"
				+ "each player gets an equal number of turns as the code maker and the code breaker."); //outputting the game description
		System.out.println("---"); //outputting a border for user friendliness
	}
	
	/**
	 * Return a randomly generated code based on the feedback from the previous guess.
	 * @param prevGuess is a String variable containing the previous guess.
	 * @param strFeedback is the feedback for the previous guess.
	 * @return a new randomly generated 4-character code based on the feedback for the previous guess.
	 */
	public static String computerGuess(String prevGuess, String strFeedback) {
		String feedback=strFeedback; //storing the value of strFeedback in a new string to avoid altering the original value
		String guess="XXXX"; //initializing the guess to four 'X's that are placeholders for characters that it will guess
		while(feedback.indexOf((int)'B')!=-1) { //while there is still a 'B' in the feedback
			int randomIndex; //declare an integer variable to randomly select an index
			do {
				randomIndex=(int)(Math.random()*4); //randomly generating a number between 0 and 3 inclusive (the indices of the 4-character guess)
			}while(guess.charAt(randomIndex)!='X'); //ensure randomIndex is not an index that already has a guess (that isn't 'X')
			char charToKeep=prevGuess.charAt(randomIndex); //get the index of the character from the previous guess and store it in the char variable charToKeep
			guess=guess.substring(0,randomIndex)+charToKeep+guess.substring(randomIndex+1); //replacing the 'X' placeholder with the character that will be kept the same at the same index as the previous guess
			feedback=feedback.substring(0,feedback.indexOf((int)'B'))+"_"+feedback.substring(feedback.indexOf((int)'B')+1); //replacing the 'B' in the feedback string with an underscore to prevent extra characters being kept from the previous guess
		}

		while(feedback.indexOf((int)'W')!=-1) { //while there is still a 'W' in the feedback
			int randomIndex; //declare an integer variable to randomly select an index
			do {
				randomIndex=(int)(Math.random()*4); //randomly generating a number between 0 and 3 inclusive (the indices of the 4-character guess)
			}while(guess.charAt(randomIndex)!='X'); //ensure randomIndex is not an index that already has a guess (that isn't 'X')
			char charToKeep=prevGuess.charAt((int)(Math.random()*4)); //randomly choose a character to keep the same
			guess=guess.substring(0,randomIndex)+charToKeep+guess.substring(randomIndex+1); //replacing the 'X' placeholder with the randomly selected character that will be kept from the previous guess
			feedback=feedback.substring(0,feedback.indexOf((int)'W'))+"_"+feedback.substring(feedback.indexOf((int)'W')+1); //replacing the 'W' in the feedback string with an underscore to prevent extra characters being kept from the previous guess
		}
		
		while(guess.indexOf((int)'X')!=-1) { //while there are still 'X's in the guess string 
			int indexOfX=guess.indexOf((int)'X'); //find the index of 'X' in the guess string and store its index in an integer variable indexOfX
			int colourIndex; //declaring an integer variable that will hold the colour index
			colourIndex=(int)(Math.random()*COLOURS.length); //randomly generate an index of the COLOURS array and storing the index value in colourIndex
			guess=guess.substring(0,indexOfX)+COLOURS[colourIndex]+guess.substring(indexOfX+1); //replacing the 'X' placeholder with the randomly generated character
		}
		return guess; //return the guess
	}
	
	public static void main(String[] args) throws Exception{
		Scanner consoleScan=new Scanner(System.in); //create a scanner that will scan from the console
		String strPlayAgain=""; //declaring and initializing a string variable that will hold the user's input when asked if they want to play again or not
		boolean playAgain=true; //declaring a boolean variable that is true if the user wants to play again, false if the user does not want to play again
		outputGameDesc(); //call the outputGameDesc method to output the game description
		System.out.println("Are you ready? Let's play!"); //informing the user that the game will begin now
		
		do {
			int intGames=-1; //declaring an integer variable for the number of games that will be played and initializing it to an invalid number
			do {
				System.out.print("How many games would you like to play? "); //prompting the user for the number of games they would like to play
				try{ //using a try catch to avoid input mismatch error
					intGames=consoleScan.nextInt(); //getting and storing the number of games that the user would like to play
					if(intGames%2!=0 || intGames<=0) System.out.println("You must enter a positive even number."); //if the user enters an invalid number, output that they must enter a positive even number
				}
				catch(Exception e) { //catching an input mismatch exception
					System.out.println("Invalid input. Please enter a positive even number."); //output that the user must enter a positive even number
					consoleScan.nextLine(); //clearing the erroneous input
				}
			}while(intGames%2!=0 || intGames<=0); //ensure that the user's input is valid
			consoleScan.nextLine(); //avoiding a Scanner.nextLine() error
			
			int intGameNum=0; //game number count
			int intPoints=0; //declaring an integer variable for the number of points for the code maker each round
			String[] strGuesses=new String[10]; //declaring a string array to store the code breaker's guesses 
			String[] strFeedback=new String[10]; //declaring a string array to store the code maker's feedback
			
			
			//initializing elements in arrays to empty strings in the guess array and strings of 4 underscores in the feedback array
			for(int i=0;i<strGuesses.length;i++) { //looping through the strGuesses array 
				strGuesses[i]=""; //initialize each element in the strGuesses array to an empty array
				strFeedback[i]="____"; //initialize each element in the strFeedback array to a string of 4 underscores
			}
			
			//The next lines will be the code for the game.
			wipePointsFile(); //clearing the points file for the new game
			for(int i=0;i<intGames;i++) { //looping for the number of games that the user decided that they wanted to play
				intGameNum++; //incrementing the game number 
				System.out.println("Game "+intGameNum+"!"); //outputting which game this is
				
				//reinitializing the elements in the array for each new game
				for(int j=0;j<strGuesses.length;j++) { //looping through the strGuesses array 
					strGuesses[j]=""; //initialize each element in the strGuesses array to an empty array
					strFeedback[j]="____"; //initialize each element in the strFeedback array to a string of 4 underscores
				}
				
				String hiddenCode; //declaring a string variable for the hidden code
				
				if(intGameNum%2==1) { //if the game number is odd (if the user is the code breaker)
					System.out.println("You will be the code breaker."); //output that the user will be the code breaker
					hiddenCode=createCode(); //call the createCode method and store the code created in the string variable hiddenCode
				}
				else { //if the game number is even (if the user is the code maker)
					System.out.println("You will be the code maker."); //output that the user will be the code maker
					do{
						System.out.print("Enter the hidden code: "); //prompting the user to enter the hidden code
						hiddenCode=consoleScan.nextLine(); //getting and storing the user input into the string variable hiddenCode
						if(!codeIsValid(hiddenCode)) { //call the codeIsValid method to check if the code is valid, if it's not valid
							System.out.println("Your code is invalid. It should be 4 digits and only contain \"B\", \"G\", \"Y\", \"W\", \"P\", or \"R\"."); //output an invalid message
						}
					}while(!codeIsValid(hiddenCode)); //ensure that the user inputted code is valid
				}
				
				writeToFile(strGuesses, strFeedback, hiddenCode, intGameNum); //call the writeToFile method that writes the decoding board to a file whose name corresponds with the game number
				outputBoard(intGameNum); //call the outputBoard method to output the board
						
				//guess and feedback
				if(intGameNum%2==1) { //if the game number is odd (if the user is the code breaker)
					
					boolean codeBroken=false; //declare a boolean variable that is true if the code is broken, false if it is not broken
					int guessCount=0; //declare a counter variable for the number of guesses 
					do {
						userTurn(); //call the userTurn method to tell the user that it's their turn
						do{
							System.out.print("Enter your guess: "); //prompt the user for a guess
							strGuesses[guessCount]=consoleScan.nextLine(); //store the user's guess in its respective index in the strGuesses array
							if(!codeIsValid(strGuesses[guessCount])) { //call the codeIsValid method, if it's not valid
								System.out.println("Your guess is invalid. It should be 4 digits and only contain \"B\", \"G\", \"Y\", \"W\", \"P\", or \"R\"."); //output an invalid message
							}
							
						}while(!codeIsValid(strGuesses[guessCount]) && guessCount<10); //ensure that the user's guess is valid and that not all the guesses have been used up
						
						
						String feedback=feedback(strGuesses[guessCount], hiddenCode); //call the feedback method and store its return value in a string variable feedback
						strFeedback[guessCount]=feedback; //storing the feedback in its respective index in the strFeedback array
						
						computersTurn(); //call the computersTurn method to tell the user that it's the computer's turn
						
						System.out.print("Feedback: "); //describe the next output
						for(int j=0;j<feedback.length();j++) System.out.print(feedback.charAt(j)+" "); //output the feedback by looping through the string and outputting each character, space separated
						System.out.println(""); //terminate the current line
											
						writeToFile(strGuesses, strFeedback, hiddenCode, intGameNum); //write the current values to the decoding board file
						System.out.println("Board: "); //describe the next output
						outputBoard(intGameNum); //output the board with its filled in line(s) for the guess(es)
						
						if(feedback.equals("BBBB")) { //if the code is guessed correctly
							codeBroken=true; //set codeBroken to true
							System.out.println("Congratulations, you broke the code!"); //output a congratulations message
						}
						else if(guessCount!=9) { //if it's not the 10th guess (we increment at the end) yet
							System.out.println("Try again!"); //output a try again message
						}
						else System.out.println("Sorry, you failed!"); //if the user ran out of guesses and didn't break the code, output a fail message
						
						guessCount++; //increment the guesses counter
						
						if(guessCount<=10 && codeBroken) intPoints=guessCount; //if the code is broken and the number of guesses is smaller than or equal to 10, set the number of points to the number of guesses
						else if(!codeBroken) intPoints=guessCount+1; //if the code is not broken after 10 guesses, set the number of points to 11
						
					}while(!codeBroken && guessCount<10); //loops while the code is not broken and the number of guesses is smaller than 10
					
					writeToFile(intPoints, intGameNum); //write the number of points for the code maker to the points file
					
				}
				
				else { //if the game number is even (if the user is the code maker)
					
					boolean codeBroken=false; //declare a boolean variable that is true if the code is broken, false if it is not broken 
					int guessCount=0; //declare a counter variable for the number of guesses
					
					do{					
						computersTurn(); //call the computersTurn method to tell the user that it's the computer's turn
						if(guessCount==0) { //if there's no previous guess yet
							strGuesses[guessCount]=computerGuess("XXXX", "XXXX"); //call the computerGuess method to produce a random guess without a previous guess or feedback for it
						}
						else { //if there are more than 0 guesses
							strGuesses[guessCount]=computerGuess(strGuesses[guessCount-1], strFeedback[guessCount-1]); //call the computerGuess method to produce a random guess based on the previous guess and feedback for it
						}
											
						System.out.println("Computer's guess: "+strGuesses[guessCount]); //output the computer's guess
						
						userTurn(); //call the usersTurn method to tell the user that it's their turn

						do{
							System.out.print("Give your feedback: "); //prompt the user for feedback
							strFeedback[guessCount]=consoleScan.nextLine(); //get and store the user feedback to its appropriate index in the strFeedback array
							if(!feedbackIsValid(strFeedback[guessCount])) { //by calling the feedbackIsValid method, if the feedback is not valid
								System.out.println("Your feedback is invalid. It should be 4 digits and only contain \"B\", \"W\", or \"X\"."); //output an invalid message
							}
						}while(!feedbackIsValid(strFeedback[guessCount])); //ensure that the feedback is valid					
						
						writeToFile(strGuesses, strFeedback, hiddenCode, intGameNum); //write the game's decoding board to its appropriate file
						
						System.out.println("Board: "); //describe what will be outputted next
						outputBoard(intGameNum); //output the game board
						
						if(strFeedback[guessCount].equals("BBBB")) { //if the code is guessed correctly
							codeBroken=true; //set codeBroken to true
							System.out.println("The computer broke the code!"); //output that the computer broke the code
						}
						
						else if(guessCount==9) System.out.println("Congratulations, the computer was unable to break the code!"); //if the computer wasn't able to guess the code within 10 tries, output a congratulations message
						
						guessCount++; //increment the guessCount counter
						
						if(guessCount<=10 && codeBroken) intPoints=guessCount; //if the code is broken and the number of guesses is smaller than or equal to 10, set the number of points to the number of guesses
						else if(!codeBroken) intPoints=guessCount+1; //if the code is not broken after 10 guesses, set the number of points to 11
					}while(!codeBroken && guessCount<10); //loops while the code is not broken and the number of guesses is smaller than 10
					
					writeToFile(intPoints, intGameNum); //write the number of points for the code maker to the points file
				}
				
				//ask the user if they want to see the points log
				String strPointsOrNot; //declare a string variable called strPointsOrNot
				do {
					System.out.print("Do you want to see the points log? (Y/N) "); //ask the user if they want to see the points log
					strPointsOrNot=consoleScan.nextLine(); //get and store the user's answer in strPointsOrNot 
					if(strPointsOrNot.equals("Y")) { //if the user wants to see the points log
						System.out.println("Points Log: "); //describe what will be outputted next
						outputPoints(); //call the outputPoints method
						System.out.println("------------"); //output a border for user friendliness in the console
					}
					else if(!(strPointsOrNot.equals("Y") || strPointsOrNot.equals("N"))){ //if the user did not input 'Y' or 'N'
						System.out.println("Enter 'Y' or 'N'."); //output that the user needs to enter 'Y' or 'N'
					}
				} while(!(strPointsOrNot.equals("Y") || strPointsOrNot.equals("N"))); //ensure that the user enters 'Y' or 'N'
			}
			
			//accumulating the points from each game
			int computerPoints=0, userPoints=0; //declare two accumulator variables for the computer's points and the user's points
			
			File pointsFile=new File("points.txt"); //create a file object for the points file
			Scanner pointsScan=new Scanner(pointsFile); //create a scanner that will read from the points file
			while(pointsScan.hasNext()) { //reading from the file
				int points=0; //declaring an integer variable to hold the points for each player
				
				String player=pointsScan.next(); //read by word in the file and store each word in the String variable player
				
				if(pointsScan.hasNextInt()) { //if the next input is an integer
					points=pointsScan.nextInt(); //store the integer value in the points variable
				}
				
				if(player.equals("Computer:")) computerPoints+=points; //if the value of player is equal to "Computer:" accumulate the points to computerPoints
				else if(player.equals("You:")) userPoints+=points; //if the value of player is equal to "You:" accumulate the points to userPoints
			}
			pointsScan.close(); //close the scanner for the points file
			
			//outputting results
			System.out.println("------------"); //output a border for user friendliness
			if(userPoints>computerPoints) System.out.println("You won!"); //if userPoints is greater than computerPoints output that the user won
			else if(userPoints<computerPoints) System.out.println("The computer won! Good try :)"); //if userPoints is smaller than computerPoints output that the computer won
			else System.out.println("Tie!"); //if userPoints is equal to computerPoints output a tie message 
			System.out.println("Computer points: "+computerPoints+" point(s)"); //output the total points for the computer
			System.out.println("Your points: "+userPoints+" point(s)"); //output the total points for the user
			System.out.println("------------"); //output a border for user friendliness
			
			//ask the user if they 
			String seeGameBoards; //declare a string variable called seeGameBoards
			boolean seeBoards=true; //declare a boolean variable called seeBoards and initializing it to true 
			System.out.print("Would you like to see the game board for any of the games you played? (Y/N) "); //ask the user if they want to see the game board for any of the games played
			seeGameBoards=consoleScan.nextLine(); //get and store the user input in the seeGameBoards variable
			
			while(seeBoards){ //while the boolean seeBoards is true
				if(seeGameBoards.equals("Y")) { //if the user wants to see a game board
					System.out.println("Games played: "); //describing the next output
					for(int i=0;i<intGames;i++) { //looping the number of times based on the number of games played
						System.out.print((i+1)+"\t"); //outputting each game's number separated by a tab
					}
					System.out.println(""); //terminating the line
					
					int boardNum=-1; //declaring an integer variable for the board number that will be outputted and initializing it to an invalid number
					do{
						System.out.print("Please input the game number that you want to see the board for. "); //prompt the user for the game number they want to see the board for
						try { //using a try catch to avoid input mismatch error
							boardNum=consoleScan.nextInt(); //get and store the user input into boardNum
							if(boardNum<=0 || boardNum>intGames) System.out.println("Please enter a number between 1 and "+intGames+" inclusive."); //if boardNum is invalid, output an invalid message
						}
						catch(Exception e){ //catching the input mismatch exception
							System.out.println("Please enter a number between 1 and "+intGames+" inclusive."); //output an invalid message
							consoleScan.nextLine(); //clear the erroneous input
						}
						consoleScan.nextLine(); //avoiding the Scanner.nextLine() error
						if(boardNum>0 && boardNum<=intGames) { //if the board number is valid
							System.out.println("Board "+boardNum+":"); //describe what will be outputted next
							outputBoard(boardNum); //outputting the board
						}
					}while(boardNum<=0 || boardNum>intGames); //ensure that boardNum is a valid number, loops while it's invalid
					
					System.out.print("Would you like to see another game board? (Y/N) "); //ask the user if they want to see another board
					seeGameBoards=consoleScan.nextLine(); //get and store the user input in seeGameBoards
				}
				else if(seeGameBoards.equals("N")) seeBoards=false; //if the user doesn't want to see the game boards, set seeBoards to false
				else { //if the user entered an invalid input
					System.out.print("Please enter 'Y' or 'N'. "); //output that the user needs to enter 'Y' or 'N'
					seeGameBoards=consoleScan.nextLine(); //get and store the user input in seeGameBoards
				}
			}
			
			//deleting the files from the game
			for(int i=1;i<=intGames;i++) { //going through all of the files that held the board from the respective game
				File board=new File("decodingBoard"+i+".txt"); //creating a file object for each decoding board of the game
				board.deleteOnExit(); //deleting the decoding board file
			}
			
			pointsFile.deleteOnExit(); //deleting the points file
			
			System.out.print("Would you like to play again? (Y/N) "); //ask the user if they want to play again
			do {
				strPlayAgain=consoleScan.nextLine(); //get and store the user input into the string variable strPlayAgain
				if(!(strPlayAgain.equals("Y") || strPlayAgain.equals("N"))) { //if the user input is invalid
					System.out.print("Please enter 'Y' or 'N'. "); //output an error message and ask them to enter the correct input
				}
				else if(strPlayAgain.equals("N")) playAgain=false; //if the user does not want to play again, set playAgain to false
			}while(!(strPlayAgain.equals("Y") || strPlayAgain.equals("N"))); //ensure that the user is entering valid input
			
		}while(playAgain); //the game loops as long as the user wants to play again
		
		consoleScan.close(); //discarding the console scanner
		
		System.out.println("------------"); //output a border for user friendliness
		System.out.println("Thank you for playing!"); //output a thank you for playing message
		
	}
}
