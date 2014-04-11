package soda.aggregator.collector.tool.minix;

import java.util.Map;

import soda.aggregator.collector.tool.sigarsupportos.DFCollector;


/**
 * Collector Tool that responsible to collect the disk usages (Free, used in %)
 * This tool aggregates Sigar.Df
 * 
 * This class is intentionally left unimplemented for the purpose to show how to
 * implement new Collector tool for Minix OS. So, you can do the same for other OS.
 * You also need to implement MinixCollectorFactory.
 * Then you can just change "OS" value in the config file, it will works automatically. 
 * 
 * @author Vong Vithyea Srey
 *
 */
public class MinixDFCollector extends DFCollector{

	@Override
	public Map<String, String> getPerformance() {
		// TODO Auto-generated method stub
		return null;
	}

}
