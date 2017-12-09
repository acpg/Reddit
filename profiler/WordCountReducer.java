import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, Text, Text, Text> {
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
	throws IOException, InterruptedException { 
		int myscore = 0;
		int mycomments = 0;
		int mycount = 0;
		for (Text value : values) { 
			String[] myvals = value.toString().split(",");
			myscore += Integer.parseInt(myvals[0]);
			mycomments += Integer.parseInt(myvals[1]);
			mycount += Integer.parseInt(myvals[2]);
		}
		String finalval = String.valueOf(myscore)+","+String.valueOf(mycomments)+","+String.valueOf(mycount);
		context.write(key, new Text(finalval));
	}
}
