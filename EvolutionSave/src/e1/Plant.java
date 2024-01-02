package e1;

class Plant {
	int PosX;
	int PosY;
	int Size;
	
	
	public Plant(int posX, int posY, int size) {
		PosX = posX;
		PosY = posY;
		Size = size;
	}
	
	public void wachsen() {
		Size++;
	}
	
}