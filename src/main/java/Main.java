import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum sortType{ normal, reverse }
enum dataType{ Integer, String }
public class Main {
    public static void main(String[] args) {
        ArrayList<String> _filePathes = new ArrayList<>();
        sortType _sortType = sortType.normal;
        dataType _dataType = dataType.String;
        String _name = "";
        for (String str : args) {
            if(str.equals("-a") || str.equals("-d"))
                _sortType = str.equals("-a") ? sortType.normal : sortType.reverse;
            else if (str.equals("-i") ||str.equals("-s"))
                _dataType = str.equals("-i") ? dataType.Integer : dataType.String;
            else if(str.contains(".txt"))
                _filePathes.add(str);
            else if(_name.equals("")){
                _name = str;
            }
        }

        if(_name.equals("")) {
            System.out.println("Please, restart program and enter name");
            return;
        }
        if(_dataType == dataType.String){
            ArrayList<String> strings = new ArrayList<>();
            for (String filePath: _filePathes) {
                strings.addAll(GetStringArray(filePath));
            }
            String[] resultArray = strings.toArray(new String[0]);

            mergeSort(resultArray);
            if(_sortType == sortType.reverse)
                ReverseArray(resultArray);
            System.out.println(Arrays.toString(resultArray));
            saveArray(resultArray, _name);
        }
        else  if(_dataType == dataType.Integer){
            ArrayList<Integer> integers = new ArrayList<>();
            for (String filePath: _filePathes) {
                integers.addAll(GetIntegerArray(filePath));
            }
            int[] resultArray = integers.stream().mapToInt(i->i).toArray();

            int[] result = mergeSort(resultArray);
            if(_sortType == sortType.reverse)
                ReverseArray(result);
            System.out.println(Arrays.toString(result));
            saveArray(result, _name);
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter any string");
        scan.hasNext();
    }

    public static void ReverseArray(int[] array){
        for(int i = 0; i < array.length / 2; i++) {
            // Swapping the elements
            int j = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = j;
        }
    }

    public static void ReverseArray(String[] array){
        for(int i = 0; i < array.length / 2; i++) {
            // Swapping the elements
            String j = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = j;
        }
    }

    public static void saveArray(Object array, String name)
    {
        Object[] objects;
        Class  ofArray = array.getClass().getComponentType();
        if (ofArray.isPrimitive()) {
            List ar = new ArrayList();
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                ar.add(Array.get(array, i));
            }
            objects = ar.toArray();
        }
        else {
            objects = (Object[]) array;
        }
        try(FileWriter fw = new FileWriter(name+".txt", false)){
            for (Object i : objects) {
                fw.append(i.toString() + "\n");
            }
            System.out.println("Saved!");
        }
        catch (IOException e) {
            System.out.println("Save failed!" + e.getMessage());
        }
    }

    public static ArrayList<String> GetStringArray(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String s;
            ArrayList<String> list = new ArrayList<String>();
            while((s=br.readLine())!=null){
                list.add(s.trim());
            }
            br.close();
            return list;
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }
    public static ArrayList<Integer> GetIntegerArray(String fileName){


        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String s;
            ArrayList<Integer> list = new ArrayList<>();
            while((s=br.readLine())!=null){
                try{
                    list.add(Integer.parseInt(s.trim()));
                }
                catch (Exception ex){
                    System.out.println("error file " + ex.getMessage()+ "\n");
                }
            }
            br.close();
            return list;
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }

    }
    public static int[] mergeSort(int[] sortArr) {
        int[] buffer1 = Arrays.copyOf(sortArr, sortArr.length);
        int[] buffer2 = new int[sortArr.length];
        return mergeSortInner(buffer1, buffer2, 0, sortArr.length);
    }

    public static int[] mergeSortInner(int[] buffer1, int[] buffer2, int startIndex, int endIndex) {
        if (startIndex >= endIndex - 1) {
            return buffer1;
        }

        int middle = startIndex + (endIndex - startIndex) / 2;
        int[] sorted1 = mergeSortInner(buffer1, buffer2, startIndex, middle);
        int[] sorted2 = mergeSortInner(buffer1, buffer2, middle, endIndex);

        int index1 = startIndex;
        int index2 = middle;
        int destIndex = startIndex;
        int[] result = sorted1 == buffer1 ? buffer2 : buffer1;
        while (index1 < middle && index2 < endIndex) {
            result[destIndex++] = sorted1[index1] < sorted2[index2]
                    ? sorted1[index1++] : sorted2[index2++];
        }
        while (index1 < middle) {
            result[destIndex++] = sorted1[index1++];
        }
        while (index2 < endIndex) {
            result[destIndex++] = sorted2[index2++];
        }
        return result;
    }

    public static void mergeSort(String[] nameGo) {
        if (nameGo.length > 1) {
            String[] leftGo = new String[nameGo.length / 2];
            String[] rightGo = new String[nameGo.length - nameGo.length / 2];
            for (int so = 0; so < leftGo.length; so++) {
                leftGo[so] = nameGo[so];
            }
            for (int ki = 0; ki < rightGo.length; ki++) {
                rightGo[ki] = nameGo[ki + nameGo.length / 2];
            }
            mergeSort(leftGo);
            mergeSort(rightGo);
            merge(nameGo, leftGo, rightGo);
        }
    }
    public static void merge(String[] nameH, String[] leftH, String[] rightH) {
        int as = 0;
        int bs = 0;
        for (int i = 0; i < nameH.length; i++) {
            if (bs >= rightH.length || (as < leftH.length && leftH[as].compareToIgnoreCase(rightH[bs]) < 0)) {
                nameH[i] = leftH[as];
                as++;
            } else {
                nameH[i] = rightH[bs];
                bs++;
            }
        }
    }
}