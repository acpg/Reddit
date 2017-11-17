import json
import praw
from praw.models import MoreComments
from datetime import datetime
import credentials
# import your own credentials with
# reddit = praw.Reddit(client_id='my client id', client_secret='my client secret', user_agent='my user agent')

reddit = credentials.mycredentials()

with open('../data/reddit_raw', 'w') as output:
	for submission in reddit.subreddit('bitcoin').top('all'): 
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