package hello.advanced.trace.template.study;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Woman extends StudyAbstractTemplate{

    @Override
    protected void call() {
        log.info("나는 여자 입니다");
    }
}
