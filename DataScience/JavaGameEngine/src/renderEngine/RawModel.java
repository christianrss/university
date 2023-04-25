package renderEngine;

public class RawModel {
	private int vaoID;
	private int vertextCount;
	
	public RawModel(int vaoID, int vertextCount)
	{
		this.vaoID 		  	= vaoID;
		this.vertextCount	= vertextCount;
		
	}

	public int getVaoID() {
		return vaoID;
	}

	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

	public int getVertextCount() {
		return vertextCount;
	}

	public void setVertextCount(int vertextCount) {
		this.vertextCount = vertextCount;
	}
}
