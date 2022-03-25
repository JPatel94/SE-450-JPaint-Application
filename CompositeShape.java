package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import model.persistence.CreateShapeCommand;

public class CompositeShape {

	ArrayList<ArrayList<Shape>> groups = new ArrayList<>();
	Stack<CompositeShape> undo = new Stack<CompositeShape>();
	Shape oldShape = null;
	Shape activeShape = null;
	CreateShapeCommand mycommand = null;

	public void add(ArrayList<Shape> currentGroup) {
		groups.add(currentGroup);
	}

	public void UbGroup() {
	
		
		
		if (oldShape != null) {
			mycommand.setCommand(CommandType.ERASE_COMMAND);
			oldShape.Execute(mycommand);
		}
		for (int x = 0; x < groups.size(); x++) {

			for (int y = 0; y < groups.get(x).size(); y++) {

				Shape p = groups.get(x).get(y);
				//mycommand.setCommand(CommandType.ERASE_COMMAND);
				//p.Execute(mycommand);
				mycommand.setCommand(CommandType.DRAW_COMMAND);
				p.Execute(mycommand);
				
				

			}
		}
		
		
		
	}

	private void undo() {
		if (oldShape != null) {
			mycommand.setCommand(CommandType.ERASE_COMMAND);
			oldShape.Execute(mycommand);
		}
		mycommand.setCommand(CommandType.DRAW_COMMAND);
		activeShape.setType(ShapeType.RECTANGLE);
		activeShape.setShddinType(ShapeShadingType.OUTLINE);
		activeShape.Execute(mycommand);

		for (int x = 0; x < groups.size(); x++) {

			for (int y = 0; y < groups.get(x).size(); y++) {

				Shape p = groups.get(x).get(y);
				mycommand.setCommand(CommandType.ERASE_COMMAND);
				p.Execute(mycommand);
				mycommand.setCommand(CommandType.DRAW_COMMAND);
				p.Execute(mycommand);
				mycommand.setCommand(CommandType.SELECT_SHAPE);
				p.Execute(mycommand);
				

			}
		}

	}

	public void BoundingBox(Shape activeShape, CreateShapeCommand mycommand) {
		
	
		
		
		this.mycommand = mycommand;
		this.activeShape = activeShape;
		
		if (oldShape != null) {
			mycommand.setCommand(CommandType.ERASE_COMMAND);
			oldShape.Execute(mycommand);
		}
		mycommand.setCommand(CommandType.DRAW_COMMAND);
		activeShape.setType(ShapeType.RECTANGLE);
		activeShape.setShddinType(ShapeShadingType.OUTLINE);
		activeShape.Execute(mycommand);

		for (int x = 0; x < groups.size(); x++) {

			for (int y = 0; y < groups.get(x).size(); y++) {

				Shape p = groups.get(x).get(y);
				mycommand.setCommand(CommandType.ERASE_COMMAND);
				p.Execute(mycommand);
				mycommand.setCommand(CommandType.DRAW_COMMAND);
				p.Execute(mycommand);
				mycommand.setCommand(CommandType.SELECT_SHAPE);
				p.Execute(mycommand);
				

			}
		}

		oldShape = activeShape;
		//
		undo.push(this);
		//
	}

}
