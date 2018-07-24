package com.chnghx.web.demo.component;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class SyncComponent {

	@Async
    public Future<String> task() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(2000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("任务耗时:"+(currentTimeMillis1-currentTimeMillis));
        return new AsyncResult<String>("任务执行完成");
    }
	
    
}
