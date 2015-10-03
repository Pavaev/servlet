package ru.kpfu.itis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Scanner;

/**
 * @author Ramil Safin
 */

public class ServletForm extends HttpServlet {

    //database file, where storing email, password, checkbox status
    public static final File DATA = new File("/Users/Ramil/Desktop/data.txt");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().println(getHTMLCode());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("\\form");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        doGet(req,resp);
        writeToFile(req.getParameter("email"), req.getParameter("password"), req.getParameter("checkbox"));
    }

    /**
     * @return true, if email doesn't exist and writing successful
     * else false
     */
    protected void writeToFile(String email, String password, String checkbox_st){
        StringBuilder sb = new StringBuilder();

        try(Scanner sc = new Scanner(DATA)) {
            while (sc.hasNext()){
                sb.append(sc.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try(PrintWriter pw = new PrintWriter(new FileOutputStream(DATA))) {
            pw.print(sb.toString());
            if (checkbox_st == null){
                checkbox_st = "off";
            }
            pw.printf("%30s\t%20s\t%3s",email,password,checkbox_st);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected String getHTMLCode(){
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html> <head><title>My Task</title></head>");
        sb.append("<body>");
        sb.append("<form action=\"\\form\" method=\"POST\">");
        sb.append("<p>Email:<br><input type=\"email\" name=\"email\" required placeholder=\"example@mailbox.ru\"></p>");
        sb.append("<p>Password:<br><input id=\"pass\" type=\"password\" name=\"password\" value=\"\" required placeholder=\"password\"></p>");
        sb.append("<p><br><br><select id=\"selecter\" size=\"1\" name=\"gender\">");
        sb.append("<option selected disabled>выберите пол</option>");
        sb.append("<option>мужской</option>");
        sb.append("<option>женский</option>");
        sb.append("</select></p><p><br>");
        sb.append("<input type=\"checkbox\" name=\"checkbox\" style=\"height:15px; width:20px;\">Подписаться на новости</input></p>");
        sb.append("<p><input id=\"submit\" type=\"submit\" value=\"Отправить\"></form></p><p></p></form>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }


}
