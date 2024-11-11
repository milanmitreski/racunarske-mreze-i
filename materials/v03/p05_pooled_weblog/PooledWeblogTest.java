package p05_pooled_weblog;

import java.io.FileInputStream;

final class PooledWeblogTest {

	public static void main(String[] args) throws Exception {

		// We wish to create a class which will have an Input-Output Stream API
		// Now we maybe have a file where the log is dumped but we might as well
		// be downloading it online via InputStream and give it to our class which
		// will process it without any modifications in the code. Similarly, the
		// output is not hardcoded as well - we can write the result where we want.

		// The name is inspired from a popular design pattern
		// https://en.wikipedia.org/wiki/Thread_pool

		PooledWeblog pw = new PooledWeblog(
				new FileInputStream(
						"c03_InetAddress/i06_pooled_weblog/apache.logfile"
				),
				System.out,
				6
		);

		pw.processLogFile();
		pw.close();
	}

}
