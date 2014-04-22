package soda.aggregator.collector.tool.sigarsupportos;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import soda.aggregator.collector.tool.CollectorTool;
import soda.util.logger.LoggerBuilder;


/**
 * Collector Tool that responsible to collect the disk usages (Free, used in %)
 * This tool aggregates Sigar.Df
 * 
 * Classes and Interfaces in this package is implemented using Sigar.
 * Sigar is supporting Linux, Windows, HPUX, Solaris, AIX, FreeBSD, MacOSX.
 * So, these OS(es) will not be implemented separately. The implementations
 * in this package will be able to support those OS(es).
 * However, if you want this tool to support any other OS(es), you need to
 * implements each tools for each OS by extending the tool from this package.
 * And you also need to implement the CollectorFactory class for that OS as well.
 * An example has been done for the Minix OS, please check package soda.aggregator.collector.tool.minix
 * and MinixCollectorFactory 
 * 
 * @author Vong Vithyea Srey
 *
 */
public class DFCollector extends CollectorTool{
	
	/**
	 * configure the logger during this object instantiation
	 * @throws SigarException if the Method cannot retrieve the info about that hardware (i.e. for CPU, can't get number of core, etc)
	 */
	public DFCollector() throws SigarException{
		configureLogger();
		LoggerBuilder.getAppLogger().info("DFCollector is instantiated successfully");
	}

	
	
	/**
	 * a helper method to convert the size in MB (in long) to GB 
	 * @param size in MB
	 * @return String of size in Byte
	 */
	public static long megaByteToByte(long size){
		//return Sigar.formatSize(size * 1024);  (Sigar.formatSize(sizeInByte) will return according the the value, MB, GB or TB)
		return size * 1024;
	}
	
	
	
	/**
	 * a helper method to collect data for the give Volume (fs)
	 * @param fs - sigar.FileSystem Object represent a FileSystem (or a Volume)
	 * @return a map (LinkedHashMap) of "Performance Description" (as a key) and "Performance Value" for the given volume (fs)
	 */
	public Map<String, String> getPerformanceOfGivenVolume(FileSystem fs) throws SigarException {
		
		Sigar sigar = new Sigar();

		Map<String, String> performance = new LinkedHashMap<String, String>();
		
		// check whether this volume can be accessed or not (if it is a NFS FileSystem)
		if (fs instanceof NfsFileSystem) {
			NfsFileSystem nfs = (NfsFileSystem) fs;
			// if nfs can't reply the ping, means this nfs drive can't be accessed.
			// we are not concerning about this drive anyway. => return empty map
			if (!nfs.ping()) {
				LoggerBuilder.getAppLogger().error(nfs.getUnreachableMessage());
				return performance;
			}
		}
		
		FileSystemUsage volume = sigar.getFileSystemUsage(fs.getDirName());
		
		// get the volume name
		String name = fs.getDevName();
		// if there's no "/" => substring start from 0 (-1 + 1)
		// else it will start from index + 1 ("/" will not be included)
		name = name.substring(name.lastIndexOf("/") + 1);
		
		performance.put(DEVICE_NAME, "DF-"+name);
		performance.put(DESCRIPTION, "Size-MB,Used-MB,Avail-MB,UsedInPerc-%");
		
		strBuilder.setLength(0);
		
		// getTotal() and getFree() and getAvail() are producing MB result
		strBuilder.append(volume.getTotal());
		strBuilder.append(" ");
		
		long temp = volume.getTotal() - volume.getFree();
		strBuilder.append(temp);
		strBuilder.append(" ");
		
		strBuilder.append(volume.getAvail());
		strBuilder.append(" ");
		
		DecimalFormat d = new DecimalFormat("0.0");
		temp = (long)(volume.getUsePercent() * 100);
		strBuilder.append(d.format(temp));
		
		performance.put(VALUE, strBuilder.toString());
		
		strBuilder.setLength(0);
		
		return performance;
    }
	
	
	
	/**
	 * collect a map of "Performance Description" (as a key) and "Performance Value" from the implemented CollectorTool
	 * @return a map (LinkedHashMap) of "Performance Description" (as a key) and "Performance Value" from the implemented CollectorTool
	 * @throws SigarException if the Method cannot retrieve the data about that hardware (i.e. for CPU, can't get number of core, etc)
	 */
	@Override
	public Set<Map<String, String>> getPerformance() throws SigarException{
		Sigar sigar = new Sigar();
		Set<Map<String,String>> perfSet = new LinkedHashSet<Map<String, String>>();
		
		FileSystem[] fslist = sigar.getFileSystemList();
		
		for(int i=0; i < fslist.length; i++){
			// there's no reason to add an empty map into the set
			// the empty map, means the device is on network and we can't access that device for some reasons (no permission to access, not available, etc)
			Map<String, String> performance = getPerformanceOfGivenVolume(fslist[i]);
			if(!performance.isEmpty()){
				perfSet.add(performance);
			}
		}

		return perfSet; 
	}

}
