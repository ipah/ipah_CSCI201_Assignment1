package client;

import java.awt.Rectangle;

import libraries.ImageLibrary;

public class FactoryWall extends FactoryObject {

	protected FactoryWall(Rectangle inDimensions) {
		super(inDimensions);
		// TODO Auto-generated constructor stub
		mImage = ImageLibrary.getImage("Resources/img/Wall.png");
	}

}
