package hello.proxy.config;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderControllerV1 orderControllerV1(final LogTrace logTrace) {
        final OrderControllerV1Impl orderController = new OrderControllerV1Impl(orderServiceV1(logTrace));

        final LogTraceFilterHandler logTraceBasicHandler = new LogTraceFilterHandler(orderController, logTrace, PATTERNS);
        final OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), new Class[]{OrderControllerV1.class}, logTraceBasicHandler);
        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(final LogTrace logTrace) {
        final OrderServiceV1Impl orderService = new OrderServiceV1Impl(orderRepositoryV1(logTrace));

        final LogTraceFilterHandler logTraceBasicHandler = new LogTraceFilterHandler(orderService, logTrace, PATTERNS);
        final OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), new Class[]{OrderServiceV1.class}, logTraceBasicHandler);
        return proxy;
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(final LogTrace logTrace) {
        final OrderRepositoryV1Impl orderRepository = new OrderRepositoryV1Impl();

        final LogTraceFilterHandler logTraceBasicHandler = new LogTraceFilterHandler(orderRepository, logTrace, PATTERNS);
        final OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), new Class[]{OrderRepositoryV1.class}, logTraceBasicHandler);
        return proxy;
    }

}
