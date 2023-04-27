package readwritelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

	static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	static Lock r = rwl.readLock();
	static Lock w = rwl.writeLock();
	
	
	public static void main(String[] args) throws InterruptedException {
		//读
		Thread thread1 = new Thread(() -> {
			System.out.println("Thread1: 获取读锁");
			r.lock();
			try {
				for(int i=0; i<10; i++) {
					m1(i);
				}
			}finally {
				r.unlock();
			}
		}, "t1");
		
		//写
		Thread thread2 = new Thread(()->{
			System.out.println("Thread2: 获取写锁");
			w.lock();
			try {
				for(int i=0; i<20; i++) {
					m1(i);
				}
			}finally {
				w.unlock();
			}
		}, "t2");
		
		//读
		Thread thread3 = new Thread(()->{
			System.out.println("Thread3: 获取读锁");
			r.lock();
			try {
				for(int i=0 ; i < 20; i++) {
					m1(i);
				}
			}finally {
				r.unlock();
			}
		}, "t3");
		
		thread1.start();
		thread2.start();
		thread3.start();
	}


	private static void m1(int i) {
		System.out.println(Thread.currentThread().getName()+ ":"+ i);
		try {
			TimeUnit.SECONDS.sleep(1);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
