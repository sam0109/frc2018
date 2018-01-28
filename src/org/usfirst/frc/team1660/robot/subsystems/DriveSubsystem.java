package org.usfirst.frc.team1660.robot.subsystems;
import org.usfirst.frc.team1660.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.SPI;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import java.lang.Math;

/**
 * The subsystem that manages the robot's movement.
 */
public class DriveSubsystem extends Subsystem
{
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	//Drive train motor declarations
	private WPI_TalonSRX m_frontLeft;
	private WPI_TalonSRX m_backLeft;
	private WPI_TalonSRX m_frontRight;
	private WPI_TalonSRX m_backRight;
	private MecanumDrive m_mecDrive;
	private AHRS m_navx;
	
	// local movement variables
	private double m_forwardSpeed;
	private double m_strafeRightSpeed;
	private double m_clockwiseRotationSpeed;
	private double m_angleOffset;
	
	public DriveSubsystem()
	{
		m_frontLeft = new WPI_TalonSRX(RobotMap.DRIVE_FRONT_LEFT_CHANNEL);
		m_backLeft = new WPI_TalonSRX(RobotMap.DRIVE_BACK_LEFT_CHANNEL);
		m_frontRight = new WPI_TalonSRX(RobotMap.DRIVE_FRONT_RIGHT_CHANNEL);
		m_backRight = new WPI_TalonSRX(RobotMap.DRIVE_BACK_RIGHT_CHANNEL);

		m_mecDrive = new MecanumDrive(m_frontLeft, m_backLeft, m_frontRight, m_backRight);
		
		try
		{
			// navX-MXP initialized with (SPI, I2C, TTL UART) and USB 
			// http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
			m_navx = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex )
		{
			//TODO: Decide on the best way to report an error here
			//DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}

		resetAngle();
	}

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());		
	}
	
	public void moveNorthContinuous(double speed)
	{
		m_forwardSpeed = speed * Math.cos(getTrueAngle()); //if using a MecanumDrive object and feeding it an angle parameter, you may not need this...
	}
	
	public void moveEastContinuous(float speed)
	{
		m_strafeRightSpeed = speed * Math.sin(getTrueAngle());
	}
	
	public void rotateClockwiseContinuous(float speed)
	{
		m_clockwiseRotationSpeed = speed;
	}
	
	public void stop()
	{
		m_forwardSpeed = 0;
		m_strafeRightSpeed = 0;
		m_clockwiseRotationSpeed = 0;
	}
	
	public void drive()
	{
		// TODO: Not sure if this is correct, we need to calibrate it.
		m_mecDrive.driveCartesian(m_strafeRightSpeed, m_forwardSpeed, m_clockwiseRotationSpeed, 0);
	}
	
	public void resetAngle()
	{
		m_angleOffset = m_navx.getAngle();
	}
	
	public double getTrueAngle()
	{
		return Math.floorMod((int) (m_navx.getAngle() - m_angleOffset), 360);
	}
	
}