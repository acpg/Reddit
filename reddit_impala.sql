-- Modified from cloudera.com

DROP TABLE IF EXISTS reddit_table;
-- The EXTERNAL clause means the data is located outside the central location
-- for Impala data files and is preserved when the associated Impala table is dropped.
-- We expect the data to already exist in the directory specified by the LOCATION clause.
CREATE EXTERNAL TABLE reddit
(
   year INT,
   month INT,
   word STRING,
   count INT
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ' '
LOCATION '/user/cloudera/redditImpala';
