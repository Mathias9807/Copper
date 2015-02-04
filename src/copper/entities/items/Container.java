package copper.entities.items;

/**
 * Wrapper for an ItemType array. Handles adding and removing Items in the appropriate place. 
 * @author Mathias Johansson
 *
 */
public class Container {
	
	/**
	 * Main Item array. 
	 */
	private ItemType[] 	contents;
	
	/**
	 * Index of first empty index. Can exceed the contents array length. 
	 */
	private int 	firstEmpty;
	
	public Container(int size) {
		contents 	= new ItemType[size];
		firstEmpty 	= 0;
	}
	
	/**
	 * Tries to add ItemType to the ItemType array. Returns false if ItemType array is full, otherwise true. 
	 * @param item
	 * @return
	 */
	public boolean add(ItemType item) {
		if (firstEmpty >= contents.length) return false;
		contents[firstEmpty] = item;
		
		for (int i = firstEmpty + 1; i < contents.length; i++) {
			if (contents[i] == null) {
				firstEmpty = i;
				break;
			}
		}
		return true;
	}
	
	/**
	 * Removes an ItemType and sets the array entry to null. 
	 * @param index
	 */
	public void remove(int index) {
		contents[index] = null;
		
		if (index < firstEmpty) firstEmpty = index;
	}
	
	/**
	 * Gives ItemType from this Container to c. Does nothing if item is empty. 
	 * @param c
	 * @param item
	 */
	public void give(Container c, int item) {
		ItemType i = contents[item];
		
		if (i == null) return;
		
		c.add(i);
		remove(item);
	}
	
	/**
	 * Returns an ItemType from the ItemType array. Returns null if empty. 
	 * @param index
	 * @return
	 */
	public ItemType get(int index) {
		return contents[index];
	}
	
}
