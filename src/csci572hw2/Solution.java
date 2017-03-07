package csci572hw2;
import java.util.HashMap;

public class Solution<K, V> {
	HashMap<K, Node<K, V>> map;
	Node<K, V> dummyNode;
	int size, limit;
	//Constructor
	public Solution(int limit) {
		map = new HashMap<K, Node<K, V>>();
		dummyNode = new Node(null, null);
		dummyNode.next  = dummyNode;
		dummyNode.prev = dummyNode;
		size = 0;
		this.limit = limit;
	} 
	public void set(K key, V value) {
		if (map.containsKey(key)) {
			map.get(key).value = value;
			reorder(map.get(key));
		} else {
			Node<K, V> node = new Node(key, value);
			Node<K, V> oldHead = dummyNode.next;
			dummyNode.next = node;
			node.prev = dummyNode;
			node.next = oldHead;
			oldHead.prev = node;
			//Do not forget to update hashmap
			map.put(key, node);
			size++;
			if (size > limit) {
				Node<K, V> tail = dummyNode.prev;
				dummyNode.prev = tail.prev;
				tail.prev.next = dummyNode;
				tail.next = null;
				tail.prev = null;
				//Do not forget to update hashmap
				map.remove(tail.key);
				size--;
			}
		}
	}
	public V get(K key) {
		Node<K, V> temp = map.get(key);
		reorder(temp);
		return temp.value;
	}
	private void reorder(Node<K, V> node) {
		Node<K, V> prev = node.prev;
		Node<K, V> next = node.next;
		prev.next = next;
		next.prev = prev;
		Node<K, V> oldHead = dummyNode.next;
		dummyNode.next = node;
		node.prev = dummyNode;
		oldHead.prev = node;
		node.next = oldHead;
	}
}
class Node<K, V> {
	K key;
	V value;
	Node<K, V> next, prev;
	public Node(K key, V value) {
		this.key = key;
		this.value = value;
		prev = null;
		next = null;
	}
}
