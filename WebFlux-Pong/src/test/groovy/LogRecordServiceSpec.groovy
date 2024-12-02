import com.momo.service.ILogRecordService
import com.momo.service.impl.LogRecordServiceImpl
import com.momo.service.impl.PongService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class LogRecordServiceSpec extends Specification {
    @Shared
    ILogRecordService logRecordService = new LogRecordServiceImpl();

    def "add log"() {
        when:
        logRecordService.addLog("ping","test")
        then:
        thrown(NullPointerException)
    }
}