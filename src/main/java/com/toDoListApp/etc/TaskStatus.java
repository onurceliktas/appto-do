package com.toDoListApp.etc;

public enum TaskStatus {

	PENDING(1),
	COMPLETED(2),
	POSTPONED(3),

	;
	
	int val;
	
	private TaskStatus(int val) {
		this.val = val;
	}
	
	public int intVal() {
		return this.val;
	}
	
	public static String getNameFromVal(int val) {
		String result = "";
		if (val == PENDING.intVal()) 
			result = PENDING.name();
		else if (val == COMPLETED.intVal())
			result = COMPLETED.name();
		else
			result = POSTPONED.name();
		return result;
	}
	
	public int getValue() {
		if (this == PENDING) {
			return 1;
		}else if (this == COMPLETED) {
			return 2;
		}
		return 3;
	}	
}