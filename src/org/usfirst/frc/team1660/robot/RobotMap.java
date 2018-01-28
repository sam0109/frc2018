/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1660.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Xbox controller button mappings
	public final static int A_BUTTON = 1;
	public final static int B_BUTTON = 2;
	public final static int X_BUTTON = 3;
	public final static int Y_BUTTON = 4;
	public final static int LB_BUTTON = 5;
	public final static int RB_BUTTON = 6;
	public final static int BACK_BUTTON = 7;
	public final static int START_BUTTON = 8;
	public final static int LEFT_JOY_BUTTON = 9;
	public final static int RIGHT_JOY_BUTTON = 10;
	public final static int LEFT_X_AXIS = 0;
	public final static int LEFT_Y_AXIS = 1;
	public final static int LT_AXIS = 2;
	public final static int RT_AXIS = 3;
	public final static int RIGHT_X_AXIS = 4;
	public final static int RIGHT_Y_AXIS = 5;
	public final static int POV_UP = 0;
	public final static int POV_LEFT = 270;
	public final static int POV_DOWN = 180;
	public final static int POV_RIGHT = 90;
	
	// Joystick ports
	public final static int DRIVER_JOYSTICK_PORT = 0;
	public final static int MANIPULATOR_JOYSTICK_PORT = 1;
	
	// Drive train motor channels
	public final static int DRIVE_FRONT_LEFT_CHANNEL = 3;
	public final static int DRIVE_BACK_LEFT_CHANNEL = 4;
	public final static int DRIVE_FRONT_RIGHT_CHANNEL = 2;
	public final static int DRIVE_BACK_RIGHT_CHANNEL = 1;
	
	// Mouth motor channels
	public final static int MOUTH_RIGHT_CHANNEL = 6;
	public final static int MOUTH_LEFT_CHANNEL = 5;
	public final static int MOUTH_LIMITER_CHANNEL = 0;
	
	// Lift motor channel
	public final static int LIFT_MOTOR_CHANNEL = 7;
	public final static int LIFT_LIMIT_TOP_CHANNEL = 1;
	public final static int LIFT_LIMIT_BOTTOM_CHANNEL = 2;
}
