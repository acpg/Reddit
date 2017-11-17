# Reddit

This repository gets Reddit data asociated with Bitcoin use.

The python script in the scraping folder gets the initial data. You can modify the code itself with your own credentials or add a credentials.py file. The data will be stored in a new folder.

The raw data gets cleaned with the MapReduce on the cleaner folder. 

The cleaned data gets profiled with MapReduce on the profiler folder.

The sql file then creates a table in impala for our analytic.



