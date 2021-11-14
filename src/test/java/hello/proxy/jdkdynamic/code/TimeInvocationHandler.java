package hello.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target;

    public TimeInvocationHandler(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object o, final Method method, final Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        final long startTime = System.currentTimeMillis();

        final Object result = method.invoke(target, args);

        final long endTime = System.currentTimeMillis();
        final long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
