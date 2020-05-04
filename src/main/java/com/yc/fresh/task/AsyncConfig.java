package com.yc.fresh.task;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 多线程执行配置类
 * 源辰信息
 * @author navy
 * @date 2020年5月4日
 */
@Configuration // 表明该类是一个配置文件类
@EnableAsync  // 开始异步事件的支持
public class AsyncConfig {
	// 此处成员变量可以使用@Value()从配置文件中读取
	private int corePoolSize = 10;  // 核心线程数
	private int maxPoolSize = 200; // 最大线程数
	private int queueCapacity = 10; // 队列大小
	private int keepAliveSeconds = 3000; // 最大空闲时间
	
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.initialize();
		return executor;
	}
}
