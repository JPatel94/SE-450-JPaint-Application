package view.gui;

import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import model.MouseMode;
import model.Shape;
import model.interfaces.IApplicationState;
import model.interfaces.IDialogProvider;
import view.interfaces.IDialogChoice;

public class DialogProvider implements IDialogProvider {
	
    private final IDialogChoice chooseShapeDialog;
  
    private final IApplicationState applicationState;

    public DialogProvider(IApplicationState applicationState) {
        this.applicationState = applicationState;
        chooseShapeDialog = new ChooseShapeDialog(this.applicationState);
        
    }

	@Override
	public IDialogChoice getChooseActiveShapeAndMode() {
		// TODO Auto-generated method stub
		return chooseShapeDialog;
	}

  
}
