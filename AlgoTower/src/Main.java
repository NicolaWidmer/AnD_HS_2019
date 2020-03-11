//NB: Fügen Sie keine Pakete hinzu.

//NB: Das Importieren von anderen Klassen ist NICHT ERLAUBT.
//NB: Das Benützen anderer Klassen, die nicht zu java.lang.* gehören, ist NICHT ERLAUBT.
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

// BITTE VERÄNDERN SIE DEN OBENSTEHENDEN CODE NICHT.

//NB: Verändern Sie die untenstehende Deklaration der Klasse Main und der Methode main() NICHT, 
//sonst kann der Judge das Programm nicht laufen lassen. 
//Die Klasse MUSS als "class Main { ... }" deklariert werden, 
//die Methode als "public static void main(String[] args) { ... }".
class Main
{		

	//n bezeichnet die Anzahl der ALGO Steine.
	//l[i] enthält die Länge des i-ten ALGO Steins, für i=1,...,n
	//b[i] enthält die Breite des i-ten ALGO Steins, für i=1,...,n
	//h[i] enthält die Höhe des i-ten ALGO Steins, für i=1,...,n
	static int solve(int n, int[] l, int[] b, int[] h)
	{
		//TODO: Geben Sie die Höhe des höchsten ALGO Turms, der aus den vorhandenen Steinen
		//gebaut werden kann, zurück.
		LinkedList ans= new LinkedList();
		for(int i=1;i<=n;i++) {
			ans.add(l[i],b[i],h[i]);
		}
		
		return ans.hight();
	}

	public static void main(String[] args)
	{
		read_and_solve(System.in, System.out);
	}

	public static void read_and_solve(InputStream istream, PrintStream output) 
	{
		Scanner scanner = new Scanner(istream);
				
		int ntestcases = scanner.nextInt();
		for(int t=0; t<ntestcases; t++)
		{
			int n = scanner.nextInt();

			int[] l = new int[n+1];
			int[] b = new int[n+1];
			int[] h = new int[n+1];

			for(int i=1; i<=n; i++)
				l[i] = scanner.nextInt();

			for(int i=1; i<=n; i++)
				b[i] = scanner.nextInt();

			for(int i=1; i<=n; i++)
				h[i] = scanner.nextInt();

			output.println(solve(n, l, b, h));
		}
		
		scanner.close();
	}
}

class Node{
	Node next;
	Node prev;
	int w;
	int l;
	int h;
	Node(int w, int l, int h){
		this.w=w;
		this.l=l;
		this.h=h;
	}
	public String toString(){
		return w+" "+l+" "+h;
	}
}

class LinkedList{
	Node first;
	Node last;
	LinkedList(){
		first= new Node(Integer.MAX_VALUE,Integer.MAX_VALUE,0);
		last=first;
	}
	void add(int w,int l, int h){
		Node cur= first;
		Node hig=last;
		int hih=0;
		
		while(cur!=null) {
			
			if(l<=cur.l&&w<=cur.w) {
				if(cur.h>hih) {
					hig=cur;
					hih=cur.h;
				}
				
			}
			else if(l<=cur.w&&w<=cur.l) {
				if(cur.h>hih) {
					hig=cur;
					hih=cur.h;
				}
				
			}
			cur=cur.next;
		}
		cur=hig;
		Node neu= new Node(w,l,cur.h+h);
		neu.prev=cur.prev;
		cur.prev=neu;
		neu.next=cur;
		if(neu.prev==null) {
			first=neu;
		}
		else {
			neu.prev.next=neu;
		}
		
	}
	int hight() {
		int ans=0;
		for(Node cur= first;cur!=null;cur=cur.next) {
			ans=cur.h>ans?cur.h:ans;
		}
		return ans;
	}
	public String toString() {
		String ans="";
		for(Node cur= first;cur!=null;cur=cur.next) {
			ans+=cur+"\n";
		}
		return ans;
	}
	
}
