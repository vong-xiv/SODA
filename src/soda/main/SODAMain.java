package soda.main;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.hyperic.sigar.SigarException;

import soda.aggregator.core.Aggregator;
import soda.util.config.ConfigReader;
import soda.util.logger.LoggerBuilder;


/**
 * This is the main method (starting point) of SODA
 * @author Vong Vithyea Srey
 *
 */
public class SODAMain{
	
	public static String CONFIG_PATH = null;
	
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SigarException{
		
		// 1). get the config file path, from -config flag (if there is)
		List<String> argsList = Arrays.asList(args);
		int configIndex = argsList.indexOf("-config");
		if(configIndex >= 0){
			CONFIG_PATH = argsList.get(configIndex + 1);
			ConfigReader.setDefaultConfigPath(CONFIG_PATH);
		}
		
		
		// 2). config the appLogger
		LoggerBuilder.setAppenderForAppLoggerFromDefaultConfigFile();
		
		
		
		// 3). Instantiate aggregator and start collecting and logging machine performance.
		Aggregator aggregator = new Aggregator();
		aggregator.runAggregation();
	}
}