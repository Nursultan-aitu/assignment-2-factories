package kz.aitu.factories.dispatch;

import kz.aitu.factories.family.NetworkFamily;

public class EcoCreator<F extends NetworkFamily> extends DispatchCreator<F> {
    @Override
    protected DispatchStrategy<F> createStrategy() {
        return new EcoDispatch<>();
    }
}
