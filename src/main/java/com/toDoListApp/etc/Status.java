package com.toDoListApp.etc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Status {

	ACTIVE(1),
	PASSIVE(2),
	;
	
	int val;
	
	private Status(int val) {
		this.val = val;
	}
	
	public int intVal() {
		return this.val;
	}
	
	public static String getNameFromVal(int val) {
		String result = "";
		if (val == ACTIVE.intVal()) 
			result = "ACTIVE";
		else
			result = "PASSIVE";
		return result;
	}
	
	public static Status getEnumByInt(int val) {
		if (val == 1) {
			return ACTIVE;
		}
		return PASSIVE;
	}
	
	
	public int getValue() {
		if (this == ACTIVE) {
			return 1;
		}
		return 2;
	}	
	
	
	public static List<String> getStringValues() {
		return new ArrayList<String>(Arrays.asList(Status.values()).stream().map(u -> u.name()).collect(Collectors.toList()));
	}
	
}