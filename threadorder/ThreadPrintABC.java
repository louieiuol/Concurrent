package threadorder;

//3个线程顺序打印ABC四次
//使用synchronized, wait(), notifyAll(), volatile决定线程的执行顺序

//调用wait(), notify() 前必须对对象进行加锁, wait()和notify()必须是同一对象, wait在线程获取对象锁后, 主动释放对象锁, 同时本线程阻塞, 直到其他线程调用notify()唤醒该线程,
//才能继续获得对象锁, 并继续执行
//notifyAll()唤醒其他线程的wait()后, 被唤醒的线程会再加一次锁
//notify()调用后不是马上释放对象锁的, 而是在synchronized(){}释放对象锁后, JVM会在wait()对象锁的线程中随机选取一线程, 赋予其对象锁唤醒线程
public class ThreadPrintABC{
	//定义线程间可见变量
	private volatile int value = 1; 
	 
	public void printA() {
		//加锁
		synchronized(this) {
			//不到1的时候阻塞等待
			while(value != 1) {
				try {
					wait();
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName()+ ": A");
			//修改标识
			value = 2;
			//唤醒其他线程
			notifyAll();
		}
	}
	
	public void printB() {
		synchronized(this) {
			while(value != 2) {
				try {
					wait();
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + ": B");
			value = 3;
			notifyAll();
		}		
	}
	
	public void printC() {
		synchronized(this) {
			while(value != 3) {
				try {
					wait();
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + ": C");
			value = 1;
			notifyAll();
		}		
	}
	
	public static void main(String[] args) {
		ThreadPrintABC p = new ThreadPrintABC();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 4; i++) {
					p.printA();
				}
			}
		}, "线程1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 4; i++) {
					p.printB();
				}	
			}
		}, "线程2");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 4; i++) {
					p.printC();
				}	
			}			 
		}, "线程3");
		t1.start();
		t2.start();
		t3.start();
	}

}
