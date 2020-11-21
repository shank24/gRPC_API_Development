package com.proj.server.ssl;

import com.proj.models.*;
import com.proj.server.rpctypes.AccountDatabase;
import com.proj.server.rpctypes.CashStreamingRequest;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {

        int accountNumber = request.getAccountNumber();

        System.out.println(
                "Received the request for : "+ accountNumber
        );
        Balance balance = Balance.newBuilder()
                .setAmount(AccountDatabase.getBalance(accountNumber))
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }


    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDatabase.getBalance(accountNumber);

        if(balance<amount){
            Status status = Status.FAILED_PRECONDITION.withDescription("Not Enough Money, You have only " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }


        for (int i = 0; i <amount/10 ; i++) {
            Money money = Money.newBuilder().setValue(10).build();
            responseObserver.onNext(money);
            AccountDatabase.deductBalance(accountNumber,10);
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}
