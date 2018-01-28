/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1660.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1660.robot.OI;
import org.usfirst.frc.team1660.robot.Robot;
import org.usfirst.frc.team1660.robot.RobotMap;

/**
 * An example command.  You can replace me with your own command.
 */
public class MecDriveGamerCommand extends Command {

	Joystick driverStick;

	public MecDriveGamerCommand() {
		requires(Robot.m_DriveSubsystem);
		Robot.m_DriveSubsystem.enable();
		Robot.m_DriveSubsystem.stop();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driverStick = Robot.m_oi.m_driverJoystick;
		Robot.m_DriveSubsystem.enable();

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		double joyHeadingX = squareIt(driverStick.getRawAxis(RobotMap.HEADING_X_AXIS)) ; 
		double joyHeadingY = squareIt(driverStick.getRawAxis(RobotMap.HEADING_Y_AXIS));
		double joyMovingX = squareIt(driverStick.getRawAxis(RobotMap.MOVING_X_AXIS)) ; 
		double joyMovingY = squareIt(driverStick.getRawAxis(RobotMap.MOVING_Y_AXIS));

		//TODO: Add deadzones for joysticks


		//Determine angle to be heading/facing
		double targetAngle = convertJoyToAngle(joyHeadingX, joyHeadingY);

		Robot.m_DriveSubsystem.setSetpoint(targetAngle);
		Robot.m_DriveSubsystem.moveEastContinuous(joyMovingX);
		Robot.m_DriveSubsystem.moveNorthContinuous(joyMovingY);
		Robot.m_DriveSubsystem.drive();	

		/*		
		//Prints
		SmartDashboard.putNumber("move",	joyForward);
		SmartDashboard.putNumber("rotate",	joyTurn);
		SmartDashboard.putNumber("strafe",	joyStrafe);
		 */
	}

	//method to convert the X&Y joystick values into a degree angle
	public double convertJoyToAngle(double X, double Y) {
		//TODO: write some code here
		return 0.0;
	}


	//method to square the joystick values to provide less sensitivity for small movements -Matthew W
	public double squareIt(double joy) {

		double squared = joy * joy;
		if(joy < 0) { squared *= -1; }
		return squared;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		Robot.m_DriveSubsystem.onTarget();
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_DriveSubsystem.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
