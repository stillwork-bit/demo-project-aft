package helper;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

public class PropertiesFileReader {

    public Session getSession(String hostName, Integer port, String user, String password)
            {
        JSch jsch = new JSch();
                Session session = null;
                try {
                    session = jsch.getSession(user, hostName, port);
                } catch (JSchException e) {
                    e.printStackTrace();
                }
                session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig(
                "PreferredAuthentications",
                "publickey,keyboard-interactive,password");
        return session;
    }

    /**
     * @param session            (Сессия для подключения по SSH)
     * @param propertiesFilePath (example: /rsa/opt/config/cfgAgent.properties)
     * @param setOfProperties    (Набор проверяемых параметров)
     * @return
     */
    public Map<String, String> getValuesOfPropertyFile(Session session,
                                                       String propertiesFilePath,
                                                       HashSet<String> setOfProperties) {
        Map<String, String> valuesOfProperties = new HashMap<>();

        try {
            session.connect();
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            InputStream out = sftpChannel.get(propertiesFilePath);
            Properties properties = new Properties();
            properties.load(out);

            for (String parameter : setOfProperties) {
                valuesOfProperties.put(parameter, properties.getProperty(parameter));
            }

            sftpChannel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.err.print(e);
        }
        return valuesOfProperties;
    }
}


