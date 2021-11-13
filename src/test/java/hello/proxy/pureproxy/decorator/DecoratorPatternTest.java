package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        final Component realComponent = new RealComponent();
        final DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }

    @Test
    void decorator1() {
        final RealComponent realComponent = new RealComponent();
        final Component messageDecorator = new MessageDecorator(realComponent);
        final DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
        client.execute();
    }

    @Test
    void decorator2() {
        final RealComponent realComponent = new RealComponent();
        final Component messageDecorator = new MessageDecorator(realComponent);
        final Component timeDecorator = new TimeDecorator(messageDecorator);
        final DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }

}
