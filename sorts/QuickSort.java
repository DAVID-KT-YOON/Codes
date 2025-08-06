/**
 * @param array The unsorted array that will be partitioned and sorted.
 * @param start The beginning of the array. Initial call at index 0.
 * @param end The length of the each partitioned array -1.
 * @return Sorted array.
 * */
public class QuickSort {
    public int[] sort(int[] array, int start, int end){

        // base condition of invalid array

        if (start < end){
        
            int pivot = partition(array, start, end);
            sort(array, start, pivot - 1);
            sort(array, pivot + 1, end); 
        }
           
        return array;
    }

    /*purpose: get the pivot in the correct place, return the value to the mother call
        so the pivot can be used to make recursive calls. */ 
    /**
     * @param array The unsorted array that will be partitioned and sorted.
     * @param start The beginning of the array. Initial call at index 0.
     * @param end The length of the each partitioned array -1.
     * This method gets an array, chooses the end index as the pivot value,
     * and iteratively move values that are smaller than the pivot to the left of the pivot.
     * @return The index position of the pivot value.
     * */
    public int partition(int[] array, int start, int end){
        int pivotVal = array[end];
        int i = start -1;

        for(int j = start; j < end; j++){
            if(array[j] < pivotVal){
                int temp = array[++i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        int temp = array[++i];
        array[i] = array[end];
        array[end] = temp;

        return i;
    }
    
    
}
