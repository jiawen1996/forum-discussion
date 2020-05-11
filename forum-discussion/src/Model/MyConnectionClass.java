package Model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lounis
 */
public class MyConnectionClass {

    // implémenté sous forme de singleton //
    private static Connection singleton;

    private MyConnectionClass() throws IOException, ClassNotFoundException, SQLException {
        // protection 
        Properties param = new Properties();
        URL urlFichierProp = MyConnectionClass.class.getResource("jdbc.properties");
        if (urlFichierProp == null) {
            throw new IOException("Fichier " + "BDparam" + " pas trouvé !");
        }
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(urlFichierProp.openStream());
            param.load(bis);
            String driver = param.getProperty("driver");
            String url = param.getProperty("url");
            String utilisateur = param.getProperty("utilisateur");
            String mdp = param.getProperty("mdp");
            Class.forName(driver);
            singleton = DriverManager.getConnection(url, utilisateur, mdp);
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
    }

    public static Connection getInstance() throws IOException, ClassNotFoundException, SQLException {
        if (singleton == null) {
            new MyConnectionClass();
        }
        return singleton;
    }

    public static void main(String [] args) {
        //pour tester la connexion
        try {
            Connection con = MyConnectionClass.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(MyConnectionClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyConnectionClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MyConnectionClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
