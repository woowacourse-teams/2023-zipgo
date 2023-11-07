package zipgo.common.config;

import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import zipgo.common.persistence.RoutingDataSource;

import static zipgo.common.persistence.DataSourceType.Key.REPLICA_NAME;
import static zipgo.common.persistence.DataSourceType.Key.ROUTING_NAME;
import static zipgo.common.persistence.DataSourceType.Key.SOURCE_NAME;
import static zipgo.common.persistence.DataSourceType.REPLICA;
import static zipgo.common.persistence.DataSourceType.SOURCE;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @Qualifier(SOURCE_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier(REPLICA_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.replica")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier(ROUTING_NAME)
    public DataSource routingDataSource(
            @Qualifier(SOURCE_NAME) DataSource sourceDataSource,
            @Qualifier(REPLICA_NAME) DataSource replicaDataSource
    ) {
        return RoutingDataSource.from(Map.of(
                SOURCE, sourceDataSource,
                REPLICA, replicaDataSource
        ));
    }

    @Bean
    @Primary
    public DataSource dataSource(@Qualifier(ROUTING_NAME) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

}
