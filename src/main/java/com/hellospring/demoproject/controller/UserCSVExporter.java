package com.hellospring.demoproject.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.hellospring.demoproject.enity.User;

import jakarta.servlet.http.HttpServletResponse;

public class UserCSVExporter extends AbstractExporter {
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
       super.setResponseHeader(response, "text/csv", ".csv");
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = { "User ID", "E-mail", "First Name", "Last Name", "Roles", "Enabled" };
        String[] fieldMapping = {"id", "email","firstName", "lastName", "roles", "enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (User user : listUsers) {
            csvBeanWriter.write(user, fieldMapping);
        }
        csvBeanWriter.close();
    }
}
