import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanMapper extends Mapper<LongWritable, Text, Text, Text> {

@Override
public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	String line = (value.toString()).toLowerCase().substring(1);
	int ii = line.indexOf("[");
	String title = line.substring(0,ii);
	line = line.substring(ii+1);
	String[] words = line.split(" ");
	for(String word:words) {
		word = word.replaceAll("[^a-z0-9]", " ");
		context.write(new Text(title), new Text(word));
	}
}
}
