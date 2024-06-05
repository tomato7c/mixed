package com.example.mixed;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RealTimeController {

    @Autowired
    private RealTimeService service;

    @GetMapping("/realtime")
    public void realTime(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            service.allCodes().forEach(it -> {
                RealTimeInfo res = service.get(it);
                writer.write(PrintUtil.body(res));
                writer.write("\r\n");
                writer.flush();
            });
        }
    }
}
