package e1;

class Tier {
	int PosX;
	int PosY;
	int Size;
	int hunger;
	int speed;
	
	
	public Tier(int posX, int posY, int size) {
		PosX = posX;
		PosY = posY;
		Size = size;
		hunger=50;
		speed=3;
	}
	
	public void wachsen() {
		Size++;
	}
	
	public void bewegen(int x, int y) {
		PosX+=x;
		PosY+=y;
	}
	
}