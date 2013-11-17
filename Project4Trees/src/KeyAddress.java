

public class KeyAddress implements Comparable<KeyAddress> { 
	Integer key = 0;
	Integer address = 0;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String toString(){
		
		return key.toString() + "(" + address.toString()+")";
		
	}
	@Override
	public int compareTo(KeyAddress o) {
		return key.compareTo(o.key);
	}

}
