package hello.advanced.app.v5;

import hello.advanced.app.callback.TraceCallback;
import hello.advanced.app.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;

    //LogTrace 같은 경우에는 어떤 구현체를 사용할 것인지 bean 주입 해주었기 때문에 쓸 수 있다.
    //이렇게 TraceTemplate 를 빈으로 등록해서 사용하는 것이 아닌 직접 주입해주는 이유는
    //후에 테스트 코드를 만들게 될 때 의존성이 있는 부분들을 다 주입해줘야 하는데
    //직접 주입하게 되면 LogTrace trace 이 부분은 목으로 만들거나 덜 번거롭게 하기 위해서이다.
    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {

//        return template.execute("OrderController.request()", () -> {
//            orderService.order(itemId);
//            return "OK";
//        });

        return template.execute("OrderController.request()", new TraceCallback<String>() {
            @Override
            public String call() {
                orderService.order(itemId);
                return "OK";
            }
        });
    }
}
