package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.SupportDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.SupportModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "SupportCtl", urlPatterns = "/ctl/SupportCtl")
public class SupportCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> issueTypeMap = new HashMap<>();
        issueTypeMap.put("Story", "Story");
        issueTypeMap.put("Task", "Task");
        issueTypeMap.put("Epic", "Epic");
        request.setAttribute("issueTypeMap", issueTypeMap);
        
        HashMap<String, String> ticketStatusMap = new HashMap<>();
        ticketStatusMap.put("OPEN", "OPEN");
        ticketStatusMap.put("IN PROGRESS", "IN PROGRESS");
        ticketStatusMap.put("CLOSED", "CLOSED");
        request.setAttribute("ticketStatusMap", ticketStatusMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("userName"))) {
            request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("userName"))) {
            request.setAttribute("userName", "Invalid User Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("issueType"))) {
            request.setAttribute("issueType", PropertyReader.getValue("error.require", "Issue Type"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("issueDescription"))) {
            request.setAttribute("issueDescription", PropertyReader.getValue("error.require", "Issue Description"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("ticketStatus"))) {
            request.setAttribute("ticketStatus", PropertyReader.getValue("error.require", "Ticket Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        SupportDTO dto = new SupportDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setUserName(DataUtility.getString(request.getParameter("userName")));
        dto.setIssueType(DataUtility.getString(request.getParameter("issueType")));
        dto.setIssueDescription(DataUtility.getString(request.getParameter("issueDescription")));
        dto.setTicketStatus(DataUtility.getString(request.getParameter("ticketStatus")));
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = DataUtility.getLong(req.getParameter("id"));
        SupportModelInt model = ModelFactory.getInstance().getSupportModel();
        if (id > 0) {
            try {
                SupportDTO dto = model.findByPK(id);
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
        SupportModelInt model = ModelFactory.getInstance().getSupportModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            SupportDTO bean = (SupportDTO) populateDTO(req);
            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Support Ticket Added Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("User Name Already Exist !!!", req);
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
            ServletUtility.redirect(ORSView.SUPPORT_CTL, req, resp);
            return;
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            SupportDTO dto = (SupportDTO) populateDTO(req);
            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Support Ticket Updated Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("User Name Already Exist !!!", req);
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
            ServletUtility.redirect(ORSView.SUPPORT_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.SUPPORT_VIEW;
    }
}