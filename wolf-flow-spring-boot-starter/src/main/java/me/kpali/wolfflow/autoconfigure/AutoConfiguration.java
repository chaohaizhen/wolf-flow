package me.kpali.wolfflow.autoconfigure;

import me.kpali.wolfflow.autoconfigure.config.*;
import me.kpali.wolfflow.autoconfigure.properties.ExecutorProperties;
import me.kpali.wolfflow.autoconfigure.properties.SchedulerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 自动配置
 *
 * @author kpali
 */
@EnableConfigurationProperties({SchedulerProperties.class,
        ExecutorProperties.class})
@Import({SchedulerConfiguration.class,
        ExecutorConfiguration.class,
        QuerierConfiguration.class,
        LoggerConfiguration.class,
        ClusterConfiguration.class})
@Configuration
public class AutoConfiguration {
}