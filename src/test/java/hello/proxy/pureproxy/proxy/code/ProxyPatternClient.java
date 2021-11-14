package hello.proxy.pureproxy.proxy.code;

public class ProxyPatternClient {

    private final Subject subject;

    public ProxyPatternClient(final Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        subject.operation();
    }
}
