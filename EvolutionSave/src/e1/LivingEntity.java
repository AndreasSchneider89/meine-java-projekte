package e1;

abstract class LivingEntity {
    int PosX;
    int PosY;
    int Size;
    int hp;
    int speed;

    public LivingEntity(int posX, int posY, int size, int hp, int speed) {
        PosX = posX;
        PosY = posY;
        Size = size;
        this.hp = hp;
        this.speed = speed;
    }

    public void wachsen() {
        Size++;
    }

    public void bewegen(int x, int y) {
        PosX += x;
        PosY += y;
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getSchaden() {
        return Size / 2; // Beispielhafter Schaden abhängig von der Größe
    }
}