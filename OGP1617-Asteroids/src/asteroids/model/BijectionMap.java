package asteroids.model;
/**
 * BIJECTION MAP INTERFACE FOR USE IN PART III OF ASSIGNMENT
 * not yet implemented in the assignment part II
 * @author Martijn
 *
 * @param <K>
 * @param <V>
 */
public interface BijectionMap<K, V> {
	
		/**
		 * Add an element to the Map
		 * @param k the key value
		 * @param v the value value
		 */
		public void put(K k, V v) throws IllegalArgumentException;
		
		/**
		 * remove a certain key and its corresponding value from the map
		 * @param k
		 */
		public void removeKey(K k) throws IllegalArgumentException;
		/**
		 * remove a certain value and its corresponding key
		 * @param v
		 */
		public void removeValue(V v);
		
		/**
		 * Get the corresponding key value for the given Value
		 * @param v
		 * @return
		 */
		public K getKey(V v);
		
		/**
		 * Get the corresponding value for the given key
		 * @param v
		 * @return
		 */
		public V getValue(K v);
		
		/**
		 * checks if the Map contains a certain key K
		 * @param k
		 * @return
		 */
		public boolean containsKey(K k);
		
		/**
		 * checks if the Map contains a value V
		 * @param v
		 * @return
		 */
		public boolean containsValue(V v);
		
	}
	
	


