package compactMobs;

public class Vect {

    public int x;
    public int y;
    public int z;

    public Vect(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vect add(Vect other) {
        Vect result = new Vect(this.x, this.y, this.z);
        result.x += other.x;
        result.y += other.y;
        result.z += other.z;
        return result;
    }

    public Vect multiply(float factor) {
        Vect result = new Vect(this.x, this.y, this.z);
        Vect tmp21_20 = result;
        tmp21_20.x = (int) (tmp21_20.x * factor);
        Vect tmp33_32 = result;
        tmp33_32.y = (int) (tmp33_32.y * factor);
        Vect tmp45_44 = result;
        tmp45_44.z = (int) (tmp45_44.z * factor);
        return result;
    }

    public String ToString() {
        return this.x + "x" + this.y + "x" + this.z;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Vect)) {
            return false;
        }
        Vect other = (Vect) obj;
        return (this.x == other.x) && (this.y == other.y) && (this.z == other.z);
    }
}