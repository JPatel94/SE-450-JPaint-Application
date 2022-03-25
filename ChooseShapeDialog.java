package view.gui;

import model.Shape;
import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.IApplicationState;
import view.interfaces.IDialogChoice;

public class ChooseShapeDialog implements IDialogChoice {
	
	
    private final IApplicationState applicationState;

    public ChooseShapeDialog(IApplicationState applicationState) {

        this.applicationState = applicationState;
    }

	@Override
	public String getDialogTitle() {
		// TODO Auto-generated method stub
		return "Select Shape Configuration Click Next";
	}

	@Override
	public String getDialogText() {
		// TODO Auto-generated method stub
		return "Shape";
	}

	@Override
	public ShapeType[] getShapeTypeOptions() {
		// TODO Auto-generated method stub
		return ShapeType.values();
	}

	@Override
	public ShapeColor[] getShapeColorOptions() {
		// TODO Auto-generated method stub
		return ShapeColor.values();
	}

	@Override
	public ShapeShadingType[] getShapeShaddingOptions() {
		// TODO Auto-generated method stub
		return ShapeShadingType.values();
	}

 

	
   
}
