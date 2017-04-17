package asteroids.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Not for usage in Part II of the assignment, will be used in part III
 * @author Martijn
 *
 * @param <K>
 * @param <V>
 * 
 * @Invar 	At any given moment in time all the keys and all
 * 			the values are unique (a bijection between K and V exists)
 */
public class BMap<K, V> implements BijectionMap<K, V>{
	
	public BMap(){
		
	}
	
	/**
	 * return the key mapped to the value V
	 */
	public K getKey(V v){
		if(!this.containsValue(v))
			throw new IllegalArgumentException();
		return valueKeyMap.get(v);
	}
	
	/**
	 * return the value mapped to the key K
	 */
	public V getValue(K k){
		if(!this.containsKey(k))
			throw new IllegalArgumentException();
		return keyValueMap.get(k);
	}
	
	/**
	 * Basic checker, returns true if the map contains key K
	 */
	public boolean containsKey(K k){
		return keyValueMap.containsKey(k);
	}
	
	/**
	 * Basic checker, returns true if the Map contains value V
	 */
	public boolean containsValue(V v){
		return valueKeyMap.containsKey(v);
	}
	
	/**
	 * Puts the value K and V in the Bmap
	 * @throws  IllegalArgumentException()
	 * 			thrown if the provided key or value are already contained within the Bmap
	 * 			|containsKey(k)||containsValue(v)
	 */
	public void put(K k, V v){
		if(this.containsKey(k)||this.containsValue(v))
			throw new IllegalArgumentException();
		
		else{
			this.keyValueMap.put(k, v);
			this.valueKeyMap.put(v, k);
		}
	}
	
	/**
	 * Removes the given key k from the map
	 * @throws 	IllegalArgumentException
	 * 			thrown if no key value k can be found in the map
	 * 			|containsKey(K)
	 * @post	the key value pair (K, V) is removed from the map
	 * 			|(new)containsKey(k) == false
	 */
	public void removeKey(K k)throws IllegalArgumentException{
		if(!this.containsKey(k))
			throw new IllegalArgumentException();
		V value = this.getValue(k);
		keyValueMap.remove(k);
		valueKeyMap.remove(value);
	}

	/**
	 * Removes the given key k from the map
	 * @throws 	IllegalArgumentException
	 * 			thrown if no key value k can be found in the map
	 * 			|containsKey(K)
	 * @post	the key value pair (K, V) is removed from the map
	 * 			|(new)containsValue(v) == false
	 */
	public void removeValue(V v) throws IllegalArgumentException{
		if(!this.containsValue(v))
			throw new IllegalArgumentException();
		K key = this.getKey(v);
		valueKeyMap.remove(v);
		keyValueMap.remove(key);
	}
	
	public Set<K> keySet(){
		return new HashSet<K>(keyValueMap.keySet());
	}
	
	public Set<V> values(){
		return new HashSet<V>(valueKeyMap.keySet());
	}
	


	private Map<K, V> keyValueMap = new HashMap<K, V>();
	private Map<V, K> valueKeyMap = new HashMap<V, K>();
	
}