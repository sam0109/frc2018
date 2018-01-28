/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1660.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1660.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class AutoTurnCommand extends Command {
	
	double targetAngle;
	
	public AutoTurnCommand(double targetAngle) {
		requires(Robot.m_DriveSubsystem);
		this.targetAngle = targetAngle;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.m_DriveSubsystem.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		Robot.m_DriveSubsystem.setSetpoint(targetAngle);
		Robot.m_DriveSubsystem.setStrafeSpeed(0.0);
		Robot.m_DriveSubsystem.setForwardSpeed(0.0);
		Robot.m_DriveSubsystem.drive();	
		
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
