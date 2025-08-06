/**
 * @param array The unsorted array.
 * This method takes unsorted array and conquers it down to a single item.
 * @return The sorted array.
 * */
public class MergeSort {
    public int[] sort(int[] array){
        if( array.length <= 1){
            return array;
        }
        int mid = array.length/2;
        int[] leftarray = new int[mid];
        int[] rightarray = new int[array.length - mid];

        for(int i = 0; i < mid; i++){
            leftarray[i] = array[i];
        }
        for(int i = mid; i < array.length; i++){
            rightarray[i - mid] = array[i];
        }

        sort(leftarray);
        sort(rightarray);

        merge(array, leftarray,rightarray);
        return array;
    }
    /**
     * @param array The array to hold merged values.
     * @param leftarray The left array to merge.
     * @param rightarray The right array to merge.
     * This method increments array with values in the correct position,
     * traversing the left and the right array.
     * @return The sorted array.
     * 
     * */
    private int[] merge(int[] array, int[] leftarray, int[] rightarray){
        int i = 0, j = 0 , k = 0;
        while(i < leftarray.length && j < rightarray.length){
            if(leftarray[i] < rightarray[j]){
                array[k++] = leftarray[i++];
            }
            else{
                array[k++] = rightarray[j++];
            } 
        }
        while( i < leftarray.length){
            array[k++] = leftarray[i++];
        }
        while( j < rightarray.length){
            array[k++] = rightarray[j++];
        }
        return array;
    }
}
