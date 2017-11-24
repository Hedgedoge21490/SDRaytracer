package Math;

public class Vec3D {
	public float x, y, z, w = 1;

	public Vec3D(float xx, float yy, float zz) {
		x = xx;
		y = yy;
		z = zz;
	}

	public Vec3D(float xx, float yy, float zz, float ww) {
		x = xx;
		y = yy;
		z = zz;
		w = ww;
	}

	public Vec3D add(Vec3D v) {
		return new Vec3D(x + v.x, y + v.y, z + v.z);
	}

	public Vec3D minus(Vec3D v) {
		return new Vec3D(x - v.x, y - v.y, z - v.z);
	}

	public Vec3D mult(float a) {
		return new Vec3D(a * x, a * y, a * z);
	}

	public void normalize() {
		float dist = (float) Math.sqrt((x * x) + (y * y) + (z * z));
		x = x / dist;
		y = y / dist;
		z = z / dist;
	}

	public float dot(Vec3D v) {
		return x * v.x + y * v.y + z * v.z;
	}

	public Vec3D cross(Vec3D v) {
		return new Vec3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}
}