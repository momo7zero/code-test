import com.momo.service.PongService
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import spock.lang.Specification

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
        pongService.handle(session)
        then:
        thrown(NullPointerException)
    }

    def "pong msg"() {
        given:
        PongService pongService = new PongService();
        def session = Mock(WebSocketSession)
        def msg = Mock(WebSocketMessage)
        def now = "20220202"
        when:
        def msg1 = pongService.condMsg(session, msg, now)
        then:
        msg1 == null
    }

    def "pong msg success"() {
        given:
        PongService pongService = new PongService();
        def session = Mock(WebSocketSession)
        def msg = Mock(WebSocketMessage)
        when:
        session.textMessage("World")
        msg.payloadAsText == "Hello"
        def msg1 = pongService.condMsg(session, msg, null)
        then:
        msg1 == null
    }
}