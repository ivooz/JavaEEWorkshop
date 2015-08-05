/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webfundamentals;

import com.mycompany.dataaccess.Dao;
import com.mycompany.model.Account;
import com.mycompany.model.AccountData;
import com.mycompany.model.Transaction;
import com.mycompany.model.UserData;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author izielinski
 */
@WebServlet(name = "Home", urlPatterns = {"/home"}, initParams = {
    @WebInitParam(name = "ble", value = "zło")})
@Named
public class Home extends HttpServlet {

    private final Dao dao;
    private StringBuilder sb = new StringBuilder();

    @Inject
    public Home(Dao dao) {
        System.out.println("INJECTING" + dao);
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new IOException("CZEGO TU SZUKASZ???");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        Account account = dao.getAccountByLogin(login);
        if (account != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/account.jsp");
            req.setAttribute("account", account);
            AccountData accountData = account.getAccountData();
            req.setAttribute("accountData", accountData);
            List<Transaction> transactions = account.getTransactions();
            req.setAttribute("transactions", transactions);
            UserData userData = account.getUserData();
            req.setAttribute("userData", userData);
            dispatcher.forward(req, resp);
        } else {
            throw new ServletException("No Such username!");
        }
    }
}
