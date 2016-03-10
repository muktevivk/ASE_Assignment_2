package edu.umkc.mongo.service.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.umkc.mongo.beans.UserBO;
import edu.umkc.mongo.utils.UserCURDUtils;

/**
 * @author Muktevi
 *
 */

@Component
@Path("/user")
public class UserCURDServiceImpl {
	
	@Autowired private UserCURDUtils userCURDUtils;
	private Gson gson;
	
	public static final Logger logger = Logger.getLogger(UserCURDServiceImpl.class);

	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser (@PathParam ("username") String username){
		logger.info("Entered getUser() method with username: "+ username);
		String result = userCURDUtils.getUserDetails(username);
		return Response.ok(result).build();
		
	}
	
	@POST
	@Path("/addUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addUser(UserBO userBO){
		if (userBO == null){
			logger.error("userBO is null");
			return Response.status(Status.BAD_REQUEST).build();
		}
		logger.info("Entered addUser() method with username: "+ userBO.getUsername());
		String result = userCURDUtils.insertUserDetails(userBO);
		gson = new Gson();
		String jsonResult = gson.toJson(userBO);
		String message = "User is successfully added!! \n"+jsonResult;
		return Response.ok(message).build();
	}

	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response update(UserBO userBO){
		if (userBO == null){
			logger.error("userBO is null");
			return Response.status(Status.BAD_REQUEST).build();
		}
		logger.info("Entered update() method with username: "+ userBO.getUsername());
		String result = userCURDUtils.updateUserDetails(userBO);
		gson = new Gson();
		String jsonResult = gson.toJson(userBO);
		String message = "User details successfully updated !!! \n"+jsonResult;
		return Response.ok(message).build();
	}
	
	@DELETE
	@Path("/remove/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response remove (@PathParam("username") String username){
		logger.info("Entered remove() method with username: "+ username);
		String result = userCURDUtils.removeUser(username);
		String message = "User is successfully removed!! \n"+result;
		return Response.ok(message).build();
	}
}
