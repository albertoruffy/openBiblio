package com.openbiblio.service;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FtpBackupService {

	private String host;
	private int port;
	private String user;
	private String password;
	
	public void descargarBackup(File destino) throws Exception {
	    FTPClient ftp = new FTPClient();

	    try {
	        ftp.connect(host, port);

	        if (!ftp.login(user, password)) {
	            throw new RuntimeException("Login FTP incorrecto");
	        }

	        ftp.enterLocalPassiveMode();
	        ftp.setFileType(FTP.BINARY_FILE_TYPE);

	        String installationId = new InstallationService().getInstallationId();

	        String remotePath =
	                "/htdocs/backups/" + installationId + "/openbiblio_backup.csv";

	        FileOutputStream output = new FileOutputStream(destino);

	        boolean ok = ftp.retrieveFile(remotePath, output);

	        output.close();

	        if (!ok) {
	            throw new RuntimeException(
	                    "No se pudo descargar el backup. Respuesta FTP: "
	                            + ftp.getReplyString()
	            );
	        }

	    } finally {
	        if (ftp.isConnected()) {
	            ftp.logout();
	            ftp.disconnect();
	        }
	    }
	}
	
    public void subirBackup(File archivo) throws Exception {
        FTPClient ftp = new FTPClient();

        try {
        	ftp.connect(host, port);

            if (!ftp.login(user, password)) {
                throw new RuntimeException("Login FTP incorrecto");
            }

            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            FileInputStream input = new FileInputStream(archivo);
            String installationId = new InstallationService().getInstallationId();

            ftp.makeDirectory("/htdocs/backups");
            ftp.makeDirectory("/htdocs/backups/" + installationId);

            String remotePath = "/htdocs/backups/" + installationId + "/openbiblio_backup.csv";

            boolean ok = ftp.storeFile(remotePath, input);
            input.close();

            if (!ok) {
                throw new RuntimeException("No se pudo subir el archivo");
            }

        } finally {
            if (ftp.isConnected()) {
                ftp.logout();
                ftp.disconnect();
            }
        }
    }
    public FtpBackupService() throws Exception {

        Properties props = new Properties();

        FileInputStream fis =
                new FileInputStream("src/ftp.properties");

        props.load(fis);

        host = props.getProperty("ftp.host");

        port = Integer.parseInt(
                props.getProperty("ftp.port")
        );

        user = props.getProperty("ftp.user");

        password = props.getProperty("ftp.password");

        fis.close();
    }
}