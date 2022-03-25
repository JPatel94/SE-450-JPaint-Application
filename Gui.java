package view.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.*;

import model.Shape;
import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import view.EventName;
import view.interfaces.IDialogChoice;
import view.interfaces.IEventCallback;
import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;

public class Gui implements IUiModule {

	private final IGuiWindow gui;

	public Gui(IGuiWindow gui) {
		this.gui = gui;
	}

	@Override
	public void addEvent(EventName eventName, IEventCallback callback) {
		JButton button = gui.getButton(eventName);
		button.addActionListener((ActionEvent) -> callback.run());
	}

	@Override
	public Shape getDialogResponse(IDialogChoice dialogSettings) {
		Object[] choices = {"Cancle", "Next"};
		Object[] choices1 = {"Cancle", "Finish"};
		
		Shape object = new Shape();
		object.setType( 
				(ShapeType) JOptionPane.showInputDialog(null, "Select Shape",
				"Shape:", JOptionPane.PLAIN_MESSAGE, null, dialogSettings.getShapeTypeOptions(),
				null));
		///
		object.setColor( 
				(ShapeColor) JOptionPane.showInputDialog(null, "Select Primary Color",
				"Color:", JOptionPane.PLAIN_MESSAGE, null, dialogSettings.getShapeColorOptions(),
				null));
		//
		object.setSecondaryColor( 
				(ShapeColor) JOptionPane.showInputDialog(null, "Select Secondary Color",
				"Color:", JOptionPane.PLAIN_MESSAGE, null, dialogSettings.getShapeColorOptions(),
				null));
		//
		object.setShddinType( 
				(ShapeShadingType) JOptionPane.showInputDialog(null, "Select Shadding",
				"Shadding:", JOptionPane.PLAIN_MESSAGE, null, dialogSettings.getShapeShaddingOptions(),
				null));
		
		
		
		
		
		return object;
	}

	@Override
	public Graphics2D getGraphics() {
		// TODO Auto-generated method stub

		return gui.get2DGraphics();
	}

	@Override
	public JFrame getFrame() {
		JFrame frame = gui.getFrame();
		return frame;

	}
}
