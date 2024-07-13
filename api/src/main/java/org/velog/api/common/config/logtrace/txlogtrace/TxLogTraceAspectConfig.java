package org.velog.api.common.config.logtrace.txlogtrace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.velog.api.common.config.logtrace.txlogtrace.advisor_aspect.TxLogTrace;
import org.velog.api.domain.log.service.LogService;
import org.velog.api.utils.logtrace.LogTrace;

@Configuration
public class TxLogTraceAspectConfig {
    @Bean
    public TxLogTrace txLogTrace(LogTrace logTrace, LogService logService){
        return new TxLogTrace(logTrace, logService);
    }
}
