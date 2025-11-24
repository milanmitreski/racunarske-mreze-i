Task:

Process the Apache server log file. These log files can have huge size (GBs in size). One of the reasons why you would
want to do that is for example checking the server load (to determine whether a DDoS attack was attempted), or looking up 
the IP adresses that made the requests for data mining. Since looking up an IP adress is a relatively slow operation, it is 
inefficient to do this in a single thread. Therefore, as the log file is processed, the IP adresses found should be queried 
in multiple threads (similar to FileTreeWalker example seen previously) in the meantime.

For this example, a blocking queue can be used as well, but we will demostrate the use of other collections and
signalling methods to achieve the same result as before.