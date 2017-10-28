package info.thereisonlywe.core.essentials;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.logging.Level;

public class SystemEssentials
{
	public static class Logger
	{
		private static String tobewritten = ""; // we will write these later on.

		public static synchronized void flush()
		{
			final String st = tobewritten;
			tobewritten = "";
			write(st + "\n");
		}

		public static synchronized void log(Level a, Exception b)
		{
			if (a.equals(Level.SEVERE) || a.equals(Level.WARNING))
			{
				writeF(b.getMessage());
			}
			else
			{
				writeI(b.getMessage());
			}
		}

		public static synchronized void log(Level a, String b)
		{
			if (a.equals(Level.SEVERE) || a.equals(Level.WARNING))
			{
				writeF(b);
			}
			else
			{
				writeI(b);
			}
		}

		private static String getTime()
		{
			final Calendar cal = Calendar.getInstance();
			final String time = TimeEssentials.formatTime(
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND))
				+ " on "
				+ StringEssentials.addPaddingToNumber(
					String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2)
				+ "."
				+ StringEssentials.addPaddingToNumber(
					String.valueOf(cal.get(Calendar.MONTH) + 1), 2)
				+ "."
				+ cal.get(Calendar.YEAR);
			return time;
		}

		private static void write(String st)
		{
			try
			{
				final FileWriter fstream = new FileWriter("log.txt", true);
				final BufferedWriter out = new BufferedWriter(fstream);
				final String stx = tobewritten + st;
				out.write(stx);
				tobewritten = "";
				out.flush();
				out.close();
			}
			catch (final Exception e)
			{
				tobewritten += st + "\n";
			}
		}

		// for writing failure logs to file
		private static void writeF(String info)
		{
			String st = "[E] ";
			st += info + " IN "
				+ new Throwable().fillInStackTrace().getStackTrace()[3].getMethodName()
				+ " {"
				+ new Throwable().fillInStackTrace().getStackTrace()[3].getLineNumber()
				+ "}, "
				+ new Throwable().fillInStackTrace().getStackTrace()[3].getClassName()
				+ " @ " + getTime();
			System.err.println(st);
			write(st + "\n");
		}

		// for writing information logs to file
		private static void writeI(String info)
		{
			String st = "[I] ";
			st += info + " @ " + getTime();
			System.out.println(st);
			write(st + "\n");
		}
	}

	public static String getHostname()
	{
		String hostname = null;
		try
		{
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		}
		return hostname;
	}

	public static void collectGarbage()
	{
		Object obj = new Object();
		final WeakReference ref = new WeakReference(obj);
		obj = null;
		while (ref.get() != null)
		{
			System.gc();
		}
	}

	public static boolean isRunningOnAndroid()
	{
		return System.getProperties().get("java.vm.name") == null
			|| System.getProperties().get("java.vm.name").equals("Dalvik")
			|| System.getProperties().get("java.vm.vendor")
				.equals("The Android Project");
	}

	public static String runCommand(String cmd)
	{
		try
		{
			final StringBuilder result = new StringBuilder("");
			String s;
			final Process p = Runtime.getRuntime().exec(cmd);
			final BufferedReader in = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
			while ((s = in.readLine()) != null)
			{
				result.append(s);
			}
			return result.toString();
		}
		catch (final IOException ex)
		{
			Logger.log(Level.WARNING, ex);
		}
		return null;
	}

	public static void sleep(long echoDelay)
	{
		try
		{
			Thread.sleep(echoDelay);
		}
		catch (final InterruptedException ex)
		{
		}
	}
}
