package deadlock;

import java.util.concurrent.TimeUnit;


//如果线程需要获取多把锁可能会发生死锁
public class DeadLockTest {
	//定义两个对象
	static Object x = new Object();
	static Object y = new Object();
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(() -> {
		synchronized(x) {
				System.out.println("locked X");
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				synchronized(y) {
					System.out.println("locked Y");
					System.out.println("Thread 1 ends -----------");
				}
			}
		});
		Thread thread2 = new Thread(() -> {
			synchronized(y) {
				System.out.println("locked Y");
				try {
					TimeUnit.SECONDS.sleep(2);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				synchronized(x) {
					System.out.println("locked X");
					System.out.println("Thread 2 ends ------------");
				}
			}
		});
		thread1.start();
		thread2.start();
	}
}
