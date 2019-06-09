package sossim.core;

public class Partition{
	  private int size;
	  private int baseAddress;
	  private boolean isFree;
	  
	  public Partition(int size,int baseAddress) {
	  this.size = size;
	  this.baseAddress = baseAddress;
	  isFree=true;
	  }

	  public int getSize() {
	  return size;
	  }

	  public int getBaseAddress() {
	    return baseAddress;
	  }

	  public boolean getIsFree() {
	    return isFree;
	  }

	  public void setSize(int size) {
	  	if(size>0)
	    this.size = size;
	  }

	  public void setBaseAddress(int baseAddress) {
      if(baseAddress >=0)
	    this.baseAddress = baseAddress;
	  }

	  public void setIsFree(boolean isFree) {
	    this.isFree = isFree;
	  }

	  public String toString() {
	    String s = "Base Address:" + baseAddress + " Size:" + size + "is free = " + isFree;;
	    return s;
	  }



	}
