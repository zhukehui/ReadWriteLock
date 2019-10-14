package com.atkehui.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author eternity
 * @create 2019-10-14 10:59
 */

class Resource
{
//    private Lock lock = new ReentrantLock();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private volatile Map<String,String> map = new HashMap<>();

    public void write(String k , String v)
    {
        readWriteLock.writeLock().lock();//加锁
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t---开始写");
            map.put(k, v);
            System.out.println(Thread.currentThread().getName()+"\t结束写");
        }
        finally
        {
            readWriteLock.writeLock().unlock();//释放锁
        }
    }

    public void read(String k)
    {
        readWriteLock.readLock().lock();//加锁
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t***开始读取");
            String result = map.get(k);
            System.out.println(Thread.currentThread().getName()+"\t读取结束,result:"+result);
        }
        finally
        {
            readWriteLock.readLock().unlock();//释放锁
        }
    }
   /* public void write(String k , String v)
    {
        lock.lock();//加锁
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t---开始写");
            map.put(k, v);
            System.out.println(Thread.currentThread().getName()+"\t结束写");
        } finally
        {
            lock.unlock();//释放锁
        }
    }

    public void read(String k)
    {
        lock.lock();//加锁
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t***开始读取");
            String result = map.get(k);
            System.out.println(Thread.currentThread().getName()+"\t读取结束,result:"+result);
        } finally
        {
            lock.unlock();//释放锁
        }
    }*/
}

public class ReadWriteLock
{
    public static void main(String[] args)
    {
        Resource resource = new Resource();
        for (int i = 1; i <= 10; i++)
        {
            int finalI = i;
            int finalI1 = i;
            new Thread(() ->
            {
                resource.write(finalI +"", finalI1 +"");
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 10; i++)
        {
            int finalI = i;
            new Thread(() ->
            {
                resource.read(finalI +"");
            }, String.valueOf(i)).start();
        }
    }
}
