package com.proj.client.loadbalancing;


import com.proj.models.Balance;
import com.proj.models.BalanceCheckRequest;
import com.proj.models.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientSideLoadBalancingTest {


    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    public void setUp(){

        List<String> instances = new ArrayList<>();
        instances.add("localhost:6565");
        instances.add("localhost:7575");



        ServiceRegistry.register("bank-service", instances);

        NameResolverRegistry.getDefaultRegistry()
                .register(new TempNameResolverProvider());

        ManagedChannel managedChannel = ManagedChannelBuilder
                //.forAddress("localhost", 8585)
                .forTarget("bank-service")
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();

        this.blockingStub  = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @Test
    public void balanceTest() {
        for (int i = 0; i < 100; i++) {
            BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(ThreadLocalRandom.current().nextInt(1, 11))
                    .build();
            Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
            System.out.println(
                    "Received : " + balance.getAmount()
            );
        }
    }

}
