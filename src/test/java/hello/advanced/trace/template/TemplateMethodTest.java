package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubclassLogic1;
import hello.advanced.trace.template.code.SubclassLogic2;
import hello.advanced.trace.template.study.Man;
import hello.advanced.trace.template.study.StudyAbstractTemplate;
import hello.advanced.trace.template.study.Woman;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비지니스 로직 실행
        log.info("비지니스 로직1 실행");
        //비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);

    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비지니스 로직 실행
        log.info("비지니스 로직2 실행");
        //비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    /**
     * 템플릿 메서드 패턴 적용
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubclassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubclassLogic2();
        template2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            //익명 내부 클래스
            @Override
            protected void call() {
                log.info("비지니스 로직1 실행");
            }
        };
        log.info("클래스 이름1={}", template1.getClass());
        template1.execute();
        AbstractTemplate template2 = new AbstractTemplate() {
            //익명 내부 클래스
            @Override
            protected void call() {
                log.info("비지니스 로직2 실행");
            }
        };
        log.info("클래스 이름2={}", template2.getClass());
        template2.execute();
    }

    @Test
    void studyTemplateMethod() {
        //추상 클레스를 인스턴스화 시키면 추상 메서드 override 해줘야 한다.
        StudyAbstractTemplate aniMal = new StudyAbstractTemplate() {
            @Override
            protected void call() {
                log.info("나는 동물입니다");
            }
        };
        aniMal.amI();

        //상속 받은 자식 클레스에서 오버라이드 한 추상메서드가 실행된다.
        StudyAbstractTemplate man = new Man();
        man.amI();

        StudyAbstractTemplate woman = new Woman();
        woman.amI();
    }
}
