package myDemos;

import java.awt.Color;

public class KreisPunkt {

	// Darstellung
	int size;
	Color color;
	boolean filled;
	boolean nah;
	boolean fern;

	// Masse
	double masse;

	// Orts Eigenschaften
	double posX;
	double posY;
	double posZ;

	// initMaxima
	double posXMax;
	double posYMax;
	double posZMax;

	// Bewegung
	double dx;
	double dy;
	double dz;
	
	double damagedistanz;
	double damagedistanzIMax;
	
	double leben;
	double lebenIMax;
	
	double schaden= Math.random()*1000;
	double schadenIMax=1000;;
	
	double speed;
	double speedIMax;

	// Nummer
	int nr;

	double[][] dimensionsarray;// [Raumdimensionen][Ableitungsdimensionen]

	// Blocknummern
	boolean[] bocknummern;

	// Sekundär Atribute

	// Beschleunigung

	public void dimmove() {
		// System.out.println(dx);
		// posX = posX + (dx * 0);
		// if(dx>150000)dx=150000;
		// if(dy>150000)dy=150000;
		// if(dz>150000)dz=150000;
		// if(dx<-150000)dx=-150000;
		// if(dy<-150000)dy=-150000;
		// if(dz<-150000)dz=-150000;
		
		for (int ab = 0; ab < dimensionsarray[0].length; ab++) {
			for (int dim = 0; dim < dimensionsarray.length; dim++) {
				if (ab > 0)
					dimensionsarray[dim][ab] = dimensionsarray[dim][ab]+ dimensionsarray[dim][ab - 1]*0.00000001;

			}
		}
		posX = dimensionsarray[0][0];
		posY = dimensionsarray[1][0];
		posZ = dimensionsarray[2][0];
		dx= dimensionsarray[0][1];
		dy= dimensionsarray[1][1];
		dz= dimensionsarray[2][1];
	}

	public void beschleunigen(double ax, double ay, double az) {
		dx = dx + ax;
		dy = dy + ay;
		dz = dz + az;
	}

	public KreisPunkt(int size, Color color, double masse, double posX, double posY, double posZ) {
		filled = true;
		dx = 0;
		dy = 0;
		dz = 0;
		this.size = size;
		this.color = color;
		this.masse = masse;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public void beschleunigung(double fx, double fy, double fz) {
		if (masse != 0)
			beschleunigen(fx / masse, fy / masse, fz / masse);
	}

	// Wechselwirkung mit anderem Kreis
	public double gravity(KreisPunkt b) {
		if (posX == b.posX && posY == b.posY && posZ == b.posZ)
			return 0;
		// double tempoq= (dx-b.dx)*(dx-b.dx)+(dy-b.dy)*(dy-b.dy)+(dz-b.dz)*(dz-b.dz);
		// double tempo=Math.pow(tempoq, 0.5);

		double gf = masse * b.masse / ((posX - b.posX) * (posX - b.posX)
				+ (posY - b.posY) * (posY - b.posY) * (posZ - b.posZ) * (posZ - b.posZ));

		return gf;
	}

	public double beschleunigungNeu(KreisPunkt b) {
		double a = 0;
		// a=b.masse/dist
		a = b.masse / ((this.posX - b.posX) * (this.posX - b.posX) + (this.posY - b.posY) * (this.posY - b.posY)
				+ (this.posZ - b.posZ) * (this.posZ - b.posZ));
		return a;
	}

	// Kraftrichtungsvektor von a richtung b und von b richtung a
	public double[] richtungsvektor(KreisPunkt b) {
		double[] vektor = new double[3];
		vektor[0] = b.posX - posX;
		vektor[1] = b.posY - posY;
		vektor[2] = b.posZ - posZ;
		return vektor;
	}

	// Richtungsvektor mit gravity multiplizieren und fx fy fz in beschleunigung
	// This Kreis beschleunigt richtung Kreis b
	public void eigenbeschleunigung(KreisPunkt b) {
		double[] vektor = richtungsvektor(b);

		double fx = vektor[0] * gravity(b);
		double fy = vektor[1] * gravity(b);
		double fz = vektor[2] * gravity(b);
		// System.out.println(fx);
		beschleunigung(fx, fy, fz);
	}

	public void move() {
		// System.out.println(dx);
		// posX = posX + (dx * 0);
		// if(dx>150000)dx=150000;
		// if(dy>150000)dy=150000;
		// if(dz>150000)dz=150000;
		// if(dx<-150000)dx=-150000;
		// if(dy<-150000)dy=-150000;
		// if(dz<-150000)dz=-150000;
		posX = posX + (dx * 0.00000001);
		posY = posY + (dy * 0.00000001);
		posZ = posZ + (dz * 0.00000001);
	}

	// wechselwire eigenbesch jedes mit jedem und addiere danach jeweil den Speed
	// zur x,y und zpos

}
