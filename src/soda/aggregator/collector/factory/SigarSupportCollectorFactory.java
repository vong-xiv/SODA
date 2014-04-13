package soda.aggregator.collector.factory;

import soda.aggregator.collector.tool.sigarsupportos.CPUCollector;
import soda.aggregator.collector.tool.sigarsupportos.DFCollector;
import soda.aggregator.collector.tool.sigarsupportos.DiskCollector;
import soda.aggregator.collector.tool.sigarsupportos.MemoryCollector;
import soda.aggregator.collector.tool.sigarsupportos.NetworkCollector;

/**
 * Collector Factory for OS(es) that are supported by Sigar. It will create all
 * the collector tools that are supposed to work on those OS(es).
 * 
 * OS(es) that supported by Sigar are: Linux, Windows, HPUX, Solaris, AIX, FreeBSD, MacOSX
 *
 * @author Vong Vithyea Srey
 *
 */
public class SigarSupportCollectorFactory implements CollectorFactory{
	
	/**
	 * producing the CPU collector and logger for this OS
	 * @return collector and logger of CPU performance for this OS
	 */
	@Override
	public CPUCollector getCPUCollector() {
		return new CPUCollector();
	}

	
	
	/**
	 * producing the Memory collector and logger for this OS
	 * @return collector and logger of Memory performance for this OS
	 */
	@Override
	public MemoryCollector getMemoryCollector() {
		// TODO Auto-generated method stub
		return new MemoryCollector();
	}

	
	
	/**
	 * producing the DF collector and logger for this OS (DF is producing used space and free space of each HDD. Same as df command in Linux/Unix)
	 * @return collector and logger of DF performance for this OS
	 */
	@Override
	public DFCollector getDFCollector() {
		// TODO Auto-generated method stub
		return new DFCollector();
	}

	
	
	/**
	 * producing the Disk collector and logger for this OS (the amount of writing and reading, IO)
	 * @return collector and logger of Disk performance for this OS
	 */
	@Override
	public DiskCollector getDiskCollector() {
		// TODO Auto-generated method stub
		return new DiskCollector();
	}

	
	
	/**
	 * producing the Network collector and logger for this OS (TCP, and UDP traffic volumes)
	 * @return collector and logger of Network performance for this OS
	 */
	@Override
	public NetworkCollector getNetworkCollector() {
		// TODO Auto-generated method stub
		return new NetworkCollector();
	}

}
