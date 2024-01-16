package frc.robot;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

public class SwerveSteerVoltageRequest implements SwerveRequest {
    private final MotionMagicVoltage m_motionMagicControl = new MotionMagicVoltage(0, false, 0, 0, false, false, false);
    private final VoltageOut m_voltageOutControl = new VoltageOut(0.0);

    private double m_targetVoltage = 0.0;

    @Override
    public StatusCode apply(SwerveControlRequestParameters parameters, SwerveModule... modulesToApply) {
        for (var module : modulesToApply) 
        {
            // Command steer motor to zero
            module.getSteerMotor().setControl(m_voltageOutControl.withOutput(m_targetVoltage));

            // Command drive motor to voltage
            module.getDriveMotor().setControl(m_motionMagicControl);
        }

        return StatusCode.OK;
    }

    /**
     * 
     * @param targetVoltage Voltage for all modules to target
     * @return 
     */
    public SwerveSteerVoltageRequest withVoltage(double targetVoltage) {
        this.m_targetVoltage = targetVoltage;
        return this;
    }
}