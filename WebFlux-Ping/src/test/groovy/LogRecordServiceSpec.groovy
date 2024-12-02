import com.momo.config.KafkaProducer
import com.momo.service.impl.LogRecordService
import com.momo.task.PingTask
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class LogRecordServiceSpec extends Specification {

    def "test add log "() {
        given:
        def logRecordService=Mock(LogRecordService)
        when:
        logRecordService.addLog(null)
        then:
        void
    }
}
