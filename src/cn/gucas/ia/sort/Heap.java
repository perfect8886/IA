package cn.gucas.ia.sort;

public class Heap<T> {
	HeapNode<T>[] heap;
	int heapSize;

	public Heap(HeapNode<T>[] heapNodes) {
		this.heap = heapNodes;
	}

	public final int parent(int i) {
		return (i + 1) >> 1 - 1;
	}

	public final int left(int i) {
		return i << 1 + 1;
	}

	public final int right(int i) {
		return i << 1 + 2;
	}

	public final void exchange(int i, int j) {
		HeapNode<T> temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}

	public void maxHeapify(int i) {
		int l = left(i);
		int r = right(i);
		int largest = 0;
		if (l <= heapSize && heap[l].compareTo(heap[i]) > 0) {
			largest = l;
		} else {
			largest = i;
		}
		if (r <= heapSize && heap[r].compareTo(heap[largest]) > 0) {
			largest = r;
		}
		if (largest != i) {
			exchange(largest, i);
			maxHeapify(largest);
		}
	}

	public void buildMaxHeap() {
		heapSize = heap.length;
		for (int i = heap.length / 2 - 1; i >= 0; --i) {
			maxHeapify(i);
		}
	}
}