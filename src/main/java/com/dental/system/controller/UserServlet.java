package com.dental.system.controller;

import com.dental.system.dao.UserDAO;
import com.dental.system.model.User;
import com.dental.system.service.InUserService;
import com.dental.system.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final InUserService userService;

    public UserServlet() {
        this.userService = new UserService(new UserDAO());
    }


    // Load users
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {

            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("loggedUser");



        if (!"Administrator".equalsIgnoreCase(loggedUser.getRole())) {

            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        String action = request.getParameter("action");

        String idValue = request.getParameter("id");




        if ("delete".equalsIgnoreCase(action) && idValue != null) {

            try {

                int userId = Integer.parseInt(idValue);

                User targetUser = userService.getUserById(userId);

                if (targetUser == null) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/users?deleteError=true"
                    );
                    return;
                }

                if (userId == loggedUser.getUserId()) {

                    response.sendRedirect(request.getContextPath() + "/users?selfDeleteError=true");
                    return;
                }

                boolean deleted = userService.deleteUser(userId);

                if (deleted) {

                    response.sendRedirect(request.getContextPath() + "/users?deleteSuccess=true");

                } else {

                    response.sendRedirect(request.getContextPath() + "/users?deleteError=true");
                }

                return;

            } catch (NumberFormatException e) {

                response.sendRedirect(request.getContextPath() + "/users?deleteError=true");
                return;
            }
        }



        List<User> users = userService.getAllUsers();

        request.setAttribute("users", users);

        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }


    // Handle user submit
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {

            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (!"Administrator".equalsIgnoreCase(loggedUser.getRole())) {

            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        String formAction = request.getParameter("formAction");

        if ("add".equalsIgnoreCase(formAction)) {

            addUser(request, response);
            return;
        }

        if ("update".equalsIgnoreCase(formAction)) {

            updateUser(request, response, loggedUser, session);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/users");
    }


    //Add user
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");

        if (username != null) {
            username = username.trim();
        }

        String password = request.getParameter("password");

        if (password != null) {
            password = password.trim();
        }

        String role = request.getParameter("role");

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        boolean added = userService.addUser(user);

        if (added) {

            response.sendRedirect(request.getContextPath() + "/users?addSuccess=true");

        } else {

            response.sendRedirect(request.getContextPath() + "/users?addError=true");
        }
    }


    //Update user
    private void updateUser(HttpServletRequest request, HttpServletResponse response, User loggedUser, HttpSession session) throws IOException {

        try {

            int userId = Integer.parseInt(request.getParameter("userId"));

            User existingUser = userService.getUserById(userId);

            if (existingUser == null) {

                response.sendRedirect(request.getContextPath() + "/users?updateError=true");
                return;
            }

            String username = request.getParameter("username");

            if (username != null) {
                username = username.trim();
            }

            String password = request.getParameter("password");

            if (password == null || password.trim().isEmpty()) {

                password = existingUser.getPassword();
            }

            String role = request.getParameter("role");

            existingUser.setUsername(username);
            existingUser.setPassword(password);

            if (userId == loggedUser.getUserId()) {

                existingUser.setRole(loggedUser.getRole());

            } else {

                existingUser.setRole(role);
            }

            boolean updated = userService.updateUser(existingUser);

            if (updated) {

                if (userId == loggedUser.getUserId()) {

                    session.setAttribute("loggedUser", existingUser);
                }

                response.sendRedirect(request.getContextPath() + "/users?updateSuccess=true");

            } else {

                response.sendRedirect(request.getContextPath() + "/users?updateError=true");
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(request.getContextPath() + "/users?updateError=true");
        }
    }

}
