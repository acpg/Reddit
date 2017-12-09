# Reddit

This repository gets Reddit data asociated with Bitcoin use. The code presented works for Dumbo.

The python script in the scraping folder gets the initial data. At the beginning of the file there is a parameter to put a start date and the number of days it will download. Remember that you need to install [PRAW](https://praw.readthedocs.io/en/latest/) and a Reddit account to access the API. You can modify the code itself with your own credentials or add a credentials.py file. The data will be stored in a **Data** folder in a different file per day. 

```
python reddit_import.py
hdfs dfs -mkdir /user/apg367/redditraw
hdfs dfs -put data/reddit_raw2* /user/apg367/redditraw
```

The raw data gets cleaned with the MapReduce on the cleaner folder. 

```
javac -classpath `yarn classpath` -d . CleanMapper.java
javac -classpath `yarn classpath` -d . CleanReducer.java
javac -classpath `yarn classpath`:. -d . Clean.java
jar -cvf Clean.jar *.class
hdfs dfs -rmr /user/apg367/redditcleaner
hadoop jar Clean.jar Clean /user/apg367/redditraw /user/apg367/redditcleaner
```

The cleaned data gets profiled with MapReduce on the profiler folder.

```
javac -classpath `yarn classpath` -d . WordCountMapper.java
javac -classpath `yarn classpath` -d . WordCountReducer.java
javac -classpath `yarn classpath`:. -d . WordCount.java
jar -cvf WordCount.jar *.class
hdfs dfs -rmr /user/apg367/redditprofiler
hadoop jar WordCount.jar WordCount /user/apg367/redditcleaner /user/apg367/redditprofiler
```

The sql file then creates a table in impala for our analytic.
Run the sql file and output the file in a csv file with the following commands.

```
hdfs dfs -chown apg367 /user/apg367
hdfs dfs -chmod -R 777 /user/apg367
impala-shell -i compute-2-4 -f reddit_impala.sql
impala-shell -i compute-2-4 -B -q 'select * from reddit' -o output/reddit_final.csv --print_header '--output_delimiter=,'
```


