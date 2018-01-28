package org.usfirst.frc.team1660.robot.subsystems;

import org.usfirst.frc.team1660.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.SPI;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import java.lang.Math;

/**
 * The subsystem that manages the robot's movement.
 */
public class DriveSubsystem extends PIDSubsystem {
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
	private double m_forwardSpeed = 0.0;
	private double m_strafeRightSpeed = 0.0;
	private double m_clockwiseRotationSpeed = 0.0;
	private double m_angleOffset = 0.0;

	//PID coefficients
	static final double kP = 0.03;
	static final double kI = 0.0;
	static final double kD = 0.0;
	static final double kTolerance = 2.0;

	public DriveSubsystem()
	{
		//setup PID controller
		super("turnController", kP, kI, kD);
		getPIDController().setInputRange(-180.0,  180.0);
		getPIDController().setOutputRange(-1.0, 1.0);
		setAbsoluteTolerance(kTolerance);
		getPIDController().setContinuous(false);	//navx says true?
		LiveWindow.addActuator("DriveSystem", "RotateController", this.getPIDController());
		//LiveWindow.addChild(this, this.getPIDController());

		//instantiate speed controllers
		m_frontLeft = new WPI_TalonSRX(RobotMap.DRIVE_FRONT_LEFT_CHANNEL);
		m_backLeft = new WPI_TalonSRX(RobotMap.DRIVE_BACK_LEFT_CHANNEL);
		m_frontRight = new WPI_TalonSRX(RobotMap.DRIVE_FRONT_RIGHT_CHANNEL);
		m_backRight = new WPI_TalonSRX(RobotMap.DRIVE_BACK_RIGHT_CHANNEL);

		m_mecDrive = new MecanumDrive(m_frontLeft, m_backLeft, m_frontRight, m_backRight);

		//instantiate navx
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
		m_forwardSpeed = speed * Math.cos(getFieldAngle()); //if using a MecanumDrive object and feeding it an angle parameter, you may not need this...
	}

	public void moveEastContinuous(double speed)
	{
		m_strafeRightSpeed = speed * Math.sin(getFieldAngle());
	}

	public void rotateClockwiseContinuous(double speed)
	{
		m_clockwiseRotationSpeed = speed;
	}

	//strafe mutator
	public void setStrafeSpeed(double ss) {
		this.m_strafeRightSpeed = ss;
	}

	//forward mutator
	public void setForwardSpeed(double fs) {
		this.m_forwardSpeed = fs;
	}
	
	//turn mutator
	public void setTurnSpeed(double ts) {
		this.m_clockwiseRotationSpeed = ts;
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

	public double getFieldAngle()
	{
		return Math.floorMod((int) (m_navx.getAngle() - m_angleOffset), 360);
	}

	@Override
	// use the field angle as the input value for the PID control loop
	protected double returnPIDInput() {
		return getFieldAngle();
	}

	@Override
	/* This function is invoked periodically by the PID Controller, */
	/* based upon navX-MXP yaw angle input and PID Coefficients.    */
	public void usePIDOutput(double output) {
		this.m_clockwiseRotationSpeed = output;
	}

}