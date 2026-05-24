package com.openbiblio.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.UUID;

public class InstallationService {

    private static final String FILE_NAME = "installation.properties";
    private static final String KEY = "installation.id";

    public String getInstallationId() throws Exception {

        Properties props = new Properties();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            props.load(fis);
            fis.close();

            String existingId = props.getProperty(KEY);

            if (existingId != null && !existingId.trim().isEmpty()) {
                return existingId;
            }
        }

        String newId = UUID.randomUUID().toString();

        props.setProperty(KEY, newId);

        FileOutputStream fos = new FileOutputStream(file);
        props.store(fos, "OpenBiblio installation configuration");
        fos.close();

        return newId;
    }
}