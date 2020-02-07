/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.json
 * File: 		JsonDownloader.java
 *
 * Created:		Oct 18, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.druffel.foodservice.service.OrderprocessService;

/**
 * <b>Description:</b><br>
 * 
 *Web Servlet  that provides the download of the JSON file
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@WebServlet("/downloadJson")
public class JsonDownloader extends HttpServlet
{

    private final Logger logger = Logger.getLogger(getClass().getName());
    
    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    @Inject
    OrderprocessService orderprocessFacade;
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
     // reads input file from an absolute path

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        File downloadFile = new File("auszahlung-" + sdf.format(orderprocessFacade.getLast().getOrderedtime()) + ".json");
        FileWriter fw = new FileWriter(downloadFile);
        fw.write(new String(orderprocessFacade.getLast().getJson()));
        fw.flush();
        fw.close();
        FileInputStream inStream = new FileInputStream(downloadFile);
         
        logger.info("Created Json: " + downloadFile.getName());
        
        ServletContext context = getServletContext();
         
        // gets MIME type of the file
        String mimeType = context.getMimeType("json.json");
        if (mimeType == null) {        
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
         
        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
         
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
         
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
         
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
         
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
         
        inStream.close();
        outStream.close();  
        logger.info("Finished file download");
    }
    
}
