# Reddit

This repository gets Reddit data asociated with Bitcoin use. The following code works for the Cloudera VM.

The python script in the scraping folder gets the initial data. You can modify the code itself with your own credentials or add a credentials.py file. The data will be stored in a *Data* folder in a different file per day.

```
hdfs dfs -mkdir /user/cloudera/redditraw
hdfs dfs -put data/reddit_raw2* /user/cloudera/redditraw
```

The raw data gets cleaned with the MapReduce on the cleaner folder. 

```
javac -classpath `yarn classpath` -d . CleanMapper.java
javac -classpath `yarn classpath` -d . CleanReducer.java
javac -classpath `yarn classpath':. -d . Clean.java
jar -cvf Clean.jar *.class
hdfs dfs -rmr /user/cloudera/redditcleaner
hadoop jar Clean.jar Clean /user/cloudera/redditraw /user/cloudera/redditcleaner
```

The cleaned data gets profiled with MapReduce on the profiler folder.

```
javac -classpath `yarn classpath` -d . WordCountMapper.java
javac -classpath `yarn classpath` -d . WordCountReducer.java
javac -classpath `yarn classpath':. -d . WordCount.java
jar -cvf WordCount.jar *.class
hdfs dfs -rmr /user/cloudera/redditprofiler
hadoop jar WordCount.jar WordCount /user/cloudera/redditcleaner /user/cloudera/redditprofiler
```

The sql file then creates a table in impala for our analytic.
Run the sql file and output the file in a csv file with the following commands.
```
impala-shell -f reddit_impala.sql
impala-shell -B -q 'select * from reddit_final order by count desc' -o output/reddit_final.csv --print_header '--output_delimiter=,'
```


