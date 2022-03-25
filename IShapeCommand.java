package model.interfaces;

import java.awt.Graphics2D;

import model.CommandType;
import model.Shape;

public interface IShapeCommand {
	public CommandType getCommand();
	public void DrawShape(Shape object);
	public void EraseShape(Shape object);
	public void DrawOutLine(Shape shape);
	public void EraseOutLine(Shape shape);
}
