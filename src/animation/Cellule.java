package animation;


public class Cellule {
	// Varibales d'instance
	private int posLigne;
	private int posColonne;//Position du coin en haut à gauche
	private int hauteur;
	private int largeur;
	private String type; // void, wall, door, stairs
	
	String[] typesPossibles = {"void", "wall", "door", "stai", "    "};

	//Constructeur d'initialisation
	public Cellule(int posLigne, int posColonne, int largeur, int hauteur, String type) {
		this.type = type;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.posColonne = posColonne;
		this.posLigne = posLigne;
	}
	public Cellule() {
		posLigne = 0;
		posColonne = 0;
		hauteur = 1;
		largeur = 1;
		type = "    ";
	}
	
	// Getters
	public int getPosLigne() {
		return posLigne;
	}
	public int getPosColonne() {
		return posColonne;
	}
	public int getHauteur() {
		return hauteur;
	}
	public int getLargeur() {
		return largeur;
	}
	public String getType() {
		return type;
	}

	// Setters
	public void setPosLigne(int ligne) {
		if (ligne<0) {
			System.out.println("Position de ligne entrée négative");
		}else {
			posLigne = ligne;
		}
		
	}
	public void setPosColonne(int colonne) {
		if (colonne<0) {
			System.out.println("Position de colonne entrée négative");
		}else {
			posColonne = colonne;
		}
	}
	public void setLargeur(int maLargeur) {
		if (maLargeur<0) {
			System.out.println("Largeur entrée négative");
		}else {
			largeur = maLargeur;
		}
	}
	public void setHauteur(int maHauteur) {
		if (maHauteur<0) {
			System.out.println("Hauteur entrée négative");
		}else {
			hauteur = maHauteur;
		}
	}
	public void setType(String pType) {
		if(typesPossibles[0].equals(pType) || typesPossibles[1].equals(pType) || typesPossibles[2].equals(pType) || typesPossibles[3].equals(pType) || typesPossibles[4].equals(pType)) {
			type = pType;
		} else {
			System.out.println("Le type entré n'existe po");
		}
		
	}
	
	// Méthodes
	public boolean exists(Cellule a) {
		if ( a.getPosColonne()>=0 && a.getHauteur()>0 && a.getLargeur()>0 && a.getPosLigne()>=0) {
			return true;
		}
	return false;
	}
	public boolean neighboor(Cellule a, Cellule b) {
		int xA = a.getPosLigne();
		int yA = a.getPosColonne();
		int xB = b.getPosLigne();
		int yB = b.getPosColonne();
		if(xA != xB || yA != yB) {
			return false;
		}
		if(xA == xB+1 || xA == xB-1 || yA == yB+1 || yA == yB-1) {
			return true;
		}
		return false;
	}
	public Cellule fusion(Cellule a, Cellule b) {
		if (!a.getType().equals(b.getType())){
			System.out.println("Fusion impossible : types différents");
		}
		if(neighboor(a,b) == false) {
			System.out.println("Fusion impossible : les cellules ne sont pas voisines");
		}
		int xA = a.getPosLigne();
		int yA = a.getPosColonne();
		int xB = b.getPosLigne();
		int yB = b.getPosColonne();
		int largeurA = a.getLargeur();
		int hauteurA = a.getHauteur();
		int largeurB = b.getLargeur();
		int hauteurB = b.getHauteur();
		int largeurC = 0;
		int hauteurC = 0;
		if(xA==xB) {
			largeurC = largeurB+largeurA;
			hauteurC = Math.min(hauteurA, hauteurB);
		}else {
			largeurC = Math.min(largeurA, largeurB);
			hauteurC = hauteurB+hauteurA;
		}
		Cellule c = new Cellule(Math.min(xA, xB),Math.min(yA, yB),largeurC,hauteurC,a.getType());
		return c;
		}
	public boolean isEmpty(Cellule a) {
		if(a.getType().equals("void")) {
			return true;
		}
	return false;
	}
	
	// Main
	public static void main(String[] args) {
		
	}
	
}



