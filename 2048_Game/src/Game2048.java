import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.text.ChangedCharSetException;



public class Game2048 {
	static int N=4;
	static int a[][] = {{4,2,0,2},
		 				{0,0,0,0},
		 				{0,0,0,0},
		 				{0,2,0,0}};
	static long score = 0;
	
	//*************************************************************RightClick***********************
	static void rigthClick(){
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i=0;i<N;i++){
			boolean flag=false;
			for(int j=N-1;j>=0;j--){
				if(a[i][j]!=0){
					if(!al.isEmpty()){
						if(al.get(al.size()-1)==a[i][j] && flag==true){
							flag=false;
							int temp =2* al.get(al.size()-1);
							al.remove(al.size()-1);
							al.add(temp);
						}
						else{
							flag=true;
							al.add(a[i][j]);
						}
					}
					else{
						flag=true;
						al.add(a[i][j]);
					}
				}
			}
			int k=0;
			for(int j=N-1;j>=0;j--,k++)
				a[i][j] = k<al.size()?al.get(k):0;
			al.clear();
		}
		randomGenrator();
		printGame();
	}
	//*************************************************************LiftClick***********************
	static void leftClick(){
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i=0;i<N;i++){
			boolean flag=false;
			for(int j=0;j<N;j++){
				if(a[i][j]!=0){
					if(!al.isEmpty()){
						if(al.get(al.size()-1)==a[i][j] && flag==true){
							flag=false;
							int temp =2* al.get(al.size()-1);
							al.remove(al.size()-1);
							al.add(temp);
						}
						else{
							flag=true;
							al.add(a[i][j]);
						}
					}
					else{
						flag=true;
						al.add(a[i][j]);
					}
				}
			}
			int k=0;
			for(int j=0;j<N;j++,k++)
				a[i][j] = k<al.size()?al.get(k):0;
			al.clear();
		}
		randomGenrator();
		printGame();
	}
	//*************************************************************UpClick***********************
	static void upClick(){
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i=0;i<N;i++){
			boolean flag=false;
			for(int j=0;j<N;j++){
				if(a[j][i]!=0){
					if(!al.isEmpty()){
						if(al.get(al.size()-1)==a[j][i] && flag==true){
							flag=false;
							int temp =2* al.get(al.size()-1);
							al.remove(al.size()-1);
							al.add(temp);
						}
						else{
							flag=true;
							al.add(a[j][i]);
						}
					}
					else{
						flag=true;
						al.add(a[j][i]);
					}
				}
			}
			int k=0;
			for(int j=0;j<N;j++,k++)
				a[j][i] = k<al.size()?al.get(k):0;
			al.clear();
		}
		randomGenrator();
		printGame();
	}
	
	//*************************************************************DownClick***********************
	static void downClick(){
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i=0;i<N;i++){
			boolean flag=false;
			for(int j=N-1;j>=0;j--){
				if(a[j][i]!=0){
					if(!al.isEmpty()){
						if(al.get(al.size()-1)==a[j][i] && flag==true){
							flag=false;
							int temp =2* al.get(al.size()-1);
							al.remove(al.size()-1);
							al.add(temp);
						}
						else{
							flag=true;
							al.add(a[j][i]);
						}
					}
					else{
						flag=true;
						al.add(a[j][i]);
					}
				}
			}
			int k=0;
			for(int j=N-1;j>=0;j--,k++)
				a[j][i] = k<al.size()?al.get(k):0;
			al.clear();
		}
		randomGenrator();
		printGame();
	}
	
	//*************************************************************RandomGenrate***********************
	static void randomGenrator(){
		int temp = Math.random()>=0.9?4:2;
		System.out.println();
		boolean locationFound = false;
        while(!locationFound) {
            int x = new Random().nextInt(4);
            int y = new Random().nextInt(4);
            if (a[x][y]==0) {
                a[x][y] = temp;
                locationFound = true;
            }
        }
	}
	static void printGame(){
		for(int i=0;i<N;i++){
			System.out.println();
			for(int j=0;j<N;j++)
				System.out.print(a[i][j]+"  ");
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Intital State");
		printGame();
		System.out.println("Change State");
		while(true){
			System.out.println();
			int click = sc.nextInt();
			if(click==1)
				rigthClick();
			else if(click==2)
				leftClick();
			else if(click==3)
				upClick();
			else if(click==4)
				downClick();
			else
				break;
		}
		sc.close();
	}
}
