package e1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;
import acm.program.*;

public class PflanzenSimmulation3 extends GraphicsProgram {

	/**
	 * Simmuliert ein Ökosystem.
	 * Simmuliert das Wachstum und die Ausbreitung einer Pflanze.
	 * Simmuliert das Wachstum, die Vermehrung, die Nahrungsaufnahme und die Fortbewegung eines Tieres. 
	 */
	private static final long serialVersionUID = 1L;
	List<Plant> PlantList = new ArrayList();
	List<Tier> TierList = new ArrayList();

	public void run() {
//		GLabel label = new GLabel("Hello, Universe!");
//		label.setFont("SansSerif-100");
//		double x = (getWidth() - label.getWidth()) / 2;
//		double y = (getHeight() + label.getAscent()) / 2;
//		add(label, x, y);

		
		
		Plant p = new Plant(500, 500, 10);
		PlantList.add(p);
		Tier t = new Tier(300, 500, 10);
		TierList.add(t);
		
		Jaeger jaeger = new Jaeger(700, 500, 15);
        JaegerList.add(jaeger);

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
		pause(500);
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
	}

	public void hunger() {
		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			t.hunger++;
			if (t.hunger > 150)
				TierList.remove(i);
		}
	}

	public void moveToPlants() {
		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			zuPflanzeLaufenUndFressen(t);
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
		}
		else next =new Plant(t.PosX,t.PosY,0);
		return next;
	}

	public void zuPflanzeLaufenUndFressen(Tier t) {
		Plant p = nextPlant(t);
		int distX = p.PosX - t.PosX;
		int distY = p.PosY - t.PosY;
		double distq = distX * distX + distY * distY;
		double dist = Math.pow(distq, 0.5);
		if (dist > 20) {
			distX = (int) (distX * t.speed / dist);
			distY = (int) (distY * t.speed / dist);
			t.bewegen(distX, distY);
//			    System.out.println(distY);
		} else {
			int pos = PlantList.indexOf(p);
			if (t.hunger >= 5 && pos!=-1)
				t.hunger -= 5;
			else if(pos!=-1)
				t.hunger = 0;
			if(pos!=-1)PlantList.remove(pos);

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
		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			if (t.Size > 20 && Math.random() > 0.8 && t.hunger < 10) {
				int x = (int) (t.PosX + Math.random() * 200 - 100);
				int y = (int) (t.PosY + Math.random() * 200 - 100);
				Tier tier = new Tier(x, y, 10);
				if (x >= 0 && y >= 0 && x <= 1900 && y <= 1060)
					TierList.add(tier);
			}
		}
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
		for (int i = 0; i < PlantList.size(); i++) {
			Plant p = PlantList.get(i);
			GOval Pflanze = new GOval(p.Size, p.Size);
			Pflanze.setColor(Color.GREEN);
			Pflanze.setFilled(true);
			Pflanze.setFillColor(Color.GREEN);
			add(Pflanze, p.PosX, p.PosY);
		}
		for (int i = 0; i < TierList.size(); i++) {
			Tier t = TierList.get(i);
			GOval Tier = new GOval(t.Size, t.Size);
			Tier.setColor(Color.GREEN);
			Tier.setFilled(true);
			Tier.setFillColor(Color.BLUE);
			add(Tier, t.PosX, t.PosY);
		}
	}

	/* Standard Java entry point */
	/* This method can be eliminated in most Java environments */
	public static void main(String[] args) {
		new PflanzenSimmulation3().start(args);
	}
}
