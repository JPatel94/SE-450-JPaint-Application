package model.persistence;

import model.ShapeColor;
import model.ShapeGroup;
import model.ShapeShadingType;
import model.ShapeType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;
import javax.swing.event.MouseInputListener;

import model.ClipboardStates;
import model.CommandType;
import model.CompositeShape;
import model.MouseMode;
import model.Shape;
import model.interfaces.IApplicationState;
import model.interfaces.IDialogProvider;
import view.gui.DialogProvider;
import view.interfaces.IUiModule;

public class ApplicationState implements IApplicationState, MouseInputListener {
	private final IUiModule uiModule;
	private final IDialogProvider dialogProvider;
	private Shape activeShape;
	public static Stack<Shape> undo = new Stack<Shape>();
	public static Stack<Shape> undo1 = new Stack<Shape>();
	public CompositeShape groupObject = new CompositeShape();
	public ArrayList<Shape> currentGroup = new ArrayList();

	public static ClipboardStates clips;
	Stack<Shape> redo = new Stack<Shape>();
	Stack<Shape> redo1 = new Stack<Shape>();
	CreateShapeCommand command = new CreateShapeCommand();
	ClipBoardStates clipboard = new ClipBoardStates(command);
	Graphics2D graphics2d;

	private int x1;

	private int y1;

	private int x2;

	private int y2;

	//
	boolean move = false;

	public ApplicationState(IUiModule uiModule) {
		this.uiModule = uiModule;
		graphics2d = uiModule.getGraphics();
		this.dialogProvider = new DialogProvider(this);

	}

	@Override
	public void setActiveShapeAndMode() {
		// TODO Auto-generated method stub
		activeShape = uiModule.getDialogResponse(dialogProvider.getChooseActiveShapeAndMode());

	}

	@Override
	public void undoLastAction() {
		// TODO Auto-generated method stub
		System.out.println("Undo Last");
		activeShape = null;
		if (!undo.isEmpty())
			activeShape = undo.pop();
		if (activeShape != null) {
			command.setCommand(CommandType.ERASE_COMMAND);
			activeShape.Execute(command);
			// command.EraseShape(graphics2d,activeShape);
			redo.push(activeShape.clone());
			if (!undo1.empty()) {
				activeShape = null;
				activeShape = undo1.pop();
				if (activeShape != null) {
					// command.DrawShape(graphics2d, activeShape);
					command.setCommand(CommandType.DRAW_COMMAND);
					activeShape.Execute(command);
					undo.push(activeShape.clone());
					redo1.push(activeShape.clone());
				}

			}
		}
	}

	@Override
	public void redoLastAction() {
		// TODO Auto-generated method stub

		if (!redo1.empty()) {
			activeShape = null;
			activeShape = redo1.pop();
			if (activeShape != null) {
				// command.DrawShape(graphics2d, activeShape);
				command.setCommand(CommandType.ERASE_COMMAND);
				activeShape.Execute(command);
				undo1.push(activeShape.clone());
			}
		}

		System.out.println("Undo Last");
		activeShape = null;
		if (!redo.isEmpty())
			activeShape = redo.pop();
		if (activeShape != null) {
			// command.DrawShape(graphics2d, activeShape);
			command.setCommand(CommandType.DRAW_COMMAND);
			activeShape.Execute(command);
			undo.push(activeShape.clone());
		}

	}

	@Override
	public void registerMouseEvents() {

		JFrame myframe = uiModule.getFrame();
		myframe.addMouseListener(this);
		myframe.addMouseMotionListener(this);
		graphics2d = (Graphics2D) myframe.getGraphics();
		command.setGraphics(graphics2d);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Clicked");

		Shape activeShape = new Shape();
		try {
			activeShape.setColor(this.activeShape.getColor());
			activeShape.setSecondaryColor(this.activeShape.getSecondaryColor());
			activeShape.setShddinType(this.activeShape.getShddinType());
			activeShape.setType(this.activeShape.getType());
		} catch (Exception ex) {
		}
		if (clips == ClipboardStates.PASTE) {
			Shape p = new Shape();
			p.setX1(e.getPoint().x);
			p.setY1(e.getPoint().y);
			undo.add(clipboard.ProcessState(p));
			clips = ClipboardStates.DESELECT;
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
			clips = ClipboardStates.DESELECT;
		}
		if (clips == ClipboardStates.DELETE) {
			x1 = e.getPoint().x;
			y1 = e.getPoint().y;
			activeShape.setX1(x1);
			activeShape.setY1(y1);
			command.setCommand(CommandType.CHECK_BOUNDARY);
			int i = activeShape.Execute(command);
			if (i >= 0) {
				Shape p = undo.get(i);
				command.setCommand(CommandType.ERASE_COMMAND);
				p.Execute(command);
				undo.add(p.clone());
			}
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
			clips = ClipboardStates.DESELECT;
			JOptionPane.showMessageDialog(null, "Shape Deleted !!");

		} else {
			x1 = e.getPoint().x;
			y1 = e.getPoint().y;
			activeShape.setX1(x1);
			activeShape.setY1(y1);
			command.setCommand(CommandType.CHECK_BOUNDARY);
			int i = activeShape.Execute(command);
			if (i >= 0) {
				Shape p = undo.get(i);
				command.setCommand(CommandType.SELECT_SHAPE);
				clipboard.ProcessState(p);
				p.Execute(command);
				// currentGroup.add(p);
				// JOptionPane.showMessageDialog(null, "Shape Selected,Now Click
				// Copy to Clipboard !!");
			}
		}
		//

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Pressed");

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Released");

		if (clips == ClipboardStates.PASTE || clips == ClipboardStates.DELETE || clips == ClipboardStates.SELECT)
			return;

		if (x1 == 0 && y1 == 0)
			return;

		x2 = e.getPoint().x;
		y2 = e.getPoint().y;

		Shape activeShape = new Shape();
		activeShape.setColor(this.activeShape.getColor());
		activeShape.setSecondaryColor(this.activeShape.getSecondaryColor());
		activeShape.setShddinType(this.activeShape.getShddinType());
		activeShape.setType(this.activeShape.getType());

		activeShape.setX1(x1);
		activeShape.setY1(y1);
		activeShape.setX2(x2);
		activeShape.setY2(y2);

		if (clips == ClipboardStates.GROUP) {

			currentGroup = new ArrayList<Shape>();

			for (int z = x1; z < x2; z++) {
				for (int q = y1; q < y2; q++) {
					activeShape.setX1(z);
					activeShape.setY1(q);
					activeShape.setX2(x2);
					activeShape.setY2(y2);
					command.setCommand(CommandType.CHECK_BOUNDARY);
					int i = activeShape.Execute(command);
					if (i != -1) {
						Shape p = undo.get(i);
						if (!currentGroup.contains(p)) {
							command.setCommand(CommandType.SELECT_SHAPE);
							clipboard.ProcessState(p);
							p.Execute(command);
							currentGroup.add(p);
							System.out.println(p.getType().toString());
						}
					}
				}
			}
			groupObject.add(currentGroup);
			command.setCommand(CommandType.DRAW_COMMAND);
			activeShape.setX1(x1);
			activeShape.setY1(y1);
			activeShape.setX2(x2);
			activeShape.setY2(y2);
			groupObject.BoundingBox(activeShape, command);
			clips = ClipboardStates.DESELECT;
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
			return;
		}
		/*
		 * Shape activeShape = new Shape();
		 * activeShape.setColor(this.activeShape.getColor());
		 * activeShape.setSecondaryColor(this.activeShape.getSecondaryColor());
		 * activeShape.setShddinType(this.activeShape.getShddinType());
		 * activeShape.setType(this.activeShape.getType());
		 * 
		 * activeShape.setX1(x1); activeShape.setY1(y1); activeShape.setX2(x2);
		 * activeShape.setY2(y2);
		 */
		//
		command.setCommand(CommandType.CHECK_BOUNDARY);
		int i = activeShape.Execute(command);
		if (i == -1) {
			command.setCommand(CommandType.DRAW_COMMAND);
			activeShape.Execute(command);
			undo.push(activeShape);
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
		} else if (i >= 0 && move == true) {

			Shape oldObject = undo.remove(i);
			command.setCommand(CommandType.ERASE_COMMAND);
			oldObject.Execute(command);
			undo1.push(oldObject.clone());
			oldObject.setX1(oldObject.x1 + (x2 - x1));
			oldObject.setY1(oldObject.y1 + (y2 - y1));
			oldObject.setX2(oldObject.x2 + (x2 - x1));
			oldObject.setY2(oldObject.y2 + (y2 - y1));
			command.setCommand(CommandType.DRAW_COMMAND);
			activeShape = oldObject;
			activeShape.Execute(command);
			undo.push(activeShape);
			move = false;
		} else {
			JOptionPane.showMessageDialog(null, "Error! Overlapped Shapes");
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
		}
		// command.DrawShape(graphics2d,activeShape);
		//
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Entered");

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Exited");

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Dragged");

		if (x1 == 0 && y1 == 0) {
			x1 = e.getPoint().x;
			y1 = e.getPoint().y;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Move");

	}

	@Override
	public void moveShape() {
		// TODO Auto-generated method stub
		move = !move;
	}

	@Override
	public void Copy() {
		// TODO Auto-generated method stub
		clips = ClipboardStates.COPY;
		clipboard.ProcessState(null);
		JOptionPane.showMessageDialog(null, "Shape Copied to Clipboard !!");
	}

	@Override
	public void Paste() {
		// TODO Auto-generated method stub
		clips = ClipboardStates.PASTE;
		JOptionPane.showMessageDialog(null, "Click where to Paste !!");
	}

	@Override
	public void Delete() {
		// TODO Auto-generated method stub
		clips = ClipboardStates.DELETE;
		JOptionPane.showMessageDialog(null, "Choose Shape to Delete !!");

	}

	@Override
	public void selectShape() {
		// TODO Auto-generated method stub
		if (clips == ClipboardStates.SELECT) {
			clips = ClipboardStates.DESELECT;
			JOptionPane.showMessageDialog(null, "Shape DeSelected");
		} else {
			clips = ClipboardStates.SELECT;
			JOptionPane.showMessageDialog(null, "Now Click on Shape");
		}
	}

	@Override
	public void Group() {
		// TODO Auto-generated method stub
		clips = ClipboardStates.GROUP;
	}

	@Override
	public void UnGroup() {
		// TODO Auto-generated method stub
		clips = ClipboardStates.UNGROUP;
		groupObject.UbGroup();
		clips = ClipboardStates.DESELECT;
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
	}

}
