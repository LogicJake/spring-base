package com.scy.base.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CollectionUtils {
	public static <T> boolean isNullOrEmpty(Collection<T> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * Returns a new list containing the second list appended to the first list.
	 */
	public static <T> List<T> mergeLists(List<T> list1, List<T> list2) {
		List<T> merged = new LinkedList<T>();
		if (list1 != null) {
			merged.addAll(list1);
		}
		if (list2 != null) {
			merged.addAll(list2);
		}
		return merged;
	}
}
