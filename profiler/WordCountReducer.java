import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, Text, Text, Text> {
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
	throws IOException, InterruptedException { 
		int mysum = 0;
		int mycount = 0;
		for (Text value : values) { 
			String[] myvals = value.toString().split(",");
			mysum += Integer.parseInt(myvals[0]);
			mycount += Integer.parseInt(myvals[1]);
		}
		String finalval = String.valueOf(mysum)+","+String.valueOf(mycount);
		context.write(key, new Text(finalval));
	}
}
