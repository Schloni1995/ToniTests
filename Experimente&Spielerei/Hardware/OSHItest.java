package Hardware;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Firmware;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.UsbDevice;
import oshi.hardware.platform.windows.WindowsSensors;
import oshi.software.os.NetworkParams;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;

public class OSHItest
{
	public static void main(final String[] args)
	{
		new OSHItest();
	}

	ArrayList<String> printList;

	public OSHItest()
	{
		printList = new ArrayList<>();
		final SystemInfo si = new SystemInfo();
		OperatingSystem os = null;
		os = si.getOperatingSystem();
		fillListForPrinting("--OperatingSystem--");
		fillListForPrinting(os + "");
		fillListForPrinting("familiy: " + os.getFamily());
		fillListForPrinting("manufacturer: " + os.getManufacturer());
		fillListForPrinting("");

		final HardwareAbstractionLayer hal = si.getHardware();

		final ComputerSystem cs = hal.getComputerSystem();
		fillListForPrinting("--ComputerSystem--");
		fillListForPrinting("model: " + cs.getModel());
		fillListForPrinting("manufacturer: " + cs.getManufacturer());
		fillListForPrinting("");

		final Firmware firmware = cs.getFirmware();
		fillListForPrinting("--Firmware--");
		fillListForPrinting("name: " + firmware.getName());
		fillListForPrinting("description: " + firmware.getDescription());
		fillListForPrinting("releasedate: " + firmware.getReleaseDate());
		fillListForPrinting("version: " + firmware.getVersion());
		fillListForPrinting("manufacturer: " + firmware.getManufacturer());
		fillListForPrinting("");

		final Baseboard bb = cs.getBaseboard();
		fillListForPrinting("--Motherboard--");
		fillListForPrinting("manufacturer: " + bb.getManufacturer());
		fillListForPrinting("model: " + bb.getModel());
		fillListForPrinting("serialnumber: " + bb.getSerialNumber());
		fillListForPrinting("version: " + bb.getVersion());
		fillListForPrinting("");

		final OperatingSystemVersion version = os.getVersion();
		fillListForPrinting("--OperationSystemVersion--");
		fillListForPrinting("codename: " + version.getCodeName());
		fillListForPrinting("buildnumber: " + version.getBuildNumber());
		fillListForPrinting("version: " + version.getVersion());
		fillListForPrinting("");

		final NetworkParams np = os.getNetworkParams();
		fillListForPrinting("--NetworkParams--");
		fillListForPrinting("hostname: " + np.getHostName());
		fillListForPrinting("domainname: " + np.getDomainName());
		fillListForPrinting("");

		final HWDiskStore[] ds = hal.getDiskStores();
		fillListForPrinting("--HWDiskStore--");
		for (final HWDiskStore diskStore : ds)
		{
			fillListForPrinting("name: " + diskStore.getName());
			fillListForPrinting("model: " + diskStore.getModel());
		}
		fillListForPrinting("");

		final GlobalMemory gm = hal.getMemory();
		fillListForPrinting("--GlobalMemory--");
		fillListForPrinting("available space: " + gm.getAvailable());
		fillListForPrinting("total space: " + gm.getTotal());
		fillListForPrinting("");

		final NetworkIF[] nif = hal.getNetworkIFs();
		fillListForPrinting("--NetworkIF--");
		for (final NetworkIF networkIF : nif)
		{
			fillListForPrinting("displayname: " + networkIF.getDisplayName());
			fillListForPrinting("IPv4-Adresses: " + Arrays.asList(networkIF.getIPv4addr()));
			fillListForPrinting("MAC adresses: " + networkIF.getMacaddr());
			fillListForPrinting("name: " + networkIF.getName());
			fillListForPrinting("speed: " + networkIF.getSpeed());
		}
		fillListForPrinting("");

		final PowerSource[] ps = hal.getPowerSources();
		fillListForPrinting("--PowerSource--");
		for (final PowerSource powerSource : ps)
		{
			fillListForPrinting("name: " + powerSource.getName());
			fillListForPrinting("remaining capacity: " + powerSource.getRemainingCapacity());
			fillListForPrinting("time remaining: " + powerSource.getTimeRemaining());
		}
		fillListForPrinting("");

		final CentralProcessor cp = hal.getProcessor();

		fillListForPrinting("--CentralProcessor--");
		fillListForPrinting("family: " + cp.getFamily());
		fillListForPrinting("logical processorCount: " + cp.getLogicalProcessorCount());
		fillListForPrinting("model: " + cp.getModel());
		fillListForPrinting("physical processorCount: " + cp.getPhysicalProcessorCount());
		fillListForPrinting("system cpuload: " + cp.getSystemCpuLoad());
		fillListForPrinting("vendor: " + cp.getVendor());
		fillListForPrinting("identifierer: " + cp.getIdentifier());
		fillListForPrinting("name: " + cp.getName());
		fillListForPrinting("processorID: " + cp.getProcessorID());
		fillListForPrinting("stepping: " + cp.getStepping());
		fillListForPrinting("vendorfreq: " + cp.getVendorFreq());
		fillListForPrinting("");

		final WindowsSensors sensors = new WindowsSensors();
		fillListForPrinting("--WindowSensors--");
		fillListForPrinting("cpu temp: " + sensors.getCpuTemperature());
		fillListForPrinting("cpu volt: " + sensors.getCpuVoltage());
		fillListForPrinting("");

		final int[] fanSpeeds = sensors.getFanSpeeds();
		fillListForPrinting("--Fanspeeds--");
		for (final int i : fanSpeeds)
			fillListForPrinting(i + "");
		fillListForPrinting("");

		final UsbDevice[] usbdev = hal.getUsbDevices(false);
		fillListForPrinting("--UsbDevices--");
		for (final UsbDevice usb : usbdev)
		{
			fillListForPrinting("name: " + usb.getName());
			fillListForPrinting("vendor: " + usb.getVendor());
		}

		final String computerName = cs.getModel() + cs.getManufacturer();

		printinTXT(computerName);
	}

	private void fillListForPrinting(final String data)
	{
		printList.add(data);
	}

	private void printinTXT(final String computerName)
	{
		PrintWriter pWriter = null;
		try
		{
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter(
					"\\\\rs5\\RimeTool\\Hardware\\" + System.getProperty("user.name") + " " + computerName + ".txt")));
			for (final String spec : printList)
				pWriter.println(spec);
		}
		catch (final IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			if (pWriter != null)
			{
				pWriter.flush();
				pWriter.close();
				System.out.println("PrintWriter closed");
			}
		}
	}

}
