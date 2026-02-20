package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.FeedbackDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.FeedbackModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.DataValidator;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "FeedbackCtl", urlPatterns = "/ctl/FeedbackCtl")
public class FeedbackCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> ratingMap = new HashMap<>();
        ratingMap.put("1 Stars", "1 Stars");
        ratingMap.put("2 Stars", "2 Stars");
        ratingMap.put("3 Stars", "3 Stars");
        ratingMap.put("4 Stars", "4 Stars");
        ratingMap.put("5 Stars", "5 Stars");
        request.setAttribute("ratingMap", ratingMap);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("feedbackCode"))) {
            request.setAttribute("feedbackCode", PropertyReader.getValue("error.require", "Feedback Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("customerName"))) {
            request.setAttribute("customerName", PropertyReader.getValue("error.require", "Customer Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("customerName"))) {
            request.setAttribute("customerName", "Invalid Customer Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("rating"))) {
            request.setAttribute("rating", PropertyReader.getValue("error.require", "Rating"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("comments"))) {
            request.setAttribute("comments", PropertyReader.getValue("error.require", "Comments"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("feedbackDate"))) {
            request.setAttribute("feedbackDate", PropertyReader.getValue("error.require", "Feedback Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("feedbackDate"))) {
            request.setAttribute("feedbackDate", PropertyReader.getValue("error.date", "Feedback Date"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setFeedbackCode(DataUtility.getString(request.getParameter("feedbackCode")));
        dto.setCustomerName(DataUtility.getString(request.getParameter("customerName")));
        dto.setRating(DataUtility.getString(request.getParameter("rating")));
        dto.setComments(DataUtility.getString(request.getParameter("comments")));
        dto.setFeedbackDate(DataUtility.getDate(request.getParameter("feedbackDate")));
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = DataUtility.getLong(req.getParameter("id"));
        FeedbackModelInt model = ModelFactory.getInstance().getFeedbackModel();
        if (id > 0) {
            try {
                FeedbackDTO dto = model.findByPK(id);
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
        FeedbackModelInt model = ModelFactory.getInstance().getFeedbackModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            FeedbackDTO bean = (FeedbackDTO) populateDTO(req);
            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Feedback Added Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Feedback Code Already Exist !!!", req);
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
            ServletUtility.redirect(ORSView.FEEDBACK_CTL, req, resp);
            return;
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            FeedbackDTO dto = (FeedbackDTO) populateDTO(req);
            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Feedback Updated Successfully !!!", req);
            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Feedback Code Already Exist !!!", req);
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
            ServletUtility.redirect(ORSView.FEEDBACK_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.FEEDBACK_VIEW;
    }
}