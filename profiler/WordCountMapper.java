import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

@Override
public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	// common words that we will ignore, https://en.wikipedia.org/wiki/Most_common_words_in_English
	String[] common = { "a", "an", 
						 "about",
						 "after",
						 "all",
						 "also",
						 "an",
						 "and",
						 "any",
						 "as",
						 "at",
						 "back",
						 "be",
						 "because",
						 "but",
						 "by",
						 "can",
						 "come",
						 "could",
						 "day",
						 "do",
						 "even",
						 "first",
						 "for",
						 "from",
						 "get",
						 "give",
						 "go",
						 "good",
						 "have",
						 "he",
						 "her",
						 "him",
						 "his",
						 "how",
						 "i",
						 "if",
						 "in",
						 "into",
						 "it",
						 "its",
						 "just",
						 "know",
						 "like",
						 "look",
						 "make",
						 "me",
						 "most",
						 "my",
						 "new",
						 "no",
						 "not",
						 "now",
						 "of",
						 "on",
						 "one",
						 "only",
						 "or",
						 "other",
						 "our",
						 "out",
						 "over",
						 "people",
						 "say",
						 "see",
						 "she",
						 "so",
						 "some",
						 "take",
						 "than",
						 "that",
						 "the",
						 "their",
						 "them",
						 "then",
						 "there",
						 "these",
						 "they",
						 "think",
						 "this",
						 "time",
						 "to",
						 "two",
						 "up",
						 "us",
						 "use",
						 "want",
						 "way",
						 "we",
						 "well",
						 "what",
						 "when",
						 "which",
						 "who",
						 "will",
						 "with",
						 "work",
						 "would",
						 "year",
						 "you",
						 "your"};
	String line = value.toString();
	int ii = line.indexOf(",");
	String title = line.substring(0,ii);
	String year = line.substring(ii+2,ii+6);
	String month = line.substring(ii+7,ii+9);
	ii = line.lastIndexOf(",")+1;
	line = line.substring(ii);
	String[] words = (title+" "+line).split(" ");
	for(String word:words) {
		if(word != null && !word.isEmpty() && word.length() > 1) {
			// if not a common word
			if(!Arrays.asList(common).contains(word)){
				context.write(new Text(year+","+month+","+word+","), new IntWritable(1));
			}
		}
	}
}
}
