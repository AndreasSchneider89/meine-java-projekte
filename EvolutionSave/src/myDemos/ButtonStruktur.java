package myDemos;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class ButtonStruktur extends GraphicsProgram {

	double[][] ButtonCount;
	String[][] ButtonTitle;
	int buttonL;
	int buttonO;

	double gx = 10;
	double gy = 10;

	GOval testO = new GOval(gx, gy);

	double zoom = 1000.0;
	long t = 5;

	KreisPunkt[] strukt = new KreisPunkt[1000];

	int moveKreis = strukt.length;

	double counter = 1.0;

	void drawButtonsNumbers(int x, int y, int sizeX, int sizeY) {

		if (ButtonCount == null) {
			ButtonCount = new double[sizeX][sizeY];
			ButtonTitle = new String[sizeX][sizeY];
			buttonL = x;
			buttonO = y;
		}

		test2();

		for (int breite = 0; breite < sizeX; breite++) {
			for (int hohe = 0; hohe < sizeY; hohe++) {
				GRect feld = new GRect(100, 100);
				feld.setFilled(true);
				feld.setFillColor(new Color(0, 0, 0));
				feld.setColor(new Color(0, 253, 0));
				add(feld, breite * 100 + x, hohe * 100 + y);
				double count = ButtonCount[breite][hohe];
				String s = Double.toString(count);
				GLabel l = new GLabel(s);
				l.setFont(l.getFont().deriveFont(30.0f));
				l.setColor(new Color(0, 253, 0));
				add(l, breite * 100 + x, hohe * 100 + y + 100);
				if (ButtonTitle[breite][hohe] == null)
					ButtonTitle[breite][hohe] = "";
				GLabel tit = new GLabel(ButtonTitle[breite][hohe]);
				tit.setFont(l.getFont().deriveFont(30.0f));
				tit.setColor(new Color(0, 253, 0));
				add(tit, breite * 100 + x, hohe * 100 + y + 40);

				testO = new GOval(gx, gy);
				add(testO, 50, 50);
			}
		}
	}
	
	void drawButtonsFight(int x, int y, int sizeX, int sizeY) {

		if (ButtonCount == null) {
			ButtonCount = new double[sizeX][sizeY];
			ButtonTitle = new String[sizeX][sizeY];
			buttonL = x;
			buttonO = y;
		}

		test2();

		for (int breite = 0; breite < sizeX; breite++) {
			for (int hohe = 0; hohe < sizeY; hohe++) {
				//Außenkasten
				GRect feld = new GRect(100, 100);
				feld.setFilled(true);
				feld.setFillColor(new Color(0, 0, 0));
				feld.setColor(new Color(0, 253, 0));
				add(feld, breite * 100 + x, hohe * 100 + y);
				
				//InnenKreis
				GOval ik = new GOval(100, 100);
				ik.setFilled(true);
				ik.setFillColor(new Color(0, 0, 0));
				ik.setColor(new Color(0, 253, 0));
				add(ik, breite * 100 + x, hohe * 100 + y);
				
				double count = ButtonCount[breite][hohe];
				String s = Double.toString(count);
				GLabel l = new GLabel(s);
				l.setFont(l.getFont().deriveFont(30.0f));
				l.setColor(new Color(0, 253, 0));
				add(l, breite * 100 + x, hohe * 100 + y + 100);
				if (ButtonTitle[breite][hohe] == null)
					ButtonTitle[breite][hohe] = "";
				GLabel tit = new GLabel(ButtonTitle[breite][hohe]);
				tit.setFont(l.getFont().deriveFont(30.0f));
				tit.setColor(new Color(0, 253, 0));
				add(tit, breite * 100 + x, hohe * 100 + y + 40);

				testO = new GOval(gx, gy);
				add(testO, 50, 50);
			}
		}
	}

	public void test2() {
		ButtonTitle[0][1] = "zoom";
		zoom = ButtonCount[0][1];
		ButtonTitle[0][2] = "iter";
		t = (long) ButtonCount[0][2];
		ButtonTitle[0][0] = "moveKreis";
		moveKreis = (int) ButtonCount[0][0];

//	testO = new GOval(gx, gy);
//	add(testO, 50, 50);
	}

	// strukt konstruieren
	public void konstStrukt(double maxx, double maxy, double maxz, double maxMass) {

		for (int i = 0; i < strukt.length; i++) {
			int size = 7;

			double masse = 0;

			double posX = 0;
			double posY = 0;
			double posZ = 0;
			double dx = 0;
			double dy = 0;
			double dz = 0;

			if (i < 1) {
				masse = 1;
				size = 7;
				posX = Math.random();
				posY = Math.random();
				posZ = Math.random();
				dx = Math.random();
				dy = Math.random();
				dz = Math.random();

			}
			// {
			// masse = Math.random() * maxMass;
			// // masse
			// if (maxMass * 0.75 < masse)
			// size++;
			// else if (maxMass * 0.25 > masse)
			// size--;
			// }
			else {
				masse = maxMass * Math.random();
			}
			// if(masse>5*maxMass)size=20;
			// if(masse>25*maxMass)size=25;
			// masse = 100;

			// double posX = Math.random() * maxx;
			// double posY = Math.random() * maxy;
			// double posZ = Math.random() * maxz;

			double r2 = masse;
			double g2 = 255 - masse * 0.5;
			double b2 = 255 / masse;
			if (r2 < 0)
				r2 = -r2;
			if (g2 < 0)
				g2 = -g2;
			if (b2 < 0)
				b2 = -b2;
			while (r2 > 255 || g2 > 255 || b2 > 255) {
				r2 *= Math.random();
				g2 *= Math.random();
				b2 *= Math.random();
			}

			int r = (int) (r2);
			int g = (int) (g2);
			int b = (int) (b2);

			// if (masse % 2 == 0)
			// r += 10;
			// if (masse % 4 == 0)
			// r += 10;
			// if (masse % 3 == 0)
			// g += 10;
			// if (masse % 9 == 0)
			// g += 10;
			// if (masse % 5 == 0)
			// b += 10;
			// if (masse % 25 == 0)
			// b += 10;

			Color color = new Color(r, g, b);

			strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);
			strukt[i].dx = dx;
			strukt[i].dy = dy;
			strukt[i].dz = dz;
			strukt[i].nr = i;
			strukt[i].bocknummern = new boolean[strukt.length];
			// strukt[i].posXMax = maxx;
			// strukt[i].posYMax = maxy;
			// strukt[i].posZMax = maxz;

		}
	}

	public void mousePressed(final MouseEvent m) {
		// raumzeit1();
		// getKreisGrav(1);
		//
//			if (m.getX() > 1500) {
//				if (t <= 0)
//					t = 1;
//				t *= 2;
//			}
//			if (m.getX() < 100)
//				t /= 2;
//			if (m.getY() > 800)
//				zoom *= 1.5;
//			if (m.getY() < 100)
//				zoom /= 1.5;

		// getKreisGravAutokl(10);

		// raumzeitneu(t);
//			getKreisGrav2(t);
//			drawKreisePunkte(zoom);

		int x = m.getX();
		x = x - buttonL;
		int x2 = x / 50;
		x2 = x2 % 2;
		int x3 = x / 33;
		x3 = x3 % 3;
		x = x / 100;
		int y = m.getY();
		y = y - buttonO;
		int y2 = y / 50;
		y2 = y2 % 2;
		y = y / 100;
		int y3 = y / 33;
		y3 = y3 % 3;

//			if(ButtonCount==null)ButtonCount=new double[1][15];

		if (x >= 0 && x < 1 && y >= 0 && y < 15) {
			if (x2 == 0 && y2 == 1) {
				ButtonCount[x][y]--;
			}
			if (x2 == 1 && y2 == 1) {
				ButtonCount[x][y]++;
			}
			if (x3 == 0 && y2 == 0) {
				ButtonCount[x][y] *= 0.5;
			}
			if (x3 == 1 && y2 == 0) {
				ButtonCount[x][y] = 0;
			}
			if (x3 == 2 && y2 == 0) {
				ButtonCount[x][y] *= 2;
			}
		}
		// forsch für butten zahlen
		// test();

		// button 0

		removeAll();
//		drawButtonsNumbers(1500, 30, 1, 8);
		drawButtonsFight(1500, 30, 1, 8);
	}

	public void run() {
		counter = 1;
		setBackground(new Color(10, 10, 10));
		setSize(3000, 3000);
		// initt();
		addMouseListeners();
		konstStrukt(1000, 1000, 1000, 1000);
		// urknall();
	}

	public static void main(String[] args) {
		new ButtonStruktur().start(args);
	}
}
