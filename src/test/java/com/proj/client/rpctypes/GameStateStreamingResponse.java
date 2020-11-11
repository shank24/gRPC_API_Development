package com.proj.client.rpctypes;

import com.google.common.util.concurrent.Uninterruptibles;
import com.proj.models.Die;
import com.proj.models.GameState;
import com.proj.models.Player;
import io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GameStateStreamingResponse implements StreamObserver<GameState> {

    private CountDownLatch latch;
    private StreamObserver<Die> dieStreamObserver;


    public GameStateStreamingResponse(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(GameState gameState) {
        List<Player> list = gameState.getPlayerList();

        list.forEach(player -> System.out.println(player.getName() +    ":" + player.getPosition()));
        boolean isGameOver= list.stream()
                .anyMatch(player -> player.getPosition() == 100);

        if(isGameOver){
            System.out.println("Game Over !! ");
            this.onCompleted();
        }
            else {
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
                this.roll();
        }
        System.out.println("-------------------------------");
    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        this.latch.countDown();
    }

    public void setDieStreamObserver(StreamObserver<Die> streamObserver) {
        this.dieStreamObserver= streamObserver;
    }

    public void roll(){
        int dieValue = ThreadLocalRandom.current().nextInt(1,7);
        Die die = Die.newBuilder().setValue(dieValue).build();
        this.dieStreamObserver.onNext(die);
    }
}
