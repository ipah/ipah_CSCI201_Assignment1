package client;

import java.awt.Rectangle;

import libraries.ImageLibrary;
public class FactoryWall2 extends FactoryObject{
	protected FactoryWall2(Rectangle inDimensions) {
		super(inDimensions);
		// TODO Auto-generated constructor stub
		mImage = ImageLibrary.getImage("Resources/img/Wall2.png");
	}

	
}
