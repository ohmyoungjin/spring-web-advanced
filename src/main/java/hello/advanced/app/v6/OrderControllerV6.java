package hello.advanced.app.v6;

import hello.advanced.app.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV6 {

    private final OrderServiceV6 orderService;
    private final TraceTemplate template;

    //LogTrace 같은 경우에는 bean 주입 해주었기 때문에 쓸 수 있다.

    @GetMapping("/v6/request")
    public String request(String itemId) {

        return template.execute("OrderController.request()", () -> {
            orderService.order(itemId);
            return "OK";
        });
    }
}
