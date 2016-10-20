package com.project.invitar.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.invitar.model.Registration;
import com.project.invitar.service.RegistrationService;

@Component
@Path("registrationResourceInterface")
@XmlRootElement
public class RegistrationResource implements RegistrationResourceInterface {

	@Autowired
	private RegistrationService registrationService;

	@GET
	@Path("signup")
	@Produces(MediaType.TEXT_HTML)
	public Response signup() {
		return Response.ok(new Viewable("/signup")).build();
	}

	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response signup(@FormParam("userName") String userName,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("dateOfBirth") String dateOfBirth,
			@FormParam("emailAddress") String emailAddress)
			throws ParseException {

		if (userName == null || password == null || firstName == null
				|| lastName == null || dateOfBirth == null
				|| emailAddress == null) {
			return Response.status(Status.PRECONDITION_FAILED).build();
		}

		Registration reg = new Registration();
		reg.setUserName(userName);
		reg.setPassword(password);
		reg.setFirstName(firstName);
		reg.setLastName(lastName);

		reg.setDateOfBirth(new java.sql.Date(new SimpleDateFormat(
				"MM/dd/yyyy").parse(dateOfBirth.substring(0, 10)).getTime()));

		reg.setEmailAddress(emailAddress);

		if (registrationService.findByUserName(userName)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "User Name exists. Try another user name");
			map.put("registration", reg);
			return Response.status(Status.BAD_REQUEST)
					.entity(new Viewable("/signup", map)).build();
		} else {
			registrationService.save(reg);
			return Response.ok().entity(new Viewable("/login")).build();
		}
	}

	@GET
	@Path("login")
	@Produces(MediaType.TEXT_HTML)
	public Response login() {
		return Response.ok(new Viewable("/login")).build();
	}

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response login(@FormParam("userName") String userName,
			@FormParam("password") String password) {

		if (userName == null || password == null) {
			return Response.status(Status.PRECONDITION_FAILED).build();
		}

		boolean found = registrationService.findByLogin(userName, password);
		if (found) {
			return Response.ok().entity(new Viewable("/success")).build();
		} else {
			return Response.status(Status.BAD_REQUEST)
					.entity(new Viewable("/failure")).build();
		}
	}
}
