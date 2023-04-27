package threadorder;

import java.util.concurrent.atomic.AtomicInteger;

//两个线程交替打印0-100, 使用wait()和notify()
public class ThreadSwitchPrint {
	private AtomicInteger i = new AtomicInteger(0);
	
	public void printOdd() {
		synchronized(this){
			if(i.get() % 2 != 0) {
				try {
					wait();
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + ": "+ i);
			i.getAndIncrement();
			notify();
		}	
	}
	
	
	public void printEven() {
		synchronized(this){
			if(i.get() % 2 == 0) {
				try {
					wait();
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + ": "+ i);
			i.getAndIncrement();
			notify();
		}
	}
	
	public static void main(String[] args) {
		ThreadSwitchPrint tsp = new ThreadSwitchPrint();
		Thread t1 = new Thread(() -> {
			for(int i = 0; i < 50; i++) {
				tsp.printEven();
			}
		}, "线程1");
		Thread t2 = new Thread(() -> {
			for(int i=0; i < 50; i++) {
				tsp.printOdd();
			}
		}, "线程2");
		t1.start();
		t2.start();
	}
}
