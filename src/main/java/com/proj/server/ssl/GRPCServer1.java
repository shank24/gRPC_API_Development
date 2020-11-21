package com.proj.server.ssl;

import com.proj.server.loadbalancing.BankService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;

import java.io.File;
import java.io.IOException;

public class GRPCServer1 {
    public static void main(String[] args) throws IOException, InterruptedException {

        SslContext sslContext = GrpcSslContexts.configure(
                SslContextBuilder.forServer(
                        new File("/home/shanky/Personal/Code/gRPC_Intro_Code/ssl-tls/localhost.crt"),
                        new File("/home/shanky/Personal/Code/gRPC_Intro_Code/ssl-tls/localhost.pem")
                )
        ).build();


        Server server = NettyServerBuilder.forPort(6565)
                        .sslContext(sslContext)
                        .addService(new BankService())
                        .build();

        server.start();

        server.awaitTermination();

    }
}
