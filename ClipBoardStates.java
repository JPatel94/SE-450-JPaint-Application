package model.persistence;

import model.CommandType;
import model.Shape;
import model.interfaces.IClipBoardStates;

public class ClipBoardStates implements IClipBoardStates {

	public Shape selectShape = null;
	public Shape copyShape = null;
	CreateShapeCommand command;

	public ClipBoardStates(CreateShapeCommand command) {
		// TODO Auto-generated constructor stub
		this.command = command;
	}

	@Override
	public Shape ProcessState(Shape p) {
		// TODO Auto-generated method stub
		switch (ApplicationState.clips) {

		case COPY:
			copyShape = selectShape;
			selectShape = null;
			return null;

		case PASTE:

			int offsetX = Math.abs(copyShape.getX2() - copyShape.getX1());
			int offsetY = Math.abs(copyShape.getY2() - copyShape.getY1());

			copyShape.setX1(p.getX1());
			copyShape.setY1(p.getY1());
			copyShape.setX2(p.getX1() + offsetX);
			copyShape.setY2(p.getY1() + offsetY);

			command.setCommand(CommandType.DRAW_COMMAND);
			copyShape.Execute(command);

			return copyShape.clone();

		case DELETE:

			return null;
		case SELECT:
			selectShape = p;
			return null;

		case DESELECT:

			return null;

		default:

			return null;

		}
	}

}
