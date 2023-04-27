package consumerandproducer;

import java.util.*;

public class ConsumerProducer {
	Queue<Integer> queue = new LinkedList<>();
	int max = 2;
	
	public class Consumer implements Runnable{

		@Override
		public void run() {
			synchronized(this){
				while(queue.isEmpty()) {
					try {
						wait();
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(!queue.isEmpty()) {
					queue.poll();
					System.out.println(Thread.currentThread().getId() + ": poll");
					notify();
				}
			}
		}
	}
	
	public class Producer implements Runnable {

		@Override
		public void run() {
			synchronized(this) {
				while(queue.size() == max) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				queue.add(1);
				System.out.println(Thread.currentThread().getId() + ": offer");
				notify();
			}
		}
	}
	
	public static void main(String[] args) {
		ConsumerProducer cp = new ConsumerProducer();
		Producer p = cp.new Producer();
		Consumer c = cp.new Consumer();
		for(int i =0; i<10; i++) {
			new Thread(p).start();
			new Thread(c).start();
		}
	}
}
