package soda.aggregator.collector.factory;

import soda.aggregator.collector.tool.sigarsupportos.CPUCollector;
import soda.aggregator.collector.tool.sigarsupportos.DFCollector;
import soda.aggregator.collector.tool.sigarsupportos.DiskCollector;
import soda.aggregator.collector.tool.sigarsupportos.MemoryCollector;
import soda.aggregator.collector.tool.sigarsupportos.NetworkCollector;


/**
 * Factory that is producing the collector correspond to the OS
 * 
 * Note: The CollectorFactoryManager using Java Reflection to invoke all the methods inside this class.
 * Thus, the methods that can be added into this class are only those that are used to get a specific Collector.
 * i.e. If you are creating a new Collector to collect and log HTTP traffics, where the new Collector class named "HttpCollector"
 *      You should add a new method in this class named "getHttpCollector()", where this method will return a new Object of HttpCollector.
 * Please don't add any helper methods with public accessibility. If you really need to add any helper methods, you can add with a protected or private modifiers.
 * @author Vong Vithyea Srey
 *
 */
public interface CollectorFactory {
	
	/**
	 * producing the CPU collector and logger for this OS
	 * @return collector and logger of CPU performance for this OS
	 */
	public CPUCollector	getCPUCollector();
	
	
	
	/**
	 * producing the Memory collector and logger for this OS
	 * @return collector and logger of Memory performance for this OS
	 */
	public MemoryCollector getMemoryCollector();
	
	
	
	/**
	 * producing the DF collector and logger for this OS (DF is producing used space and free space of each HDD. Same as df command in Linux/Unix)
	 * @return collector and logger of DF performance for this OS
	 */
	public DFCollector getDFCollector();
	
	
	
	/**
	 * producing the Disk collector and logger for this OS (the amount of writing and reading, IO)
	 * @return collector and logger of Disk performance for this OS
	 */
	public DiskCollector getDiskCollector();
	
	
	
	/**
	 * producing the Network collector and logger for this OS (TCP, and UDP traffic volumes)
	 * @return collector and logger of Network performance for this OS
	 */
	public NetworkCollector getNetworkCollector();
}
