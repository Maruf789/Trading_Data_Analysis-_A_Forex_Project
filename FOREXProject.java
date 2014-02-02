/*
* Creator: Ahmad Maruf
* Institution: Columbia University, New York
* Project Name: FOREXproject
* Description:
*	Java code to analyze the history of prices (data) of the 
*	different currency pairs (i.e. UDS/JPY) and stock quotes 
*	to predict future prices and anticipate gain (profit or loss).
* All Rights Reserved 09.29.2013.
*/
package project_forex_of_USD_JPY;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FOREXproject {

	public static void main(String[] args) throws FileNotFoundException {
		
		FillArrays();// fills the arrays Open, Low, High, Close, DL, DH with data from the csv file
		
		// this part of the code is just to make running the analysis convenient for the user
		// this is not part of the analysis.. 
		// It can be edited so that it only calls FindPattern() (short or long) and Display()
		try
		{
		int Ctr =0; // how many times the patterns are executed
		do{
			
		Scanner choose = new Scanner(System.in);
		//ask the user to choose which pattern to run: 1 for Long Pattern, 2 for Short Pattern
		System.out.println("\nWhich pattern do you want to use: \nEnter 1 for Long Pattern, Enter 2 for Short Pattern");
		int userInput = choose.nextInt();		
		
		if(userInput == 1)
		{
			Ctr++; //increment the Counter
			FindLongPattern(); // long pattern 00 00 00 11
			System.out.println("****************************SUMMARY OF RESULTS******************************");
			System.out.println("Results of Trade(Buy/Sell) for Long Reversal Pattern(00 00 00 11)");
			Display();//display results of long pattern
		}
		
		else if (userInput == 2)
		{
			Ctr++; //increment the Counter
			FindShortPattern(); // short pattern 11 11 11 00
			System.out.println("****************************SUMMARY OF RESULTS******************************");
			System.out.println("Results of Trade(Buy/Sell) for Short Reversal Pattern(11 11 11 00)");
			Display();//display result of short pattern
		}
		
		else if ((userInput != 1) && (userInput !=2))	//if the user inputs something else other than 1 or 2
			System.out.println("Wrong Input!! choose 1 or 2: ");
		
		else
			throw new Exception("Invalid Input.");
		} 
		while(Ctr<2);//once executed twice, we stop
		} 
	
	catch (Exception e)
	{
		System.out.println(" Invalid Input. Please try again (Run the program).");
	}
	System.out.println("\nEnd of the program.");
}

	
//We create the arrays to store data	
private static double Open[] = new double [70000];
private static double Close[] = new double [70000];
private static double Low[] = new double [70000];
private static double High[] = new double [70000];
private static int DL[] = new int [70000]; // array DL stores the direction of Low
private static int DH[] = new int [70000]; // array DH stores the direction of High

//we create variables to keep track of results 
private static int NumOfWins =0;
private static int NumOfLosses =0;
private static double totalGain =0;
private static double totalLoss =0;

//we create variables to set Entry and Exit criteria
private static double buyPrice;
private static double SellPrice;
private static double pivotLowPrice;
private static double pivotHighPrice;
private static double takeProfit;
private static double minProfit;
private static double maxLoss;

private static int lineCtr = 0; //csv file's line counter
private static int[]longRevPattern = {0,0,0,0,0,0,1,1};//Set Long Reversal Pattern as 00 00 00 11
private static int[]ShortRevPattern = {1,1,1,1,1,1,0,0};//Set short Reversal Pattern as 11 11 11 00

//Fill the Array from csv data file....
public static void FillArrays() throws FileNotFoundException
{		
		Scanner in = new Scanner(new File("E:/Laguardia Docs/MAC286/EURUSD_hour_new.csv")); 
		/* this reads from the 'EURUSD_hour_new' file, which is in my hardDrive, 
		*  but this program will actually work for ANY forex data, 
		*  as long as you convert it to a .csv file and format it 
		*  according to out METHODOLOGY (explained in READMME.txt)
		*  (the currency pairs can be downloaded from http://www.fxhistoricaldata.com/)
		*  Note:  pips = a 10000th of USD;   pips = a 100th of JPY; etc.
		*  Again for example, you can read data ofr other currency pairs as well:
		*  Scanner in = new Scanner(new File("H:/Laguardia Docs/MAC286/USDJPY_hour.csv")); //read from the 'USDJPY_hour' file
		*  Scanner in = new Scanner(new File("J:/Laguardia Docs/MAC286/USDCAD_hour.csv")); //read from the 'USDCAD_hour_new' file
		*  Scanner in = new Scanner(new File("F:/Laguardia Docs/MAC286/IBM_table.csv")); //read from the 'EURUSD_hour_new' file
		*/
	in.nextLine();//skip first line of csv file
	while(in.hasNextLine()) //as long as there is a next line of data
	{
		String[] n= in.nextLine().split(",");	//split each line and identify token by "CSV" comma-separated values
		Open[lineCtr]=Double.parseDouble(n[3]);//fill array Open[] from .csv file column 3.<OPEN> in each line
		Low[lineCtr]=Double.parseDouble(n[4]);	//fill array Low[] from .csv file column 4.<LOW> in each line
		High[lineCtr]=Double.parseDouble(n[5]);//fill array High[] from .csv file column 5.<HIGH> in each line
		Close[lineCtr]=Double.parseDouble(n[6]);//fill array Close[] from .csv file column 6.<CLOSE> in each line
		DL[lineCtr]=Integer.parseInt(n[7]);	//fill array DriveLow[] from .csv file column 7.<low> in each line
		DH[lineCtr]=Integer.parseInt(n[8]);	//fill array DriveHigh[] from .csv file column 8.<high> in each line
		
		lineCtr++; //increments the csv file's line counter
	}//close while loop
	
	in.close(); //After reading file, we should close it

}// end of FillArrays()

//Search Pattern as 00 00 00 11(Long Reversal Pattern)
private static void FindLongPattern()
{
	for (int i=0; i<lineCtr-3; i++) //try to find a match for Long pattern
	{
		if(DL[i]==longRevPattern[0]&& DL[i+1]==longRevPattern[2]  
		 && DL[i+2]==longRevPattern[4]&& DL[i+3]==longRevPattern[6])
		 {
			if(DH[i]==longRevPattern[1]&& DH[i+1]==longRevPattern[3] 
			 && DH[i+2]==longRevPattern[5]&& DH[i+3]==longRevPattern[7])
			{
				buyPrice = Open[i + 4]; // buy at the opening of the 5th candle
				pivotLowPrice = Low[i + 2]; // Assume Pivot Low Price, low of 3rd candle(Pivot Candle )
				takeProfit = (buyPrice-pivotLowPrice); // set up the limit				
				minProfit = buyPrice+takeProfit;  // Expected Minimum profit then triggering sell and gain it.
				maxLoss = buyPrice-takeProfit; //UnExpected Maximum Loss then triggering sell and gain it.
				
				for (int out = i + 4; out < Open.length; out++) // look at the 5th candle and so on... for find trade
				{
					if ((High[out] >= minProfit) && (Low[out] <= minProfit))
					{
						NumOfWins++;
						totalGain +=takeProfit;
						//System.out.println("Buy Position # "+(i+4+2)+" *Buy Value:"+buyPrice+ " *Sell Price:"+minProfit+"****Wins...");
						break;
					}
					else if ((High[out] >= maxLoss) && (Low[out] <= maxLoss))
					{
						NumOfLosses++;
						totalLoss -=takeProfit;
						//System.out.println("Buy Position # "+(i+4+2)+" *Buy Value:"+buyPrice+ " *Sell Price:"+maxLoss+"*Loss...");
						break;
					}
				}//end of inner for loop
			}//end of inner if
		 }//end of outer if
	}//end of outer for loop
}// end of FindPattern method


//Search Pattern as 11 11 11 00 (Short Reversal Pattern)
private static void FindShortPattern()
{
	for (int i=0; i<lineCtr-3; i++)
	{
		if(DL[i]==ShortRevPattern[0]&& DL[i+1]==ShortRevPattern[2] 
		 && DL[i+2]==ShortRevPattern[4]&& DL[i+3]==ShortRevPattern[6])
		 {
			if(DH[i]==ShortRevPattern[1]&& DH[i+1]==ShortRevPattern[3] 
			 && DH[i+2]==ShortRevPattern[5]&& DH[i+3]==ShortRevPattern[7])
			{				
				SellPrice = Open[i + 4]; // Sell at the open of the 5th candle				
				pivotHighPrice = High[i + 2]; // Assume Pivot High Price, High of 3rd candle(Pivot Candle )
				takeProfit = (pivotHighPrice-SellPrice); // set up the limit
				minProfit = SellPrice-takeProfit;  // Expected Minimum profit then triggering buy at lower price (not too low)
				maxLoss = SellPrice+takeProfit; //UnExpected Maximum Loss then triggering buy at a higher price (not too high)
				
				for (int in = i + 4; in < Open.length; in++) // look at the 5th candle and so on... for find trade
				{
					if ((High[in] >= minProfit) && (Low[in] <= minProfit))
					{
						NumOfWins++;
						totalGain+=takeProfit;
						//System.out.println("Sell Position # "+(i+4+2)+" *Sell Value:"+SellPrice+ " *Buy Price:"+minProfit+"****Wins...");
						break;
					}
					else if ((High[in] >= maxLoss) && (Low[in] <= maxLoss))
					{
						NumOfLosses++;
						totalLoss-=takeProfit;
						//System.out.println("Sell Position # "+(i+4+2)+" *Sell Value:"+SellPrice+ " *Buy Price:"+maxLoss+"*Loss...");
						break;
					}					
				}//end of inner for loop			
			}//end of inner if			
		 }//end of outer if		
	}//end of outer for loop	
}// end of FindPattern method
	
	public static void Display()
	{
		System.out.println("Number of buy/sell operations is: "+(NumOfWins+NumOfLosses));
		System.out.println("Number of winning trades is: "+NumOfWins+ "Representing: "+(100*NumOfWins/(NumOfWins+NumOfLosses))+"%");
		System.out.println("Number of losing trades is: "+NumOfLosses+ "Representing: "+(100*NumOfLosses/(NumOfWins+NumOfLosses))+"%");
	
		System.out.println("Total gain of all trades is: "+totalGain);
		System.out.println("Total loss of all trades is: "+totalLoss);
		 //for USDJPY we multiply by 100 instead of 10000
		System.out.println("Total (Profit/Loss)trades is: "+10000*(totalGain+totalLoss)+" pips");
		System.out.println("Number of pips made per trade is \n"+ 10000*(totalGain+totalLoss)/(NumOfWins+NumOfLosses)+" pips per trade");
	
	}// end of display method
	
}//end FOREXproject class

/* 
¬	(SAMPLE) OUTPUT: 

Which pattern do you want to use: 
Enter 1 for Long Pattern, Enter 2 for Short Pattern
1
****************************SUMMARY OF RESULTS******************************
Results of Trade (Buy/Sell) for Long Reversal Pattern (00 00 00 11)
Number of buy/sell operations is: 1398
Number of winning trades is: 745 Representing: 53%
Number of losing trades is: 653 Representing: 46%
Total gain of all trades is: 1.8704999999999967
Total loss of all trades is: -1.4921999999999984
Total (Profit/Loss) trades is: 3782.999999999983 pips
Number of pips made per trade is 
2.7060085836909753 pips per trade

Which pattern do you want to use: 
Enter 1 for Long Pattern, Enter 2 for Short Pattern
2
****************************SUMMARY OF RESULTS******************************
Results of Trade (Buy/Sell) for Short Reversal Pattern (11 11 11 00)
Number of buy/sell operations is: 2867
Number of winning trades is: 1490 Representing: 51%
Number of losing trades is: 1377 Representing: 48%
Total gain of all trades is: 3.5174999999999983
Total loss of all trades is: -3.258500000000007
Total (Profit/Loss) trades is: 2589.9999999999145 pips
Number of pips made per trade is 
0.903383327520026 pips per trade

End of the program.

*/

