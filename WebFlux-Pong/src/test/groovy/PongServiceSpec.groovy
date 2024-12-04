import com.momo.service.ILogRecordService
import com.momo.service.impl.LogRecordServiceImpl
import com.momo.service.impl.PongService
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@SpringBootTest
@ActiveProfiles("test")
class PongServiceSpec extends Specification {

    def "pong test null"() {
        given:
        PongService pongService = new PongService();
        when:
        pongService.handle(null)
        then:
        thrown(NullPointerException)
    }

    def "pong test"() {
        given:
        PongService pongService = new PongService();
        def session = Mock(WebSocketSession)
        when:
        def ssesion1 = pongService.handle(session)
        then:
        thrown(NullPointerException)
    }

    def "pong msg test"() {
        given:
        PongService pongService = new PongService();
        def session = Mock(WebSocketSession)
        def msg = Mock(WebSocketMessage)
        def now = "20220202"
        when:
        pongService.condMsg(session, msg, now)
        then:
        thrown(NullPointerException)
    }

    def "pong msg get err 429"() {
        given:
        PongService pongService = new PongService();
        def session = Mock(WebSocketSession)
        def msg = Mockito.mock(WebSocketMessage)
        def now = "20220202"
        Mockito.when(msg.getPayloadAsText()).thenReturn("Hello")
        pongService.logRecordService = new LogRecordServiceImpl();
        pongService.map=new ConcurrentHashMap<>()
        when:
        def msg1 = pongService.condMsg(session, msg, now)
        then:
        thrown(NullPointerException)
    }

    def "pong msg get success"() {
        given:
        PongService pongService = new PongService();
        def session = Mock(WebSocketSession)
        def msg = Mock(WebSocketMessage)
        when:
        def msg1 = pongService.getSuccessMsg(session, null)
        then:
        thrown(NullPointerException)
    }

    def "pong msg add log"() {
        given:
        PongService pongService = new PongService();
        when:
        pongService.addToLog(null)
        then:
        thrown(NullPointerException)
    }
}