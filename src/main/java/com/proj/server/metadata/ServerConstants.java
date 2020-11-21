package com.proj.server.metadata;

import io.grpc.Context;
import io.grpc.Metadata;

public class ServerConstants {

    public static final Metadata.Key<String> TOKEN = Metadata.Key.of("client-token"
            ,Metadata.ASCII_STRING_MARSHALLER);


    public static final Metadata.Key<String> USER_TOKEN = Metadata.Key.of("user-token"
            ,Metadata.ASCII_STRING_MARSHALLER);

    //The Key which is used to store the value, the same can be used to extract it.
    public static final Context.Key<UserRole> CTX_USER_ROLE= Context.key("user-role");
    public static final Context.Key<UserRole> CTX_USER_ROLE1= Context.key("user-role");

}

