import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

@Override
public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
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
			context.write(new Text(year+","+month+","+word+","), new IntWritable(1));
		}
	}
}
}
