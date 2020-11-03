package com.flab.makedel.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*
    스프링의 @Async를 사용할 때 비동기처리를 새로운 스레드 풀에서 해주기 위한 설정입니다.
    이 설정이 없다면 SimpleAsyncTaskExecutor를 사용하는데 이는 새로운 비동기 작업을
    스레드 풀에서 처리하는 것이 아니라 새로운 스레드를 매번 생성하여 작업을 수행시킵니다.
    또한 스레드 관리를 직접 할 수 없어 위험할 수 있습니다.
    따라서 밑에 설정에서 스레드 풀을 빈으로 설정해서 @Async 로직이 수행될 때
    이 스레드 풀을 이용하도록 설정해줍니다.
 */

@Configuration
@EnableAsync
public class SpringAsyncConfig {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 500;
    private static final int QUEUE_CAPACITY = 0;
    private static final int KEEP_ALIVE_SECONDS = 60;
    private static final String NAME_PREFIX = "springAsyncTask-";

    @Bean(name = "springAsyncTask")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setThreadNamePrefix(NAME_PREFIX);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        taskExecutor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setRejectedExecutionHandler(new AbortPolicy());
        return taskExecutor;
    }

}
