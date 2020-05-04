package animation;

import java.util.ArrayList;

public class Salle extends Cellule{
	// Variable d'intance
	private Cellule[][] cellule;
	
	// Constructeurs
	public Salle(int nbLignes, int nbColonnes) {
		cellule = new Cellule[nbLignes][nbColonnes];
		for(int i = 0; i < nbLignes; i++) {
			for(int j = 0; j < nbColonnes; j++) {
				Cellule c = new Cellule();
				this.cellule[i][j] = c;
			}
		}
	}
	public Salle(Cellule[][] cellule) {
		this.cellule = cellule;
	}
	
	// Setters
	public void setSalle(Cellule[][] cellule) {
		this.cellule = cellule;
	}
	public void setCellule(int i, int j, Cellule cellule) {
		this.cellule[i][j] = cellule; 
	}	
	public void setLength(int i, int j) {
		this.cellule = new Cellule[i][j];
	}
	
	// Getters
	public Cellule[][] getSalle() {
		return this.cellule;
	}
	public int getLigne() {
		return this.cellule.length;
	}
	public int getColonne() {
		return this.cellule[0].length;
	}
	public Cellule getCellule(int i, int j) {
		return this.cellule[i][j];
	}
	
	// Méthodes
	public ArrayList<Integer[]> getXYDoor(){
		int ligne = this.getLigne();
		int colonne = this.getColonne();
		ArrayList<Integer[]> result = new ArrayList<Integer[]>();
		Integer[] pos = {ligne,colonne};
		result.add(0, pos);
		int cpt = 1;
		for(int i = 0; i < ligne; i++) {
			for(int j = 0; j < colonne; j++) {
				if(this.getCellule(i, j).getType().equals("door")) {
					Integer[] posDoor = {i,j};
					result.add(cpt, posDoor);
					cpt++;
				}
			}
		}
		return result;
	}
	public void displaySalle() {
		for(int i = 0; i < this.getLigne(); i++) {
			for(int j = 0; j < this.getColonne(); j++) {
				String contenu = this.getCellule(i, j).getType();
				System.out.print(contenu + " ");
			}
			System.out.print("\n");
		}
	}
	public void borderWall() {
		int ligne  = this.getLigne();
		int colonne = this.getColonne();
		for(int i = 0; i < this.getLigne(); i++) {
			for(int j = 1; j < this.getColonne(); j++) {
				this.cellule[i][0].setType("wall");
				this.cellule[i][colonne-1].setType("wall");
				this.cellule[0][j].setType("wall");
				this.cellule[ligne-1][j].setType("wall");
			}
		}
		for(int i = 0; i < this.getLigne(); i++) {
			for(int j = 1; j < this.getColonne(); j++) {
				if(this.cellule[i][j].getType().equals("    ")) {
					this.cellule[i][j].setType("void");
				}
			}
		}
	}
	public void insertObstacle(int ligne, int colonne) {
		this.cellule[ligne][colonne].setType("wall");
	}
	public void insertDoor(int ligne, int colonne) {
		this.cellule[ligne][colonne].setType("door");
	}
	public void insertStairs(int ligne, int colonne) {
		this.cellule[ligne][colonne].setType("stairs");
	}
	public void cleanCellule(int ligne, int colonne) {
		this.cellule[ligne][colonne].setType("void");
	}
	public static boolean isInBorder(Salle a, int posLigne, int posColonne) {
		int ligne = a.getLigne();
		int colonne = a.getColonne();
		if(((posColonne == 0) || (posColonne == colonne-1)) || ((posLigne == 0) || (posLigne == ligne-1))) {
			return true;
		}
		return false;
	}
	public static Salle deleteLigne(int ligne, Salle a) {
		int ligneA = a.getLigne();
		int colonneA = a.getColonne();
		Salle b = new Salle(ligneA - 1, colonneA);
		int i = 0;
		int j = 0;
		while((i < ligneA -1) && (i != ligne)) {
			while(j < colonneA) {
				b.setCellule(i, j, a.getCellule(i,j));
				i++;
				j++;
			}
		}
		return b;
	}
	public static Salle deleteColonne(int colonne, Salle a) {
		int ligneA = a.getLigne();
		int colonneA = a.getColonne();
		Salle b = new Salle(ligneA, colonneA - 1);
		int i = 0;
		int j = 0;
		while(i < ligneA) {
			while((j < colonneA)  && (i != colonne)) {
				b.setCellule(i, j, a.getCellule(i, j));
				i++;
				j++;
			}
		}
		return b;
	}
	public static Salle joinSallesDessous(Salle a, Salle b, int posXDoorA, int posYDoorA, int posXDoorB, int posYDoorB) {
		// On récupère les données utiles
		int ligneA = a.getLigne();
		int ligneB = b.getLigne();
		int colonneA = a.getColonne();
		int colonneB = b.getColonne();
		
		// On définit l'erreur
		Salle erreur = new Salle(0,0);
		
		// On vérifie que les deux portes soient bien en bordure de salle
		boolean test1 = isInBorder(a, posXDoorA, posYDoorA);
		boolean test2 = isInBorder(b, posXDoorB, posYDoorB);
		if(!(test1 && test2)) {
			System.out.println("Jointure impossible : Au moins une des deux portes n'est pas en bordure de la salle");
			return erreur;
		}
		
		// On vérifie que les deux cellules soient bien des portes
		if(!(a.cellule[posXDoorA][posYDoorA].getType().equals("door") && b.cellule[posXDoorB][posYDoorB].getType().equals("door"))) {
			System.out.println("Jointure impossible : il n'y a pas de porte sur au moins une des poistions rensignée");
			return erreur;
		}
		
		// On vérifie que les deux portes coïncident  : portes sur les bordures 'opposées'
		boolean testA = (posXDoorA == 0 && posXDoorB == ligneB-1);
		boolean testB = (posXDoorA == ligneA-1 && posXDoorB == 0);
		if(!(testA || testB)) {
			System.out.println("Jointure impossible : les portes sont mal placées, elles ne coïncident pas");
			System.out.println("Les portes ne sont pas sur des murs 'opposés'");
			return erreur;
		}
		
		
		// On joint les salles
		if(testA) {
			int offset = Math.abs(posYDoorA-posYDoorB);
			Salle c = new Salle(ligneA + ligneB, Math.max(colonneA, colonneB) + offset);
			if(offset <= posYDoorB) {
				for(int i = 0; i < ligneB; i++) {
					for(int j=0; j < colonneB; j++) {
						c.setCellule(i, j, b.getCellule(i, j));
					}
				}
				for(int i = ligneB; i < ligneA + ligneB; i++) {
					for(int j = offset; j < colonneA + offset; j++) {
						c.setCellule(i,j,a.getCellule(i - ligneB,j - offset));
					}
				}
			} else {
				for(int i = 0; i < ligneB; i++) {
					for(int j = offset; j < colonneB + offset; j++) {
						c.setCellule(i, j, b.getCellule(i, j - offset));
					}
				}	
				for(int i = ligneB; i < ligneA + ligneB; i++) {
					for(int j = 0; j < colonneA; j++) {
						c.setCellule(i,j,a.getCellule(i - ligneB, j));
					}
				}
			}
			return c;
		}
		if(testB) {
			int offset = Math.abs(posYDoorA-posYDoorB);
			Salle c = new Salle(ligneA + ligneB , Math.max(colonneA, colonneB) + offset);
			if(offset <= posYDoorA) {
				for(int i = 0; i < ligneA; i++) {
					for(int j=0; j < colonneA; j++) {
						c.setCellule(i, j, a.getCellule(i, j));
					}
				}
				for(int i = ligneA; i < ligneA + ligneB; i++) {
					for(int j = offset; j < colonneB + offset; j++) {
						c.setCellule(i, j, b.getCellule(i - ligneA,j - offset));
					}
				}
			} else {
				for(int i = 0; i < ligneA; i++) {
					for(int j = offset; j < colonneA + offset; j++) {
						c.setCellule(i, j, a.getCellule(i, j - offset));
					}
				}	
				for(int i = ligneA; i < ligneA + ligneB; i++) {
					for(int j = 0; j < colonneB; j++) {
						c.setCellule(i, j, b.getCellule(i - ligneA, j));
					}
				}
			}
			return c;
		}
		return erreur;
	}
	public static Salle joinSallesCote(Salle a, Salle b, int posXDoorA, int posYDoorA, int posXDoorB, int posYDoorB) {
		// On récupère les données utiles
		int ligneA = a.getLigne();
		int ligneB = b.getLigne();
		int colonneA = a.getColonne();
		int colonneB = b.getColonne();
		
		// On définit l'erreur
		Salle erreur = new Salle(0,0);
		
		// On vérifie que les deux portes soient bien en bordure de salle
		boolean test1 = isInBorder(a, posXDoorA, posYDoorA);
		boolean test2 = isInBorder(b, posXDoorB, posYDoorB);
		if(!(test1 && test2)) {
			System.out.println("Jointure impossible : Au moins une des deux portes n'est pas en bordure de la salle");
			return erreur;
		}
		
		// On vérifie que les deux cellules sont bien des portes
		if(!(a.cellule[posXDoorA][posYDoorA].getType().equals("door") && b.cellule[posXDoorB][posYDoorB].getType().equals("door"))) {
			System.out.println("Jointure impossible : il n'y a pas de porte sur au moins une des poistions rensignée");
			return erreur;
		}
		
		// On vérifie que les deux portes coïncident  : portes sur les bordures 'opposées'
		boolean testA = (posYDoorA == 0 && posYDoorB == colonneB-1);
		boolean testB = (posYDoorA == colonneA-1 && posYDoorB == 0);
		if(!(testA || testB)) {
			System.out.println("Jointure impossible : les portes sont mal placées, elles ne coïncident pas");
			System.out.println("Les portes ne sont pas sur des murs 'opposés'");
			return erreur;
		}
		
		
		// On joint les salles
		if(testA) {
			int offset = Math.abs(posXDoorA-posXDoorB);
			Salle c = new Salle(Math.max(ligneA, ligneB) + offset, colonneA + colonneB);
			if(offset <= posXDoorB) {
				for(int i = 0; i < ligneB; i++) {
					for(int j=0; j < colonneB; j++) {
						c.setCellule(i, j, b.getCellule(i, j));
					}
				}
				for(int i = offset; i < ligneA + offset; i++) {
					for(int j = colonneB; j < colonneA + colonneB; j++) {
						c.setCellule(i, j, a.getCellule(i - offset,j - colonneB));
					}
				}
			} else {
				for(int i = offset; i < ligneB + offset; i++) {
					for(int j = 0; j < colonneB; j++) {
						c.setCellule(i, j, b.getCellule(i - offset, j));
					}
				}	
				for(int i = 0; i < ligneA; i++) {
					for(int j = colonneB; j < colonneA + colonneB; j++) {
						c.setCellule(i,j,a.getCellule(i, j - colonneB));
					}
				}
			}
		return c;
		}
		if(testB) {
			int offset = Math.abs(posXDoorA-posXDoorB);
			Salle c = new Salle(Math.max(ligneA, ligneB) + offset, colonneA + colonneB);
			if(offset <= posYDoorA) {
				for(int i = 0; i < ligneA; i++) {
					for(int j=0; j < colonneA; j++) {
						c.setCellule(i, j, a.getCellule(i, j));
					}
				}
				for(int i = offset; i < ligneB + offset; i++) {
					for(int j = colonneA; j < colonneB + colonneA; j++) {
						c.setCellule(i, j, b.getCellule(i - offset,j - colonneA));
					}
				}
			} else {
				for(int i = offset; i < ligneA + offset; i++) {
					for(int j = 0; j < colonneA ; j++) {
						c.setCellule(i, j, a.getCellule(i - offset, j));
					}
				}	
				for(int i = 0; i < ligneB; i++) {
					for(int j = colonneA; j < colonneB + colonneA; j++) {
						c.setCellule(i, j, b.getCellule(i, j - colonneA));
					}
				}
			}
			return c;
		}
		
		return erreur;
	}
	
	
	// Main
	public static void main(String[] args) {
		
	}
	
}
