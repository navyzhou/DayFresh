package com.yc.fresh.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling  // 开启对定时任务的支持
@Async  // 异步处理
public class TaskDemo2 {
	// 上一次开始执行时间点之后每隔5秒再执行一次
	@Scheduled(fixedRate = 5000)
	public void run1() throws InterruptedException {
		Thread.sleep(8000);
		System.out.println(Thread.currentThread().getName() + " ===>>> run1 == " + (System.currentTimeMillis()/ 1000));
	}


	// 上一次开始执行时间点之后每隔5秒再执行一次
	@Scheduled(fixedRate = 5000)
	public void run2() throws InterruptedException {
		Thread.sleep(6000);
		System.out.println(Thread.currentThread().getName() + " ===>>> run2 == " + (System.currentTimeMillis()/ 1000));
	}

	// 第一次延迟2秒后执行，之后每隔5秒执行一次
	@Scheduled(initialDelay = 2000, fixedRate = 5000)
	public void run3() throws InterruptedException {
		Thread.sleep(6000);
		System.out.println(Thread.currentThread().getName() + " ===>>> run3 == " + (System.currentTimeMillis()/ 1000));
	} 
}
