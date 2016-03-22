import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class DbConnector {
    private static Statement st;
    private static ResultSet rs;

    public void connectDb(String username, String password, String dbServerName, int port, String dbName) throws Exception{
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

        if(Global.dbConnection != null){
            Logger.write("Connection to SQL Server established.");
        }
    }

    public void disconnectDb() throws Exception{
        if(!st.isClosed()){
            st.close();
        }

        if(Global.dbConnection == null){
            Logger.write("DB Connection has been closed or no connection has not been established.");
        } else {
            Global.dbConnection.close();
        }
    }

    public void executeStatement(String sqlStatement) throws Exception{
        st = Global.dbConnection.createStatement();
        rs = st.executeQuery(sqlStatement);
        closeStatement();
    }

    public void closeStatement() throws Exception{
        if(st != null || !st.isClosed()){
            st.close();
        }
    }
}
