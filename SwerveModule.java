public class SwerveModule {

    private float[] origin;
    private float rotationDirection;

    public SwerveModule(float[] origin) {
        this.origin = origin;
        initRotVectorDirection();
    }

    private void initRotVectorDirection() {
        if (origin[0] > 0 && origin[1] == 0) {
            rotationDirection = (float) (Math.PI / 2);
        } else if (origin[0] < 0 && origin[1] == 0) {
            rotationDirection = (float) (3 * Math.PI) / 2;
        } else {
            rotationDirection = ((float) Math.PI / 2) + (float) Math.atan(origin[0] /
                    origin[1]);
        }
        // rotationDirection = (float) (Math.PI / 2);
        // System.out.println("rotationDirection:" + rotationDirection);
    }

    public float[] calculateVector(float frwd, float strf, float rttn) {

        if (!(frwd >= 0.1 || frwd <= -0.1))
            frwd = 0;
        if (!(strf >= 0.1 || strf <= -0.1))
            strf = 0;

        System.out.println("frwd: " + frwd);
        System.out.println("strf: " + strf);
        // float strafeMagnitude = (float) Math.sqrt(
        // Math.pow(frwd, 2) + Math.pow(strf, 2));
        // DO NOT DO IT THIS WAY! Does not respect sign of input

        float strafeDirection;
        float strafeMagnitude;
        if (frwd == 0 && strf == 0) {
            strafeDirection = 0;
            strafeMagnitude = 0;
        } else if (frwd == -1.0 && strf == 0.0) {
            strafeDirection = (float) (3 * Math.PI / 2);
            strafeMagnitude = 1;
        } else if (frwd == 0.0 && strf == 1) {
            strafeDirection = 0;
            strafeMagnitude = 1;
        } else if (frwd == 1 && strf == 0.0) {
            strafeDirection = (float) (Math.PI / 2);
            strafeMagnitude = 1;
        } else if (frwd == 0.0 && strf == -1) {
            strafeDirection = (float) Math.PI;
            strafeMagnitude = 1;
        } else if (frwd == 0 && strf > 0) {
            strafeDirection = 0;
            strafeMagnitude = strf;
        } else if (frwd == 0 && strf < 0) {
            strafeDirection = 0;
            strafeMagnitude = strf;
        } else {
            strafeDirection = (float) Math.atan(frwd / strf);
            strafeMagnitude = (float) (frwd / Math.sin(strafeDirection));
        }
        // System.out.println("StrafeDriection:" + strafeDirection);
        // System.out.println("StrafeMagnitude:" + strafeMagnitude);

        float rotationMagnitude;
        if (rttn >= 0.1 || rttn <= -0.1) {
            rotationMagnitude = rttn;
        } else {
            rotationMagnitude = 0;
        }

        float[] strafeComponents = {
                (float) (strafeMagnitude * Math.cos(strafeDirection)),
                (float) (strafeMagnitude * Math.sin(strafeDirection)),
        };
        // System.out.println("StrafeComponent X" + strafeComponents[0]);
        // System.out.println("StrafeComponent Y" + strafeComponents[1]);

        float[] rotationComponents = {
                (float) (rotationMagnitude * Math.cos(rotationDirection)),
                (float) (rotationMagnitude * Math.sin(rotationDirection)),
        };
        // System.out.println("RotationComponet X:" + rotationComponents[0]);
        // System.out.println("RotaionComponent Y:" + rotationComponents[1]);

        float[] totalComponents = {
                strafeComponents[0] + rotationComponents[0],
                strafeComponents[1] + rotationComponents[1],
        };
        // System.out.println("TotalComponents X:" + totalComponents[0]);
        // System.out.println("TotalComponents Y:" + totalComponents[1]);

        float[] totalVector = {
                (float) (Math.sqrt(
                        Math.pow(totalComponents[0], 2) + Math.pow(totalComponents[1], 2))),
                (float) (Math.atan(totalComponents[1] / totalComponents[0])),
        };

        return totalComponents;
    }

    public float[] getOrigin() {
        return origin;
    }
}
