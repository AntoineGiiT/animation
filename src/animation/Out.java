package animation;

class Out {
	public int mX , mY , mSize;
	
	public Out (int x , int y, int size) {
		
		this.mX = x;
		this.mY = y;
		this.mSize = size;
		
	}
			
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Out(mX , mY , mSize);
	}
}

