package com.proj.server.loadbalancing;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRPCServer2 {
    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(7575)
                .addService(new BankService())
                .build();

        server.start();

        server.awaitTermination();

    }
}
