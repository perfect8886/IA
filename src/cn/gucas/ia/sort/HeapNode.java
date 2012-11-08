package cn.gucas.ia.sort;

public class HeapNode<T> implements Comparable<HeapNode<T>> {
	private T value;
	private Object[] others;

	public HeapNode(T value, Object[] others) {
		this.value = value;
		this.others = others;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Object[] getOthers() {
		return others;
	}

	public void setOthers(Object[] others) {
		this.others = others;
	}

	public boolean equals(Object obj) {
		if (obj instanceof HeapNode) {
			HeapNode<T> node = (HeapNode<T>) obj;
			if (0 == this.compareTo(node)) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	public int hashCode() {
		if (null == this.value) {
			return 0;
		}
		return String.valueOf(value).hashCode();
	}

	public int compareTo(HeapNode<T> node) {
		T obj = node.getValue();
		if (value instanceof String) {
			return ((String) value).compareTo((String) obj);
		} else if (value instanceof Character) {
			return (String.valueOf(value)).compareTo(String.valueOf(obj));
		} else if ((value instanceof Byte)) {
			if (((Byte) value) > ((Byte) obj)) {
				return 1;
			} else if (((Byte) value) == ((Byte) obj)) {
				return 0;
			} else {
				return -1;
			}
		} else if (value instanceof Short) {
			if (((Short) value) > ((Short) obj)) {
				return 1;
			} else if (((Short) value) == ((Short) obj)) {
				return 0;
			} else {
				return -1;
			}
		} else if (value instanceof Integer) {
			if (((Integer) value) > ((Integer) obj)) {
				return 1;
			} else if (((Integer) value) == ((Integer) obj)) {
				return 0;
			} else {
				return -1;
			}
		} else if (value instanceof Float) {
			if (((Float) value) > ((Float) obj)) {
				return 1;
			} else if (((Float) value) == ((Float) obj)) {
				return 0;
			} else {
				return -1;
			}
		} else if (value instanceof Long) {
			if (((Long) value) > ((Long) obj)) {
				return 1;
			} else if (((Long) value) == ((Long) obj)) {
				return 0;
			} else {
				return -1;
			}
		} else if (value instanceof Double) {
			if (((Double) value) > ((Double) obj)) {
				return 1;
			} else if (((Double) value) == ((Double) obj)) {
				return 0;
			} else {
				return -1;
			}
		}
		return 0;
	}
}