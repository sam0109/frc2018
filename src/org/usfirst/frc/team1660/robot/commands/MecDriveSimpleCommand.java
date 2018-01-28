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
public class MecDriveSimpleCommand extends Command {

	Joystick driverStick;

	public MecDriveSimpleCommand() {
		requires(Robot.m_DriveSubsystem);
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

		double joyStrafe = squareIt(driverStick.getRawAxis(RobotMap.STRAFE_AXIS)) ; // right and left on the left thumb stick?
		double joyForward = squareIt(driverStick.getRawAxis(RobotMap.FORWARD_AXIS));// up and down on left thumb stick?
		double joyTurn = squareIt(driverStick.getRawAxis(RobotMap.TURN_AXIS));// right and left on right thumb stick


		//TODO: Add a range of the joysticks in which the robot will not respond


		//Setting the speeds
		double currentSetPoint = Robot.m_DriveSubsystem.getSetpoint();
		double incrementAmount = joyTurn;
		double targetAngle = currentSetPoint + joyTurn;
		
		Robot.m_DriveSubsystem.setSetpoint(targetAngle);
		Robot.m_DriveSubsystem.setStrafeSpeed(joyStrafe);
		Robot.m_DriveSubsystem.setForwardSpeed(joyForward);
		Robot.m_DriveSubsystem.drive();	
		
		//Prints
		SmartDashboard.putNumber("move",	joyForward);
		SmartDashboard.putNumber("rotate",	joyTurn);
		SmartDashboard.putNumber("strafe",	joyStrafe);

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
