package pt.technic.apps.minesfinder.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	public static final ExecutorService timeThreadPool = Executors.newSingleThreadExecutor();

	private ThreadPool() {
		throw new IllegalStateException("Utility class");
	}


}
