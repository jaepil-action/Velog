package org.velog.api.common.config.logtrace.txlogtrace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.velog.api.common.config.logtrace.txlogtrace.advisor_aspect.TxLogTrace;
import org.velog.api.common.config.logtrace.v2_autoaopcreator_aspect.advisor_aspect.LogTraceAspect;
import org.velog.api.utils.logtrace.LogTrace;
import org.velog.db.log.TxLogRepository;

@Configuration
public class TxLogTraceAspectConfig {
    @Bean
    public TxLogTrace txLogTrace(LogTrace logTrace, TxLogRepository txLogRepository){
        return new TxLogTrace(logTrace, txLogRepository);
    }
}
