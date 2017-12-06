-- Modified from cloudera.com

DROP TABLE IF EXISTS reddit_table;
-- The EXTERNAL clause means the data is located outside the central location
-- for Impala data files and is preserved when the associated Impala table is dropped.
-- We expect the data to already exist in the directory specified by the LOCATION clause.
CREATE EXTERNAL TABLE reddit_table
(
   year STRING,
   month STRING,
   word STRING,
   count INT
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' 
LOCATION '/user/cloudera/redditprofiler';

DROP TABLE IF EXISTS reddit_final;
CREATE TABLE reddit_final AS SELECT date_sub(add_months(cast(concat_ws('-',year,month,'01') as timestamp),1),1) as date_month, word, count 
	FROM reddit_table WHERE count>2;

