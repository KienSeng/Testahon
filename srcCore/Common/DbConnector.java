package Common;

import Debugger.Logger;
import Global.Global;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class DbConnector {
    private static Statement st;
    private static ResultSet rs;
    private static int is;
    private static CallableStatement cs;

    public void connectDb(String username, String password, String dbServerName, int port, String dbName) throws Exception {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            SQLServerDataSource con = new SQLServerDataSource();

            //Set DB connection settings
            con.setUser(username);
            con.setPassword(password);
            con.setServerName(dbServerName);
            con.setPortNumber(port);
            con.setDatabaseName(dbName);

            //Display connection info for debug purpose
//            Logger.write("DB Username: " + username);
//            Logger.write("DB Password: " + password);
//            Logger.write("DB Server Name: " + dbServerName);
//            Logger.write("DB Port: " + port);
//            Logger.write("DB Name: " + dbName);

            Logger.write("Connecting to SQL Server.");
            Global.dbConnection = con.getConnection();

            if (Global.dbConnection != null) {
                Logger.write("Connection to SQL Server established.");
            }
        }catch(Exception e){
            Logger.write("[ SQL Error ]" + "Unable to establish MSSQL connection");
            Logger.write("[ SQL Error ]" + e.getMessage());
        }
    }

    public void connectMysqlDb(String username, String password, String dbServerName, int port, String dbName) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Global.dbConnection = DriverManager.getConnection("jdbc:mysql://" + dbServerName + ":" + port + "/" + dbName, username, password);

            //Display connection info for debug purpose
//            Logger.write("DB Username: " + username);
//            Logger.write("DB Password: " + password);
//            Logger.write("DB Server Name: " + dbServerName);
//            Logger.write("DB Port: " + port);
//            Logger.write("DB Name: " + dbName);

            Logger.write("Connecting to MYSQL Server.");

            if (Global.dbConnection != null) {
                Logger.write("Connection to MYSQL Server established.");
            }
        }catch(Exception e){
            Logger.write("[ SQL Error ]" + "Unable to establish MSSQL connection");
            Logger.write("[ SQL Error ]" + e.getMessage());
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
        try{
            st = Global.dbConnection.createStatement();
            rs = st.executeQuery(sqlStatement);

            Logger.write("Statement execute completed.");
        }catch(Exception e){
            Logger.write("Statement execute failed.");
            Logger.write("[ SQL ERROR ]\t" + e.getMessage());
        }
        return rs;
    }

    public int executeUpdate(String sqlStatement) throws Exception{
        try {
            st = Global.dbConnection.createStatement();
            is = st.executeUpdate(sqlStatement);

            Logger.write("Statement execute completed with code: " + is);
        }catch(Exception e){
            Logger.write("Statement execute failed.");
            Logger.write("[ SQL ERROR ]\t" + e.getMessage());
        }
        return is;
    }

    public ResultSet executeStoredProc(String storedProcQuery, ArrayList<String> parameterArray) throws Exception{
        cs = Global.dbConnection.prepareCall(storedProcQuery);

        for(int i = 0; i < parameterArray.size(); i++){
            String[] splittedString = parameterArray.get(i).split("\\|");
            String parameterType = splittedString[0];
            String parameterName = splittedString[1];
            String parameterValue = splittedString[2];

            switch(parameterType.toLowerCase()){
                case "integer":
                    if(parameterValue.equalsIgnoreCase("null")){
                        cs.setNull(parameterName, Types.INTEGER);
                    }else{
                        cs.setInt(parameterName, Integer.parseInt(parameterValue));
                    }
                    break;

                case "string":
                    if(parameterValue.equalsIgnoreCase("null")){
                        cs.setNull(parameterName, Types.NVARCHAR);
                    }else{
                        cs.setString(parameterName, parameterValue);
                    }
                    break;
            }
        }

        rs = cs.executeQuery();

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
