package model.persistence;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.lang.reflect.Field;
import java.text.BreakIterator;

import javafx.scene.paint.ColorBuilder;
import model.CommandType;
import model.Shape;
import model.interfaces.IShapeCommand;

public class CreateShapeCommand implements IShapeCommand {

	CommandType command;

	Graphics2D canvas;

	public CommandType getCommand() {
		return command;
	}

	public void setCommand(CommandType command) {
		this.command = command;
	}

	@Override
	public void DrawOutLine(Shape object) {

		Stroke stroke = new BasicStroke(5, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1, new float[] { 9 }, 0);
		
		canvas.setStroke(stroke);

		switch (object.getType()) {
		case RECTANGLE:
			canvas.drawRect(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);
			break;
		case TRIANGLE:
			int[] x = { object.x1, object.x2, 0 };
			int[] y = { object.y1, object.y2, 0 };
			Point2D p = computeTipPoint(new Point(x[0], y[0]), new Point(x[1], y[1]), true);
			x[2] = (int) p.getX();
			y[2] = (int) p.getY();
			canvas.drawPolygon(x, y, 3);
			break;
		case ELLIPSE:
			canvas.drawOval(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);
			break;

		}
	

	}

	@Override
	public void EraseOutLine(Shape object) {

	}

	@Override
	public void DrawShape(Shape object) {
		
		
		// TODO Auto-generated method stub
		Color colorPrimary = null;
		Color colorSecondary = null;
		try {
			final Field f = Color.class.getField(object.getColor().name());
			final Field f1 = Color.class.getField(object.getSecondaryColor().name());
			colorPrimary = (Color) f.get(null);
			colorSecondary = (Color) f1.get(null);

			canvas.setColor(colorPrimary);

		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// canvas.fillRect(object.x1, object.y1, object.x2 - object.x1,
		// object.y2 - object.y1);
		switch (object.getType()) {

		case RECTANGLE:
			switch (object.getShddinType()) {
			case OUTLINE:
				canvas.setStroke(new BasicStroke(5));
				canvas.drawRect(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);
				break;
			case FILLED_IN:
				canvas.fillRect(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);
				break;
			case OUTLINE_AND_FILLED_IN:
				Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[] { 9 },
						0);
				canvas.setStroke(new BasicStroke(5));
				canvas.drawRect(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);
				canvas.setColor(colorSecondary);
				canvas.fillRect(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);

				break;

			}
			break;
		case TRIANGLE:
			int[] x = { object.x1, object.x2, 0 };
			int[] y = { object.y1, object.y2, 0 };
			Point2D p = computeTipPoint(new Point(x[0], y[0]), new Point(x[1], y[1]), true);
			x[2] = (int) p.getX();
			y[2] = (int) p.getY();
			switch (object.getShddinType()) {
			case OUTLINE:
				canvas.setStroke(new BasicStroke(5));
				canvas.drawPolygon(x, y, 3);
				break;
			case FILLED_IN:
				canvas.fillPolygon(x, y, 3);
				break;
			case OUTLINE_AND_FILLED_IN:

				Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[] { 9 },
						0);
				canvas.setStroke(new BasicStroke(5));
				//canvas.setStroke(stroke);
				canvas.drawPolygon(x, y, 3);
				canvas.setColor(colorSecondary);
				canvas.fillPolygon(x, y, 3);
				break;

			}
			break;
		case ELLIPSE:
			switch (object.getShddinType()) {
			case OUTLINE:
				canvas.setStroke(new BasicStroke(5));
				canvas.drawOval(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);
				break;
			case FILLED_IN:
				canvas.fillOval(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);

				break;
			case OUTLINE_AND_FILLED_IN:
				Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[] { 9 },
						0);
				canvas.setStroke(new BasicStroke(5));
				canvas.drawOval(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);
				canvas.setColor(colorSecondary);
				canvas.fillOval(object.x1, object.y1, object.x2 - object.x1, object.y2 - object.y1);
				break;

			}

			break;

		}

	}

	@Override
	public void EraseShape(Shape object) {
		// TODO Auto-generated method stub
		switch (object.getType()) {

		case RECTANGLE:
			canvas.setBackground(Color.WHITE);
			canvas.clearRect(object.x1 - 2, object.y1 - 2, (object.x2 + 2) - (object.x1 - 2) + 2,
					(object.y2 + 3) - (object.y1) + 2);
			break;
		case ELLIPSE:
			canvas.setBackground(Color.WHITE);
			canvas.clearRect(object.x1 - 2, object.y1 - 2, (object.x2 + 2) - (object.x1 - 2) + 2,
					(object.y2 + 3) - (object.y1) + 2);
			break;
		case TRIANGLE:
			int[] x = { object.x1 - 2, object.x2 + 2, 0 };
			int[] y = { object.y1 - 2, object.y2 + 2, 0 };
			Point2D p = computeTipPoint(new Point(x[0], y[0]), new Point(x[1], y[1]), true);
			x[2] = (int) p.getX();
			y[2] = (int) p.getY();
			canvas.setColor(Color.WHITE);
			canvas.fillPolygon(x, y, 3);
			break;
		}
		
	}

	public void setGraphics(Graphics2D graphics2d) {
		// TODO Auto-generated method stub
		this.canvas = graphics2d;
	}

	private static Point2D computeTipPoint(Point2D p0, Point2D p1, boolean right) {
		double dx = p1.getX() - p0.getX();
		double dy = p1.getY() - p0.getY();
		double length = Math.sqrt(dx * dx + dy * dy);
		double dirX = dx / length;
		double dirY = dy / length;
		double height = Math.sqrt(3) / 2 * length;
		double cx = p0.getX() + dx * 0.5;
		double cy = p0.getY() + dy * 0.5;
		double pDirX = -dirY;
		double pDirY = dirX;
		double rx = 0;
		double ry = 0;
		if (right) {
			rx = cx + height * pDirX;
			ry = cy + height * pDirY;
		} else {
			rx = cx - height * pDirX;
			ry = cy - height * pDirY;
		}
		return new Point2D.Double(rx, ry);
	}

}
