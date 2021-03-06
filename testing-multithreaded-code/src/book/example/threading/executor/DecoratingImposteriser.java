package book.example.threading.executor;

import org.jmock.api.Imposteriser;
import org.jmock.api.Invocation;
import org.jmock.api.Invokable;


public abstract class DecoratingImposteriser implements Imposteriser {
    private final Imposteriser imposteriser;

    protected DecoratingImposteriser(Imposteriser imposteriser) {
        this.imposteriser = imposteriser;
    }

    public boolean canImposterise(Class<?> type) {
        return imposteriser.canImposterise(type);
    }

    public <T> T imposterise(Invokable mockObject, Class<T> mockedType, Class<?>... ancilliaryTypes) {
        return imposteriser.imposterise(decorate(mockObject), mockedType, ancilliaryTypes);
    }

    private Invokable decorate(final Invokable mockObject) {
        return new Invokable() {
            public Object invoke(Invocation invocation) throws Throwable {
                return applyInvocation(mockObject, invocation);
            }
        };
    }

    protected abstract Object applyInvocation(Invokable imposter, Invocation invocation)
            throws Throwable;
}
