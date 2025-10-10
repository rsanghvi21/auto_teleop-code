package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorController;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
//import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import java.util.Locale;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
//Imports for TensorFlow
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

// Imports for color sensor
import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * FIRST Tech Challenge - Autonomous OpMode Template
 * 
 * This is a template for creating autonomous routines for the Into The Deep season.
 * It extends LinearOpMode, which means the code runs sequentially from top to bottom.
 * 
 * KEY FEATURES:
 * - Encoder-based precise movement (forward, backward, strafe, turn)
 * - Pre-built methods for common movements
 * - Elevator control for manipulating game elements
 * - TensorFlow integration for object detection (optional)
 * 
 * USAGE:
 * 1. Scroll down to the "START Writing here" comment in runOpMode()
 * 2. Add your autonomous routine using the provided movement methods
 * 3. Test each movement individually before combining them
 * 
 * IMPORTANT: Always use timeouts to prevent infinite loops!
 */
@Autonomous(name="IntoTheDeepAutonomousTemplate", group="Pushbot")
public class IntoTheDeepAutonomousTemplate extends LinearOpMode  { 

    /* Declare OpMode members. */
    HardwarePushbot robot   = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();    // Timer for tracking elapsed time
    
    // ===== ENCODER CONFIGURATION =====
    // These constants are used to calculate accurate distances based on motor encoders
    static final double     COUNTS_PER_MOTOR_REV    = 28;        // HD Hex Motor encoder ticks per revolution
    static final double     DRIVE_GEAR_REDUCTION    = 19.2;      // Gear reduction ratio (motor revs to wheel revs)
    static final double     WHEEL_DIAMETER_INCHES   = (96/25.4); // 96mm wheels converted to inches (3.78")
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * Math.PI);
    // This formula calculates how many encoder ticks equal one inch of robot movement
    /**
     * Main autonomous method - This runs when you press INIT and then PLAY
     */
    @Override
    public void runOpMode() 
    { 
        // ===== INITIALIZATION PHASE =====
        // Initialize all hardware (motors, servos, sensors)
        robot.init(hardwareMap);
        
        // Initialize TensorFlow for object detection (optional - comment out if not using)
        // initTfod();  // Uncomment this line if you implement TensorFlow detection
        
        // Send telemetry message to signify robot waiting
        telemetry.addData("Status", "Resetting Encoders");   
        telemetry.update();
    
        // Reset all motor encoders to zero - critical for accurate autonomous movement
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to run using encoders for precise movement control
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        // Set motors to brake when power is zero (prevents coasting)
        robot.frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                          robot.frontLeftDrive.getCurrentPosition(),
                          robot.frontRightDrive.getCurrentPosition());
        telemetry.update();

        // ===== WAIT FOR START =====
        // Robot is initialized and ready. Waiting for driver to press PLAY button.
        waitForStart(); 

        // ===== AUTONOMOUS ROUTINE =====
        // The autonomous period has started! Add your robot movements below.
        
        // ==========================
        // START Writing here
        // ==========================
        
        // EXAMPLE MOVEMENTS (delete these and add your own):
        // forward(0.5, 24, 5.0);      // Move forward 24 inches at 50% speed with 5 second timeout
        // sleep(500);                  // Wait 0.5 seconds
        // turnRight(0.3, 90, 3.0);    // Turn right 90 degrees at 30% speed with 3 second timeout
        // sleep(500);
        // strafeLeft(0.4, 12, 3.0);   // Strafe left 12 inches at 40% speed with 3 second timeout
        
        // ==========================
        // END of autonomous routine
        // ==========================
        
        telemetry.addData("Path", "Autonomous Complete!");
        telemetry.update();
    }
    
    // ===== MOVEMENT METHODS =====
    // These methods provide easy-to-use functions for robot movement
    // Use these in your autonomous routine above
    
    /**
     * Move robot forward in a straight line
     * @param speed Motor power (0.0 to 1.0)
     * @param f Distance in inches
     * @param timeoutS Maximum time allowed for movement (safety timeout)
     */
    public void forward (double speed, double f, double timeoutS)
    {
        encoderDrive(speed, -f, f, -f, f, timeoutS);
    }
    
    /**
     * Move robot backward in a straight line
     * @param speed Motor power (0.0 to 1.0)
     * @param b Distance in inches
     * @param timeoutS Maximum time allowed for movement
     */
    public void backward(double speed, double b, double timeoutS)
    {
        encoderDrive(speed, b, -b, b, -b, timeoutS);
    }
    
    /**
     * Strafe robot to the right (sideways movement)
     * @param speed Motor power (0.0 to 1.0)
     * @param r Distance in inches
     * @param timeoutS Maximum time allowed for movement
     */
    public void strafeRight(double speed, double r, double timeoutS)
    {
        encoderDrive(speed, -r, -r, r, r, timeoutS);
    }
    
    /**
     * Strafe robot to the left (sideways movement)
     * @param speed Motor power (0.0 to 1.0)
     * @param l Distance in inches
     * @param timeoutS Maximum time allowed for movement
     */
    public void strafeLeft(double speed, double l, double timeoutS)
    {
        encoderDrive(speed, l, l , -l, -l, timeoutS);
    }
    
    /**
     * Turn robot to the right (clockwise)
     * @param speed Motor power (0.0 to 1.0)
     * @param t Turn amount - may need calibration for your robot
     * @param timeoutS Maximum time allowed for movement
     * Note: Turning calculations may need adjustment based on robot weight and friction
     */
    public void turnRight(double speed, double t, double timeoutS)
    {
       // t = t * (3.75/90.0);  // Uncomment and adjust this multiplier to calibrate turns
        encoderDrive(speed, -t, -t, -t, -t, timeoutS);
    }
    
    /**
     * Turn robot to the left (counter-clockwise)
     * @param speed Motor power (0.0 to 1.0)
     * @param t Turn amount - may need calibration for your robot
     * @param timeoutS Maximum time allowed for movement
     */
    public void turnLeft(double speed, double t, double timeoutS)
    {
        // t = t * (3.75/90.0);  // Uncomment and adjust this multiplier to calibrate turns
        encoderDrive(speed, t, t, t, t, timeoutS);
    }
    
    /**
     * Core encoder-based driving method
     * This method is called by all the movement methods above
     * It converts inches to encoder counts and moves the robot precisely
     * 
     * @param speed Motor power (0.0 to 1.0) - use lower speeds for more accuracy
     * @param frontLeftInches Distance for front left wheel
     * @param frontRightInches Distance for front right wheel
     * @param backLeftInches Distance for back left wheel
     * @param backRightInches Distance for back right wheel
     * @param timeoutS Maximum time to wait for movement to complete
     * 
     * HOW IT WORKS:
     * 1. Reset encoders to zero
     * 2. Calculate target positions based on distance
     * 3. Set motors to run to those positions
     * 4. Wait until targets are reached or timeout occurs
     * 5. Stop and return to normal encoder mode
     */
    public void encoderDrive(double speed, double frontLeftInches, 
                             double frontRightInches, double backLeftInches, 
                             double backRightInches, double timeoutS) 
    {
        
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;
        
        // Reset encoders before each move for accurate positioning
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        sleep(200);  // Brief pause to ensure encoders reset
        
        // Ensure that the opmode is still active
        if (opModeIsActive()) 
        {
            // Calculate target positions by converting inches to encoder counts
            newFrontLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int)(frontLeftInches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.frontRightDrive.getCurrentPosition() + (int)(frontRightInches * COUNTS_PER_INCH);
            newBackLeftTarget = robot.backLeftDrive.getCurrentPosition() + (int)(backLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = robot.backRightDrive.getCurrentPosition() + (int)(backRightInches * COUNTS_PER_INCH);
            
            // Set target positions for each motor
            robot.frontLeftDrive.setTargetPosition(newFrontLeftTarget);
            robot.frontRightDrive.setTargetPosition(newFrontRightTarget);
            robot.backLeftDrive.setTargetPosition(newBackLeftTarget);
            robot.backRightDrive.setTargetPosition(newBackRightTarget);
          
            sleep(200);  // Brief pause to ensure targets are set
            
            // Switch to RUN_TO_POSITION mode - motors will automatically move to targets
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            
            // Reset the timeout timer and start motion
            runtime.reset();
            robot.frontLeftDrive.setPower(Math.abs(speed));
            robot.frontRightDrive.setPower(Math.abs(speed));
            robot.backLeftDrive.setPower(Math.abs(speed));
            robot.backRightDrive.setPower(Math.abs(speed));
     
            // Loop while motors are moving to their targets
            // This loop continues until:
            // 1. OpMode is no longer active (STOP pressed), OR
            // 2. Timeout is reached, OR
            // 3. Both front motors reach their targets
            //
            // Note: Using && (AND) means we stop as soon as ANY motor reaches target (safer)
            //       Using || (OR) means we wait until ALL motors reach targets (more precise)
            while (opModeIsActive() &&
                   (runtime.seconds() < timeoutS) &&
                   (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy())) {
                
                // Display current progress for debugging
                telemetry.addData("Path1",  "Running to %7d :%7d", newFrontLeftTarget,  newFrontRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                                   robot.frontLeftDrive.getCurrentPosition(),
                                   robot.frontRightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Movement complete - stop all motors
            robot.frontLeftDrive.setPower(0);
            robot.frontRightDrive.setPower(0);
            robot.backLeftDrive.setPower(0);
            robot.backRightDrive.setPower(0);
          
            // Return motors to normal encoder mode (not RUN_TO_POSITION)
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            
            // Ensure motors brake (don't coast) when stopped
            robot.frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            
            sleep(200);  // Brief pause for stability before next movement
        }
    }
    
    /**
     * Move the elevator mechanism
     * Used for raising/lowering game elements
     * 
     * @param speed Motor power (0.0 to 1.0)
     * @param rotation Number of rotations to move
     * @param time Maximum timeout in seconds
     */
    public void moveElevator (double speed, double rotation, double time)
    {
         // Calculate rotation distance
         rotation = (rotation * (1.5 * Math.PI));
         double counts_per_inch = (28 * 19.2) / rotation;
         int target; 
         
         // Reset elevator encoder
         robot.elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         
         if (opModeIsActive())
         {
             // Calculate target position
             target = robot.elevator.getCurrentPosition() + (int)(counts_per_inch * rotation);
             robot.elevator.setTargetPosition(target);
             sleep(200);
             
             // Start moving to target
             robot.elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             runtime.reset();
             robot.elevator.setPower(speed);
             
             // Wait for elevator to reach position or timeout
             while ((opModeIsActive() && (runtime.seconds() < time)))
             {
                telemetry.addData("Elevator Target",  "%7d", target);
                telemetry.addData("Elevator Position",  "%7d",
                                   robot.elevator.getCurrentPosition());
                telemetry.update();
             }
         }
         
        // Hold elevator in place when stopped
        robot.elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sleep(200);
    }
}
