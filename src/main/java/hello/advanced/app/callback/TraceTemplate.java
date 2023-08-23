package hello.advanced.app.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Component;

@Component
public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    //함수 부분에 <T> 를 선언한 이유는 call 자체에 <T> 제네릭으로 선언하게 되면,
    //클레스 인스턴스화 할 때 마다 맞춰서 제네릭 타입을 선언해줘야 하기 때문에
    //execute 사용 할 때만 제네릭 타입을 선언해주기 위해서이다.
    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            //로직 호출 추상화 변하는 부분
            T result = callback.call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
