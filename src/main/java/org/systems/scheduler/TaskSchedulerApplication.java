package org.systems.scheduler;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"org.systems.scheduler"})
@EntityScan("org.systems.scheduler.model")
public class TaskSchedulerApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerApplication.class);
	
	@Autowired
	private RedisConnectionFactory connectionFactory;

	public static void main(String[] args) {
		SpringApplication.run(TaskSchedulerApplication.class);
	}
	
    @Override
    public void run(String... args) throws Exception {
        try (RedisConnection connection = connectionFactory.getConnection()) {
            // Get the server info
            Properties serverInfo = connection.info();
            LOGGER.info("Connected to Redis cluster successfully!");
        } catch (Exception e) {
            LOGGER.error("Failed to connect to Redis cluster: ", e);
        }
    }

}
