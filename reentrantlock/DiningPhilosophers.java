package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

//哲学家进餐问题
//5 个沉默寡言的哲学家围坐在圆桌前，每人面前一盘意面。叉子放在哲学家之间的桌面上。（5 个哲学家，5 根叉子）

//所有的哲学家都只会在思考和进餐两种行为间交替。哲学家只有同时拿到左边和右边的叉子才能吃到面，而同一根叉子在同一时间只能被一个哲学家使用。每个哲学家吃完面后都需要把叉子放回桌面以供其他哲学家吃面。只要条件允许，哲学家可以拿起左边或者右边的叉子，但在没有同时拿到左右叉子时不能进食。

//假设面的数量没有限制，哲学家也能随便吃，不需要考虑吃不吃得下。

//设计一个进餐规则（并行算法）使得每个哲学家都不会挨饿；也就是说，在没有人知道别人什么时候想吃东西或思考的情况下，每个哲学家都可以在吃饭和思考之间一直交替下去。

//前面说过，该题的本质是考察 如何避免死锁。
//而当5个哲学家都左手持有其左边的叉子 或 当5个哲学家都右手持有其右边的叉子时，会发生死锁。
//故只需设计1个避免发生上述情况发生的策略即可。
//
//即可以让一部分哲学家优先去获取其左边的叉子，再去获取其右边的叉子；再让剩余哲学家优先去获取其右边的叉子，再去获取其左边的叉子
public class DiningPhilosophers {
		//1个Fork视为1个ReentrantLock，5个叉子即5个ReentrantLock，将其都放入数组中
    	private final ReentrantLock[] lockArray = {
            new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), 
            new ReentrantLock(), new ReentrantLock()
        };
        public DiningPhilosophers() {}

        // call the run() method of any runnable to execute its code
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            int leftFork = (philosopher + 1) % 5; //左边的叉子 的编号
            int rightFork = philosopher; //右边的叉子 的编号
            
          //编号为偶数的哲学家，优先拿起左边的叉子，再拿起右边的叉子
            if(philosopher % 2 == 0){
                lockArray[leftFork].lock();
                lockArray[rightFork].lock();
            }else{
            	//编号为奇数的哲学家，优先拿起右边的叉子，再拿起左边的叉子
                lockArray[rightFork].lock();
                lockArray[leftFork].lock();
            }
            pickLeftFork.run();
            pickRightFork.run();
            eat.run();
            putLeftFork.run();
            putRightFork.run();
            
            lockArray[leftFork].unlock();  //放下左边的叉子
            lockArray[rightFork].unlock(); //放下右边的叉子
        }
}
