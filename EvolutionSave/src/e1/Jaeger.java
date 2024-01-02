package e1;

import java.util.List;

class Jaeger {
    int PosX;
    int PosY;
    int Size;
    int hunger;
    int speed;

    public Jaeger(int posX, int posY, int size) {
        PosX = posX;
        PosY = posY;
        Size = size;
        hunger = 50;
        speed = 4; // Geschwindigkeit des Jägers
    }

    public void wachsen() {
        Size++;
    }

    public void bewegen(int x, int y) {
        PosX += x;
        PosY += y;
    }

    public void jagen(List<Tier> tierList) {
        for (int i = 0; i < tierList.size(); i++) {
            Tier t = tierList.get(i);
            int distX = t.PosX - PosX;
            int distY = t.PosY - PosY;
            double distq = distX * distX + distY * distY;
            double dist = Math.pow(distq, 0.5);

            if (dist < 20) {
                if (hunger >= 10) {
                    hunger -= 10;
                    tierList.remove(i);
                } else {
                    hunger = 0;
                }
            }
        }
    }
}