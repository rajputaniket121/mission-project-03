package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.AlertDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.AlertModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "AlertCtl", urlPatterns = "/ctl/AlertCtl")
public class AlertCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> alertStatusMap = new HashMap<>();
        alertStatusMap.put("ACTIVE", "ACTIVE");
        alertStatusMap.put("INACTIVE", "INACTIVE");
        alertStatusMap.put("TRIGGERED", "TRIGGERED");
        alertStatusMap.put("RESOLVED", "RESOLVED");
        request.setAttribute("alertStatusMap", alertStatusMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("alertCode"))) {
            request.setAttribute("alertCode", PropertyReader.getValue("error.require", "Alert Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("alertType"))) {
            request.setAttribute("alertType", PropertyReader.getValue("error.require", "Alert Type"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("alertTime"))) {
            request.setAttribute("alertTime", PropertyReader.getValue("error.require", "Alert Time"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("alertTime"))) {
            request.setAttribute("alertTime", PropertyReader.getValue("error.date", "Alert Time"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("alertStatus"))) {
            request.setAttribute("alertStatus", PropertyReader.getValue("error.require", "Alert Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        AlertDTO dto = new AlertDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setAlertCode(DataUtility.getString(request.getParameter("alertCode")));
        dto.setAlertType(DataUtility.getString(request.getParameter("alertType")));
        dto.setAlertTime(DataUtility.getDate(request.getParameter("alertTime")));
        dto.setAlertStatus(DataUtility.getString(request.getParameter("alertStatus")));
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = DataUtility.getLong(req.getParameter("id"));
        AlertModelInt model = ModelFactory.getInstance().getAlertModel();
        if (id > 0) {
            try {
                AlertDTO dto = model.findByPK(id);
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
        AlertModelInt model = ModelFactory.getInstance().getAlertModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            AlertDTO bean = (AlertDTO) populateDTO(req);
            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Alert Added Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Alert Code Already Exist !!!", req);
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
            ServletUtility.redirect(ORSView.ALERT_CTL, req, resp);
            return;
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            AlertDTO dto = (AlertDTO) populateDTO(req);
            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Alert Updated Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Alert Code Already Exist !!!", req);
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
            ServletUtility.redirect(ORSView.ALERT_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.ALERT_VIEW;
    }
}