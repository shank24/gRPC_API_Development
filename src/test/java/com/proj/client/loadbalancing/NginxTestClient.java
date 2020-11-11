package com.proj.client.loadbalancing;


import com.proj.models.Balance;
import com.proj.models.BalanceCheckRequest;
import com.proj.models.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NginxTestClient {


    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    public void setUp(){

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext()
                .build();

        this.blockingStub  = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @Test
    public void balanceTest() throws InterruptedException {


        for (int i = 0; i < 11 ; i++) {

            BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(i)
                    .build();

            Balance balance = this.blockingStub.getBalance(balanceCheckRequest);

            System.out.println("Received : " + balance.getAmount());
        }
        }

}
