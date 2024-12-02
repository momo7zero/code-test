import com.momo.config.KafkaProducer
import com.momo.service.impl.LogRecordService
import com.momo.task.PingTask
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class PingTaskSpec extends Specification {

    def "test ping "() {
        given:
        def pingService = new PingTask();
        def service = pingService.logRecordService = new LogRecordService()
        service.kafkaProducer=Mock(KafkaProducer)
        when:
        pingService.ping()
        then:
        void
    }

    def "test ping null func"() {
        given:
        def pingService = new PingTask();
        when:
        pingService.checkFileLock(null, null);
        then:
        thrown(NullPointerException)
    }

    def "test ping func"() {
        given:
        def pingService = new PingTask();
        def service = pingService.logRecordService = new LogRecordService()
        service.kafkaProducer=Mock(KafkaProducer)
        def file = new RandomAccessFile("test.txt","rw")
        when:
        pingService.checkFileLock("20240202", file);
        then:
        void
    }
    def "test rate limit"() {
        given:
        def pingService = new PingTask();
        def service = pingService.logRecordService = new LogRecordService()
        service.kafkaProducer=Mock(KafkaProducer)
        def file = Mockito.mock(RandomAccessFile.class)
        when:
        pingService.rateLimit(null)
        then:
        void
    }
}
