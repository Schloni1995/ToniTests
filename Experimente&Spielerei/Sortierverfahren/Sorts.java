package Sortierverfahren;

public class Sorts
{
	static int temporär;
	int[] unsortiert;
	int[] zuSortieren;
	int[] sortiert;

	// INSERTSORT
	// public static void main(String[] args)
	// {
	// int[] unsortiert = { 24, 15, 12, -33 };
	// int[] sortiert = insertSort(unsortiert);
	//
	// for (int i = 0; i < sortiert.length; i++) {
	// System.out.print(sortiert[i] + ", ");
	//
	// }
	// }
	//
	// public static int[] insertSort(int[] sortieren)
	// {
	// for (int i = 1; i < sortieren.length; i++)
	// {
	// tempor�r = sortieren[i];
	// int j = i;
	// while (j > 0 && sortieren[j - 1] > tempor�r)
	// {
	// sortieren[j] = sortieren[j - 1];
	// j--;
	// }
	// sortieren[j] = tempor�r;
	// System.out.println(i);
	// }
	// return sortieren;
	// }

	// SELECTIONSORT
	// public static void main(String[] args)
	// {
	// int[]unsortiert={9,6,-2,19,0};
	// int[]sortiert = selectionsort(unsortiert);
	// for (int i = 0; i < sortiert.length; i++)//bei 5 Zahlen, muss er 5 Zahlen
	// sortiert haben um fertig zu sein
	// {
	// System.out.print(sortiert[i] + ", ");
	// }
	// }
	// public static int[]selectionsort(int[]sortiert)
	// {
	// for (int i = 0; i < sortiert.length - 1; i++)
	// {
	// for (int j = i + 1; j < sortiert.length; j++)
	// {
	// if (sortiert[i] > sortiert[j])
	// {
	// int temp = sortiert[i];
	// //Das ist unser Zielort f�r die kleinste
	// sortiert[i] = sortiert[j];
	// //Das ist unser Zielort f�r ausgewechselte Zahl
	// sortiert[j] = temp;
	// }
	// }
	// }
	// return sortiert;
	// }
	//
	//
	// BUBBLESORT
	// public static int[] bubblesort(int[]zuSortieren)
	// {
	// int tempor�r;
	// for(int i=1; i<zuSortieren.length; i++)
	// {
	// for(int j=0; j<zusortieren.length-i; j++)
	// {
	// if(zuSortieren[j]>zuSortieren[j+1])
	// {
	// tempor�r=zuSortieren[j];
	// zuSortieren[j]=zuSortieren[j+1];
	// zuSortieren[j+1]=tempor�r;
	// }
	//
	// }
	// }
	// return zuSortieren;
	// }
	//
	// public static void main(String[] args)
	// {
	//
	// int[] unsortiert={4,9,2,1,6,7};
	// int[] sortiert=bubblesort(unsortiert);
	// for (int i = 0; i < sortiert.length; i++)
	// {
	// System.out.print(sortiert[i] + ", ");
	// }
	// }
}
