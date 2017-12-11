import json
import praw
from praw.models import MoreComments
from datetime import datetime
from datetime import timedelta
import credentials
# import your own credentials with
# reddit = praw.Reddit(client_id='my client id', client_secret='my client secret', user_agent='my user agent')

reddit = credentials.mycredentials()
# date looping modified from giltay 2008
# https://stackoverflow.com/questions/153584/how-to-iterate-over-a-timespan-after-days-hours-weeks-and-months-in-python
start_date = datetime(2017,12,7,0,0,0)
delta = timedelta(days=1)
current_date = start_date
for i in range(4):
	new_date = current_date + delta
	with open('../data/reddit_raw'+current_date.strftime("%Y%m%d"), 'w') as output:
		for submission in reddit.subreddit('bitcoin').submissions(int(current_date.strftime('%s')),int(new_date.strftime('%s'))-1): 
			list_of_items = []
			list_of_items.append(submission.title.encode("utf-8"))
			list_of_items.append(datetime.fromtimestamp(submission.created_utc).isoformat())
			list_of_items.append(submission.score)
			list_of_items.append(submission.num_comments)
			list_of_comments = []
			for comment in submission.comments:
				if isinstance(comment, MoreComments):
					continue
				list_of_comments.append(comment.body.encode("utf-8"))
			list_of_items.append(list_of_comments)
			json.dump(list_of_items,output)
			output.write('\n')
	current_date = new_date