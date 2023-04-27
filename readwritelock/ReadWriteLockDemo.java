package readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

//应用于缓存是否过期
public class ReadWriteLockDemo {
	//定义数据
	static String data = "数据库虚假的数据";
	//判断缓存是否过期
	static volatile boolean cacheValid;
	//定义读写锁
	final static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(()->{
			use(data);
			processCachedData();
		}, "t1");
		Thread thread2 = new Thread(() -> {
			use(data);
			processCachedData();
		}, "t2");
		thread1.start();
		thread2.start();
	}
	
	static void processCachedData() {
		//加读锁
		rwl.readLock().lock();
		//如果缓存没有过期则调用use(data)
		if(!cacheValid) { //如果缓存过期, 要去load真实数据, set给缓存拿到写锁
			//释放读锁, 因为不能读锁升级, 需要先释放
			rwl.readLock().unlock();
			//加写锁
			rwl.writeLock().lock();
			try {
				//双重检查 防止中间其他并发程序刷新缓存
				if(!cacheValid) {
					System.out.println(Thread.currentThread().getName()+ ": 获取真实数据");
					data = "数据库得到真实数据";
					cacheValid = false;
				}
				//更新缓存后接着读取 所以先加锁
				rwl.readLock().lock();
			}finally {
				//解锁写锁, 读锁仍然保持
				rwl.writeLock().unlock();
			}
		}
		try {
			//缓存最后可用了
			use(data);
		}finally {
			//解最后的读锁
			rwl.readLock().unlock();
		}
	}

	private static void use(String data) {
		System.out.println(Thread.currentThread().getName() + ":" + data);
	}
	
}
