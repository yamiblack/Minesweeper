package pt.technic.apps.minesfinder.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	public static final ExecutorService timeThreadPool = Executors.newSingleThreadExecutor();
	// Thread Pool : Thread 사용을 위한 Worker 미리 생성

}
