package com.proj.client.loadbalancing;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import java.net.URI;

public class TempNameResolverProvider extends NameResolverProvider {

    @Override
    public String getDefaultScheme() {
        return "dns";
    }

    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 5;
    }

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        System.out.println(
                "Looking For Service : " + targetUri.toString()
        );
        return new TempNameResolver(targetUri.toString());
    }
}
