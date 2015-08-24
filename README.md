# StockCalculator

Assumptions:
- I created described functionality as a REST web service based on json (StockCalculatorController.java)

- I am not familiar with stock market so I might understand (and I probably did) sth incorrectly
(i.e. I have no idea if ticker price should be changed or not, how trade indicator - buy, sell - influence evaluations etc)
In case you could point out my mistakes I can fix the logic

- I assume that stock values can be described with double precision and evaluations of calculations can be returned as a double (without formatting)

- I believe that self describing code removes necessity of comments


Testing:
- You should import project as maven project
- To test application one can send request (below json a request body) to localhost:8080/calculateDividendYield
{
	"symbol":"EUM",
    "stockType":"COMMON",
    "lastDivident":"10",
    "fixedDivident":"10",
    "parValue":"10",
    "tickerPrice":"10"
}


Technologies/Frameworks used:
- java 8 (predicates)
- spring framework (Dependency Injection, REST web service)
- log4j
- maven
- jUnit4
- tomcat (to test it)