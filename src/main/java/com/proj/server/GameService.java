package com.proj.server;

import com.proj.models.Die;
import com.proj.models.GameServiceGrpc;
import com.proj.models.GameState;
import com.proj.models.Player;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {

    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {

        Player client = Player.newBuilder().setName("client").setPosition(0).build();
        Player server = Player.newBuilder().setName("server").setPosition(0).build();

        return new DieStreamingRequest(client,server,responseObserver);
    }
}
