import java.awt.*;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.GraphicsProgram;

//Punkte:
//-0.062,-1.2559915
//-0.0800116743,-0.7499080363
//-0.0800116743,-0.74990803
public class Mandelbrotmenge extends GraphicsProgram {
	double[] Punkt = {0,0};  //erstes+ -> runter, zweites+ -> rechts;
	double zoom = 1L;
	double maxIteratinos=300;
	
	
	double obererRand = Punkt[0]-1.1/zoom;
	double linkerRand = Punkt[1]-2.1/zoom;
	double zelle = 0.00325/zoom;
	

	// C-Werte checken nach Zn+1 = Zn^2 + C, Zo = 0. 100 Iterationen.
	public int checkC(double reC, double imC) {
		double reZ = 0, imZ = 0, reZ_minus1 = 0, imZ_minus1 = 0;
		int i = 0;
		for (i = 0; i < maxIteratinos; i++) {
			imZ = 2 * reZ_minus1 * imZ_minus1 + imC;
			reZ = reZ_minus1 * reZ_minus1 - imZ_minus1 * imZ_minus1 + reC;
			if (reZ * reZ + imZ * imZ > 4)
				return i;
			reZ_minus1 = reZ;
			imZ_minus1 = imZ;
		}
		return i;
	}

	// Punkte berechnen und setzen.
	public void paint(Graphics g) {
		double reC, imC; 
		int x, y;

		imC = obererRand; // oberer Rand
		for (y = 0; y < 1000; y++) {
			reC = linkerRand; // linker Rand
			for (x = 0; x < 1500; x++) {
				int R = 0;
				int Gr = 0;
				int B = 0;
				if (checkC(reC, imC) <= 10) R=25 * checkC(reC, imC);
				else if (checkC(reC, imC) <= 35){ R=250; Gr=10*(checkC(reC, imC)-10);}
				else if (checkC(reC, imC) <= 85){Gr=250; R=250-5*(checkC(reC, imC)-35);}
				else if (checkC(reC, imC) <= 135){Gr=250; B=5*(checkC(reC, imC)-85);}
				else if (checkC(reC, imC) <= 185){B=250; Gr=250-5*(checkC(reC, imC)-135);}
				else if (checkC(reC, imC) <= 435){B=250; R=(checkC(reC, imC)-185);}
				else if (checkC(reC, imC) <= 3099){R=250; B=250-(int)(Math.pow(checkC(reC, imC)-435,0.7));}
				
				
//				if (checkC(reC, imC) <= 10*präzision) R=(int) (25/präzision * checkC(reC, imC));
//				if (checkC(reC, imC) > 10*präzision && checkC(reC, imC) <= 20*präzision) R=(int) (500-25/präzision * checkC(reC, imC));
//				if (checkC(reC, imC) > 10*präzision && checkC(reC, imC) <= 35*präzision) Gr=(int) (10/präzision * (checkC(reC, imC)-10*präzision));
//				if (checkC(reC, imC) > 35*präzision && checkC(reC, imC) <= 45*präzision) Gr=(int) (250 -25/präzision * (checkC(reC, imC)-35*präzision));
//				if (checkC(reC, imC) > 35*präzision && checkC(reC, imC) <= 60*präzision) B=(int) (10/präzision * (checkC(reC, imC)-35*präzision));
//				if (checkC(reC, imC) > 60*präzision) B=250;
//				if (checkC(reC, imC) > 75*präzision) R=(int) (10/präzision * (checkC(reC, imC)-75*präzision));
				
				
					{Color colAppleman = new Color(R, Gr, B); // Farbe Apfelmännchen
					g.setColor(colAppleman);
					g.drawLine(x, y, x, y);
				}
				reC = reC + zelle; // nächste Spalte
			}
			imC = imC + zelle; // nächste Zeile
		}
	}
	
}