package Database;

import Debugger.Logger;
import Global.Global;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class DbConnector {
    private static Statement st;
    private static ResultSet rs;
    private static CallableStatement cs;

    public void connectDb(String username, String password, String dbServerName, int port, String dbName) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        SQLServerDataSource con = new SQLServerDataSource();

        //Set DB connection settings
        con.setUser(username);
        con.setPassword(password);
        con.setServerName(dbServerName);
        con.setPortNumber(port);
        con.setDatabaseName(dbName);

        //Display connection info for debug purpose
        Logger.write("DB Username: " + username);
        Logger.write("DB Password: " + password);
        Logger.write("DB Server Name: " + dbServerName);
        Logger.write("DB Port: " + port);
        Logger.write("DB Name: " + dbName);

        Logger.write("Connecting to SQL Server.");
        Global.dbConnection = con.getConnection();

        if (Global.dbConnection != null) {
            Logger.write("Connection to SQL Server established.");
        }
    }

    public void disconnectDb() throws Exception {
        if (!st.isClosed()) {
            st.close();
        }

        if(!cs.isClosed()){
            cs.close();
        }

        if (Global.dbConnection == null) {
            Logger.write("DB Connection has been closed or no connection has not been established.");
        } else {
            Global.dbConnection.close();
        }
    }

    public ResultSet executeStatement(String sqlStatement) throws Exception {
        st = Global.dbConnection.createStatement();
        rs = st.executeQuery(sqlStatement);

        return rs;
    }

    public ResultSet executeStoredProc(String storedProcQuery, ArrayList<String> parameterArray) throws Exception{
        cs = Global.dbConnection.prepareCall(storedProcQuery);

        for(int i = 0; i < parameterArray.size(); i++){
            String[] splittedString = parameterArray.get(i).split("|");
            String parameterType = splittedString[0];
            String parameterName = splittedString[1];
            String parameterValue = splittedString[2];

            switch(parameterType.toLowerCase()){
                case "integer":
                    cs.setInt(parameterName, Integer.parseInt(parameterValue));
                    break;

                case "string":
                    cs.setString(parameterName, parameterValue);
                    break;
            }
        }

        cs.executeUpdate();

        return cs.getResultSet();
    }

    public void closeStatement() throws Exception {
        if (st != null) {
            st.close();
        }
        if (cs != null) {
            cs.close();
        }
    }
}
