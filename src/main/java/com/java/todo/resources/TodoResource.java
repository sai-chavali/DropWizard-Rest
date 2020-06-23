package com.java.todo.resources;

import com.google.gson.Gson;
import com.java.todo.db.MongoService;
import com.java.todo.domain.Task;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private MongoCollection<Document> collection;
    private final MongoService mongoService;

    public TodoResource(MongoCollection<Document> collection, MongoService mongoService) {
        this.collection = collection;
        this.mongoService = mongoService;
    }

    @GET
    public Response getTasks() {
        List<Document> documents = mongoService.getTasks(collection);
        return Response.ok(documents).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTask(@NotNull @Valid final Task task) {
        Gson gson = new Gson();
        String json = gson.toJson(task);
        ObjectId id = mongoService.addTask(collection,new Document(BasicDBObject.parse(json)));
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task created successfully");
        response.put("id",id.toString());
        return Response.ok(response).build();
    }

    @Path("/{id}")
    @GET
    public Response getTask(@PathParam(value = "id") String id) {
        List<Document> documents = mongoService.getTaskByKey(collection,"_id", id);
        if(documents.isEmpty()){
            Map<String, String> response = new HashMap<>();
            response.put("message", "Don't try Hacking!!We are aware. Please provide correct inputs");
            return Response.ok(response).build();
        }
        return Response.ok(documents).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam(value = "id") String id) {
        long isDeleted = mongoService.deleteTask(collection, "_id",id);
        Map<String, String> response = new HashMap<>();
        if(isDeleted == 1)
            response.put("message", "Task with id: " + id + " deleted successfully");
        else
            response.put("message", "Don't try Hacking!!We are aware. Please provide correct inputs");
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam(value = "id") String id, @NotNull @Valid final Task task) {
        Gson gson = new Gson();
        String json = gson.toJson(task);
        long updatedCount = mongoService.updateTask(collection, new Document(BasicDBObject.parse(json)),"_id", id);
        Map<String, String> response = new HashMap<>();
        if(updatedCount==0)
            response.put("message", "Don't try Hacking!!We are aware. Please provide correct inputs");
        else
            response.put("message", "Task with id: " + id + " updated successfully");
        return Response.ok(response).build();
    }

}
