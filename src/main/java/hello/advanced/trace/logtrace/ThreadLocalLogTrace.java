package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>(); // traceId 동기화, 동시성 이슈 해결 // 내부에 조그마한 데이터 베이스가 있다고 생각하면 편할 듯 ..?
    // private T setInitialValue() {
    //        T value = initialValue();
    //        Thread t = Thread.currentThread();
    //        ThreadLocalMap map = getMap(t);
    //        if (map != null) {
    //            map.set(this, value);
    //        } else {
    //            createMap(t, value);
    //        }
    //        if (this instanceof TerminatingThreadLocal) {
    //            TerminatingThreadLocal.register((TerminatingThreadLocal<?>) this);
    //        }
    //        return value;
    //    } 이런식으로 저장된다.


    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        long startTimeMs = System.currentTimeMillis();
        //로그 출력
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        TraceStatus traceStatus = new TraceStatus(traceId, startTimeMs, message);
        return traceStatus;
    }

    private void syncTraceId() {
        if (traceIdHolder.get() == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceIdHolder.get().createNextId());
        }
    }
    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        //TraceId traceId = status.getTraceId();

        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceIdHolder.get().getId(),
                    addSpace(COMPLETE_PREFIX, traceIdHolder.get().getLevel()), status.getMessage(),
                    resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceIdHolder.get().getId(),
                    addSpace(EX_PREFIX, traceIdHolder.get().getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());
        }

        releaseTraceId();
    }

    private void releaseTraceId() {
        if (traceIdHolder.get().isFirstLevel()) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceIdHolder.get().createPreviousId());
        }
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
}
