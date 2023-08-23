package hello.advanced.app.v6;

import hello.advanced.app.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV6 {

    private final OrderRepositoryV6 orderRepository;
    private final TraceTemplate template;

    public void order(String itemId) {
        template.execute("orderService.save()", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}
