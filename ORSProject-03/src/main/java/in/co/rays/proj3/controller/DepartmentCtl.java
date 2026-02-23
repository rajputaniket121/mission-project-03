package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.DepartmentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.DepartmentModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "DepartmentCtl", urlPatterns = "/ctl/DepartmentCtl")
public class DepartmentCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> locationMap = new HashMap<>();
        locationMap.put("Indore", "Indore");
        locationMap.put("Bhopal", "Bhopal");
        locationMap.put("Mumbai", "Mumbai");
        request.setAttribute("locationMap", locationMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("departmentCode"))) {
            request.setAttribute("departmentCode", PropertyReader.getValue("error.require", "Department Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("departmentName"))) {
            request.setAttribute("departmentName", PropertyReader.getValue("error.require", "Department Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("departmentName"))) {
            request.setAttribute("departmentName", "Invalid Department Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("departmentHead"))) {
            request.setAttribute("departmentHead", PropertyReader.getValue("error.require", "Department Head"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("departmentHead"))) {
            request.setAttribute("departmentHead", "Invalid Department Head Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("location"))) {
            request.setAttribute("location", PropertyReader.getValue("error.require", "Location"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setDepartmentCode(DataUtility.getString(request.getParameter("departmentCode")));
        dto.setDepartmentName(DataUtility.getString(request.getParameter("departmentName")));
        dto.setDepartmentHead(DataUtility.getString(request.getParameter("departmentHead")));
        dto.setLocation(DataUtility.getString(request.getParameter("location")));
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = DataUtility.getLong(req.getParameter("id"));
        DepartmentModelInt model = ModelFactory.getInstance().getDepartmentModel();
        if (id > 0) {
            try {
                DepartmentDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, req);
            } catch (DatabaseException e) {
                e.printStackTrace();
                ServletUtility.handleExceptionDBDown(e, req, resp, getView());
                return;
            } catch (ApplicationException e) {
                e.printStackTrace();
                if (e.getClass().toString().equals(e.toString())) {
                    ServletUtility.handleExceptionDBDown(e, req, resp, getView());
                } else {
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
        DepartmentModelInt model = ModelFactory.getInstance().getDepartmentModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            DepartmentDTO bean = (DepartmentDTO) populateDTO(req);
            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Department Added Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Department Code Already Exist !!!", req);
            } catch (ApplicationException e) {
                e.printStackTrace();
                if (e.getClass().toString().equals(e.toString())) {
                    ServletUtility.handleExceptionDBDown(e, req, resp, getView());
                } else {
                    ServletUtility.handleException(e, req, resp);
                }
                return;
            }
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.DEPARTMENT_CTL, req, resp);
            return;
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            DepartmentDTO dto = (DepartmentDTO) populateDTO(req);
            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Department Updated Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Department Code Already Exist !!!", req);
            } catch (ApplicationException e) {
                e.printStackTrace();
                if (e.getClass().toString().equals(e.toString())) {
                    ServletUtility.handleExceptionDBDown(e, req, resp, getView());
                } else {
                    ServletUtility.handleException(e, req, resp);
                }
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.DEPARTMENT_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.DEPARTMENT_VIEW;
    }
}