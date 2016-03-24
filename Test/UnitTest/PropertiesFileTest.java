package UnitTest;

import Debugger.Logger;
import Global.Global;
import PropertiesFile.PropertiesFileReader;

/**
 * Created by kienseng on 3/24/2016.
 */
public class PropertiesFileTest {
    static PropertiesFileReader file = new PropertiesFileReader();

    public static void testRetrieveAllValue() throws Exception{
        Logger.debugMode(true);
        file.setFile("PropertyTest.properties");
        file.loadAllPropertyToMap();

        Logger.loopMap("Property Map", Global.propertyMap);
    }
}
