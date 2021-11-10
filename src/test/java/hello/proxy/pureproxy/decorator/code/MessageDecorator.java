package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private Component component;

    public MessageDecorator(final Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");
        final String result = component.operation();
        final String decoResult = "*****" + result + "*****";
        log.info("적용 전={}, 적용 후={}", result, decoResult);
        return decoResult;
    }
}
