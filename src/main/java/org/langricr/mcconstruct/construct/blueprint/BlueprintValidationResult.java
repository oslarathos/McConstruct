package org.langricr.mcconstruct.construct.blueprint;

import org.langricr.util.WorldCoordinate;
import org.langricr.util.PolarCoordinate.Rotation;

public class BlueprintValidationResult {
	private final Blueprint blueprint;
	private final Rotation rotation;
	private final WorldCoordinate core;
	
	public BlueprintValidationResult( Blueprint blueprint, Rotation rotation, WorldCoordinate core ) {
		this.blueprint = blueprint;
		this.rotation = rotation;
		this.core = core;
	}
	
	public final Blueprint getBlueprint() {
		return blueprint;
	}
	
	public final Rotation getRotation() {
		return rotation;
	}
	
	public final WorldCoordinate getCore() {
		return core;
	}
}
