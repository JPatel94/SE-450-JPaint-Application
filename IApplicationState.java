package model.interfaces;

import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import model.MouseMode;

public interface IApplicationState {
    void setActiveShapeAndMode();
    void undoLastAction();
    void redoLastAction();
    void registerMouseEvents();
    void moveShape();
    void selectShape();
    void Copy();
    void Paste();
    void Delete();
    void Group();
    void UnGroup();

}
