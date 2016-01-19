===============================================
Steps to compile and run
===============================================
A. Used Observer design pattern to achieve the required functionality.
B. Synchronized methods are used to make the operations thread-safe.


1. Copy all the *.java to a local folder.
2. From the terminal/command prompt navigate to the directory where all the *.java are present.
3. Execute the following command in order to compile the program:
	
	javac PubSubMsgQMain.java Publisher.java Subscriber.java InMemPublisher.java InMemSubscriber.java Message.java
	
4. If no errors are displayed, execute the following command to run the program:
	
	java PubSubMsgQMain

5. The program outputs the published messages, message id, timestamp and subscriber name.
	






