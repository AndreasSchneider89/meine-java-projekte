package e1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;
import acm.program.*;

public class PflanzenSimmulation4 extends GraphicsProgram {

	/**
	 * Simmuliert ein Ökosystem. Simmuliert das Wachstum und die Ausbreitung einer
	 * Pflanze. Simmuliert das Wachstum, die Vermehrung, die Nahrungsaufnahme und
	 * die Fortbewegung eines Tieres.
	 */
	private static final long serialVersionUID = 1L;
	List<Plant> PlantList = new ArrayList();
	List<Tier> TierList = new ArrayList();

	public void run() {

		for (int i = 0; i < 500; i++) {

			Plant p = new Plant((int) (1000 * Math.random()), (int) (1000 * Math.random()), 10);
			PlantList.add(p);

			if (Math.random() > 0.5) {
				Tier t = new Tier((int) (1000 * Math.random()), (int) (1000 * Math.random()), 10);
				TierList.add(t);

				if (Math.random() > 0.8) {
					Jaeger jaeger = new Jaeger((int) (1000 * Math.random()), (int) (1000 * Math.random()), 15);
					JaegerList.add(jaeger);
				}
			}
		}
		while (true) {
			timestep();
		}
	}

	public void timestep() {

		wachstum();
		vermehren();
		draw();
		hunger();
		moveToPlants();
		//pause(200);
	}

	List<Jaeger> JaegerList = new ArrayList();

	public void jaegerZeitschritt() {
		for (int i = 0; i < JaegerList.size(); i++) {
			Jaeger jaeger = JaegerList.get(i);
			jaeger.bewegen(5, 0); // Beispielbewegung des Jägers
			jaeger.jagen(TierList);
		}
	}

	public void wachstum() {
		for (int i = 0; i < PlantList.size(); i++) {
			Plant p = PlantList.get(i);
			if (p.Size <= 20)
				p.wachsen();
		}

		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			if (t.Size <= 20)
				t.wachsen();
		}

		for (int i = 0; i < JaegerList.size(); i++) {
			Jaeger jaeger = JaegerList.get(i);
			if (jaeger.Size <= 20)
				jaeger.wachsen();
		}
	}

	public void hunger() {
		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			t.hunger++;
			if (t.hunger > 150) {
				TierList.remove(i);
			}
		}

		for (int i = 0; i < JaegerList.size(); i++) {
			Jaeger jaeger = JaegerList.get(i);
			jaeger.hunger += 2;
			if (jaeger.hunger > 150) {
				JaegerList.remove(i);
			}
		}
	}

	public Tier nextTier(Jaeger jaeger) {
		Tier next;
		if (!TierList.isEmpty()) {
			next = TierList.get(0);
			int distX = Math.abs(jaeger.PosX - TierList.get(0).PosX);
			int distY = Math.abs(jaeger.PosY - TierList.get(0).PosY);
			double mindist = Math.pow(distX * distX + distY * distY, 0.5);
			for (int i = 0; i < TierList.size(); i++) {
				Tier t = TierList.get(i);
				int distX2 = Math.abs(jaeger.PosX - TierList.get(i).PosX);
				int distY2 = Math.abs(jaeger.PosY - TierList.get(i).PosY);
				double mindist2 = Math.pow(distX2 * distX2 + distY2 * distY2, 0.5);
				if (mindist2 < mindist) {
					mindist = mindist2;
					next = TierList.get(i);
				}
			}
		} else {
			next = new Tier(jaeger.PosX, jaeger.PosY, 0);
		}
		return next;
	}

	public void moveToPlants() {
		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			zuPflanzeLaufenUndFressen(t);
		}

		for (int i = 0; i < JaegerList.size(); i++) {
			Jaeger jaeger = JaegerList.get(i);
			zuPflanzeLaufenUndFressen(jaeger);
		}
	}

	public void zuTierenLaufenUndFressen(Jaeger jaeger) {
		Tier t = nextTier(jaeger);
		int distX = t.PosX - jaeger.PosX;
		int distY = t.PosY - jaeger.PosY;
		double distq = distX * distX + distY * distY;
		double dist = Math.pow(distq, 0.5);

		if (dist > 20) {
			distX = (int) (distX * jaeger.speed / dist);
			distY = (int) (distY * jaeger.speed / dist);
			jaeger.bewegen(distX, distY);
		} else {
			int pos = TierList.indexOf(t);
			if (jaeger.hunger >= 5 && pos != -1) {
				jaeger.hunger -= 5;
				TierList.remove(pos);
			} else if (pos != -1) {
				jaeger.hunger = 0;
				TierList.remove(pos);
			}
		}
	}

	public Plant nextPlant(Tier t) {
		Plant next;
		if (!PlantList.isEmpty()) {
			next = PlantList.get(0);
			int distX = Math.abs(t.PosX - PlantList.get(0).PosX);
			int distY = Math.abs(t.PosY - PlantList.get(0).PosY);
			double mindist = Math.pow(distX * distX + distY * distY, 0.5);
			for (int i = 0; i < PlantList.size(); i++) {
				Plant p = PlantList.get(i);
				int distX2 = Math.abs(t.PosX - PlantList.get(i).PosX);
				int distY2 = Math.abs(t.PosY - PlantList.get(i).PosY);
				double mindist2 = Math.pow(distX2 * distX2 + distY2 * distY2, 0.5);
				if (mindist2 < mindist) {
					mindist = mindist2;
					next = PlantList.get(i);
				}
			}
		} else
			next = new Plant(t.PosX, t.PosY, 0);
		return next;
	}

	public void zuPflanzeLaufenUndFressen(Object entity) {
		if (entity instanceof Tier) {
			Tier t = (Tier) entity;
			// Bestehender Code für Tiere
			Plant p = nextPlant(t);
			int distX = p.PosX - t.PosX;
			int distY = p.PosY - t.PosY;
			double distq = distX * distX + distY * distY;
			double dist = Math.pow(distq, 0.5);

			if (dist > 20) {
				distX = (int) (distX * t.speed / dist);
				distY = (int) (distY * t.speed / dist);
				t.bewegen(distX, distY);
			} else {
				int pos = PlantList.indexOf(p);
				if (t.hunger >= 5 && pos != -1) {
					t.hunger -= 5;
				} else if (pos != -1) {
					t.hunger = 0;
					PlantList.remove(pos);
				}
			}
		} else if (entity instanceof Jaeger) {
			Jaeger jaeger = (Jaeger) entity;
			zuTierenLaufenUndFressen(jaeger);
		}
	}

	public void vermehren() {
		for (int i = 0; i < PlantList.size(); i++) {
			Plant p = PlantList.get(i);
			if (p.Size > 20 && Math.random() > 0.8) {
				int x = (int) (p.PosX + Math.random() * 400 - 200);
				int y = (int) (p.PosY + Math.random() * 400 - 200);
				Plant pflanze = new Plant(x, y, 10);
				if (frei(x, y) && x >= 0 && y >= 0 && x <= 1900 && y <= 1060)
					PlantList.add(pflanze);
			}
		}

		// Vermehrung der Tiere
		List<Tier> neueTiere = new ArrayList<>();
		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			if (t.Size > 20 && Math.random() > 0.8 && t.hunger < 10) {
				int x = (int) (t.PosX + Math.random() * 200 - 100);
				int y = (int) (t.PosY + Math.random() * 200 - 100);
				Tier tier = new Tier(x, y, 10);
				if (x >= 0 && y >= 0 && x <= 1900 && y <= 1060)
					neueTiere.add(tier);
			}
		}
		TierList.addAll(neueTiere);

		// Vermehrung der Jäger
		List<Jaeger> neueJaeger = new ArrayList<>();
		for (int i = 0; i < JaegerList.size(); i++) {
			Jaeger jaeger = JaegerList.get(i);
			if (jaeger.Size > 20 && Math.random() > 0.8 && jaeger.hunger < 10) {
				int x = (int) (jaeger.PosX + Math.random() * 200 - 100);
				int y = (int) (jaeger.PosY + Math.random() * 200 - 100);
				Jaeger neuerJaeger = new Jaeger(x, y, 15);
				if (x >= 0 && y >= 0 && x <= 1900 && y <= 1060)
					neueJaeger.add(neuerJaeger);
			}
		}
		JaegerList.addAll(neueJaeger);
	}

	public boolean frei(int x, int y) {
		boolean frei = true;
		for (int i = 0; i < PlantList.size(); i++) {
			int distX = Math.abs(x - PlantList.get(i).PosX);
			int distY = Math.abs(y - PlantList.get(i).PosY);
			double dist = Math.pow(distX * distX + distY * distY, 0.5);
			if (dist < 20)
				frei = false;
		}
		return frei;
	}

	public void draw() {
		removeAll();

		// Pflanzen zeichnen
		for (int i = 0; i < PlantList.size(); i++) {
			Plant p = PlantList.get(i);
			drawPoint(p.PosX, p.PosY, Color.GREEN);
		}

		// Tiere zeichnen
		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			drawPoint(t.PosX, t.PosY, Color.BLUE);
		}

		// Jäger zeichnen
		for (int i = 0; i < JaegerList.size(); i++) {
			Jaeger jaeger = JaegerList.get(i);
			drawPoint(jaeger.PosX, jaeger.PosY, Color.RED);
		}
	}

	private void drawPoint(int x, int y, Color color) {
		GPoint point = new GPoint(x, y);
		GOval oval = new GOval(point.getX(), point.getY(), 5, 5); // Größe der Punkte anpassen
		oval.setColor(color);
		oval.setFilled(true);
		oval.setFillColor(color);
		add(oval);
	}

	/* Standard Java entry point */
	/* This method can be eliminated in most Java environments */
	public static void main(String[] args) {
		new PflanzenSimmulation4().start(args);
	}
}