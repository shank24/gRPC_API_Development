package com.proj.client.metadata;

import io.grpc.CallCredentials;

import java.util.concurrent.Executor;

public class UserSession extends CallCredentials {

    private String jwt;
    public UserSessionToken
    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {

    }

    @Override
    public void thisUsesUnstableApi() {
        // May Change In Future
    }
}
