# FIRST Tech Challenge - Robot Code Repository

This repository contains the robot control code for FTC Into The Deep season. The code is written in Java and designed to run on the REV Robotics Control Hub or Robot Controller.

## Project Structure

### Main Files

- **HardwarePushbotTemplate.java** - Hardware configuration class that defines all motors, servos, and sensors
- **IntoTheDeepAutonomousTemplate.java** - Template for autonomous operation mode
- **IntoTheDeepTeleOp.java** - Driver-controlled operation mode (TeleOp)

## Hardware Configuration

### Drive Train
The robot uses a **mecanum drive system** with four motors:
- `frontLeftDrive` - Front left wheel motor
- `frontRightDrive` - Front right wheel motor
- `backLeftDrive` - Back left wheel motor
- `backRightDrive` - Back right wheel motor

### Manipulators
- `elevator` - Linear slide/elevator mechanism for raising and lowering game elements
- `intake` - Intake mechanism for collecting game elements

### Configuration Requirements
When configuring the robot in the REV Driver Hub:
1. Connect to the Robot Controller
2. Go to **Configuration** menu
3. Create a new configuration or edit existing
4. Name each motor/servo **exactly** as shown in the hardware class
5. Set motor directions and encoder settings as needed

**Important:** Motor and servo names in the Driver Hub configuration **must exactly match** the names in `HardwarePushbotTemplate.java`

## OpMode Guide

### TeleOp Mode (IntoTheDeepTeleOp.java)

Driver controls for operating the robot during the Driver-Controlled Period:

#### Gamepad 1 (Driver)
- **Left Stick Y-axis** - Forward/Backward movement
- **Left Stick X-axis** - Strafe left/right
- **Right Stick X-axis** - Turn left/right
- **D-Pad Up** - Slow forward movement (0.2 power)
- **D-Pad Down** - Slow backward movement (0.2 power)
- **D-Pad Left** - Slow strafe left (0.3 power)
- **D-Pad Right** - Slow strafe right (0.3 power)
- **B Button** - Toggle intake forward (automatic on/off)
- **X Button** - Toggle intake reverse (automatic on/off)

#### Gamepad 2 (Operator)
- **Y Button** - Elevator up (full power)
- **A Button** - Elevator down (0.8 power)

### Autonomous Mode (IntoTheDeepAutonomousTemplate.java)

Template for programming autonomous routines. Contains pre-built movement methods:

#### Available Movement Methods

```java
// Move forward (distance in inches, speed 0.0-1.0, timeout in seconds)
forward(0.5, 24, 5.0);

// Move backward
backward(0.5, 24, 5.0);

// Strafe right
strafeRight(0.5, 18, 5.0);

// Strafe left
strafeLeft(0.5, 18, 5.0);

// Turn right (degrees)
turnRight(0.3, 90, 3.0);

// Turn left (degrees)
turnLeft(0.3, 90, 3.0);

// Move elevator
moveElevator(0.5, rotations, timeout);
```

#### Programming Your Autonomous

1. Open `IntoTheDeepAutonomousTemplate.java`
2. Find the comment `// START Writing here` (line 108)
3. Add your movement commands using the methods above
4. Test incrementally - add one movement at a time

Example autonomous routine:
```java
// Move forward 24 inches
forward(0.5, 24, 5.0);
sleep(500);

// Turn right 90 degrees
turnRight(0.3, 90, 3.0);
sleep(500);

// Strafe left 12 inches
strafeLeft(0.4, 12, 3.0);
```

## Encoder Configuration

The code uses motor encoders for precise autonomous movement:

- **Motor Type:** HD Hex Motor (REV Robotics)
- **Counts Per Revolution:** 28 ticks
- **Gear Reduction:** 19.2:1
- **Wheel Diameter:** 96mm (3.78 inches)

These values are configured in `IntoTheDeepAutonomousTemplate.java` and are used to calculate the `COUNTS_PER_INCH` constant for accurate distance measurements.

## Getting Started

### Prerequisites
- Android Studio with FTC SDK installed
- REV Robotics Control Hub or Robot Controller Phone
- USB cable or wireless connection to robot

### Setup Instructions

1. **Clone this repository** to your local machine
2. **Open in Android Studio:**
   - File → Open
   - Navigate to this repository folder
   - Wait for Gradle sync to complete

3. **Build and Deploy:**
   - Connect to the Robot Controller via USB or WiFi
   - Click the green "Run" button
   - Select your Robot Controller device
   - Wait for build and installation

4. **Configure Hardware:**
   - Open the Driver Station app
   - Go to Configure Robot
   - Create/edit configuration to match the names in `HardwarePushbotTemplate.java`

5. **Test:**
   - Select your OpMode from the Driver Station
   - Initialize and start the robot
   - Test basic movements before competition

## Programming Tips

### For TeleOp
- Start with low motor powers and gradually increase
- Test drive controls thoroughly before adding complex mechanisms
- Use telemetry to debug: `telemetry.addData("Label", value);`
- Remember to call `telemetry.update();` after adding data

### For Autonomous
- **Always use timeouts** to prevent infinite loops
- Test each movement individually before combining
- Use `sleep()` between movements for stability
- Monitor encoder counts via telemetry for debugging
- Calculate turns may need adjustment based on your robot's weight and friction

### Debugging
- Use `telemetry.addData()` to display values on the Driver Station
- Check encoder counts: `motor.getCurrentPosition()`
- Verify motor directions in hardware configuration
- Ensure battery is fully charged for consistent testing

## Common Issues and Solutions

### Robot drives but not in expected direction
- Check motor directions in `HardwarePushbotTemplate.java`
- Verify motor wiring matches configuration
- Adjust direction using `setDirection(DcMotor.Direction.REVERSE)`

### Encoders not working
- Verify encoder cables are connected
- Check motor mode is set to `RUN_USING_ENCODER`
- Reset encoders before each autonomous run

### Motors not responding
- Check motor names match between code and configuration
- Verify Control Hub is powered and connected
- Check for loose motor connections

### Robot moves erratically
- Ensure battery is charged (>13V recommended)
- Check for loose motor mounts
- Verify wheel grip and traction

## Additional Resources

- [FTC Documentation](https://ftc-docs.firstinspires.org/)
- [FTC SDK GitHub](https://github.com/FIRST-Tech-Challenge/FtcRobotController)
- [Game Manual](https://www.firstinspires.org/resource-library/ftc/game-and-season-info)
- [REV Robotics Technical Resources](https://docs.revrobotics.com/)
- [FTC Community Forum](https://ftc-community.firstinspires.org/)

## Team Information

- **Team Name:** STEMstangs
- **Season:** 2024-2025 Into The Deep

## License

This code is provided for educational purposes as part of the FIRST Tech Challenge program.

---

**Good luck this season! Remember: Gracious Professionalism® and Coopertition®**

