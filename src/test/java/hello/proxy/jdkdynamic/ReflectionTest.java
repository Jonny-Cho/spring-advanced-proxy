package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        final Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        final String result1 = target.callA(); //호출하는 메서드가 다름
        log.info("result={}", result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        final String result2 = target.callB(); //호출하는 메서드가 다름
        log.info("result={}", result2);
        //공통 로직2 종료
    }

    @Test
    void reflection1() throws Exception {
        final Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        final Hello target = new Hello();
        //callA 메서드 정보
        final Method methodCallA = classHello.getMethod("callA");
        final Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        //callB 메서드 정보
        final Method methodCallB = classHello.getMethod("callB");
        final Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        final Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        final Hello target = new Hello();
        final Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        final Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(final Method method, final Object target) throws Exception {
        log.info("start");
        final Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }
        public String callB() {
            log.info("callB");
            return "B";
        }
    }

}
