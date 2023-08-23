import net.java.games.input.*;
import org.gamecontrolplus.*;
import org.gamecontrolplus.gui.*;

ControlDevice controller;
ControlIO control;

float leftX;
float leftY;
float rightX;
float rightY;

SwerveModule[] modules = new SwerveModule[2];

float[] module0Position = { 100,0};
float[] module1Position = { - 100, 0};
float[] module2Position = {0, 100};
float[] centerPosition = {250, 250};

void setup() {
    size(500, 500);
    control = ControlIO.getInstance(this);
    controller = control.getMatchedDevice("xboxSwerve");
    
    if (controller == null) {
        println("Controller Gone");
        System.exit( -1);
    }
    
    modules[0] = new SwerveModule(module0Position);
    modules[1] = new SwerveModule(module1Position);
}

void draw() {
    background(255);
    getUserInput();
    
    for (SwerveModule loopModule : modules) {
        float[] components = loopModule.calculateVector(leftY, leftX, rightX);
        // println("components X:" + components[0]);
        // println("components Y:" + components[1]);
        float originX = centerPosition[0] + loopModule.getOrigin()[0];
        float originY = centerPosition[1] + loopModule.getOrigin()[1];
        // println("OriginX:" + originX);
        // println("OriginY:" + originY);
        float endpointX = originX + components[0] * 100;
        float endpointY = originY + components[1] * 100;
        // println("endpointX:" + endpointX);
        // println("endpointY:" + endpointY);
        line(originX, originY, endpointX, endpointY);
    }
}

void getUserInput() {
    leftX = controller.getSlider("leftStickX").getValue();
    leftY = controller.getSlider("leftStickY").getValue();
    rightX = controller.getSlider("rightStickX").getValue();
    rightY = controller.getSlider("rightStickY").getValue();
}

float[] polarToCartesian(float[] vector) {
    float nx = (float)vector[0] * (float)Math.cos(vector[1]);
    float ny = (float)vector[0] * (float)Math.sin(vector[1]);
    
    float[] output = {nx, ny};
    
    return output;
}
