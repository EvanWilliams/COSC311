
	public class Key implements Comparable<Key>{
	Integer key = 0;
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	public String toString(){
		
		return key.toString();
		
	}
	public int compareTo(Key o) {
		return key.compareTo(o.key);
	}

	
	}

	
	
	
	
