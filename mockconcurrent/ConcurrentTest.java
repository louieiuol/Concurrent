package mockconcurrent;

import java.util.concurrent.*;

public class ConcurrentTest {
	//设定并发数量
	private static final int threadNum = 10;
	
	//定义线程共享可见的栅栏
	private static volatile CountDownLatch countDownLatch = new CountDownLatch(threadNum);
	
	//定义线程共享可见的循环屏障
	private static volatile CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum);
	
	
	public static void main(String[] args) throws InterruptedException {
		
		//引入固定数量的线程池管理线程
		ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
		
		for(int i = 0; i<threadNum; i++) {
			executorService.execute(() -> {
				try {
					//所有线程在这里等待
					System.out.println(Thread.currentThread().getName() + "开始执行: ");
					cyclicBarrier.await();
					System.out.println(Thread.currentThread().getName() + "执行完毕, 消耗时间: " + System.currentTimeMillis());
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					countDownLatch.countDown();
				}
			});
		}
		
		countDownLatch.await();
		executorService.shutdown();
	}
}
