package copper.entities.items;

import copper.entities.Entity;

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
	
	/**
	 * Returns the index of the first Item not equal to null. If no such Item can be found, returns -1. 
	 * @param index
	 * @return
	 */
	
	public int getIndexOfFirstItem() {
		for (int i = 0; i < contents.length; i++) 
			if (contents[i] != null) 
				return i;
		return -1;
	}
	
	/**
	 * Returns the first Item not equal to null. If no such Item can be found, returns null. 
	 * @return
	 */
	
	public ItemType getFirstItem() {
		int index = getIndexOfFirstItem();
		if (index == -1) 
			return null;
		
		return contents[index];
	}
	
	/**
	 * Returns the length of the internal Item array. 
	 * @return
	 */
	
	public int size() {
		return contents.length;
	}
	
	/**
	 * Returns the amount of Items not equal to null. 
	 * @return
	 */
	
	public int itemsLeft() {
		int length = 0;

		for (int i = 0; i < contents.length; i++) 
			if (contents[i] != null) length++;
		
		return length;
	}
	
	/**
	 * Uses Item i on Entity e. If the Item runs out of uses it will also be removed from this Container. 
	 * @param i
	 * @param e
	 */
	
	public void use(int i, Entity e) {
		if (contents[i] == null) return;
		
		contents[i].use(e);
		if (contents[i].usesLeft <= 0) remove(i);
	}
	
}
