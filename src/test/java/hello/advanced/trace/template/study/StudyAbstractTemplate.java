package hello.advanced.trace.template.study;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class StudyAbstractTemplate {

    public void amI() {
        log.info("나는 사람 입니다");
        call(); //변경되는 부분
        log.info("누구인지 알겠나요?");
    }

    //추상메서드로 선언 하고, 상속 받은 자식 클레스에서 어떤 부분을 실행할건지 override 한다.
    protected abstract void call();

}
