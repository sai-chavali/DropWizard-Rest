package com.java.todo.db;


import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;

public class DatabaseSet implements Managed {
    private MongoClient mongoClient;

    public DatabaseSet(MongoClient mongoClient){
        this.mongoClient = mongoClient;
    }

    @Override
    public void start(){
    }

    @Override
    public void stop() {
        mongoClient.close();
    }

}
