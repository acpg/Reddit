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
						 "after","aint","aka",
						 "all",
						 "also",
						 "an","am",
						 "and",
						 "any","are",
						 "as",
						 "at",
						 "back",
						 "be","been",
						 "because","bitcoin","bitcoins","btc",
						 "but",
						 "by",
						 "can","cant","com",
						 "come",
						 "could",
						 "day","did",
						 "do","does","dont",
						 "even",
						 "first",
						 "for",
						 "from",
						 "get",
						 "give",
						 "go","going","gone",
						 "good","great","had",
						 "have","has","hasnt",
						 "he",
						 "her","here",
						 "him",
						 "his",
						 "how","http","https",
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
						 "make","many",
						 "me","more",
						 "most","much",
						 "my","need",
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
						 "people","really","reddit",
						 "say",
						 "see",
						 "she","should",
						 "so",
						 "some","something","still",
						 "take",
						 "than",
						 "that","thats",
						 "the",
						 "their",
						 "them",
						 "then",
						 "there",
						 "these",
						 "they","theyve",
						 "think",
						 "this","through","throughout","thus",
						 "time",
						 "to","too","toward","towards",
						 "two","unto",
						 "up",
						 "us",
						 "use","using","very","via",
						 "want","wanna","wanted","wanting","wants","was","wasnt",
						 "way",
						 "we",
						 "well","went","were","werent",
						 "what",
						 "when","where","whereas","whered","wheres","whether",
						 "which","while",
						 "who","whoever","whom","whos","why",
						 "will",
						 "with","wont","www",
						 "work",
						 "would","ya","yay","ye","yeah",
						 "year","years","yep","yes",
						 "you","youll","youre","yourself",
						 "your","youve"};
	String[] line = value.toString().split(",");
	String title = line[0].trim();
	String date = line[1].trim().substring(0,7);
	String score = line[2].trim();
	String ncomment = line[3].trim();
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
					context.write(new Text(date+","+word+","), new Text(score+","+ncomment+","+"1"));
				}
			}
		}
	}
}
}
