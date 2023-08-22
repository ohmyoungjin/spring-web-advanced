package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    private TraceId traceIdHolder; //traceId 동기화, 동시성 이슈 발생 => 빈으로 등록돼있는 인스턴스는 싱글턴(인스턴스가 한 개)이기 때문에 여러곳에서 동시다발적으로 접근 하게 되면 동시성 이슈가 생긴다.
    //스프링 부트 같은 경우 빈을 싱글턴으로 등록해놓기 때문에 지역변수 같은 경우에는 공유하지 않지만, 전역변수, static 변수 같은 경우는 메모리를 공유하게 되어 정리해주지 않으면
    //빈은 해당하는 변수 값들을 저장하고 가지고 있다.
    //여기에서 요청 받은 각 각의 http 요청을 판단하기 위해서 마지막에 traceIdHolder = null; => destroy 를 실행해서 traceIdHolder 값을 삭제 후에
    //다음 요청을 받게 되면 다시 생성토록 한다.
    //traceIdHolder = null 이 부분을 실행하지 않으면 각자 다른 요청임에도 똑같은 traceIdHolder 를 공유하게 된다.
    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder;
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX,
                traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
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
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
                    resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());
        }
        releaseTraceId();
    }
    private void syncTraceId() {
        if (traceIdHolder == null) {
            log.info("traceIdHolder is null !");
            traceIdHolder = new TraceId();
        } else {
            traceIdHolder = traceIdHolder.createNextId();
        }
    }
    private void releaseTraceId() {
        if (traceIdHolder.isFirstLevel()) {
            traceIdHolder = null; //destroy
        } else {
            traceIdHolder = traceIdHolder.createPreviousId();
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
