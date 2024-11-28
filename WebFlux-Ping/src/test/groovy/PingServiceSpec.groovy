import com.momo.service.PingService
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class PingServiceSpec extends Specification {

    def "test ping "() {
        given:
        def pingService = new PingService();
        when:
        pingService.ping()
        then:
        void
    }

    def "test ping null func"() {
        given:
        def pingService = new PingService();
        when:
        pingService.checkFileLock(null, null);
        then:
        thrown(NullPointerException)
    }

    def "test ping func"() {
        given:
        def pingService = new PingService();
        RandomAccessFile file = new RandomAccessFile("test.txt", "rw")
        file.writeUTF("20240202-2")
        when:
        pingService.checkFileLock("20240202", file);
        then:
        void
    }
}
