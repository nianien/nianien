package com.nianien.core.comparator;

import java.util.Comparator;

/**
 * 字符串比较类,可满足四种排序方式:<br>
 * 按字典顺序,按字典逆序,按长度升序,按长度降序<br>
 * 无论哪种排序,null都将排在最后,亦即null的值随排序方式不同而不同
 * 
 * @author skyfalling
 * 
 */
public class StringComparator implements Comparator<String> {

	/**
	 * 排序方式
	 * 
	 * @author skyfalling
	 * 
	 */
	public enum SortType {

		/**
		 * 按字典顺序排序
		 */
		DictionaryAsc,
		/**
		 * 按字典逆序排序
		 */
		DictionaryDesc,
		/**
		 * 按长度升序排序
		 */
		LengthAsc,
		/**
		 * 按长度降序排序
		 */
		LengthDesc
	}

	/**
	 * 排序方式
	 */
	private SortType sortType;

	/**
	 * 获取排序方式
	 * 
	 * @return
	 */
	public SortType getSortType() {
		return sortType;
	}

	/**
	 * 设置排序方式
	 * 
	 * @param sortType
	 */
	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	/**
	 * 构造方法
	 * 
	 * @param sortType
	 *            排序方式
	 */
	public StringComparator(SortType sortType) {
		this.sortType = sortType;
	}

	/**
	 * 根据指定排序方式进行排序,null值排在最后
	 */
	@Override
	public int compare(String str1, String str2) {
		switch (sortType) {
		case DictionaryAsc:
			return compareD(str1, str2);
		case DictionaryDesc:
			return compareD(str2, str1);
		case LengthAsc:
			return compareL(str1, str2);
		case LengthDesc:
			return compareL(str2, str1);
		}
		return 0;
	}

	/**
	 * 按字典顺序排序
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	private int compareD(String str1, String str2) {
		return str1 == null ? 1 : str2 == null ? 1 : str1
				.compareToIgnoreCase(str2);
	}

	/**
	 * 按长度升序排序
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	private int compareL(String str1, String str2) {
		return str1 == null ? 1 : str2 == null ? 1 : str1.length()
				- str2.length();
	}

}
