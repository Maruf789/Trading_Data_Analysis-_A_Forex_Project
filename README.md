Trading_Data_Analysis-_A_Forex_Project
======================================

Summary:
Java code to analyze the history of prices (data) of the different currency pairs (i.e. USD/JPY) and stock quotes to predict future prices and anticipate gain (profit or loss).

Introduction:

Since the birth of civilization, people have been engaging in trade for survival and profit. Trading, that is to buy or sell something, involves exchanges that can be divided into different types—Commodity exchanges (trading of physical goods), Stock exchanges, and Foreign exchanges (Forex). “Forex is an exchange where different currencies are traded” [1].Forex is the world’s largest and the most famous liquid trading market. Many people consider Forex as the best home business you can ever venture in. In this article, we will only talk about Foreign Exchanges. We are going to discuss a couple of methods and terminology using technical analysis, in which we will use the history of prices (data) of the different currency pairs and stock quotes to predict future prices and anticipate gain (profit or loss).
However, trading in Forex is not all rose-colored, it does involve certain risks. We tend to make our buying and selling decisions based on uncertain predictions. Fear of loss and greed for win are the most important factors that influence the actions within a Forex market. To be a successful trader, one should be able to analyze and assess the situation in the market, and make decisions based on the analysis, not his/her emotions. When a trader enters a market, he/she has no idea when the prices (“foreign exchange rate” [2]) will rise, and when they will drop down. “Prices on exchanges are formed on the basis of the supply and demand. If more traders want to buy something (demand) than sell it (supply), then the price moves up (Uptrend). Conversely, if more traders want to sell something (supply) than buy it (demand), then the price moves down (Downtrend). So, the price is said to be floating, which provides a good opportunity for speculation on exchanges” [1]. Because there is no specific formula or rule to decide when is the right time to enter or exit a trade, traders have been striving to come up with different approaches to get as close as possible to make successful decisions. In this article, I am going to make my own entry and exit criteria depending on the pattern of prices we’re following. To find the pattern visually, I use candlestick charts.

CANDLESTICK CHARTS:

To have the best representation of data, traders come up with different charts and tables. All in order to make the change of prices in the market clearer and interpreting it easier. A Candlestick Chart shows the change of the price within a certain time period. Candlestick lines consist of three parts (Upper shadow, Body, and Lower shadow) and present four pieces of information: High, Low, Open, and Close (see figure 1). Its color is determined by the Opening price and Closing price of that particular period. If the Close is higher than the Open, it takes a green or white color. Otherwise, its color is red or black.  The upper shadow  simply tells the analyzer which price was the highest during that period (week, day, hour…). Inversely, the lower shadow shows the lowest price during that period. 	
 

REVERSAL PATTERN:

It’s a chart formation that indicates the change of prices within a market. A reversal pattern, which usually occurs after a major movement in the price of a currency or in the entire market, is an indication that investors should adjust their positions to take advantage of the coming change in market direction. In technical analysis, reversal patterns any pattern on a chart that indicates a previous trend is changing to a new trend. Generally speaking, any trend in which the highs are lower than the previous highs is bearish (Short) Reversal Candlestick Pattern, while any trend in which the lows are higher than the previous lows is Bullish (Long) Reversal Candlestick Pattern.[3] See chart below.

  1.	Bullish (Long) Reversal Candlestick Pattern: 

      The Bullish reversal pattern is an upward moving pattern that forms after a downtrend of candlestick Highs and Lows. In the binary format, it is indicated through 3 consecutive pairs of 0’s followed by at least one pair of 1’s for the Low and High of that period (day/hour). After a decline of three consecutive candles - that is every candle of the three has a lower low (0) and a lower high (0) than the previous -the fourth candle has a higher low (1) and a higher high (1) than the third (See Methodology). However, the strong finish indicates that buyers regained their footing to end the session on a strong note. After observing this reversal pattern, we would enter the trade by buying on the open of the fifth candle. We then calculate the amount of profit we want to make by subtracting that 

  2.	Bearish (Short) Reversal Candlestick Pattern:
  
      On the contrary, a Bearish market happens when prices for the online Forex market are declining. In bearish candle charting we try to foresee when the market will drop. The pattern signals a market top or a resistance level. Since it is seen after an advance, a Bearish Hanging Man Pattern sig¬¬nals that selling pressure is starting to increase. The low of the long lower shadow indicates that the sellers pushed prices lower during the session. Even though the bulls regained their footing and drove prices higher by the finish, the appearance of this selling pressure after a rally is a serious warning signal. Rising trend - the hanging man is only valid after a significant up trend in price.
Let's see it's streingth and weeknesses---     

         •	Strengths: It is a very early signal that buyers may be drying up, potentially getting you in the market very early in the process of a trend reversal. Stop losses can be set very tight and take profits can be large, since a larger scale down trend is just being formed. This sets us up with a very attractive reward: risk ratio.
          
          •	Weaknesses: The selling pressure can easily come from profit taking or from large non-speculative flows, showing many false reversals. Statistically, the hanging man pattern on its own does not provide any discernible edge in the market, and is akin to a coin toss.
 
METHODOLOGY:
  1.	I will begin by getting the data that I will be using on this research paper. I use an excel spreadsheet [5] that includes the following data:
      a.	Open: The opening price of a period (hour).
      b.	Low: The Lowest price during that period.
      c.	High: The Highest price during the period.
      d.	Close: The Closing price of the period.
      e.	Direction of High: 0 if lower than previous High, 1 if higher than previous High.
      f.	Direction of Low: 0 if lower than previous Low, 1 if higher than previous Low.

  2.	In the original excel spreadsheet I generate binary values 0’s and 1’s by inputting the formula “=if(E3>E2,1,if(E3<E2,0,H2))” for Direction of Low (DL column). Then I use the formula “=if(F3>F2,1,if(F3<F2,0,I2))” for Direction of High (DH column).

  3.	I save our spreadsheet as .csv, which denotes “comma separated values” to simplify the read operations of my program.

  4.	I create an Algorithm coded using Java programming language (See Appendix) to analyze the data.

  5.	Fill the arrays with the appropriate values from the .csv file using the comma “,” as a delimiter to store each line of values into an array of type String. Then I parse each element back to its original type and into its array, and I continue doing that for each line throughout the file. 

  6.	Create arrays to holds the data and create variables to keep track of the successful and unsuccessful trades.

  7.	Look for the Long Pattern [00 00 00 11]:
	The goal is to buy a currency at a lower price and then sell it at a higher price. Once I find a match, I buy (enter the trade) at the open of the next (fifth) candle, then I calculate our limit of gain or loss by subtracting the low of the pivot (third) candle from the open of the fifth candle. Then I sell (exit the trade) once I reach either the gain or loss limit.

  8.	Look for the short Pattern: [11 11 11 00]
	Similarly, the goal is to sell a currency at a higher price and then buy it at a lower price. Once I find a match, I sell (enter the trade) at the open of the next (fifth) candle, then I calculate our limit of gain or loss by subtracting the open of the fifth candle from the high of the pivot (third) candle. Then I buy (exit the trade) once I reach either the gain or loss limit.

  9.	If I reach the gain limit I increase the number of wins and add the value of limit to the total gain in pips (see Appendix)... Else if I reach the loss limit, I increase the number of losses and subtract the value of limit from the total loss in pips.
  
  10.	Display the results.


 
RESULTS:
➢	Results of Trade for Long Reversal Pattern (00 00 00 11)
H = hourly, D = daily
 

➢	Results of Trade for Short Reversal Pattern (11 11 11 00)
H = hourly, D = daily
 
➢	Comparing the results of both (Long and Short) reversal Patterns we get the following table:
 

DISCUSSION OF RESULTS:
 	For the long reversal pattern, I received positive results. The number of winning trades outweighed the number of losing trades, and I was able to make a fair amount of profit (pips). For example, I found 62% of winning trades, whereas 37% of losing trades in GBP/USD currency pair. 
For the short reversal pattern, however, the results were more impressive, and I found almost twice more matches than the long reversal pattern. The difference between the number of winning trades and the number of losing trades was very close compared to the result of long reversal pattern, but the amount of profit were more than that of a long reversal pattern. For instance, I found 67% of winning trades, whereas 32% of losing trades in GBP/USD currency pair. In terms of total gain, and average gain per trade, the results of short reversal pattern are more impressive than the long reversal patterns.
  
 	While trying to get optimal results, I was experiencing many different difficulties. First, I had to deal with the dilemma of filling my arrays from the .csv file, as well as finding the patterns (Long and short). I had to debate and argue my thoughts about the logic implemented in the code for long hours. I made several changes to improve and strengthen the algorithm and make it as simple as possible for a beginner to understand. After long hours of failed attempts, finally I managed to overcome those difficulties. It was rewarding at the end of the day!
  
	In order to make our algorithm simple, and due to the time constraints,  we only used two basic patterns, although there are several other advanced algorithms (e.g. Knuth-Morris-Pratt Algorithm) to come up with probably better result.

	So, once the two patterns are mastered, one can explore more patterns, and obtain more strategies to develop his/her own criteria to be a successful trader in Forex, as well as other exchanges.

CONCLUSION:
	As mentioned earlier, investing in the Forex market (or any market for that matter) can be very risky. After researching and creating the program (see CODE), I believe that my methods can help beginners better understand and identify the patterns, and make successful trades. The results I've acquired show that future prices are influenced by past ones. Being able to effectively locate reversal patterns is what separates the winners from the losers, in terms of trading. 
	
	
BIBLIOGRAPHY:

[1] Nikolay Gekht, and Sunshine. "Forex in a Nutshell." - FxCodeBaseWiki. www.fxcodebase.com, 18 Mar. 2011. Web. 14 May 2012. <http://www.fxcodebase.com/wiki/index.php/Forex_In_A_Nutshell>.[2] "Introduction to Candlesticks - ChartSchool - StockCharts.com."Introduction to Candlesticks - ChartSchool - StockCharts.com.www.StockCharts.com. Web. 14 May 2012.<http://stockcharts.com/school/doku.php?id=chart_school:chart_analysis:introduction_to_candlesticks>.

[2] Weisweiller, Rudi. How the Foreign Exchange Market Works. Original English Language ed. New York, NY: New York Institute of Finance, 1990. Print.

[3] "Reversal Pattern." TheFreeDictionary.com. Farlex Inc., 2012. Web. 28 May 2012.
  <http://financial-dictionary.thefreedictionary.com/Reversal Pattern>.

[4] Raj. "Piercing Line Candlesticks."Piercing Line Candlestick Formations. Www.forexbees.com, 23 May 2010. Web. 14 May 2012. <http://www.forexbees.com/piercing-line-candlesticks>.

[5] "Forex Historical Data." Forex Historical Data.Web. 14 May 2012. <http://www.fxhistoricaldata.com/>.

 
APPENDIX:
 
  ➢	Pip(Point):
      “Pip is the smallest possible change of the price. All currency pairs are priced to a fixed precision. There are       currency pairs which are priced to two digits after the decimal point” [1].
      For example, USD/JPY = 120.05. The smallest change of such currency pairs will always be 0.01 (1 pip = 0.01). So       if the price of USD/JPY changes from 120.05to 120.08, the pair is said to make a 3-pip change. However, most           currency pairs are priced to four digits after the decimal point.
      For example, EUR/USD = 1.2066. The smallest change of such currency pairs will always be 0.0001 (1 pip = 0.0001).       So if the price of EUR/USD changes from 1.2066 to 1.2076, the pair is said to make a 10-pipchange.
      
  ➢	Foreign Exchange Rate:
      “The [foreign] exchange rate is the price of the one currency expressed in terms of the other. It expresses a          relationship between two national monies. It is, therefore, unrealistic to assume that changes, even over a short       period, express alterations in the demand for, or supply of, only one of these national monies. Whenever an            exchange rate moves, this can be due to a change in the value of one or the other currency, or partly of one and        partly of the other.[2]”
      
#######################################################################################################################
#                                                  THE END                                                            #
#######################################################################################################################

