package hello.advanced.app.v6;

import hello.advanced.app.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV6 {

    private final TraceTemplate template;

    public void save(String itemId) {


        template.execute("OrderRepository.save()", () -> {
            if (itemId.equals("ex")) {
                throw new IllegalArgumentException("예외 발생");
            }
            sleep(1000);
            return null;
        });
    }

    private void sleep(int millis) { // 이 부분은 try catch 구문이 들어가게 되면 지저분해져서 따로 함수로 뺐다
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
