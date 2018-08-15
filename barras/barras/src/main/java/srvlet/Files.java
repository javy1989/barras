/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srvlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Producto;
import servicio.ProductoFacade;

/**
 *
 * @author USUARIO
 */
public class Files extends HttpServlet {

    @EJB
    private  ProductoFacade ejbProducto;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Files</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Files at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String codigo = "";
            codigo = request.getPathInfo().substring(1);
            if (!codigo.isEmpty()) {
                Integer imageId = Integer.parseInt((request.getPathInfo().substring(1))); // Gets string that goes after "/images/".
                Producto image = ejbProducto.find(imageId); // Get Image from DB.

                response.setHeader("Content-Type", getServletContext().getMimeType("image/jpeg"));
                response.setHeader("Content-Disposition", "inline; filename=\"" + image.getNombre() + "\"");

                BufferedInputStream input = null;
                BufferedOutputStream output = null;

                try {
                    InputStream stream = new ByteArrayInputStream(image.getFoto());
                    input = new BufferedInputStream(stream); // Creates buffered input stream.
                    output = new BufferedOutputStream(response.getOutputStream());
                    byte[] buffer = new byte[8192];
                    for (int length = 0; (length = input.read(buffer)) > 0;) {
                        output.write(buffer, 0, length);
                    }
                } finally {
                    if (output != null) {
                        try {
                            output.close();
                        } catch (IOException logOrIgnore) {
                        }
                    }
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException logOrIgnore) {
                        }
                    }
                }
            }

        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
