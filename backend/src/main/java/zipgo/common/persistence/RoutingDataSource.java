package zipgo.common.persistence;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static zipgo.common.persistence.DataSourceType.*;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static RoutingDataSource from(Map<Object, Object> dataSources) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(dataSources.get(SOURCE));
        routingDataSource.setTargetDataSources(dataSources);
        return routingDataSource;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        if (readOnly) {
            log.info("readOnly = true, request to replica");
            return REPLICA;
        }
        log.info("readOnly = false, request to source");
        return SOURCE;
    }
}
