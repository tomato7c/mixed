package com.example.mixed;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

@RestController
public class RealTimeController {

    @Autowired
    private RealTimeService service;

    private List<String> list = ImmutableList.of("sh600329", "sz000651", "sh600036", "sh600900", "sh600887");

    @GetMapping("/realtime")
    public void realTime(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            list.forEach(it -> {
                String res = null;
                try {
                    res = service.get(it);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                writer.write(res);
                writer.write("</br>");
                writer.flush();
            });
        }
    }
}
