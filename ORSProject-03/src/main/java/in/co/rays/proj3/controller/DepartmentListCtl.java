package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.DepartmentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.DepartmentModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.utill.DataUtility;
import in.co.rays.proj3.utill.PropertyReader;
import in.co.rays.proj3.utill.ServletUtility;

@WebServlet(name = "DepartmentListCtl", urlPatterns = { "/ctl/DepartmentListCtl" })
public class DepartmentListCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        HashMap<String, String> locationMap = new HashMap<>();
        locationMap.put("Indore", "Indore");
        locationMap.put("Bhopal", "Bhopal");
        locationMap.put("Mumbai", "Mumbai");
        request.setAttribute("locationMap", locationMap);
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentCode(DataUtility.getString(request.getParameter("departmentCode")));
        dto.setDepartmentName(DataUtility.getString(request.getParameter("departmentName")));
        dto.setLocation(DataUtility.getString(request.getParameter("location")));
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        DepartmentDTO dto = (DepartmentDTO) populateDTO(req);
        DepartmentModelInt model = ModelFactory.getInstance().getDepartmentModel();

        try {
            List<DepartmentDTO> list = model.search(dto, pageNo, pageSize);
            List<DepartmentDTO> next = model.search(dto, pageNo + 1, pageSize);

            if (list.isEmpty() || list == null) {
                ServletUtility.setErrorMessage("No Records found", req);
            }
            ServletUtility.setList(list, req);
            ServletUtility.setDto(dto, req);
            ServletUtility.setPageNo(pageNo, req);
            ServletUtility.setPageSize(pageSize, req);
            req.setAttribute("nextListSize", next.size());
        } catch (ApplicationException e) {
            ServletUtility.handleExceptionDBDown(e, req, resp, getView());
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<DepartmentDTO> list = null;
        List<DepartmentDTO> next = null;

        int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(req.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        DepartmentDTO dto = (DepartmentDTO) populateDTO(req);
        DepartmentModelInt model = ModelFactory.getInstance().getDepartmentModel();

        String op = DataUtility.getString(req.getParameter("operation"));

        try {
            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }
            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.DEPARTMENT_LIST_CTL, req, resp);
                return;
            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.DEPARTMENT_CTL, req, resp);
                return;
            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                String[] ids = req.getParameterValues("ids");
                if (ids != null && ids.length > 0) {
                    DepartmentDTO deleteDTO = new DepartmentDTO();
                    for (String id : ids) {
                        deleteDTO.setId(DataUtility.getLong(id));
                        model.delete(deleteDTO);
                        ServletUtility.setSuccessMessage("Deleted Successfully !!!", req);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select Atleast one Checkbox", req);
                }
            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.DEPARTMENT_LIST_CTL, req, resp);
                return;
            }

            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);

            if (list.isEmpty() || list == null) {
                ServletUtility.setErrorMessage("No Records found", req);
            }
            ServletUtility.setList(list, req);
            ServletUtility.setDto(dto, req);
            ServletUtility.setPageNo(pageNo, req);
            ServletUtility.setPageSize(pageSize, req);
            req.setAttribute("nextListSize", next.size());
        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleExceptionDBDown(e, req, resp, getView());
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.DEPARTMENT_LIST_VIEW;
    }
}