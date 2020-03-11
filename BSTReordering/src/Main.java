//NB: Fügen Sie keine Pakete hinzu.

//NB: Das Importieren von anderen Klassen ist NICHT ERLAUBT.
//NB: Das Benützen anderer Klassen, die nicht zu java.lang.* gehören, ist NICHT ERLAUBT.
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

// BITTE VERÄNDERN SIE DEN OBENSTEHENDEN CODE NICHT.

class Vertex
{
	public int key;
	public Vertex leftChild = null;
	public Vertex rightChild = null;
	public Vertex parent=null;
	
		
	public Vertex(int key)
	{
		this.key=key;
	}
}
class Node{
	Node next;
	Node prev;
	Vertex val;
	Node(Vertex val){
		this.val=val;
	}
}
class LinkedList{
	Node first;
	Node last;
	void add(Vertex v){
		if(last==null) {
			last= new Node(v);
			first=last;
		}
		else {
			Node cur=first;
			first= new Node(v);
			cur.prev=first;
			first.next=cur;
		}
	}
	Vertex remove(){
		Node cur=last;
		if(first==last) {
			first=last=null;
		}
		else {
			last=last.prev;
			last.next=null;;
		}
		return cur.val;
		
	}	
	boolean isempty() {
		return first==null;
	}
}




//NB: Verändern Sie die untenstehende Deklaration der Klasse Main und der Methode main() NICHT, 
//sonst kann der Judge das Programm nicht laufen lassen. 
//Die Klasse MUSS als "class Main { ... }" deklariert werden, 
//die Methode als "public static void main(String[] args) { ... }".
class Main
{			

	//n bezeichnet die Anzahl Knoten des Binären Suchbaumes aus dem Input
	//Das i-te Element im Array keysPreorder (hat Index i-1 und) ist der Schlüssel (engl. key) des i-ten Knotens,
	//der durch eine Preorder Traversierung des Binären Suchbaumes aus dem Input besucht wird.
	public static int[] solve(int n, int[] keysPreorder)
	{	
		//TODO: Geben Sie einen Array aus n Ganzzahlen (engl. integers) zurück.
		//Das i-te Element dieses Arrays (hat Index i-1 und) soll gleich dem Schlüssel des des i-ten Knotens sein,
		//der durch eine Breitensuche auf dem Binären Suchbaum aus dem Input besucht wird.
		Vertex root=maketree(keysPreorder);
		int[] ans= bfs(root,keysPreorder.length);
		
		return ans;
	}
	
	static int[] bfs(Vertex root,int l) {
		int[] ans=new int[l];
		int i=0;
		Vertex cur;
		LinkedList ll=new LinkedList();
		ll.add(root);
		while(!ll.isempty()) {
			cur=ll.remove();
			ans[i]=cur.key;
			i++;
			if(cur.leftChild!=null) {
				ll.add(cur.leftChild);
			}
			if(cur.rightChild!=null) {
				ll.add(cur.rightChild);
			}
			
		}
		
		return ans;
	}
	
	static Vertex maketree(int[] u) {
		Vertex ans=new Vertex(u[0]);
		Vertex cur=ans;
		boolean left=true;
		for(int i=1;i<u.length;i++) {
			if(u[i]<cur.key) {
				cur.leftChild=new Vertex(u[i]);
				cur.leftChild.parent=cur;
				cur=cur.leftChild;
				left=true;
			}
			else {
				
				cur=ans;
				while(true) {
					if(cur.key>u[i]) {
						if(cur.leftChild==null) {
							break;
						}
						cur=cur.leftChild;
					}
					else {
						if(cur.rightChild==null) {
							break;
						}
						else {
							cur=cur.rightChild;
						}
					}
					
				}
				
	
				cur.rightChild=new Vertex(u[i]);
				cur.rightChild.parent=cur;
				cur=cur.rightChild;
				left=false;
			}
			
		}
		
		return ans ;
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
			int[] keysPreorder = new int[n];

			for(int i=0; i<n; i++)
				keysPreorder[i] = scanner.nextInt();

			int[] keysBFS = solve(n, keysPreorder);

			for(int i=0; i<n; i++)
			{
				if(i!=0)
					output.print(" ");

				output.print(keysBFS[i]);
			}
			output.println();
		}
		
		scanner.close();
	}
}
