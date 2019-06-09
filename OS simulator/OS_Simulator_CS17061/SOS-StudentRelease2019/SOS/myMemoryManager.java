package sossim.core;

import java.util.ArrayList;

public class myMemoryManager implements IMemoryManager {
  protected int memorySize;
  protected ArrayList<Partition> partitions;
  private int counter = 0;
  
  
  public myMemoryManager(int size) {
    memorySize = size;
    partitions = new ArrayList();
    partitions.add(new Partition(memorySize, 0));
  }
  public void deleteProcessAtAddress(int address) {
    //find partition's index with that address
    int position = -1;
    for (int i = 0; i < partitions.size(); i++) {
      if (address == ((Partition)partitions.get(i)).getBaseAddress()) {
        position = i;
      }
    }
    //delete the process with that index
    if (position >= 0) {
      ((Partition)partitions.get(position)).setIsFree(true);
      coaless(position);
      //if (position > 0) coaless(position - 1);
    }
  }

  public int addProcess(int size) {
    int position = findPartition(size);
    if (position == -1) return -1;
    if (((Partition)partitions.get(position)).getSize() > size) {
      //seperate the partition in two parts where one is occupied by the process, the other is set free
      int nextPartitionSize = ((Partition)partitions.get(position)).getSize() - size;
      int nextPartitionAddress = ((Partition)partitions.get(position)).getBaseAddress() + size;
      //add the new partition above the old one
      partitions.add(position + 1, new Partition(nextPartitionSize,nextPartitionAddress));
      //set the size of the partition to the size of the process
      ((Partition)partitions.get(position)).setSize(size);
      //occupy it
      ((Partition)partitions.get(position)).setIsFree(false);
    } else {
      ((Partition)partitions.get(position)).setIsFree(false);
    }
    return ((Partition)partitions.get(position)).getBaseAddress();
  }

  private void coaless(int position) {
    //check not to pass the array's size
       if(position > 0 && position < partitions.size()-1){
       //coaless this partition with the above one if both free
        if (((partitions.get(position)).getIsFree()==true) && ((partitions.get(position + 1)).getIsFree()==true)) { 
          int newSize = (partitions.get(position)).getSize() + (partitions.get(position + 1)).getSize();
          (partitions.get(position)).setSize(newSize);
          partitions.remove(position + 1);
        }
        //check this partition with the below one, if both free
        if (((partitions.get(position)).getIsFree()==true) && ((partitions.get(position - 1)).getIsFree()==true)) { 
          int newSize = (partitions.get(position)).getSize() + (partitions.get(position - 1)).getSize();
          (partitions.get(position)).setSize(newSize);
          partitions.remove(position - 1);
        }
     }
  }
  
  public void compact(SOS os)
  {
    for (int i = 0; i < partitions.size() - 1; i++) {
      if (partitions.get(i).getIsFree()==true)
      {
        int p1AddressBeforeCompact = partitions.get(i).getBaseAddress();
        int p2AddressBeforeCompact = partitions.get(i + 1).getBaseAddress();
        int p1SizeBeforeCompact = partitions.get(i).getSize();
        int p2SizeBeforeCompact = partitions.get(i + 1).getSize();
        //delete the process at poistion i+1
        partitions.get(i+1).setIsFree(true);
        coaless(i+1);
         //add the process i+1 to the position the first process had, with that size
         if (partitions.get(i).getSize() > p2SizeBeforeCompact) {
          //change the size and address of the new partition above
          int partitionSizeAfterCompact = partitions.get(i).getSize() - p2SizeBeforeCompact;
          int partitionAddressAfterCompact = partitions.get(i).getBaseAddress() + p2SizeBeforeCompact;
          // add a partition above with whats left from size and address
          partitions.add(i + 1, new Partition(partitionSizeAfterCompact,partitionAddressAfterCompact));
          //set the size of this partition to the i+1 partition's size
          partitions.get(i).setSize(p2SizeBeforeCompact);
          partitions.get(i).setIsFree(false);
          
        }
      }
    }
  }
  


  public float calcFragmentation()
  {
    float free = 0.0F; // bytes free
    float freemax = 0.0F; // size of largest free block
    for (int i = 0; i < partitions.size(); i++) {
      if (((Partition)partitions.get(i)).getIsFree()==true) {
        free += ((Partition)partitions.get(i)).getSize(); // size of byte free is increased when a partition gets released
        if (freemax < ((Partition)partitions.get(i)).getSize()) freemax = ((Partition)partitions.get(i)).getSize(); // change the biggest block if the new size that is set free is bigger 
      }
    }
    //formula to calculate framgmentation
    return 100.0F * (free - freemax) / free;
  }
  
  public int getNumberOfPartitionsInMemory()
  {
    return partitions.size();
  }
  
  public int getSizeOfPartitionInMemory(int i) {
    return ((Partition)partitions.get(i)).getSize();
  }
  
  public int getAddressOfPartitionInMemory(int i) {
    return ((Partition)partitions.get(i)).getBaseAddress();
  }
  
  //algorithm for nextfit
  public int findPartition(int size){
    // the loop starts from the counter which is initialized to zero, then goes to the next partition after allocation
      for (int i = counter; i < partitions.size(); i++) {
        System.out.println("starting to look from " + counter);
         Partition p = partitions.get(i);
         if ((p.getIsFree()==true) && (p.getSize() >= size)){
            counter = i+1;
            return i;
         }
       }
    return -1;
  }
  
  public String toString() {
    return "Next Fit";
  }
  
  
  
  
  
}
