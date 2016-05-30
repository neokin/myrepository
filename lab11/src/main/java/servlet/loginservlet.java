package servlet;


import entity.User;

import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



@WebServlet(name = "loginservlet", urlPatterns = {"/loginservlet"})
public class loginservlet extends HttpServlet {

private Map<String,String> usersList;


    private static String getHashPassword(String username, String password){
        try{
            SecretKeySpec key = new SecretKeySpec((username).getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            byte[] bytes = mac.doFinal(password.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            for(byte symbol: bytes){
                stringBuilder.append(Integer.toString((symbol & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString().toUpperCase();
        }
        catch (Exception exception){
            return null;
        }
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * Get UserName and Pin from View.
         */

        String username = request.getParameter("txtUserName");
        String password = request.getParameter("txtPass");

        response.setContentType("text/html");
        InputStream inputStream = new FileInputStream(getServletContext().getRealPath("/WEB-INF/resources/users.txt"));
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
            usersList=reader.lines().collect(Collectors.toMap(k->k.split(" ")[0],v->v.split(" ")[1]));
        }
        /**
         * Check user is authenticated?.
         */
        boolean usrn = usersList.containsKey(username);
         String pass = usersList.get(username);
        if(usersList.containsKey(username) && usersList.get(username).equalsIgnoreCase(getHashPassword(username,password)) ){
            RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/succesful.jsp");
            dis.forward(request, response);
        } else {
            /**
             * If user not authenticated set address to login.jsp.
             */
            request.setAttribute("error","Wrong login or password");
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
