package com.proj.client.loadbalancing;

import io.grpc.NameResolver;

public class TempNameResolver extends NameResolver {

    @Override
    public String getServiceAuthority() {
        return "temp";
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void start(Listener2 listener) {

        listener.onResult();
        super.start(listener);
    }
}
