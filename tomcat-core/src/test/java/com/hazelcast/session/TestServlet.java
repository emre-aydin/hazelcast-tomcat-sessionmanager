/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 */

package com.hazelcast.session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getRequestURI().endsWith("write")) {
            session.setAttribute("key", "value");
            resp.getWriter().write("true");

        } else if (req.getRequestURI().endsWith("writeAnother")) {
            session.setAttribute("key2", "value2");
            resp.getWriter().write("true");

        } else if (req.getRequestURI().endsWith("read")) {
            Object value = session.getAttribute("key");
            resp.getWriter().write(value == null ? "null" : value.toString());

        } else if (req.getRequestURI().endsWith("readAnother")) {
            Object value = session.getAttribute("key2");
            resp.getWriter().write(value == null ? "null" : value.toString());

        } else if (req.getRequestURI().endsWith("remove")) {
            session.removeAttribute("key");
            resp.getWriter().write("true");

        } else if (req.getRequestURI().endsWith("invalidate")) {
            session.invalidate();
            resp.getWriter().write("true");

        } else if (req.getRequestURI().endsWith("update")) {
            session.setAttribute("key", "value-updated");
            resp.getWriter().write("true");

        } else if (req.getRequestURI().endsWith("names")) {
            List names = Collections.list(session.getAttributeNames());
            String nameList = names.toString();
            //return comma separated list of attribute names
            resp.getWriter().write(nameList.substring(1, nameList.length() - 1).replace(", ", ","));

        } else if (req.getRequestURI().endsWith("reload")) {
            session.invalidate();
            session = req.getSession();
            session.setAttribute("first-key", "first-value");
            session.setAttribute("second-key", "second-value");
            resp.getWriter().write("true");
        } else if (req.getRequestURI().endsWith("isNew")) {
            resp.getWriter().write(session.isNew() ? "true" : "false");
        } else if (req.getRequestURI().endsWith("lastAccessTime")) {
            resp.getWriter().write(String.valueOf(session.getLastAccessedTime()));
        } else if (req.getRequestURI().endsWith("nonserializable")) {
            session.setAttribute("key", new Object());

            resp.getWriter().write("true");
        }
    }
}
