package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "MyProfileCtl", urlPatterns = {"/ctl/MyProfileCtl"})
public class MyProfileCtl extends BaseCtl {
	public static final String OP_CHANGE_PASSWORD = "Change Password";
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		String op = request.getParameter("operation");
		
		if(OP_CHANGE_PASSWORD.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}
		
		if(DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require","Login Id"));
			pass = false;
		} else if(!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login",PropertyReader.getValue("error.email","Login"));
			pass = false;
		} 
		if(DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require","First Name"));
			pass = false;
		} else if(!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "Invalid First Name");
			pass = false;
		} 
		if(DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require","Last Name"));
			pass = false;
		} 
		else if(!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
		} 
		if(DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require","Date Of Birth"));
			pass = false;
		} else if(!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDTO dto = (UserDTO) session.getAttribute("user");
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserDTO dto = (UserDTO) session.getAttribute("user");
		ServletUtility.setDto(dto, req);
		ServletUtility.forward(getView(), req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String op = DataUtility.getString(req.getParameter("operation"));
		UserDTO dto = (UserDTO) populateDTO(req);
		if(BaseCtl.OP_SAVE.equalsIgnoreCase(op)) {
			UserModelInt model = ModelFactory.getInstance().getUserModel();
			try {
				model.update(dto);
			 dto = model.findByPK(dto.getId());
			 ServletUtility.setSuccessMessage("Information Updated Successfully", req);
			 ServletUtility.setDto(dto, req);
			
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Duplicate Login Id ", req);
			}catch(ApplicationException ae) {
				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
			}
		} else if (MyProfileCtl.OP_CHANGE_PASSWORD.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, req, resp);
			return;
		}
		 ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.MY_PROFILE_VIEW;
	}

}