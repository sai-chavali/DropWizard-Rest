package com.java.todo.db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoService {
    public ObjectId addTask(MongoCollection<Document> collection, Document doc) {
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public List<Document> getTaskByKey(MongoCollection<Document> collection, String key, String value){
        return convertDBObject(collection.find(new Document(key, new ObjectId(value))).into(new ArrayList<>()));
    }

    public List<Document> getTasks(MongoCollection<Document> collection) {
        return convertDBObject(collection.find().into(new ArrayList<>()));
    }

    public long updateTask(MongoCollection<Document> collection, Document doc, String key, String value){
        UpdateResult res = collection.updateOne(new Document(key, new ObjectId(value)),
                new Document("$set", doc));
        return res.getModifiedCount();

    }

    public long deleteTask(MongoCollection<Document> collection, String key, String value){
        DeleteResult res = collection.deleteOne(new Document(key, new ObjectId(value)));
        return res.getDeletedCount();
    }

    public List<Document> convertDBObject(List<Document> documents){
        documents.parallelStream().forEach(document -> {
            document.put("_id", document.get("_id").toString());
        });
        return documents;
    }
}
