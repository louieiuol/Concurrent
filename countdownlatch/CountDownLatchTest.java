package countdownlatch;

import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;

//用来使一个线程等待其他N个线程执行完毕后, 再执行, 在其他N个线程没有执行结束之前一直阻塞, 其他的N个线程执行完毕后各自退出
//例如饭店不断有客人来吃饭, 必须等所有客人吃完之后才能关闭, 第一个客人走了跟第二个客人走不走无关, 如果很晚之后客人还没有走, 则强制关闭
//CountDownLatch是一个同步工具类, 用来协调多个线程之间的同步, 或者说起到线程间的通信
//能够使一个线程在等待另外一些线程完成各自的工作后, 再继续执行, 计数器初始为线程数量, 当每一个线程完成自己的任务后, 计数器的值就会减1, 
//当计数器的值为0时, 表示所有线程都已经完成, CountDownLatch上等待的线程可以恢复执行接下来的任务
public class CountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(3);
		Thread t1 = new Thread() {
			public void run() {
				try {
					System.out.println("第一个客人进来吃饭...");
					Thread.sleep(2000);
					System.out.println("第一个客人吃完了...");
					latch.countDown();
				}catch(InterruptedException e) {
					
				}
			}
		};
		
		Thread t2 = new Thread() {
			public void run() {
				try {
					System.out.println("第二个客人进来吃饭...");
					Thread.sleep(1000);
					System.out.println("第二个客人吃完了...");	
					latch.countDown();
				}catch(InterruptedException e) {
					
				}
			}
		};
		
		Thread t3 = new Thread() {
			public void run() {
				try {
					System.out.println("第三个客人进来吃饭...");
					Thread.sleep(12000);
					System.out.println("第三个客人吃完了...");	
					latch.countDown();
				}catch(InterruptedException e) {
					
				}
			}
		};
		
		Thread t4 = new Thread() {
			public void run() {
				try {
					System.out.println("我是店主, 等客人吃完饭后再打烊...");
					latch.await();
					System.out.println("我是店主, 大家都吃完了, 该关门了");
					//System.out.println("我是店主, 饭店到时间了, 该关门了...");
				}catch(InterruptedException e) {
				}
			}
		};
		t4.start();
		t1.start();
		t2.start();
		t3.start();
	}
}

//一个构造器 public CountDownLatch(int count);
//count 为计数值

//三个方法
//public void await() throws InterruptedException;
//await方法的线程将会挂起, 直到count为0 才继续执行

//public boolean await(long timeout, TimeUnit unit) throws InterruptedException;
//和await()类似, 不过等待一定时间后count值还没变成0, 就会继续执行

//public void countDown(); 
//将count值-1
