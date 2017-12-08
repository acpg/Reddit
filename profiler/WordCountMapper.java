import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, Text> {

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
						 "any","are",
						 "as",
						 "at",
						 "back",
						 "be","been",
						 "because",
						 "but",
						 "by",
						 "can",
						 "come",
						 "could",
						 "day","did",
						 "do","dont",
						 "even",
						 "first",
						 "for",
						 "from",
						 "get",
						 "give",
						 "go",
						 "good",
						 "have","has","hasnt",
						 "he",
						 "her",
						 "him",
						 "his",
						 "how",
						 "i","im",
						 "if",
						 "in",
						 "into","is","isn","isnt",
						 "it","ive",
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
						 "that","thats",
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
						 "want","was",
						 "way",
						 "we",
						 "well",
						 "what",
						 "when",
						 "which","while",
						 "who",
						 "will",
						 "with",
						 "work",
						 "would",
						 "year",
						 "you",
						 "your"};
	String[] line = value.toString().split(",");
	String title = line[0].trim();
	String date = line[1].trim().substring(0,7);
	String score = line[2].trim();
	int ncomment = Integer.parseInt(line[3].trim());
	String comments = line[line.length-1];
	// we take both title and comments
	String[] words = (title+" "+comments).split(" ");
	for(String word:words) {
		// check if empty or single characters
		if(word != null && !word.isEmpty() && word.length() > 1) {
			//check if it contains a character
			if(word.matches("[a-z]+")){
				// check if not a common word
				if(!Arrays.asList(common).contains(word)){
					context.write(new Text(date+","+word+","), new Text(score+","+"1"));
				}
			}
		}
	}
}
}
