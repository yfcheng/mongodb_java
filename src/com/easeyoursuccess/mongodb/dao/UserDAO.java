package com.easeyoursuccess.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.easeyoursuccess.mongodb.converter.UserConverter;
import com.easeyoursuccess.mongodb.model.User;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class UserDAO {
	
	private DBCollection coll;
	
	public UserDAO(MongoClient mongo){
		this.coll= mongo.getDB("usersDB").getCollection("users");
		
	}
	public User createUser(User u){
		DBObject doc = UserConverter.toDBObject(u);
		this.coll.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		u.setId(id.toString());
		return u;
		
	}
	public List<User> readAllUsers() {
        List<User> data = new ArrayList<User>();
        DBCursor cursor = coll.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            User u = UserConverter.toUser(doc);
            System.out.println("Password is ::"+u.getPassword());
            data.add(u);
        }
        return data;
    }
	 public User readUser(User u) {
	        DBObject query = BasicDBObjectBuilder.start()
	                .append("_id", new ObjectId(u.getId())).get();
	        DBObject data = this.coll.findOne(query);
	        return UserConverter.toUser(data);
	    }
	   public void updateUser(User u) {
	        DBObject query = BasicDBObjectBuilder.start()
	                .append("_id", new ObjectId(u.getId())).get();
	        this.coll.update(query, UserConverter.toDBObject(u));
	    }
	   public void deleteUser(User u) {
	        DBObject query = BasicDBObjectBuilder.start()
	                .append("_id", new ObjectId(u.getId())).get();
	        this.coll.remove(query);
	    }

}
