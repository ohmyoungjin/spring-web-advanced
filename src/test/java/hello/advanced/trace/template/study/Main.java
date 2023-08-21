package hello.advanced.trace.template.study;

import org.junit.jupiter.api.Test;

class Unit {
    public void attack() {
        System.out.println("유닛 공격");
    }
}

class Zealot extends Unit {
    public void attack() {
        System.out.println("찌르기");
    }

    public void teleportation() {
        System.out.println("프로토스 워프");
    }
}

class Marine extends Unit {
    public void attack() {
        System.out.println("총쏘기");
    }

    public void stimPack() {
        System.out.println("스팀 팩");
    }
}

public class Main {
    @Test
    void test() {
        //Unit unit_zealot = new Zealot(); // 참조 다형성
//
//// ------------------------------------------------
//
//        Zealot zealot = new Zealot();
//        Unit unit_up = zealot; // 변수 업캐스팅(upcasting)
//
//        //unit_up.teleportation(); 변수 업 캐스팅을 했기 때문에 자식 클레스에 있는 메서드는 사용이 불가능하다.
//        unit_up.attack(); //업캐스팅 했지만 오버라이딩 된 메서드는 자식 클래스의 메서드로 실행이 된다
//        unit_zealot.attack();
//        // * 다운캐스팅(downcasting) - 자식 전용 멤버를 이용하기위해, 이미 업캐스팅한 객체를 되돌릴때 사용
//        Zealot unit_down = (Zealot) unit_up; // 캐스팅 연산자는 생략 불가능. 반드시 기재
//        unit_down.attack(); // "찌르기"
//        unit_down.teleportation(); // "프로토스 워프"
//        //형제 클래스 끼리는 서로 캐스팅이 불가능
//        Unit unit_marine = new Marine();
//        unit_marine.attack();
//        Marine unit_marine_down = (Marine) unit_marine;
//        unit_marine_down.stimPack();
//        unit_marine = unit_down;
//        unit_marine.attack(); // 이건 된다
//
//        Zealot zz = (Zealot) unit_marine;
//
//        zz.attack();
//
//        Unit unit = new Unit();
//
//        Zealot unit_down2 = (Zealot) unit;
//        unit_down2.attack();
//        unit_down2.teleportation();

//        Unit unit = new Unit();
//        Zealot zealot = new Zealot();
//
//        // * 다운캐스팅(downcasting) 예외 - 같은 상속 자식 클래스라도 구성이 같아도 타입이 다르니 불가능
//        Marine marine = new Marine(); // 마린 클래스
//
//        Unit unit_m = marine; // 업캐스팅
//
//        Zealot zealot_marine = (Zealot) unit_m; // 다른 자식클래스 질럿으로 다운캐스팅
//        zealot_marine.attack(); //! RUNTIME ERRPR - Marine cannot be cast to Zealot
//        //zealot_marine.stimpack(); //! COMPILE ERRPR - Zealot 클래스에 없는 메소드이니 에러

        Zealot zealot = new Zealot();

        if (zealot instanceof Unit) {
            System.out.println("업캐스팅 가능"); // 실행
            Unit u = zealot; // 업캐스팅
        } else {
            System.out.println("업캐스팅 불가능");
        }

        // * 다운스캐팅 유무
        Unit unit = new Unit();
        Unit unit2 = new Zealot();

        if (unit instanceof Zealot) {
            System.out.println("다운캐스팅 가능");
        } else {
            System.out.println("다운캐스팅 불가능"); // 실행
        }

        if (unit2 instanceof Zealot) {
            System.out.println("다운캐스팅 가능"); // 실행
            Zealot z = (Zealot) unit2; // 다운캐스팅
        } else {
            System.out.println("다운캐스팅 불가능");
        }
    }
}
