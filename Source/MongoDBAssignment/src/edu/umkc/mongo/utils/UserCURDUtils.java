/**
 * 
 */
package edu.umkc.mongo.utils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

import edu.umkc.mongo.beans.UserBO;
import edu.umkc.mongo.constants.MongoConstants;

/**
 * @author Muktevi
 *
 */
@Component
public class UserCURDUtils {
	private MongoClientURI mongoClientURI;
	private MongoClient client;
	private BasicDBObject queryObject;
	
	
	public static final Logger logger = Logger.getLogger(UserCURDUtils.class);
	
	public UserCURDUtils(){
		mongoClientURI = new MongoClientURI(MongoConstants.MONGO_URI);
		client = new MongoClient(mongoClientURI);
		queryObject = new BasicDBObject();
	}
	
	public String getUserDetails (String username){
		logger.info("Entered userDetails() method with username: "+ username);
		
		DBCollection users =  client.getDB(mongoClientURI.getDatabase()).getCollection("user");
		queryObject.put("username", username);
		DBCursor record = users.find(queryObject);
		return record.toArray().toString();
	}
	
	public String insertUserDetails (UserBO userBO){
		DBCollection users = client.getDB(mongoClientURI.getDatabase()).getCollection("user");
		queryObject.put("first_name",userBO.getFirstName());
		queryObject.put("last_name", userBO.getLastName());
		queryObject.put("username", userBO.getUsername());
		queryObject.put("password", userBO.getPassword());
		WriteResult record = users.insert(queryObject);
		
		return record.toString();
	}
	
	public String updateUserDetails (UserBO userBO){
		DBCollection users = client.getDB(mongoClientURI.getDatabase()).getCollection("user");
		queryObject.put("first_name",userBO.getFirstName());
		queryObject.put("last_name",userBO.getLastName());
		queryObject.put("username",userBO.getUsername());
		queryObject.put("password",userBO.getPassword());
		
		WriteResult result = users.update(new BasicDBObject().append("username", userBO.getUsername()),queryObject);
		return result.toString();
	}
	
	public String removeUser (String username){
		DBCollection users = client.getDB(mongoClientURI.getDatabase()).getCollection("user");
		queryObject.append("username", username);
		WriteResult result = users.remove(queryObject);
		return result.toString();
	}
}
