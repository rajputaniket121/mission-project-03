package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.ProfileDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ProfileModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "ProfileCtl", urlPatterns = "/ctl/ProfileCtl")
public class ProfileCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> profileStatusMap = new HashMap<>();
        profileStatusMap.put("ACTIVE", "ACTIVE");
        profileStatusMap.put("DEACTIVE", "DEACTIVE");
        request.setAttribute("profileStatusMap", profileStatusMap);
        
        HashMap<String, String> genderMap = new HashMap<>();
        genderMap.put("MALE", "MALE");
        genderMap.put("FEMALE", "FEMALE");
        request.setAttribute("genderMap", genderMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("fullName"))) {
            request.setAttribute("fullName", PropertyReader.getValue("error.require", "FullName"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("fullName"))) {
            request.setAttribute("fullName", "Invalid Profile Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("profileStatus"))) {
            request.setAttribute("profileStatus", PropertyReader.getValue("error.require", "ProfileStatus"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setFullName(DataUtility.getString(request.getParameter("fullName")));
        dto.setDob(DataUtility.getDate(request.getParameter("dob")));
        dto.setGender(DataUtility.getString(request.getParameter("gender")));
        dto.setProfileStatus(DataUtility.getString(request.getParameter("profileStatus")));
        populateBean(dto, request);
        return dto;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = DataUtility.getLong(req.getParameter("id"));
        ProfileModelInt model = ModelFactory.getInstance().getProfileModel();
        if (id > 0) {
            try {
                ProfileDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, req);
            }catch (DatabaseException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDBDown(e, req, resp,getView());
                return;
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
        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String op = DataUtility.getString(req.getParameter("operation"));
        ProfileModelInt model = ModelFactory.getInstance().getProfileModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            ProfileDTO bean = (ProfileDTO) populateDTO(req);
            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Profile Added Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Profile Already Exist !!!", req);
            } catch (ApplicationException e) {
                e.printStackTrace();
                if(e.getClass().toString().equals(e.toString())) {
                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
                }else {
                	ServletUtility.handleException(e, req, resp);
                }
                return;
            }
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PROFILE_CTL, req, resp);
            return;
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
        	ProfileDTO dto = (ProfileDTO) populateDTO(req);
            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Profile Updated Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Profile Already Exist !!!", req);
            } catch (ApplicationException e) {
                e.printStackTrace();
                if(e.getClass().toString().equals(e.toString())) {
                	 ServletUtility.handleExceptionDBDown(e, req, resp,getView());
                }else {
                	ServletUtility.handleException(e, req, resp);
                }
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PROFILE_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.PROFILE_VIEW;
    }
}