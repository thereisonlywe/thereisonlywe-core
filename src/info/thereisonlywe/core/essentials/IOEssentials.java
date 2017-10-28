package info.thereisonlywe.core.essentials;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class IOEssentials
{
	// setRequestProperty("Request Method", "POST");
	public static class FileExtensionFilter implements FilenameFilter
	{
		private final String ext;

		public FileExtensionFilter(String ext)
		{
			this.ext = ((ext.startsWith(".") ? "" : ".") + ext).toLowerCase();
		}

		@Override
		public boolean accept(File dir, String name)
		{
			return name.toLowerCase().endsWith(this.ext);
		}
	}

	public static final int FILE_UPDATE_POLICY_NEVER = 0;
	public static final int FILE_UPDATE_POLICY_IF_POSSIBLE = 1;
	public static final int FILE_UPDATE_POLICY_STRICT = 2;
	public static final int FILE_UPDATE_POLICY_FORCED = 3;
	public static final int NETWORK_TIMEOUT_SHORT = 1000;// ms
	public static final int NETWORK_TIMEOUT_MEDIUM = 3000;
	public static final int NETWORK_TIMEOUT_LONG = 9000;// ms
	private static long lastInternetValidation = 0;
	private static boolean sigTermDownload = false;
	private static boolean sigTermUpload = false;

	/*
	 * 
	 * public static boolean uploadWithPut(File file, String serverAddress, String
	 * userName, String password) {// untested method URLConnection urlconnection
	 * = null; BufferedOutputStream bos = null; BufferedInputStream bis = null;
	 * try { URL url = new URL(serverAddress); urlconnection =
	 * url.openConnection(); urlconnection.setDoOutput(true);
	 * urlconnection.setDoInput(true); if (urlconnection instanceof
	 * HttpURLConnection) { ((HttpURLConnection)
	 * urlconnection).setRequestMethod("PUT"); ((HttpURLConnection)
	 * urlconnection).setRequestProperty("Content-type", "text/plain"); String
	 * encoding = null; if (userName != null && password != null) { encoding =
	 * Base64Encoder.encodeString(userName + ":" + password); ((HttpURLConnection)
	 * urlconnection).setRequestProperty( "Authorization", "Basic " + encoding); }
	 * ((HttpURLConnection) urlconnection).connect(); } bos = new
	 * BufferedOutputStream(urlconnection.getOutputStream()); bis = new
	 * BufferedInputStream(new FileInputStream(file)); int i; // read byte by byte
	 * until end of stream while ((i = bis.read()) > 0) { bos.write(i); } return
	 * ((HttpURLConnection) urlconnection).getResponseCode() == 200; } catch
	 * (Exception e) { return false; } finally { closeQuietly(bos);
	 * closeQuietly(bis); } }
	 * 
	 * public static boolean uploadWithPush(File textFile, File binaryFile, String
	 * serverAddress, String userName, String password) { // untested method
	 * String url = serverAddress; String charset = "UTF-8"; String param =
	 * "value"; String boundary = Long.toHexString(System.currentTimeMillis());
	 * String CRLF = "\r\n"; int responseCode = -1; try { URLConnection connection
	 * = new URL(url).openConnection(); connection.setDoOutput(true);
	 * connection.setRequestProperty("Content-Type",
	 * "multipart/form-data; boundary=" + boundary); String encoding = null; if
	 * (userName != null && password != null) { encoding =
	 * Base64Encoder.encodeString(userName + ":" + password);
	 * connection.setRequestProperty("Authorization", "Basic " + encoding); }
	 * OutputStream output = connection.getOutputStream(); PrintWriter writer =
	 * new PrintWriter(new OutputStreamWriter(output, charset), true); // Send
	 * normal param. if (textFile != null) { writer.append("--" +
	 * boundary).append(CRLF);
	 * writer.append("Content-Disposition: form-data; name=\"param\"").append(
	 * CRLF); writer.append("Content-Type: text/plain; charset=" +
	 * charset).append( CRLF);
	 * writer.append(CRLF).append(param).append(CRLF).flush(); // Send text file.
	 * writer.append("--" + boundary).append(CRLF); writer.append(
	 * "Content-Disposition: form-data; name=\"textFile\"; filename=\"" +
	 * textFile.getName() + "\"").append(CRLF);
	 * writer.append("Content-Type: text/plain; charset=" + charset).append(
	 * CRLF); // Text file itself must be saved in this charset!
	 * writer.append(CRLF).flush(); Files.copy(textFile.toPath(), output);
	 * output.flush(); // Important before continuing with writer!
	 * writer.append(CRLF).flush(); // CRLF is important! It indicates end of //
	 * boundary. } if (binaryFile != null) { // Send binary file.
	 * writer.append("--" + boundary).append(CRLF); writer.append(
	 * "Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" +
	 * binaryFile.getName() + "\"").append(CRLF); writer.append( "Content-Type: "
	 * + URLConnection.guessContentTypeFromName(binaryFile.getName()))
	 * .append(CRLF);
	 * writer.append("Content-Transfer-Encoding: binary").append(CRLF);
	 * writer.append(CRLF).flush(); Files.copy(binaryFile.toPath(), output);
	 * output.flush(); // Important before continuing with writer!
	 * writer.append(CRLF).flush(); // CRLF is important! It indicates end of //
	 * boundary. } // End of multipart/form-data. writer.append("--" + boundary +
	 * "--").append(CRLF).flush(); // Request is lazily fired whenever you need to
	 * obtain information about // response. responseCode = ((HttpURLConnection)
	 * connection).getResponseCode(); } catch (Exception e) { } return
	 * responseCode == 200; }
	 */
	public static void setFileCreationTime(String filePath)
	{
        if (SystemEssentials.isRunningOnAndroid()) return;
		try
		{
			Path path = Paths.get(filePath);
			FileTime fileTime = FileTime.fromMillis(System.currentTimeMillis());
			/* Changing the Created Time Stamp */
			Files.setAttribute(path, "basic:creationTime", fileTime,
				LinkOption.NOFOLLOW_LINKS);
			new File(filePath).setLastModified(System.currentTimeMillis());
		}
		catch (Throwable aaax)
		{
		}
	}

	public static boolean ftpUpload(String string, String serverAddress,
		String pathAddress, String userName, String password)
	{
		// doesnt work on android, use apache FTPClient instead
		if (!gotInternetConnection()) return false;
		String ftpUrl = "%s:%s@%s/%s;type=i";
		if (!ftpUrl.startsWith("ftp://")) ftpUrl = "ftp://" + ftpUrl;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try
		{
			ftpUrl = String.format(ftpUrl, userName, password, serverAddress,
				pathAddress);
			URL url = new URL(ftpUrl);
			URLConnection conn = url.openConnection();
			outputStream = conn.getOutputStream();
			inputStream = new ByteArrayInputStream(string.getBytes(Charset
				.forName("UTF-8")));
			byte[] buffer = new byte[8196];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1 && !sigTermUpload)
			{
				outputStream.write(buffer, 0, bytesRead);
			}
			return true;
		}
		catch (Exception ex)
		{
			SystemEssentials.Logger.log(Level.WARNING, ex);
			return false;
		}
		finally
		{
			closeQuietly(inputStream);
			closeQuietly(outputStream);
			sigTermUpload = false;
		}
	}

	/** pathAddress is optional */
	public static boolean ftpUpload(File file, String serverAddress,
		String userName, String password, String pathAddress)
	{
		String ftpUrl = "%s:%s@%s/%s;type=i";
		if (!ftpUrl.startsWith("ftp://")) ftpUrl = "ftp://" + ftpUrl;
		OutputStream outputStream = null;
		FileInputStream inputStream = null;
		try
		{
			if (pathAddress == null)
			{
				pathAddress = "/" + file.getName();
			}
			ftpUrl = String.format(ftpUrl, userName, password, serverAddress,
				pathAddress);
			URL url = new URL(ftpUrl);
			URLConnection conn = url.openConnection();
			outputStream = conn.getOutputStream();
			inputStream = new FileInputStream(file);
			byte[] buffer = new byte[8196];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1 && !sigTermUpload)
			{
				outputStream.write(buffer, 0, bytesRead);
			}
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
		finally
		{
			closeQuietly(inputStream);
			closeQuietly(outputStream);
			sigTermUpload = false;
		}
	}

	public static void closeQuietly(Closeable closeable)
	{
		if (closeable == null) return;
		try
		{
			if (closeable instanceof OutputStream)
			{
				((OutputStream) closeable).flush();
			}
			closeable.close();
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
		}
	}

	public static boolean copy(File sourceFile, File destFile)
	{
		FileChannel source = null;
		FileChannel destination = null;
		try
		{
			if (!destFile.exists())
			{
				if (!destFile.createNewFile()) return false;
			}
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
			return true;
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return false;
		}
	}

	public static void copy(InputStream in, OutputStream out) throws IOException
	{
		final byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0)
		{
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}

	public static void deleteTempFiles(File dir)
	{
		final String[] list = dir.list();
		for (final String element : list)
		{
			if (element.endsWith(".temp"))
			{
				new File(dir.getPath(), element).delete();
			}
		}
	}

	public static void interruptDownload()
	{
		sigTermDownload = true;
	}

	public static void interruptUpload()
	{
		sigTermUpload = true;
	}

	public static boolean download(File dest, URL source)
	{
		return download(dest, source, false, false);
	}

	public static String download(URL source)
	{
		return download(source, false);
	}

	public static String download(URL source, boolean setUserAgent)
	{
		if (!gotInternetConnection()) return null;
		InputStream input = null;
		ByteArrayOutputStream output = null;
		URLConnection connection = null;
		String dest;
		try
		{
			connection = source.openConnection();
			if (setUserAgent) connection
				.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			if (source.toString().contains("?t="))
			{
				connection.setRequestProperty("Cache-Control",
					"no-cache, no-store, must-revalidate");
				connection.setUseCaches(false);
			}
			connection.connect();
			input = new BufferedInputStream(connection.getInputStream());
			output = new ByteArrayOutputStream(8196);
			final byte data[] = new byte[32768];
			int count;
			long writeCount = 0;
			while ((count = input.read(data)) != -1 && !sigTermDownload)
			{
				writeCount += count;
				output.write(data, 0, count);
			}
			if (writeCount <= 0) return null;
			dest = output.toString("UTF-8");
		}
		catch (final Exception e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return null;
		}
		finally
		{
			closeQuietly(output);
			closeQuietly(input);
		}
		if (sigTermDownload || dest == null || dest.contains("302 Found"))
		{
			sigTermDownload = false;
			return null;
		}
		return dest;
	}

	public static boolean download(File dest, URL source, boolean directTransfer,
		boolean setUserAgent)
	{
		if (!gotInternetConnection()) return false;
		if (dest.exists() && dest.isFile())
		{
			dest.delete();
		}
		if (dest.getParentFile() != null)
		{
			dest.getParentFile().mkdirs();
		}
		if (directTransfer)
		{
			FileOutputStream fos = null;
			ReadableByteChannel rbc = null;
			try
			{
				final long len = getLength(source);
				if (len >= 1)
				{
					URLConnection con = (source.openConnection());
					if (setUserAgent) con
						.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
					if (source.toString().contains("?t="))
					{
						con.setRequestProperty("Cache-Control",
							"no-cache, no-store, must-revalidate");
						con.setUseCaches(false);
					}
					rbc = Channels.newChannel(con.getInputStream()); // source.openStream()
					fos = new FileOutputStream(dest);
					fos.getChannel().transferFrom(rbc, 0,
						len <= 0 ? Integer.MAX_VALUE : len);
				}
				else return false;
			}
			catch (final IOException e)
			{
				SystemEssentials.Logger.log(Level.WARNING, e);
				return false;
			}
			finally
			{
				closeQuietly(rbc);
				closeQuietly(fos);
			}
			if (sigTermDownload || readString(dest) == null
				|| readString(dest).contains("302 Found"))
			{
				dest.delete();
				return false;
			}
			setFileCreationTime(dest.getAbsolutePath());
			return true;
		}
		else
		// Slower but more compatible (Android)
		{
			InputStream input = null;
			FileOutputStream output = null;
			URLConnection connection = null;
			FileLock lock = null;
			try
			{
				connection = source.openConnection();
				if (setUserAgent) connection
					.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				if (source.toString().contains("?t="))
				{
					connection.setRequestProperty("Cache-Control",
						"no-cache, no-store, must-revalidate");
					connection.setUseCaches(false);
				}
				connection.connect();
				input = new BufferedInputStream(connection.getInputStream());
				output = new FileOutputStream(dest);
				lock = output.getChannel().lock(0, Long.MAX_VALUE, false);
				final byte data[] = new byte[32768];
				int count;
				long writeCount = 0;
				while ((count = input.read(data)) != -1 && !sigTermDownload)
				{
					writeCount += count;
					output.write(data, 0, count);
				}
				if (lock != null)
				{
					lock.release();
				}
				if (writeCount <= 0) return false;
			}
			catch (final Exception e)
			{
				SystemEssentials.Logger.log(Level.WARNING, e);
				return false;
			}
			finally
			{
				closeQuietly(output);
				closeQuietly(input);
			}
			if (sigTermDownload || readString(dest) == null
				|| readString(dest).contains("302 Found"))
			{
				sigTermDownload = false;
				dest.delete();
				return false;
			}
			setFileCreationTime(dest.getAbsolutePath());
			return true;
		}
	}

	/**
	 * Checks if a URL is reachable. First internet connection should be checked
	 * with gotInternetConnection to ensure this is not a walled garden
	 * connection.
	 */
	public static boolean exists(URL url, int timeout)
	{
		HttpURLConnection urlc = null;
		InputStream in = null;
		try
		{
			urlc = (HttpURLConnection) url.openConnection();
			urlc.setRequestProperty("User-Agent", "Test");
			urlc.setRequestProperty("Connection", "close");
			urlc.setReadTimeout(timeout);
			urlc.setConnectTimeout(timeout);
			urlc.connect();
			if (urlc.getResponseCode() == 200)
			{
				in = new BufferedInputStream(urlc.getInputStream());
				if (!url.getHost().equals(urlc.getURL().getHost()))
				{
					return false;
				}
				else return true;
			}
			else return false;
		}
		catch (Exception e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return false;
		}
		finally
		{
			closeQuietly(in);
			if (urlc != null) urlc.disconnect();
		}
	}

	public static boolean isWalledGardenConnection(int timeout)
	{
		HttpURLConnection urlConnection = null;
		try
		{
			URL url = new URL("http://clients3.google.com/generate_204");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setInstanceFollowRedirects(false);
			urlConnection.setConnectTimeout(timeout);
			urlConnection.setReadTimeout(timeout);
			urlConnection.setUseCaches(false);
			urlConnection.getInputStream();
			// We got a valid response, but not from the real google
			return urlConnection.getResponseCode() != 204;
		}
		catch (IOException e)
		{
			return false;
		}
		finally
		{
			if (urlConnection != null)
			{
				urlConnection.disconnect();
			}
		}
	}

	public static boolean zip(File srcFile, File destFile, int level)
	{
		return zip(new File[] { srcFile }, destFile, level);
	}

	public static boolean zip(File srcFile, File destFile)
	{
		return zip(new File[] { srcFile }, destFile, Deflater.BEST_COMPRESSION);
	}

	public static boolean zip(File srcFiles[], File destFile)
	{
		return zip(srcFiles, destFile, Deflater.BEST_COMPRESSION);
	}

	public static boolean zip(File srcFiles[], File destFile, int level)
	{
		BufferedInputStream origin = null;
		FileOutputStream dest = null;
		ZipOutputStream out = null;
		FileInputStream fi = null;
		java.nio.channels.FileLock lock = null;
		try
		{
			dest = new FileOutputStream(destFile);
			out = new ZipOutputStream(new BufferedOutputStream(dest));
			out.setLevel(level);
			try
			{
				lock = dest.getChannel().lock();
			}
			catch (Exception exxx)
			{
			}
			byte data[] = new byte[8196];
			for (int i = 0; i < srcFiles.length; i++)
			{
				fi = new FileInputStream(srcFiles[i]);
				origin = new BufferedInputStream(fi, 8196);
				ZipEntry entry = new ZipEntry(srcFiles[i].getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, 8196)) != -1)
				{
					out.write(data, 0, count);
				}
				closeQuietly(origin);
			}
			setFileCreationTime(destFile.getAbsolutePath());
			return true;
		}
		catch (Exception e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return false;
		}
		finally
		{
			if (lock != null)
			{
				try
				{
					lock.release();
				}
				catch (Exception asdsa)
				{
				}
			}
			closeQuietly(origin);
			closeQuietly(fi);
			closeQuietly(out);
			closeQuietly(dest);
		}
	}

	public static boolean unzip(String srcFile, String destFolder)
	{
		FileInputStream inStream = null;
		ZipInputStream zipStream = null;
		BufferedOutputStream bufferOut = null;
		FileOutputStream outStream = null;
		java.nio.channels.FileLock lock = null;
		try
		{
			inStream = new FileInputStream(srcFile);
			zipStream = new ZipInputStream(new BufferedInputStream(inStream));
			ZipEntry entry;
			new File(destFolder).mkdirs();
			while ((entry = zipStream.getNextEntry()) != null)
			{
				File x = new File(destFolder, entry.getName());
				if (entry.isDirectory())
				{
					x.mkdirs();
				}
				else
				{
					int size;
					byte[] buffer = new byte[8196];
					outStream = new FileOutputStream(x);
					try
					{
						lock = outStream.getChannel().lock();
					}
					catch (Exception asdsadas)
					{
					}
					bufferOut = new BufferedOutputStream(outStream, buffer.length);
					while ((size = zipStream.read(buffer, 0, buffer.length)) != -1)
					{
						bufferOut.write(buffer, 0, size);
					}
					if (lock != null)
					{
						try
						{
							lock.release();
						}
						catch (Exception asdasd)
						{
						}
					}
					setFileCreationTime(x.getAbsolutePath());
				}
			}
			return true;
		}
		catch (Exception e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return false;
		}
		finally
		{
			closeQuietly(bufferOut);
			closeQuietly(outStream);
			closeQuietly(zipStream);
			closeQuietly(inStream);
		}
	}

	public static String fetchLine(Class<?> c, String path, int lineNo)
	{
		return fetchLine(c.getClassLoader(), path.charAt(0) != '/' ? path
			: new String(path.substring(1, path.length())), lineNo);
	}

	public static String fetchLine(ClassLoader c, String path, int lineNo)
	{
		try
		{
			final InputStream fstream = c.getResourceAsStream(path);
			final DataInputStream in = new DataInputStream(fstream);
			final BufferedReader br = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));
			String strLine;
			int counter = 1;
			while ((strLine = br.readLine()) != null)
			{
				if (counter == lineNo)
				{
					br.close();
					return strLine;
				}
				counter++;
			}
			br.close();
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
		}
		return null; // not found
	}

	public static String fetchValue(File file, String labelAndSeparator)
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		String strLine = null;
		try
		{
			fstream = new FileInputStream(file);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			while ((strLine = br.readLine()) != null)
			{
				if (strLine.trim().startsWith(labelAndSeparator))
				{
					break;
				}
			}
			br.close();
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return null;
		}
		finally
		{
			closeQuietly(br);
			closeQuietly(in);
			closeQuietly(fstream);
		}
		if (strLine == null) return "";
		String[] tmp = strLine.split(labelAndSeparator);
		if (tmp.length > 1) return strLine.split(labelAndSeparator)[1];
		else return "";
	}

	public static String fetchLine(File file, int lineNo)
	{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		String strLine = null;
		try
		{
			fstream = new FileInputStream(file);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			int counter = 1;
			while ((strLine = br.readLine()) != null)
			{
				if (counter == lineNo)
				{
					break;
				}
				counter++;
			}
			br.close();
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return null;
		}
		finally
		{
			closeQuietly(br);
			closeQuietly(in);
			closeQuietly(fstream);
		}
		return strLine;
	}

	public static long getLastModified(URL u)
	{
		long size = 0;
		URLConnection conn;
		try
		{
			conn = u.openConnection();
			size = conn.getLastModified();
			return size;
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return -1;
		}
	}

	public static long getLength(URL u)
	{
		int size = 0;
		HttpURLConnection conn = null;
		try
		{
			conn = (HttpURLConnection) u.openConnection();
			// conn.setRequestMethod("HEAD");
			conn.setRequestProperty("User-Agent", "Test");
			conn.setRequestProperty("Connection", "close");
			conn.getInputStream();
			size = conn.getContentLength();
			return size;
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return -1;
		}
		finally
		{
			conn.disconnect();
		}
	}

	public static int getLineCount(File file)
	{
		LineNumberReader lnr = null;
		try
		{
			lnr = new LineNumberReader(new FileReader(file));
			lnr.skip(Long.MAX_VALUE);
			return lnr.getLineNumber() + 1;
		}
		catch (final FileNotFoundException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
		}
		finally
		{
			closeQuietly(lnr);
		}
		return -1;
	}

	public static File getRandomTempFile()
	{
		return getRandomTempFile(null);
	}

	public static File getRandomTempFile(String parent)
	{
		File f = new File(parent, MathEssentials.newRandom() + ".temp");
		while (f.exists())
			f = getRandomTempFile();
		f.deleteOnExit();
		return f;
	}

	public static String getRunningClassPath(Class<?> c)
	{
		String res = c.getProtectionDomain().getCodeSource().getLocation()
			.getPath();
		try
		{
			res = URLDecoder.decode(res, "UTF-8");
		}
		catch (final UnsupportedEncodingException ex)
		{
		}
		return res;
	}

	public static boolean gotInternetConnection()
	{
		return gotInternetConnection(NETWORK_TIMEOUT_SHORT, 333);
	}

	public static boolean gotInternetConnection(int timeout,
		int allowedMillisAfterLastCheck)
	{
		if (System.currentTimeMillis() - lastInternetValidation <= allowedMillisAfterLastCheck) return true;
		else
		{
			HttpURLConnection urlConnection = null;
			try
			{
				URL url = new URL("http://clients3.google.com/generate_204");
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setInstanceFollowRedirects(false);
				urlConnection.setConnectTimeout(timeout);
				urlConnection.setReadTimeout(timeout);
				urlConnection.setUseCaches(false);
				urlConnection.getInputStream();
				if (urlConnection.getResponseCode() == 204)
				{
					lastInternetValidation = System.currentTimeMillis();
					return true;
				}
			}
			catch (Exception e)
			{
				return false;
			}
			finally
			{
				if (urlConnection != null)
				{
					urlConnection.disconnect();
				}
			}
			return false;
		}
	}

	// http://stackoverflow.com/questions/813710/java-1-6-determine-symbolic-links
	public static boolean isSymlink(File file) throws IOException
	{
		if (file == null) throw new NullPointerException("File must not be null");
		File canon;
		if (file.getParent() == null)
		{
			canon = file;
		}
		else
		{
			final File canonDir = file.getParentFile().getCanonicalFile();
			canon = new File(canonDir, file.getName());
		}
		return !canon.getCanonicalFile().equals(canon.getAbsoluteFile());
	}

	public static byte[] readBytes(File file) throws IOException
	{
		byte[] bytes;
		final InputStream is = new FileInputStream(file);
		final long length = file.length();
		if (length > Integer.MAX_VALUE)
		{
			is.close();
			return null;
		}
		bytes = new byte[(int) length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
			&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
		{
			offset += numRead;
		}
		if (offset < bytes.length)
		{
			SystemEssentials.Logger.log(Level.WARNING, new IOException(
				"Could not completely read file"));
		}
		is.close();
		return bytes;
	}

	public static String readString(InputStream inputStream)
	{
		return readString(inputStream, "UTF-8");
	}

	public static String readString(InputStream inputStream, String encoding)
	{
		if (inputStream != null)
		{
			Writer writer = new StringWriter();
			Reader reader = null;
			char[] buffer = new char[32768];
			try
			{
				reader = new BufferedReader(
					new InputStreamReader(inputStream, encoding), 32768);
				int n;
				while ((n = reader.read(buffer)) != -1)
				{
					writer.write(buffer, 0, n);
				}
			}
			catch (UnsupportedEncodingException e)
			{
			}
			catch (IOException e)
			{
			}
			finally
			{
				closeQuietly(reader);
				closeQuietly(writer);
				closeQuietly(inputStream);
			}
			return writer.toString();
		}
		else
		{
			return null;
		}
	}

	public static String readString(Class<?> c, String path)
	{
		return readString(c.getClassLoader(), path.charAt(0) != '/' ? path
			: new String(path.substring(1, path.length())), "UTF-8");
	}

	public static String readString(Class<?> c, String path, String encoding)
	{
		return readString(c.getClassLoader(), path.charAt(0) != '/' ? path
			: new String(path.substring(1, path.length())), encoding);
	}

	public static String readString(ClassLoader c, String path)
	{
		return readString(c, path, "UTF-8");
	}

	public static String readString(ClassLoader c, String path, String encoding)
	{
		InputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		final StringBuilder result = new StringBuilder();
		boolean first = true;
		try
		{
			fstream = c.getResourceAsStream(path);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in, encoding));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{
				if (!first)
				{
					strLine = "\n" + strLine;
				}
				else
				{
					first = false;
				}
				result.append(strLine);
			}
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
		}
		finally
		{
			closeQuietly(br);
			closeQuietly(in);
			closeQuietly(fstream);
		}
		return result.toString();
	}

	public static String readString(File file)
	{
		return readStringUntilLine(file, Long.MAX_VALUE, "UTF-8");
	}

	public static String readString(File file, String encoding)
	{
		return readStringUntilLine(file, Long.MAX_VALUE, encoding);
	}

	public static String[] readStrings(File file)
	{
		return StringEssentials.splitLines(readString(file));
	}

	public static String[] readStrings(InputStream inputStream)
	{
		return StringEssentials.splitLines(readString(inputStream));
	}

	public static String readStringUntilLine(Class<?> c, String path, int line)
	{
		return readStringUntilLine(c.getClassLoader(), path.charAt(0) != '/' ? path
			: new String(path.substring(1, path.length())), line);
	}

	public static String readStringUntilLine(ClassLoader c, String path, int line)
	{
		InputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		final StringBuilder result = new StringBuilder();
		boolean first = true;
		int counter = 1;
		try
		{
			fstream = c.getResourceAsStream(path);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{
				if (counter == line)
				{
					break;
				}
				else
				{
					counter++;
				}
				if (!first)
				{
					strLine = "\n" + strLine;
				}
				else
				{
					first = false;
				}
				result.append(strLine);
			}
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return null;
		}
		finally
		{
			closeQuietly(br);
			closeQuietly(in);
			closeQuietly(fstream);
		}
		return result.toString();
	}

	public static String readStringUntilLine(File file, long line, String encoding)
	{
		if (!file.exists()) return null;
		InputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		final StringBuilder result = new StringBuilder();
		long counter = 1;
		try
		{
			fstream = new FileInputStream(file);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in, encoding));
			String strLine;
			boolean first = true;
			while ((strLine = br.readLine()) != null)
			{
				if (counter == line)
				{
					break;
				}
				else
				{
					counter++;
				}
				if (!first)
				{
					strLine = "\n" + strLine;
				}
				else
				{
					first = false;
				}
				result.append(strLine);
			}
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			return null;
		}
		finally
		{
			closeQuietly(br);
			closeQuietly(in);
			closeQuietly(fstream);
		}
		return result.toString();
	}

	public static void removeBlankLines(File f)
	{
		ArrayList<String> res = new ArrayList<String>();
		String lines[] = readStrings(f);
		for (int i = 0; i < lines.length; i++)
		{
			if (!lines[i].trim().equals("")) res.add(lines[i]);
		}
		IOEssentials.write(StringEssentials.glue(res, "\n"), f);
	}

	public static boolean removeDuplicateLines(File f)
	{
		return write(StringEssentials.glue(
			StringEssentials.removeDuplicates(readStrings(f)), "\n"), f, false);
	}

	public static void removeFolder(File dir)
	{
		removeDirectory(dir);
	}

	public static void removeDirectory(File dir)
	{
		final String[] val = dir.list();
		if (val != null)
		{
			for (final String element : val)
			{
				final File f = dir.getParent() != null ? new File(dir.getParent()
					+ File.separator + dir.getName() + File.separator + element)
					: new File(dir.getName() + File.separator + element);
				if (f.isFile())
				{
					f.delete();
				}
				else
				{
					removeDirectory(f);
				}
			}
		}
		dir.delete();
	}

	/**
	 * @param File
	 * @param lines
	 *          Pass line numbers to be removed. They don't have to be in order.
	 */
	public static void removeLines(File f, int[] lines)
	{
		final ArrayList<String> entries = new ArrayList<String>();
		String[] ln = StringEssentials.splitLines(readString(f));
		OUTER: for (int i = 0; i < ln.length; i++)
		{
			for (int j = 0; j < lines.length; j++)
			{
				if (i + 1 == lines[j])
				{
					continue OUTER;
				}
			}
			entries.add(ln[i]);
		}
		ln = null;
		IOEssentials.write(StringEssentials.glue(entries, "\n"), f, false);
	}

	public static URL toURL(String path, boolean ignoreCache)
	{
		URL res = null;
		path = ignoreCache ? (path + "?t=" + System.currentTimeMillis()) : path;
		try
		{
			res = new URL(path);
		}
		catch (final MalformedURLException ex)
		{
			SystemEssentials.Logger.log(Level.WARNING, ex);
		}
		return res;
	}

	public static URL toURL(String path)
	{
		return toURL(path, true);
	}

	public static void updateInBackground(final File file, final URL url)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				update(file, url);
			}
		}).start();
	}

	public static boolean update(File file, URL url)
	{
		return update(file, url, FILE_UPDATE_POLICY_IF_POSSIBLE);
	}

	public static boolean update(File file, URL url, int updatePolicy)
	{
		SystemEssentials.Logger.log(Level.INFO, "Updating " + file + "...");
		if (updatePolicy == FILE_UPDATE_POLICY_NEVER && file.exists()) return true;
		if (!gotInternetConnection())
		{
			if (!file.exists() || updatePolicy == FILE_UPDATE_POLICY_FORCED) return false;
			else return true;
		}
		switch (updatePolicy)
		{
			case FILE_UPDATE_POLICY_NEVER:
				if (!file.exists())
				{
					if (url == null) return false;
					else return download(file, url);
				}
				else return true;
			case FILE_UPDATE_POLICY_IF_POSSIBLE:
				if (!file.exists())
				{
					if (url == null) return false;
					else return download(file, url);
				}
				else
				{
					if (url == null) return true;
					else
					{
						final long size = getLength(url);
						if (size != file.length() && size >= 1)
						{
							if (file.delete()) return download(file, url);
							else
							{
								final File temp = getRandomTempFile(file.getParent());
								if (download(temp, url))
								{
									final boolean v = copy(temp, file);
									temp.deleteOnExit();
									if (v && file.exists()) return true;
									else return false;
								}
								else
								{
									temp.deleteOnExit();
									return true;
								}
							}
						}
						else return true;
					}
				}
			case FILE_UPDATE_POLICY_STRICT: // obtain the file and if
				// it can be
				// replaced, enforce update
				if (!file.exists())
				{
					if (url == null) return false;
					else return download(file, url);
				}
				else
				{
					if (url == null) return true;
					else
					{
						final long size = getLength(url);
						long l_ = file.length();
						if (size != l_)
						{
							final File temp = getRandomTempFile(file.getParent());
							final boolean val = download(temp, url);
							if (val)
							{
								final boolean v = copy(temp, file);
								temp.delete();
								return v;
							}
							else
							{
								temp.delete();
								return file.exists() && file.length() == l_;
							}
						}
						else return true;
					}
				}
			case FILE_UPDATE_POLICY_FORCED:
				if (!file.exists())
				{
					if (url == null) return false;
					else return download(file, url);
				}
				else
				{
					if (url == null) return false;
					else
					{
						final long size = getLength(url);
						if (size != file.length())
						{
							final File temp = getRandomTempFile(file.getParent());
							final boolean val = download(temp, url);
							if (val)
							{
								final boolean v = copy(temp, file);
								temp.delete();
								return v;
							}
							temp.delete();
							return val;
						}
						else if (size == -1) return false;
						else return true;
					}
				}
			default:
				return update(file, url, FILE_UPDATE_POLICY_IF_POSSIBLE);
		}
	}

	public static boolean write(byte[] array, File file, boolean append)
	{
		boolean success = true;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		java.nio.channels.FileLock lock = null;
		if (file.getParentFile().mkdirs())
		{
			try
			{
				fos = new FileOutputStream(file, append);
				try
				{
					lock = fos.getChannel().lock();
				}
				catch (Exception tt)
				{
				}
				bos = new BufferedOutputStream(fos, 65536);
				bos.write(array);
				bos.flush();
				setFileCreationTime(file.getAbsolutePath());
				if (lock != null) lock.release();
			}
			catch (final IOException e)
			{
				SystemEssentials.Logger.log(Level.WARNING, e);
				success = false;
				if (lock != null) try
				{
					lock.release();
				}
				catch (IOException e1)
				{
				}
			}
			finally
			{
				closeQuietly(bos);
				closeQuietly(fos);
			}
		}
		return success;
	}

	public static boolean write(char[] text, File file, boolean append)
	{
		boolean success = true;
		FileOutputStream fstream = null;
		DataOutputStream out = null;
		BufferedWriter FW = null;
		java.nio.channels.FileLock lock = null;
		try
		{
			file.getParentFile().mkdirs();
			fstream = new FileOutputStream(file, append);
			try
			{
				lock = fstream.getChannel().lock();
			}
			catch (Exception tt)
			{
			}
			out = new DataOutputStream(fstream);
			FW = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			FW.write(text);
			setFileCreationTime(file.getAbsolutePath());
			if (lock != null) lock.release();
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			success = false;
			if (lock != null) try
			{
				lock.release();
			}
			catch (IOException e1)
			{
			}
		}
		finally
		{
			closeQuietly(FW);
			closeQuietly(out);
			closeQuietly(fstream);
		}
		return success;
	}

	public static boolean write(String data, File file)
	{
		return write(data, file, false);
	}

	public static boolean write(String data, File file, boolean append)
	{
		boolean success = true;
		if (file.getParentFile() != null)
		{
			file.getParentFile().mkdirs();
		}
		FileOutputStream fstream = null;
		DataOutputStream out = null;
		BufferedWriter FW = null;
		java.nio.channels.FileLock lock = null;
		try
		{
			fstream = new FileOutputStream(file, append);
			try
			{
				lock = fstream.getChannel().lock();
			}
			catch (Exception exxx)
			{
			}
			out = new DataOutputStream(fstream);
			FW = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			FW.write(data);
			setFileCreationTime(file.getAbsolutePath());
			if (lock != null) lock.release();
		}
		catch (final IOException e)
		{
			SystemEssentials.Logger.log(Level.WARNING, e);
			success = false;
			if (lock != null) try
			{
				lock.release();
			}
			catch (IOException e1)
			{
			}
		}
		finally
		{
			closeQuietly(FW);
			closeQuietly(out);
			closeQuietly(fstream);
		}
		return success;
	}
}
