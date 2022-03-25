package model;

import java.awt.Color;
import java.util.Iterator;

import model.interfaces.IExecuteMyShape;
import model.interfaces.IShapeCommand;
import model.persistence.ApplicationState;

public class Shape implements IExecuteMyShape {

	public int x1;
	public int x2;
	public int y1;
	public int y2;

	ShapeColor color;
	ShapeColor secondaryColor;
	ShapeShadingType shddinType;
	ShapeType type;

	public Shape() {
	}

	public ShapeColor getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(ShapeColor secondaryColor) {
		this.secondaryColor = secondaryColor;
	}

	public Shape(int x12, int y12, int x22, int y22) {
		this.x1 = x12;
		this.y1 = y12;
		this.x2 = x22;
		this.y2 = y22;
	}

	public ShapeColor getColor() {
		return color;
	}

	public void setColor(ShapeColor color) {
		this.color = color;
	}

	public ShapeShadingType getShddinType() {
		return shddinType;
	}

	public void setShddinType(ShapeShadingType shddinType) {
		this.shddinType = shddinType;
	}

	public ShapeType getType() {
		return type;
	}

	public void setType(ShapeType type) {
		this.type = type;
	}

	@Override
	public int Execute(IShapeCommand mycommand) {

		int x = -1;
		switch (mycommand.getCommand()) {
		case CHECK_BOUNDARY:

			for (Iterator<Shape> iterator = ApplicationState.undo.iterator(); iterator.hasNext();) {
				Shape object = (Shape) iterator.next();
				if (!object.checkPoint(this.x1, this.y1)){
					x = ApplicationState.undo.indexOf(object);
					// x = -2;
				}
			}
			break;
		case ERASE_COMMAND:
			mycommand.EraseShape(this);
			x = 0;
			break;

		case DRAW_COMMAND:
			mycommand.DrawShape(this);
			x = 0;
			break;

		case SELECT_SHAPE:
			mycommand.DrawOutLine(this);
			x = 0;
			break;
		default:
			break;
		}

		return x;
		// TODO Auto-generated method stub

	}

	private boolean checkPoint(int x, int y) {
		// TODO Auto-generated method stub
		if (x > x1 && x < x2 &&  
			    y > y1 && y < y2) 
			    return false; 
		
		return true; 
		
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	public Shape clone()
	{
		Shape o = new Shape(x1,y1,x2,y2);
		o.color=color;
		o.secondaryColor=secondaryColor;
		o.shddinType= shddinType;
		o.type = type;
		return o;
		
	}
}
