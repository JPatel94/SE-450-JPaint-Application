package controller;

import model.interfaces.IApplicationState;
import view.EventName;
import view.gui.Gui;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

public class JPaintController implements IJPaintController {
    private final IUiModule uiModule;
    private final IApplicationState applicationState;

    public JPaintController(IUiModule uiModule, IApplicationState applicationState) {
        this.uiModule = uiModule;
        this.applicationState = applicationState;
    }

    @Override
    public void setup() {
        setupEvents();
    }

    private void setupEvents() {
    	
    	//
    	uiModule.addEvent(EventName.DRAW, () -> applicationState.setActiveShapeAndMode());
    	uiModule.addEvent(EventName.UNDO, () -> applicationState.undoLastAction());
    	uiModule.addEvent(EventName.REDO, () -> applicationState.redoLastAction());
    	uiModule.addEvent(EventName.MOVE, () -> applicationState.moveShape());
    	uiModule.addEvent(EventName.SELECT, () -> applicationState.selectShape());
    	uiModule.addEvent(EventName.COPY, () -> applicationState.Copy());
    	uiModule.addEvent(EventName.PASTE, () -> applicationState.Paste());
    	uiModule.addEvent(EventName.DELETE, () -> applicationState.Delete());
    	uiModule.addEvent(EventName.GROUP, () -> applicationState.Group());
    	uiModule.addEvent(EventName.UNGROUP, () -> applicationState.UnGroup());
    	applicationState.registerMouseEvents();
    	//	applicationState.registerCanvas(uiModule);
    	//
        //uiModule.addEvent(EventName.CHOOSE_SHAPE, () -> applicationState.setActiveShape());
        //uiModule.addEvent(EventName.CHOOSE_PRIMARY_COLOR, () -> applicationState.setActivePrimaryColor());
        //uiModule.addEvent(EventName.CHOOSE_SECONDARY_COLOR, () -> applicationState.setActiveSecondaryColor());
        //uiModule.addEvent(EventName.CHOOSE_SHADING_TYPE, () -> applicationState.setActiveShadingType());
        //uiModule.addEvent(EventName.CHOOSE_MOUSE_MODE, () -> applicationState.setActiveStartAndEndPointMode());
    }
}
