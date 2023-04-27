package livelock;

import java.util.concurrent.TimeUnit;

//活锁不可避免, 可能自行解锁, 可以错开它们的执行时间
public class LiveLockTest {
	static volatile int count = 10;
	static final Object lock = new Object();
	
	public static void main(String[] args) {
		//t1线程对count做减法, 直到0结束
		Thread thread1 = new Thread(() -> {
			while(count > 0) {
				try {
					TimeUnit.SECONDS.sleep(1);
					//设定不同的时间 避开执行
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				count--;
				System.out.println("t1 count: "+count);
			}
		});
		
		Thread thread2 = new Thread(() -> {
			while(count < 20) {
				try {
					TimeUnit.SECONDS.sleep(2);
					//设定不同的时间 避开执行
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				count++;
				System.out.println("t2 count: "+ count);
			}
		});
		thread1.start();
		thread2.start();
	}
	
}
