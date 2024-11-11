Task:

The user enters a directory and a keyword. The program starts recursive file search from given directory and for each
file it finds it starts the searching the found file for given keyword. For efficiency reasons, it is not a good idea
to do this in a single thread by searching the directory and placing the found files in a list and then search them later
because the user can enter "/" as a starting directory - the list will become huge (potentially you will run out of heap
space). So, the preferred way is to use multiple threads and search the directory and individual files simultaneously.