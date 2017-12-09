-- Modified from cloudera.com
DROP TABLE IF EXISTS reddit_table;
CREATE EXTERNAL TABLE reddit_table
(
   month STRING,
   word STRING,
   score INT,
   comments INT,
   count INT
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' 
LOCATION '/user/apg367/redditprofiler';

DROP TABLE IF EXISTS reddit;
CREATE TABLE reddit AS 
-- modified from https://community.hortonworks.com/questions/24667/hive-top-n-records-within-a-group.html
SELECT from_unixtime(unix_timestamp(date_sub(add_months(cast(concat_ws('-',month,'01') as timestamp),1),1)), 'yyyy-MM-dd') as month, word, count, comments, score FROM (
SELECT month, word, count, score, comments, 
rank() over ( partition by month ORDER BY count DESC) AS rank 
FROM reddit_table ) t WHERE rank <= 10; --top ten words

DROP TABLE reddit_table;
