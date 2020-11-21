package com.proj.server.metadata;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRPCServer1 {
    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(6565)
                .intercept(new AuthInterceptor())
                .addService(new MetadataService())
                .build();

        server.start();

        server.awaitTermination();

    }
}
