import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanMapper extends Mapper<LongWritable, Text, Text, Text> {

@Override
public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	// common words that we will ignore
	String[] common = {"an","and","by","for","in","is","on","that","the","this","to"};
	String line = (value.toString()).toLowerCase().substring(1);
	int ii = line.indexOf("[");
	String title = line.substring(0,ii);
	String[] titles = title.split("\", \"");
	titles[0] = titles[0].replaceAll("\"|\\\"|'|`","");
	titles[1] = titles[1].replaceAll("\"","");
	titles[0] = titles[0].replaceAll("[^a-z0-9]"," ");
	titles[0] = titles[0].trim();
	title = titles[0]+", "+titles[1];
	line = line.substring(ii+1);
	String[] words = line.split(" ");
	for(String word:words) {
		word = word.replaceAll("\"|\\\"|'|`","");
		word = word.replaceAll("[^a-z0-9]", " ");
		// if not a common word
		if(!common.contains(word)){
			context.write(new Text(title), new Text(word));
		}
	}
}
}
