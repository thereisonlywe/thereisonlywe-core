package info.thereisonlywe.core.essentials;

import info.thereisonlywe.core.search.SearchEngine;
import info.thereisonlywe.core.search.SearchModifier;
import info.thereisonlywe.core.toolkit.Base64Encoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.xml.sax.InputSource;

public class DataEssentials
{
	public static String serializeObjectToString(Object object)
	{
		try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
				arrayOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				gzipOutputStream);)
		{
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			return new String(Base64Encoder.encode(arrayOutputStream.toByteArray()));
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public static Object deserializeObjectFromString(String objectString)
	{
		try (ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
			Base64Encoder.decode(objectString));
			GZIPInputStream gzipInputStream = new GZIPInputStream(arrayInputStream);
			ObjectInputStream objectInputStream = new ObjectInputStream(
				gzipInputStream))
		{
			return objectInputStream.readObject();
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public static byte[] toBytes(char[] chars, String charsetName)
	{
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = Charset.forName(charsetName).encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
			byteBuffer.position(), byteBuffer.limit());
		Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
		Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
		return bytes;
	}

	public static char[] toChars(byte[] data, String charsetname)
	{
		// char[] charArr = (new String(data)).toCharArray();
		// return charArr;
		CharsetDecoder cd = Charset.availableCharsets().get(charsetname)
			.newDecoder();
		CharBuffer buffer = null;
		try
		{
			buffer = cd.decode(ByteBuffer.wrap(data));
			return buffer.array();
		}
		catch (CharacterCodingException e)
		{
		}
		return null;
	}

	public static <T> boolean contains(T[] a, Object o)
	{
		if (a == null) return false;
		for (final T element : a)
		{
			if (element.equals(o)) return true;
		}
		return false;
	}

	public static <T> Set<T> getDifference(Set<T> a, Set<T> b)
	{
		final Set<T> e = new TreeSet<T>(a);
		e.removeAll(b);
		return e;
	}

	/**
	 * CAUTION! Mutates objects. Clone parameters before passing in, if you wish
	 * to use them again in their original form.
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static <T> List<T> getIntersection(List<T> l1, List<T> l2)
	{
		l1.retainAll(l2);
		return l1;
	}

	public static <T> Set<T> getIntersection(Set<T> a, Set<T> b)
	{
		final Set<T> d = new TreeSet<T>(a);
		d.retainAll(b);
		return d;
	}

	public static <T> List<T> getNegationOfIntersection(List<T> l1, List<T> l2)
	{
		final List<T> u = getUnion(l1, l2);
		l1.retainAll(l2);
		u.removeAll(l1);
		return u;
	}

	public static Object getObject(String objectName,
		Class<?>[] classesToSearchFor)
	{
		if (classesToSearchFor == null) return null;
		String tmp = objectName;
		tmp = tmp.toUpperCase(Locale.ENGLISH);
		tmp = tmp.replace(" ", "_");
		for (Class<?> element : classesToSearchFor)
		{
			Field f = null;
			Object o = null;
			try
			{
				f = element.getField(tmp);
				if (f != null)
				{
					o = f.get(f.getClass());
				}
			}
			catch (SecurityException e)
			{
				continue;
			}
			catch (NoSuchFieldException e)
			{
				continue;
			}
			catch (IllegalArgumentException e)
			{
				continue;
			}
			catch (IllegalAccessException e)
			{
				continue;
			}
			if (o != null) return o;
		}
		for (Class<?> element : classesToSearchFor)
		{
			Object o = null;
			Field fs[] = element.getFields();
			for (Field element2 : fs)
			{
				try
				{
					o = element2.get(element2.getClass());
				}
				catch (IllegalArgumentException e)
				{
					continue;
				}
				catch (IllegalAccessException e)
				{
					continue;
				}
				if (o != null && o.toString().equalsIgnoreCase(objectName)) return o;
			}
		}
		return null;
	}

	public static <T> List<T> getReverse(Set<T> a)
	{
		final List<T> list = new ArrayList<T>(a);
		java.util.Collections.reverse(list);
		return list;
	}

	public static <T> List<T> getUnion(List<T> l1, List<T> l2)
	{
		l1.removeAll(l2);
		l1.addAll(l2);
		return l1;
	}

	public static <T> Set<T> getUnion(Set<T> a, Set<T> b)
	{
		final Set<T> c = new TreeSet<T>(a);
		c.addAll(b);
		return c;
	}

	public static <T> int indexOf(T[] array, T element)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i].equals(element)) return i;
		}
		return -1;
	}

	public static int indexOf(String[] array, String element,
		SearchModifier modifier)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (SearchEngine.search(array[i], element, modifier)) return i;
		}
		return -1;
	}

	// Enlarges array and inserts value into given position
	public static String[] insertAtBeginning(String[] array, String value)
	{
		final String[] result = new String[array.length + 1];
		result[0] = value;
		for (int i = 1; i < result.length; i++)
		{
			result[i] = array[i - 1];
		}
		return result;
	}

	public static <T> List<T> removeElements(T[] array, int[] elementsInOrder)
	{
		List<T> list = new ArrayList<T>(Arrays.asList(array));
		for (int i = 0; i < elementsInOrder.length; i++)
		{
			list.remove(elementsInOrder[i] - i);
		}
		return list;
	}

	public static <T> List<T> removeNullElements(T[] array)
	{
		ArrayList<T> list = new ArrayList<T>();
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] != null) list.add(array[i]);
		}
		return list;
	}

	public static <T> List<T> removeNullElements(List<T> array)
	{
		ArrayList<T> list = new ArrayList<T>();
		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i) != null) list.add(array.get(i));
		}
		return list;
	}

	public static InputSource toInputSource(String str)
	{
		final InputSource s = new InputSource(new StringReader(str));
		return s;
	}

	public static InputStream toInputStream(String str, String encoding)
	{
		byte[] bytes = null;
		try
		{
			bytes = str.getBytes(encoding);
		}
		catch (final UnsupportedEncodingException ex)
		{
		}
		return new ByteArrayInputStream(bytes);
	}

	public static int[] toIntArray(int number)
	{
		final char[] c = (number + "").toCharArray();
		final int[] result = new int[c.length];
		for (int i = 0; i < c.length; i++)
		{
			result[i] = Integer.parseInt(c[i] + "");
		}
		return result;
	}

	public static double[] toDoubleArray(double number)
	{
		final char[] c = (number + "").toCharArray();
		final double[] result = new double[c.length];
		for (int i = 0; i < c.length; i++)
		{
			result[i] = Double.parseDouble(c[i] + "");
		}
		return result;
	}

	public static int[] toIntArray(Integer[] val)
	{
		final int[] result = new int[val.length];
		System.arraycopy(val, 0, result, 0, result.length);
		return result;
	}

	public static int[] toIntArray(List<Integer> integers)
	{
		final int[] ret = new int[integers.size()];
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = integers.get(i).intValue();
		}
		return ret;
	}

	public static int[] toIntArray(long number)
	{
		final char[] c = (number + "").toCharArray();
		final int[] result = new int[c.length];
		for (int i = 0; i < c.length; i++)
		{
			result[i] = Integer.parseInt(c[i] + "");
		}
		return result;
	}

	public static int[] toIntArray(String[] s)
	{
		final int[] result = new int[s.length];
		for (int i = 0; i < s.length; i++)
		{
			result[i] = Integer.parseInt(s[i]);
		}
		return result;
	}

	public static double[] toDoubleArray(String[] s)
	{
		final double[] result = new double[s.length];
		for (int i = 0; i < s.length; i++)
		{
			result[i] = Double.parseDouble(s[i]);
		}
		return result;
	}

	public static Integer[] toIntegerArray(String[] s)
	{
		final Integer[] result = new Integer[s.length];
		for (int i = 0; i < s.length; i++)
		{
			result[i] = Integer.parseInt(s[i]);
		}
		return result;
	}

	public static List<String> toList(String[] s)
	{
		return Arrays.asList(s);
	}

	public static long[] toLongArray(long number)
	{
		final char[] c = (number + "").toCharArray();
		final long[] result = new long[c.length];
		for (int i = 0; i < c.length; i++)
		{
			result[i] = Long.parseLong(c[i] + "");
		}
		return result;
	}

	public static long[] toLongArray(String[] s)
	{
		final long[] result = new long[s.length];
		for (int i = 0; i < s.length; i++)
		{
			result[i] = Long.parseLong(s[i]);
		}
		return result;
	}

	public static String[] toStringArray(int[] array)
	{
		final String[] res = new String[array.length];
		for (int i = 0; i < array.length; i++)
		{
			res[i] = String.valueOf(array[i]);
		}
		return res;
	}

	public static <T> String[] toStringArray(T[] array)
	{
		final String[] res = new String[array.length];
		for (int i = 0; i < array.length; i++)
		{
			res[i] = array[i].toString();
		}
		return res;
	}

	public static <T> ArrayList<String> toStringArrayList(ArrayList<T> arrayList)
	{
		final ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < arrayList.size(); i++)
		{
			result.add(String.valueOf(arrayList.get(i)));
		}
		return result;
	}

	public static <T> ArrayList<String> toStringArrayList(List<T> arrayList)
	{
		final ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < arrayList.size(); i++)
		{
			result.add(String.valueOf(arrayList.get(i)));
		}
		return result;
	}

	public static <T> String define(T classInstance)
	{
		if (classInstance == null) return null;
		final StringBuilder sb = new StringBuilder();
		Field[] fields = classInstance.getClass().getFields();
		sb.append("-" + classInstance.toString() + "-\n");
		for (int i = 0; i < fields.length; i++)
		{
			if (fields[i].isEnumConstant())
			{
				continue;
			}
			String name = fields[i].getName();
			sb.append(StringEssentials.camelCaseToStartCase(name));
			sb.append(": ");
			try
			{
				sb.append(classInstance.getClass().getField(name).get(classInstance));
			}
			catch (IllegalArgumentException e)
			{
				SystemEssentials.Logger.log(Level.WARNING, e);
			}
			catch (SecurityException e)
			{
				SystemEssentials.Logger.log(Level.WARNING, e);
			}
			catch (IllegalAccessException e)
			{
				SystemEssentials.Logger.log(Level.WARNING, e);
			}
			catch (NoSuchFieldException e)
			{
				SystemEssentials.Logger.log(Level.WARNING, e);
			}
			if (i + 1 < fields.length)
			{
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static String define(String objectName, Class<?>[] classesToSearchFor)
	{
		return define(getObject(objectName, classesToSearchFor));
	}
}
