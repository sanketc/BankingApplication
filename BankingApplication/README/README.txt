***********************************************************
Program: Simple Distributed Banking System Demo 
Author: Sanket Chandorkar
***********************************************************

***********************************************************
Problem Statement:
***********************************************************

Implement a solution that mimics a banking system where the bank maintains three identical files
at three different sites, maintaining account information. A client, executing at a fourth site,
 may issue read or write requests. 
 
All replicas of a file are consistent, to begin with. The desired operations are as follows –
a. The client may send a ‘read from file’ REQUEST to any of the sites (randomly selected). 
In this case, the site should reply with requested content (if available).
b. The client may send a ‘write to file’ REQUEST to any of the sites (randomly selected). 
   In this case, the site must report a ‘successful’ message to the client if all copies of the
   file, across the three sites, are updated consistently.	Otherwise, the site must report a
   ‘failure’ message to the client.

As part of this project you have to determine how a site, on receiving the ’write to file’
REQUEST communicates with the other sites to get the same write performed on all the copies. 
Your program must support the creation of new files, writes to the end of files, reads and writes
at specific offsets from file beginning. It must also report an error if an attempt is made to 
read from a file that does not exist at the requested site. An error must be reported if there is
an attempt to write to a file that does not exist on all the sites.

Assume there is only one client. When the client starts it must present the user with three
options: issue a read request, issue a write request, or end the session. If the user chooses to
perform a read or write, then the client should obtain the necessary information, initiate the
action, return the result, and go back to presenting the three options mentioned above. 
A client issues only one request at a time. So, for this project you need not worry about 
concurrent read/write requests for the same object.

***********************************************************
STEPS TO RUN PROGRAM: (With default Script)
***********************************************************

Run scripts\run.bat

***********************************************************
STEPS TO RUN PROGRAM: (Without default Script)
***********************************************************

1. Execute the server.
	Command:
	> java code.server.BankServer <serverId> <Address> <Port>
	Eg:
	> java code.server.BankServer SER01 cs.utdallas.edu 1224
   
2. Execute the client.
	Command:
	> java code.client.BankClient <clientId> <Address> <Port>
	Eg:
	> java code.client.BankClient CLI01 cs.utdallas.edu 1223
	
3. Enter choice for operation to perform.