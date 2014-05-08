George Beuhler 2014 gebeuhler@gmail.com

All instructions are for Linux and from project root


To compile program:

javac src/*.java -d bin


To run program interpreter style:

java -cp bin RunCreditCardProcessor


To run program from a file:

java -cp bin RunCreditCardProcessor data/transactions.txt


To compile tests:

javac -cp lib/*:src test/*.java -d bin


To run tests:

java -cp lib/*:bin TestRunner


Design Decisions:

When writing this application my goal was to keep it extensible and modular with minimum code re-use and maximum orthogonality.
I used regular expressions to parse the commands. I know they can use alot of resources if they are complicated but they work well
for simple functionality. I used a TreeMap for the main data structure because it will put the keys in alphabetical order.
Generating the transaction summary will run in O(n) because it must iterate through the entire map of Credit Cards.
  
One way to improve this application is to improve error handling with bad user input. Some examples are different numbers of command line arguments and incorrectly formmatted ones.

I also went back on forth on having the addCreditCard(), chargeCard(), and creditCard() methods return a boolean to indicate success or not. 
I went with them being type void because of the requirement of using the luhnTest() to do the add/charge/credit being the fail condition. With this choice, non luhn compliant cards are
added to the system but essentially marked as "dirty" by having the luhn field be false. In my work experience doing this "soft" add can work better because it stores more data that
can later be used and analyzed. This choice was also supported by the requirements.
Alternatively I think they could return a boolean and then that functionality can be explicitly tested. The same goes for the charge() and credit() methods in CreditCard.java.

Another thing is that the test cases could be broken down into even more specific use cases testing smaller pieces of functionality.  Example: having a test just for luhnTest() method instead
of it being included when testing add/charge/credit.

I used Java for this program because I have the most experience with it and am most comfortable with it.

debug help:
http://stackoverflow.com/questions/1853628/how-do-i-pass-console-input-to-a-running-java-program-instead-of-to-jdb
http://pages.cs.wisc.edu/~horwitz/jdb/jdb.html
