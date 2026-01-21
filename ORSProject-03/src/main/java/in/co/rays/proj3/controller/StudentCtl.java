package in.co.rays.proj3.controller;

import in.co.rays.proj3.dto.*;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.StudentModelInt;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet(name = "StudentCtl",urlPatterns = {"/ctl/StudentCtl"})
public class StudentCtl extends  BaseCtl{

    @Override
    protected void preload(HttpServletRequest request) {
        CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
        try {
            List<CollegeDTO> collegeList =  collegeModel.list();
            request.setAttribute("collegeList", collegeList);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if(OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
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
        if(DataValidator.isNull(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.require","Email Id"));
            pass = false;
        } else if(!DataValidator.isEmail(request.getParameter("email"))) {
            request.setAttribute("email",PropertyReader.getValue("error.email","Email"));
            pass = false;
        }
        if(DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.require","Date Of Birth"));
            pass = false;
        } else if(!DataValidator.isDate(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
            pass = false;
        }
        if(DataValidator.isNull(request.getParameter("gender"))){
            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender "));
            pass = false;
        }

        if(DataValidator.isNull(request.getParameter("collegeId"))){
            request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Id "));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No "));
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
        StudentDTO dto = new StudentDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
        dto.setEmail(DataUtility.getString(request.getParameter("email")));
        dto.setDob(DataUtility.getDate(request.getParameter("dob")));
        dto.setGender(DataUtility.getString(request.getParameter("gender")));
        dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
        dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	Long id = DataUtility.getLong(req.getParameter("id"));
    	StudentModelInt model = ModelFactory.getInstance().getStudentModel();
    	if(id>0) {
    		try {
    			StudentDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, req);
			} catch (ApplicationException e) {
                e.printStackTrace();
                if(e.getClass().toString().equals(e.toString())) {
                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
                }else {
                	ServletUtility.handleException(e, req, resp);
                }
                return;
            }
    	}
        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String op = DataUtility.getString(req.getParameter("operation"));
        StudentModelInt model = ModelFactory.getInstance().getStudentModel();
        if(UserCtl.OP_SAVE.equalsIgnoreCase(op)) {
            StudentDTO dto = (StudentDTO) populateDTO(req);
            try {
                model.add(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Student Added SuccessFully !!!", req);
            }catch(DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Email Already Exist !!!", req);
            }catch (ApplicationException e) {
                e.printStackTrace();
                if(e.getClass().toString().equals(e.toString())) {
                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
                }else {
                	ServletUtility.handleException(e, req, resp);
                }
                return;
            }
        }else if(OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.STUDENT_CTL, req, resp);
            return;
        }else if(OP_UPDATE.equalsIgnoreCase(op)) {
        	StudentDTO dto = (StudentDTO) populateDTO(req);
	       	try {
	               model.update(dto);
	               ServletUtility.setDto(dto, req);
	               ServletUtility.setSuccessMessage("Student Updated SuccessFully !!!", req);
	           }catch(DuplicateRecordException dre) {
	               ServletUtility.setDto(dto, req);
	               ServletUtility.setErrorMessage("Email Already Exist !!!", req);
	           }catch (ApplicationException e) {
	                e.printStackTrace();
	                if(e.getClass().toString().equals(e.toString())) {
	                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
	                }else {
	                	ServletUtility.handleException(e, req, resp);
	                }
	                return;
	            }
	       }
	       else if(OP_CANCEL.equalsIgnoreCase(op)) {
	       	 ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, req, resp);
	       	 return;
	       }
	       ServletUtility.forward(getView(), req, resp);
    }


    @Override
    protected String getView() {
        return ORSView.STUDENT_VIEW;
    }
}