package com.java.todo;

import com.java.todo.db.DatabaseSet;
import com.java.todo.db.MongoService;
import com.java.todo.resources.TodoResource;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.bson.Document;

public class ToDoApplication extends Application<ToDoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ToDoApplication().run(args);
    }

    @Override
    public String getName() {
        return "ToDo";
    }

    @Override
    public void initialize(final Bootstrap<ToDoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ToDoConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        MongoClient mongoClient = new MongoClient(configuration.getMongoHost(), configuration.getMongoPort());
        DatabaseSet databaseSet = new DatabaseSet(mongoClient);
        environment.lifecycle().manage(databaseSet);
        MongoDatabase db = mongoClient.getDatabase(configuration.getMongoDB());
        MongoCollection<Document> collection = db.getCollection(configuration.getCollectionName());
        environment.jersey().register(new TodoResource(collection,new MongoService()));
    }

}
