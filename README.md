George Beuhler 2014 gebeuhler@gmail.com

All instructions are for Linux and from project root


To compile:

javac -g src/*.java -d bin


To run interpreter style:

java -cp bin RunCreditCardProcessor


To run from a file:

java -cp bin RunCreditCardProcessor data/transactions.txt


To compile tests:

javac -cp lib/*:src test/*.java -d bin


To run tests:

java -cp lib/*:bin TestRunner


Design Decisions:

When writing this application my goal was to keep it extensible and modular with minimum code re-use and overlapping pieces.
I used regular expressions to parse the commands. I know they can use alot of resources if they are complicated but they work well
for simple functionality. I used a TreeMap for the main data structure because it will put the keys in alphabetical order.
Generating the transaction summary will run in O(n) because it must iterate through the entire map of Credit Cards.
  
One way to improve this application is to improve error handling with bad user input.

I used Java for this program because I have the most experience with it and am most comfortable with it.

debug help:
http://stackoverflow.com/questions/1853628/how-do-i-pass-console-input-to-a-running-java-program-instead-of-to-jdb
http://pages.cs.wisc.edu/~horwitz/jdb/jdb.html
