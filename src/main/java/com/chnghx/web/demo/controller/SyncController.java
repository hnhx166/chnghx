package com.chnghx.web.demo.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chnghx.web.demo.component.SyncComponent;

/**
 * 
*    
* 项目名称：chnghx   
* 类名称：SyncController   
* 类描述：   异步调用
* 创建人：guohaixiang  
* 创建时间：2018年2月28日 上午9:30:25   
* 修改人：Administrator   
* 修改时间：2018年2月28日 上午9:30:25   
* 修改备注：   
* @version 1.0
*
 */
@RestController
@RequestMapping("sync")
public class SyncController {
	
	@Autowired
	SyncComponent syncComponent;

	/**
	 * 异步方法调用
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException 
	 */
	@RequestMapping("daTask")  
    public String doTask() throws InterruptedException, ExecutionException{
        long currentTimeMillis = System.currentTimeMillis();
        this.task1();//执行void的异步方法
        
        Future<String> t2 = this.task2();//待返回值的异步方法
        if(t2.isDone()) {//判断任务完成
        	System.out.println("任务2执行完成。");
        	System.out.println(t2.get());//获取返回值，抛出ExecutionException
        }
        long currentTimeMillis1 = System.currentTimeMillis();
        
        Future<String> taskComponent = syncComponent.task();  
        if(taskComponent.isDone()) {
        	System.out.println("任务2执行完成。");
        }
        
        
        return "task任务总耗时:"+(currentTimeMillis1-currentTimeMillis);
    }
	
	/**
	 * 异步方法1, 不需要获取分返回值
	 * @throws InterruptedException
	 */
	@Async
    public void task1() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(1000);
        long currentTimeMillis2 = System.currentTimeMillis();
        System.out.println("task1任务耗时:"+(currentTimeMillis2-currentTimeMillis));
    }
	
	/**
	 * 异步方法2
	 * @throws InterruptedException
	 */
	@Async
    public Future<String> task2() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(1000);
        long currentTimeMillis2 = System.currentTimeMillis();  
        System.out.println("task2任务耗时:"+(currentTimeMillis2-currentTimeMillis));
        return new AsyncResult<String>("task2执行完毕");
    }
	
}
