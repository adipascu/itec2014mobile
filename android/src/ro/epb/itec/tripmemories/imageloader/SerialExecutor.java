package ro.epb.itec.tripmemories.imageloader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class SerialExecutor extends ThreadPoolExecutor {

	public SerialExecutor() {
		super(1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}
}
