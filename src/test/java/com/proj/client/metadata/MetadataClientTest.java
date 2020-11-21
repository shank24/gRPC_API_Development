package com.proj.client.metadata;

import com.proj.client.deadline.DeadlineInterceptor;
import com.proj.client.rpctypes.MoneyStreamingResponse;
import com.proj.models.Balance;
import com.proj.models.BalanceCheckRequest;
import com.proj.models.BankServiceGrpc;
import com.proj.models.WithdrawRequest;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MetadataClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;
    @BeforeAll
    public void setUp(){

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientConstants.getClientToken()))
                .usePlaintext()
                .build();
       this.blockingStub  = BankServiceGrpc.newBlockingStub(managedChannel);
       this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);

    }

    @Test
    public void balanceTest() throws InterruptedException {
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(10)
                .build();

        for (int i = 0; i <20 ; i++) {
            try{
                int random = ThreadLocalRandom.current().nextInt(1,4);
                System.out.println("Random : " + random);

                Balance balance = this.blockingStub
                        .withCallCredentials(new UserSessionToken("user-secret-"+random + ":standard"))
                        .getBalance(balanceCheckRequest);

                System.out.println(
                        "Received : " + balance.getAmount());
            }
            catch (StatusRuntimeException e){
                e.printStackTrace();
            }
        }



    }

    @Test
    public void withdrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(5)
                .setAmount(50)
                .build();
        try {
            this.blockingStub
                    .withDeadline(Deadline.after(2,TimeUnit.SECONDS))
                    .withdraw(withdrawRequest)
                    .forEachRemaining(money -> System.out.println("Received " + money.getValue()));
        }
        catch (StatusRuntimeException e){
            //e.printStackTrace();
        }
    }

    @Test
    public void withdrawAsyncTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(40)
                .build();
        this.bankServiceStub.withdraw(withdrawRequest,new MoneyStreamingResponse(latch));
        latch.await();
    }


}
