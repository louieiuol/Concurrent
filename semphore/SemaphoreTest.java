package semphore;

import java.util.concurrent.Semaphore;

//Java 版本的信号量实现，
//用于控制同时访问的线程个数，来达到限制通用资源访问的目的，其原理是通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可
public class SemaphoreTest {

	public static void main(String[] args) {
		//创建栅栏, 最多允许5个线程同时进行
		Semaphore semaphore = new Semaphore(5); 
		for(int i =0; i < 10; i++) {
			new Worker(i, semaphore).start();
		}
	}
	
	static class Worker extends Thread{
		private int num;
		private Semaphore semaphore;
		public Worker(int num, Semaphore semaphore) {
			this.num = num;
			this.semaphore = semaphore;
		}
		
		
		@Override
		public void run() {
			try {
				System.out.println("Thread: "+num+" start. ");
				semaphore.acquire();
				Thread.sleep(2000);
				semaphore.release();
				System.out.println("Thread: "+num+" end. ");
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
}
