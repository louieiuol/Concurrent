package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


//CyclicBarrier作用是使多个线程达到同一屏障然后才能继续后续工作, 各个线程是互相等待, 大家都达到同一个屏障后才能继续下一步工作
//例如朋友聚餐干杯, 有的人动作快, 有的人动作慢, 最终等大家都举起来之后, 才进行干杯
//循环屏障, 可以在等待的线程被释放后重新使用
//CyclicBarrier内部定一个lock对象, 每当一个线程调用await方法时, 将拦截的线程数-1, 然后判断剩余拦截数是否为初始值, 如果不是, 进入lock对象的条件队列等待
//如果是执行barrierAction对象的Runnable 方法, 然后将锁的条件队列中的所有线程放入等待队列中, 这些线程会依次获取锁, 释放锁
public class CyclicBarrierTest {
	public static void main(String[] args) {
		final CyclicBarrier cb = new CyclicBarrier(4);
		Thread t1= new Thread() {
			public void run() {
				try {
					System.out.println("这是A");
					Thread.sleep(2000);
					System.out.println("A说: 我的杯子已经端起来了");
					cb.await(10, TimeUnit.SECONDS);
					System.out.println("A说: 干杯");
				}catch(InterruptedException e) {
					e.printStackTrace();
				}catch(BrokenBarrierException e) {
					e.printStackTrace();
				}catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Thread t2 = new Thread() {
			public void run() {
				try {
					System.out.println("这是B");
					Thread.sleep(3000);
					System.out.println("B说: 我的杯子已经端起来了");
					cb.await(10, TimeUnit.SECONDS);
					System.out.println("B说: 干杯");
				}catch(InterruptedException e) {
					e.printStackTrace();
				}catch(BrokenBarrierException e) {
					e.printStackTrace();
				}catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		
		Thread t3 = new Thread() {
			public void run() {
				try {
					System.out.println("这是C");
					Thread.sleep(1000);
					System.out.println("C说: 我的杯子已经端起来了");
					cb.await(10, TimeUnit.SECONDS);
					System.out.println("C说: 干杯");
				}catch(InterruptedException e) {
					e.printStackTrace();
				}catch(BrokenBarrierException e) {
					e.printStackTrace();
				}catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Thread t4 = new Thread() {
			public void run() {
				try {
					System.out.println("这是D");
					Thread.sleep(15000);
					System.out.println("D说: 我的杯子已经端起来了");
					cb.await(10, TimeUnit.SECONDS);
					System.out.println("D说: 干杯");
				}catch(InterruptedException e) {
					e.printStackTrace();
				}catch(BrokenBarrierException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		t4.start();
		t3.start();
		t2.start();
		t1.start();
	}
}

//两个构造方法
//CyclicBarrier(int parties);
//当给定数量的线程等待它时, 它将跳闸
//CyclicBarrier(int parties, Runnable barrierAction);
//当给定数量的线程等待它时, 它将跳闸, 当屏障跳闸时执行给定的屏障动作, 最后一个进入屏障的线程执行

//int await();
//等待所有parties已经在这个障碍上调用了await

//int await(long timeout, TimeUnit timeunit);
//等待所有parties已经在这个屏障上调用了await,或者指定的等待时间过去

//int getNumberWaiting()
//返回目前正在等待障碍的线程数量

//int getParties();
//返回进行这个Barrier所需的parties数量

//boolean isBroken()
//查询这个障碍是否处于破碎状态